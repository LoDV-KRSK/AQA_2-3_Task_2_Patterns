package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {



    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    static void setUpUser(UserData user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getLoginGenerator() {
        return faker.name().username();
    }

    public static String getPasswordGenerator() {
        return faker.internet().password();
    }

    public static class Registration {
        public Registration() {
        }

        public static UserData getUser(String status) {
            return new UserData(getLoginGenerator(), getPasswordGenerator(), status);
        }

        public static UserData getRegisteredUser(String status) {
            UserData registeredUser = getUser(status);
            setUpUser(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class UserData {
        String login;
        String password;
        String status;
    }
}