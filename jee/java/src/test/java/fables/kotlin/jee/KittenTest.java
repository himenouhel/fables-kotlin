package fables.kotlin.jee;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import fables.kotlin.jee.rest.KittenRest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author Zeljko Trogrlic
 */
public class KittenTest {

  private Object OK = Response.Status.OK.getStatusCode();

  @BeforeClass
  public static void setup() {
    RestAssured.port = Optional.ofNullable(System.getProperty("server.port"))
        .map(Integer::parseInt)
        .orElse(8080);
    String app = Optional.ofNullable(System.getProperty("server.app"))
        .orElse("java-1.0");
    String defaultBase = app + "/api/kitten";
    RestAssured.basePath = Optional.ofNullable(System.getProperty("server.base"))
        .orElse(defaultBase);
    RestAssured.baseURI = Optional.ofNullable(System.getProperty("server.host"))
        .orElse("http://localhost");
  }

  @Test
  public void postAndGet() {

    final String name = UUID.randomUUID().toString();
    final int cuteness = (int) (Math.random() * 100);
    Map<String, Object> kittenMap = new HashMap<>(2);
    kittenMap.put("name", name);
    kittenMap.put("cuteness", cuteness);

    String id = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(kittenMap)
        .log().all()
        .when()
        .post()
        .prettyPeek()
        .then()
        .statusCode(200)
        .extract()
        .asString();

    KittenRest kittenRest =
        RestAssured.given()
            .accept(ContentType.JSON)
            .when()
            .get("{id}", id)
            .then()
            .statusCode(200)
            .extract()
            .as(KittenRest.class);

    assertEquals(name, kittenRest.getName());
    assertEquals(cuteness, kittenRest.getCuteness());
  }

  @Test
  public void notFound() {

    RestAssured.given()
        .accept(ContentType.JSON)
        .when()
        .get("{id}", "-9999")
        .then()
        .statusCode(404);
  }
}
