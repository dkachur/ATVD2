package lab2;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CustomTest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PET = "/pet";
    private static final String PET_ID = PET + "/{petId}";

    private static final int GROUP_NUMBER = 121211;
    private static final double STUDENT_ID = 13;
    private static final String STUDENT_NAME = "DaniilKachur";

    private long petId;
    private String petName;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyCreatePet() {
        petId = GROUP_NUMBER * 1000 + (long) STUDENT_ID;
        petName = Faker.instance().dog().name();

        Map<String, ?> body = Map.of(
                "id", petId,
                "name", petName,
                "status", "available"
        );

        given().body(body)
                .post(PET)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", equalTo(petName));
    }

    @Test(dependsOnMethods = "verifyCreatePet")
    public void verifyGetPet() {
        given().pathParam("petId", petId)
                .get(PET_ID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", equalTo(petName));
    }

    @Test(dependsOnMethods = "verifyGetPet")
    public void verifyUpdatePet() {
        petName = STUDENT_NAME + "Updated";

        Map<String, ?> body = Map.of(
                "id", petId,
                "name", petName,
                "status", "sold"
        );

        given().body(body)
                .put(PET)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", equalTo(petName))
                .body("status", equalTo("sold"));
    }
}

