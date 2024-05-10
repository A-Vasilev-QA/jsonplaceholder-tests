package qa.avasilev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class CRUDTests extends TestBase{

    @Test
    @DisplayName("GET whole lists of posts")
    public void getPostsRequestListTest() {

        Post[] postsResponse =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/posts")
                        .as(Post[].class);

        System.out.println(Arrays.toString(postsResponse));
        assertThat(postsResponse).isNotEmpty().hasSize(100);
    }

    @Test
    public void createNewPostTest() {
        
    }
}
