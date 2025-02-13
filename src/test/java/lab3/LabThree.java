package lab3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LabThree {

    private static final String BASE_URL = "https://9ea28717-b00d-494c-a032-ee4fbe02b6e3.mock.pstmn.io";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyGetOwnerNameSuccess() {
        given().get("/ownerName/success")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Daniil Kachur"));
    }

    @Test
    public void verifyGetOwnerNameUnsuccess() {
        given().get("/ownerName/unsuccess")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("exception", equalTo("I won't say my name!"));
    }

    @Test
    public void verifyPostCreateSomething() {
        given().queryParam("permission", "yes")
                .post("/createSomething")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", equalTo("Nothing was created"));
    }

    @Test
    public void verifyPostCreateSomethingWithoutPermission() {
        given().post("/createSomething")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("result", equalTo("You don't have permission to create Something"));
    }

    @Test
    public void verifyPutUpdateMe() {
        given().put("/updateMe")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void verifyDeleteWorld() {
        given().delete("/deleteWorld")
                .then()
                .statusCode(HttpStatus.SC_GONE)
                .body("world", equalTo("0"));
    }
}