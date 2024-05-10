package qa.avasilev;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static qa.avasilev.TestData.BASE_URI;
import static qa.avasilev.helpers.AllureRestAssuredFilter.withCustomTemplates;

@ExtendWith({AllureJunit5.class})

public class TestBase {

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .addFilter(withCustomTemplates())
            .log(ALL)
            .setContentType(JSON)
            .build();


    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectContentType(JSON)
            .log(ALL)
            .build();
}

