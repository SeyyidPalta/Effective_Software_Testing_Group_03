import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialMediaPosterTest {
    @Mock
    private SocialMediaAPI api;

    @InjectMocks
    private SocialMediaPoster poster;

    @Captor
    private ArgumentCaptor<String> platformCaptor;
    @Captor
    private ArgumentCaptor<String> contentCaptor;

    private static final String REDDIT = "Reddit";
    private static final String FACEBOOK = "Facebook";
    private static final String DFT_CONTENT = "Hello, world!";

    @Test
    void testPostContent_true_ok() {
        when(api.post(platformCaptor.capture(), contentCaptor.capture()))
                .thenReturn(true);

        assertTrue(poster.postContent(REDDIT, DFT_CONTENT));
        assertEquals(REDDIT, platformCaptor.getValue());
        assertEquals(DFT_CONTENT, contentCaptor.getValue());
    }

    @Test
    void testPostContent_false_ok() {
        when(api.post(platformCaptor.capture(), contentCaptor.capture()))
                .thenReturn(false);

        assertFalse(poster.postContent(REDDIT, DFT_CONTENT));
        assertEquals(REDDIT, platformCaptor.getValue());
        assertEquals(DFT_CONTENT, contentCaptor.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testPostContent_invalidPlatform_nok(String invalidPlatform) {
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> poster.postContent(invalidPlatform, DFT_CONTENT));
        assertEquals("Platform cannot be null or empty", t.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testPostContent_invalidContent_nok(String invalidContent) {
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> poster.postContent(REDDIT, invalidContent));
        assertEquals("Content cannot be null or empty", t.getMessage());
    }

    @Test
    void testPostContent_contentTooLong_nok() {
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> poster.postContent(REDDIT, "a".repeat(281)));
        assertEquals("Content exceeds maximum length of 280 characters", t.getMessage());
    }

    // TDD
    @ParameterizedTest
    @NullAndEmptySource
    void testPostBatch_invalidPlatform_ok(List<String> invalidPlatform) {
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> poster.postBatch(invalidPlatform, DFT_CONTENT));
        assertEquals("Platforms list cannot be null or empty", t.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testPostBatch_contentEmpty_ok(String invalidContent) {
        List<String> platforms = List.of(REDDIT, FACEBOOK);
        when(api.getRateLimitRemaining())
                .thenReturn(2);

        assertEquals(0, poster.postBatch(platforms, invalidContent));
    }

    @Test
    void testPostBatch_ok() {
        List<String> platforms = List.of(REDDIT, FACEBOOK);
        when(api.getRateLimitRemaining()).thenReturn(2);
        when(api.post(platformCaptor.capture(), contentCaptor.capture()))
                .thenReturn(true);

        int result = poster.postBatch(platforms, DFT_CONTENT);
        assertEquals(2, result);
        assertEquals(List.of(REDDIT, FACEBOOK), platformCaptor.getAllValues());
        assertTrue(contentCaptor
                .getAllValues()
                .stream()
                .allMatch(content -> content.equals(DFT_CONTENT)));
    }

    @Test
    void testPostBatch_exceedRateLimit_ok() {
        List<String> platforms = List.of(REDDIT, FACEBOOK, "Instagram");
        when(api.getRateLimitRemaining())
                .thenReturn(2);
        when(api.post(platformCaptor.capture(), contentCaptor.capture()))
                .thenReturn(true);

        assertEquals(2, poster.postBatch(platforms, DFT_CONTENT));
        assertEquals(List.of(REDDIT, FACEBOOK), platformCaptor.getAllValues());
        assertTrue(contentCaptor
                .getAllValues()
                .stream()
                .allMatch(content -> content.equals(DFT_CONTENT)));
    }

    @Test
    void testPostBatch_emptyPlatform_ok() {
        List<String> platforms = List.of(REDDIT, "");
        when(api.getRateLimitRemaining())
                .thenReturn(2);
        when(api.post(platformCaptor.capture(), contentCaptor.capture()))
                .thenReturn(true);

        assertEquals(1, poster.postBatch(platforms, DFT_CONTENT));
    }

    @Test
    void testPostBatch_secondPostNotSucceeded_ok() {
        List<String> platforms = List.of(REDDIT, FACEBOOK);
        when(api.getRateLimitRemaining())
                .thenReturn(2);
        when(api.post(platformCaptor.capture(), contentCaptor.capture()))
                .thenReturn(true)
                .thenReturn(false);

        assertEquals(1, poster.postBatch(platforms, DFT_CONTENT));
    }
}