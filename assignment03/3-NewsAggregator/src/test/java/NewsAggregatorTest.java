import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsAggregatorTest {

    @Mock
    private NewsAPI newsAPI;

    @Mock
    private ContentCache contentCache;

    @InjectMocks
    private NewsAggregator newsAggregator;

    private List<NewsArticle> technologyArticles;

    @BeforeEach
    void setUp() {
        technologyArticles = List.of(
                new NewsArticle(
                        "AI Breakthrough",
                        "New AI model released",
                        "Tech Daily",
                        "technology",
                        System.currentTimeMillis()
                ),
                new NewsArticle(
                        "Quantum Computing",
                        "Quantum processors reach new milestone",
                        "Science Weekly",
                        "technology",
                        System.currentTimeMillis()
                )
        );
    }

    @Test
    @DisplayName("Should return cached articles when cache contains data")
    void shouldReturnCachedArticlesWhenCacheHitOccurs() {
        when(contentCache.getCachedArticles("technology"))
                .thenReturn(technologyArticles);

        List<NewsArticle> result = newsAggregator.getLatestNews("technology");

        assertEquals(technologyArticles, result);
        verify(contentCache).getCachedArticles("technology");
        verify(newsAPI, never()).fetchNews(anyString(), anyInt());
        verify(contentCache, never()).cacheArticles(anyString(), anyList());
    }

    @Test
    @DisplayName("Should fetch and cache fresh articles when cache miss occurs")
    void shouldFetchAndCacheArticlesWhenCacheMissOccurs() {
        when(contentCache.getCachedArticles("technology"))
                .thenReturn(null);

        when(newsAPI.fetchNews("technology", 10))
                .thenReturn(technologyArticles);

        List<NewsArticle> result = newsAggregator.getLatestNews("technology");

        assertEquals(technologyArticles, result);
        verify(newsAPI).fetchNews("technology", 10);
        verify(contentCache).cacheArticles("technology", technologyArticles);
    }

    @Test
    @DisplayName("Should fetch fresh articles when cached list is empty")
    void shouldFetchFreshArticlesWhenCacheContainsEmptyList() {
        when(contentCache.getCachedArticles("technology"))
                .thenReturn(Collections.emptyList());

        when(newsAPI.fetchNews("technology", 10))
                .thenReturn(technologyArticles);

        List<NewsArticle> result = newsAggregator.getLatestNews("technology");

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(newsAPI).fetchNews("technology", 10);
        verify(contentCache).cacheArticles("technology", technologyArticles);
    }

    @Test
    @DisplayName("Should throw exception when category is null")
    void shouldThrowExceptionWhenCategoryIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> newsAggregator.getLatestNews(null)
        );

        assertEquals("Category cannot be null or empty", exception.getMessage());

        verifyNoInteractions(newsAPI, contentCache);
    }

    @Test
    @DisplayName("Should throw exception when category is blank")
    void shouldThrowExceptionWhenCategoryIsBlank() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> newsAggregator.getLatestNews("")
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> newsAggregator.getLatestNews("   ")
                )
        );

        verifyNoInteractions(newsAPI, contentCache);
    }

    @Test
    @DisplayName("Should propagate API failures during downtime")
    void shouldPropagateApiExceptionWhenApiIsUnavailable() {
        when(contentCache.getCachedArticles("sports"))
                .thenReturn(null);

        RuntimeException apiException = new RuntimeException("News API unavailable");

        when(newsAPI.fetchNews("sports", 10))
                .thenThrow(apiException);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> newsAggregator.getLatestNews("sports")
        );

        assertEquals("News API unavailable", exception.getMessage());

        verify(contentCache, never()).cacheArticles(anyString(), anyList());
    }

    @Test
    @DisplayName("Should support cache invalidation by fetching fresh data")
    void shouldFetchFreshDataAfterCacheInvalidation() {
        when(contentCache.getCachedArticles("business"))
                .thenReturn(null);

        List<NewsArticle> freshArticles = List.of(
                new NewsArticle(
                        "Markets Rally",
                        "Stocks climb after earnings reports",
                        "Finance Times",
                        "business",
                        System.currentTimeMillis()
                )
        );

        when(newsAPI.fetchNews("business", 10))
                .thenReturn(freshArticles);

        List<NewsArticle> result = newsAggregator.getLatestNews("business");

        assertEquals(freshArticles, result);

        verify(contentCache).getCachedArticles("business");
        verify(newsAPI).fetchNews("business", 10);
        verify(contentCache).cacheArticles("business", freshArticles);
    }

    @Test
    @DisplayName("Should handle empty API responses and cache them")
    void shouldHandleEmptyApiResponses() {
        when(contentCache.getCachedArticles("science"))
                .thenReturn(null);

        when(newsAPI.fetchNews("science", 10))
                .thenReturn(Collections.emptyList());

        List<NewsArticle> result = newsAggregator.getLatestNews("science");

        assertTrue(result.isEmpty());
        verify(contentCache).cacheArticles("science", Collections.emptyList());
    }
}
