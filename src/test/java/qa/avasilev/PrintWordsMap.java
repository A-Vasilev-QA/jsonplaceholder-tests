package qa.avasilev;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class CreateWordsMap extends TestBase {

    public static void main(String[] args)  {

        Post[] allPosts =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/posts")
                        .then()
                        .statusCode(200)
                        .extract().as(Post[].class);

        List<String> words = new ArrayList<>();

        Arrays.stream(allPosts)
                .map(post -> Arrays.asList(post.getBody().split(" ")))
                .forEach(words::addAll);

        List<Map.Entry<String, Integer>> entryList =
                words.stream().map(string -> string.replace(",", "").toLowerCase())
                .collect(Collectors.toMap(key -> key, val -> 1, Integer::sum))
                .entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()) * -1)
                .limit(10)
                .collect(Collectors.toList());

        entryList.forEach(e -> System.out.println(entryList.indexOf(e)+1 + ". " + e.getKey() + " - " + e.getValue()));
    }
}
