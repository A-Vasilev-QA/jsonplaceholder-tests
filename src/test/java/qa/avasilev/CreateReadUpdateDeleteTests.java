package qa.avasilev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static qa.avasilev.TestData.*;


public class CreateReadUpdateDeleteTests extends TestBase {
    static Post[] getResponse;
    static Post getAfterPostResponse;
    static Post getAfterPutResponse;
    static Post postResponse;
    static Post putResponse;

    @Test
    @DisplayName("Проверка получения всех постов")
    public void getPostsListTest() {

        step("Отправляем GET запрос", () ->
                getResponse =
                        given()
                                .spec(requestSpec)
                                .when()
                                .get(POSTS_ENDPOINT)
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(Post[].class));

        step("Проверяем количество сообщений", () ->
                assertThat(getResponse).isNotEmpty().hasSize(EXPECTED_MESSAGES));

    }

    @Test
    @DisplayName("Проверка создания нового поста")
    public void createNewPostTest() {
        step("Отправляем POST запрос", () ->
                postResponse =
                        given()
                                .spec(requestSpec)
                                .body(postRequestData())
                                .when()
                                .post(POSTS_ENDPOINT)
                                .then()
                                .spec(responseSpec)
                                .statusCode(201)
                                .extract().as(Post.class));

        step("Валидируем значения ответа", () -> {
            assertThat(postResponse.getUserId()).isEqualTo(postRequestData().get("userId").toString());
            assertThat(postResponse.getTitle()).isEqualTo(postRequestData().get("title"));
            assertThat(postResponse.getBody()).isEqualTo(postRequestData().get("body"));
        });

        step("Проверяем, что новый пост корректно добавился", () -> {
            getAfterPostResponse =
                    given()
                            .spec(requestSpec)
                            .pathParam("id", postResponse.getId())
                            .when()
                            .get(EXACT_POST_ENDPOINT)
                            .then()
                            .spec(responseSpec)
                            .statusCode(200)
                            .extract().as(Post.class);

            assertThat(getAfterPostResponse.getUserId()).isEqualTo(postRequestData().get("userId").toString());
            assertThat(getAfterPostResponse.getTitle()).isEqualTo(postRequestData().get("title"));
            assertThat(getAfterPostResponse.getBody()).isEqualTo(postRequestData().get("body"));
        });
    }

    @Test
    @DisplayName("Проверка обновления существующего поста")
    public void updatePostTest() {
        step("Отправляем PUT запрос", () ->
                putResponse =
                        given()
                                .spec(requestSpec)
                                .pathParam("id", putRequestData().get("id"))
                                .body(putRequestData())
                                .when()
                                .put(EXACT_POST_ENDPOINT)
                                .then()
                                .spec(responseSpec)
                                .statusCode(200)
                                .extract().as(Post.class)
        );

        step("Валидируем значения ответа", () -> {
            assertThat(putResponse.getId()).isEqualTo(putRequestData().get("id").toString());
            assertThat(putResponse.getUserId()).isEqualTo(putRequestData().get("userId").toString());
            assertThat(putResponse.getTitle()).isEqualTo(putRequestData().get("title"));
            assertThat(putResponse.getBody()).isEqualTo(putRequestData().get("body"));
        });

        step("Проверяем, что пост обновился", () -> {
            getAfterPutResponse =
                    given()
                            .spec(requestSpec)
                            .pathParam("id", putRequestData().get("id"))
                            .when()
                            .get(EXACT_POST_ENDPOINT)
                            .then()
                            .spec(responseSpec)
                            .statusCode(200)
                            .extract().as(Post.class);

            assertThat(getAfterPutResponse.getId()).isEqualTo(putRequestData().get("id").toString());
            assertThat(getAfterPutResponse.getUserId()).isEqualTo(putRequestData().get("userId").toString());
            assertThat(getAfterPutResponse.getTitle()).isEqualTo(putRequestData().get("title"));
            assertThat(getAfterPutResponse.getBody()).isEqualTo(putRequestData().get("body"));
        });


    }

    @Test
    @DisplayName("Проверка удаления поста")
    public void deletePostTest() {

        step("Отправляем DELETE запрос", () ->
                given()
                        .spec(requestSpec)
                        .pathParam("id", DELETE_ID)
                        .when()
                        .delete(EXACT_POST_ENDPOINT)
                        .then()
                        .statusCode(200)
        );

        step("Проверяем, что пост удалился", () ->
                given()
                        .spec(requestSpec)
                        .pathParam("id", DELETE_ID)
                        .when()
                        .get(EXACT_POST_ENDPOINT)
                        .then()
                        .statusCode(404)
        );
    }

}
