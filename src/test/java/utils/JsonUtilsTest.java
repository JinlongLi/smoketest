package utils;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtilsTest {
  private String json;
  private double DOLLAR_EPSILON = 0.001;

  @BeforeTest(alwaysRun = true)
  public void setup() throws JsonProcessingException {
    List<Person> persons = new ArrayList<>();
    persons.add(Person.builder().name("Alex").height(1.76).isStudent(true).build());
    persons.add(Person.builder().name("Bob").height(1.58).isStudent(true).build());
    persons.add(Person.builder().name("###").height(1.86).isStudent(false).build());
    Classmates classmates = new Classmates(persons);
    json = classmates.toJsonString();
  }


  @Test
  public void getValueTest() {
    Assert.assertEquals((double) new JsonUtils(json).get("persons->0->height"), 1.76,
        DOLLAR_EPSILON);
  }

  @Test
  public void updateTest() {
    JsonUtils jsonUtils = new JsonUtils(json);
    log.info(jsonUtils.getJson().toString());
    Assert.assertEquals((double) jsonUtils.get("persons->0->height"), 1.76, DOLLAR_EPSILON);
    jsonUtils.update("persons->0->name", "AlexZ");
    log.info(jsonUtils.getJson().toString());
    Assert.assertEquals(jsonUtils.get("persons->0->name"), "AlexZ");
    Assert.assertEquals((double) jsonUtils.get("persons->0->height"), 1.76, DOLLAR_EPSILON);
  }

  @Test
  public void addValueTest() {
    JsonUtils jsonUtils = new JsonUtils(json);
    log.info(jsonUtils.getJson().toString());
    Assert.assertEquals((double) jsonUtils.get("persons->0->height"), 1.76, DOLLAR_EPSILON);
    jsonUtils.add("persons->0->age", "60");
    log.info(jsonUtils.getJson().toString());
    Assert.assertEquals((String) jsonUtils.get("persons->0->age"), "60");
    Assert.assertEquals(jsonUtils.get("persons->0->name"), "Alex");
  }

  @Test
  public void removeTest() {
    JsonUtils jsonUtils = new JsonUtils(json);
    log.info(jsonUtils.getJson().toString());
    Assert.assertEquals((double) jsonUtils.get("persons->0->height"), 1.76, DOLLAR_EPSILON);
    jsonUtils.add("persons->0->age", "60");
    log.info("added:" + jsonUtils.getJson().toString());
    Assert.assertEquals((String) jsonUtils.get("persons->0->age"), "60");
    Assert.assertEquals(jsonUtils.get("persons->0->name"), "Alex");
    jsonUtils.remove("persons->0->age");
    log.info("removed:" + jsonUtils.getJson().toString());
  }

  @Test
  public void findAndUpdateTest() {
    JsonUtils jsonUtils = new JsonUtils(json);
    jsonUtils.findAndUpdate("###");
    log.info("modified:" + jsonUtils.getJson().toString());
  }
}
