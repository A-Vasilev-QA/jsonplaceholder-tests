package qa.avasilev;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    static final String POSTS_ENDPOINT = "/posts";
    static final String EXACT_POST_ENDPOINT = "/posts/{id}";
    static final int EXPECTED_MESSAGES = 100;
    static final int DELETE_ID = 100;

    static Map<String, Object> postRequestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "foo");
        map.put("body", "bar");
        map.put("userId", 1);
        return map;
    }

    static Map<String, Object> putRequestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 99);
        map.put("title", "foo");
        map.put("body", "bar");
        map.put("userId", 1);
        return map;
    }

}
