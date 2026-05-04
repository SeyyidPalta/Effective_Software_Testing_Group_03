import java.util.*;

public class SocialMediaPoster {
    private final SocialMediaAPI api;
    
    public SocialMediaPoster(SocialMediaAPI api) {
        this.api = api;
    }
    
    /**
     * Posts content to a single social media platform
     * @param platform The platform to post to
     * @param content The content to post
     * @return true if posting was successful
     */
    public boolean postContent(String platform, String content) {
        if (platform == null || platform.trim().isEmpty()) {
            throw new IllegalArgumentException("Platform cannot be null or empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (content.length() > 280) {
            throw new IllegalArgumentException("Content exceeds maximum length of 280 characters");
        }
        
        return api.post(platform, content);
    }
    
    /**
     * Posts the same content to multiple platforms (TDD implementation required)
     * @param platforms List of platforms to post to
     * @param content The content to post
     * @return number of successful posts
     */
    public int postBatch(List<String> platforms, String content) {
        if (platforms == null || platforms.isEmpty()) {
            throw new IllegalArgumentException("Platforms list cannot be null or empty");
        }

        int counter = 0;
        for (String platform : platforms) {
            if (counter >= api.getRateLimitRemaining()) break;

            boolean success;
            try {
                success = postContent(platform, content);
            } catch (IllegalArgumentException e) {
                continue;
            }

            if (success) { // boolean is dft false
               counter++;
            }
        }
        return counter;
    }
}