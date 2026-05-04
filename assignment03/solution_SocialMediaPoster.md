# Social Media Poster

## A. Posting to Social Media Platforms

1. The external dependency in the `SocialMediaPoster` class is the `SocialMediaAPI` class, which is used to interact with external APIs or some sort if library.
2. This external dependency should be tested using mocks or if no return void (stubs) because it represents an external service that we do not want to rely on during unit testing. 
3. Mocks slow down the test execution, is more unreliable, and may have other side effects if the API changes, but it allows us to test the behavior of the code without real API requests. 

--- 

## B. Batch Posting Feature

- Write test for null platform
- Write test for empty platforms list
- Write test for null content
- Write test for empty content
- Write test for valid platforms and content
- Write test for exceeding API limit
- Write tests for postContent method precondition failing (invalid platform, null content, empty content)
- Write test for partial success (some platforms succeed, some fail)

1. The external dependency in the `postBatch` method is still the `SocialMediaAPI` class, which should be tested using mocks or stubs for the same reasons as before.
2. Now we can simulate the response from the other method `postContent` to test the behavior of the `postBatch` method under different conditions, such as when some posts succeed and others fail. 
This allows us to ensure that the `postBatch` method correctly counts successful posts and handles API limits without relying on real API calls and we do not need mocks to test the `postContent` method necessary.
3. The disadvantages of using doubles in the `postBatch` method are the same issues as before (slower tests, less reliability, potential for false positives/negatives).