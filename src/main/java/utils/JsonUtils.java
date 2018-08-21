package utils;

import java.util.Iterator;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class JsonUtils {
  private Object json;

  public JsonUtils(Object json) {
    super();
    this.json = json;
  }

  public JsonUtils(String jsonStr) {
    super();
    this.json = new JSONTokener(jsonStr).nextValue();
  }

  public JsonUtils(Response response) {
    super();
    this.json = new JSONTokener(response.getResponseBody()).nextValue();
  }

  public Object get(String path) {
    String[] keys = path.split("->");
    JSONObject obj = (JSONObject) getLastElement(keys);
    return obj.get(keys[keys.length - 1]);
  }

  public <T> void update(String path, T value) {
    String[] keys = path.split("->");
    JSONObject obj = (JSONObject) getLastElement(keys);
    obj.put(keys[keys.length - 1], value);
  }

  public <T> void add(String path, T value) {
    String[] keys = path.split("->");
    JSONObject obj = (JSONObject) getLastElement(keys);
    obj.put(keys[keys.length - 1], value);
  }

  public void remove(String path) {
    String[] keys = path.split("->");
    JSONObject obj = (JSONObject) getLastElement(keys);
    obj.remove(keys[keys.length - 1]);
  }

  public Object getLastElement(String[] keys) {
    return getElement(keys, keys.length - 1);
  }

  public Object getElement(String[] keys, int depth) {
    JSONObject obj = (JSONObject) this.json;
    for (int i = 0; i < depth; i++) {
      String key = keys[i];
      key = key.trim();
      if (obj.optJSONArray(key) != null) {
        JSONArray jArray = obj.getJSONArray(key);
        key = keys[++i];
        int ordinal = Integer.parseInt(key);
        obj = (JSONObject) jArray.get(ordinal);
      } else if (obj.optJSONObject(key) != null) {
        obj = obj.getJSONObject(key);
      }
      log.info(obj.toString());
    }
    return obj;
  }

  public void findAndUpdate(String value) {
    findAndUpdate((JSONObject) this.json, value);
  }

  public void findAndUpdate(JSONObject obj, String value) {
    Iterator<?> iterator = obj.keys();
    while (iterator.hasNext()) {
      String key = (String) iterator.next();
      if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
        if ((value.equals(obj.get(key)))) {
          obj.put(key, RandomStringUtils.randomAlphabetic(6));
        }
      }

      if (obj.optJSONObject(key) != null) {
        findAndUpdate(obj.getJSONObject(key), value);
      }

      if (obj.optJSONArray(key) != null) {
        JSONArray jArray = obj.getJSONArray(key);
        for (int i = 0; i < jArray.length(); i++) {
          findAndUpdate(jArray.getJSONObject(i), value);
        }
      }
    }
  }

  public static JSONObject updateJson(JSONObject obj, String keyString, String newValue)
      throws Exception {
    Iterator<?> iterator = obj.keys();
    String key = null;
    while (iterator.hasNext()) {
      key = (String) iterator.next();
      // if the key is a string, then update the value
      if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
        if ((key.equals(keyString))) {
          // put new value
          obj.put(key, newValue);
          return obj;
        }
      }

      // if it's jsonobject
      if (obj.optJSONObject(key) != null) {
        updateJson(obj.getJSONObject(key), keyString, newValue);
      }

      // if it's jsonarray
      if (obj.optJSONArray(key) != null) {
        JSONArray jArray = obj.getJSONArray(key);
        for (int i = 0; i < jArray.length(); i++) {
          updateJson(jArray.getJSONObject(i), keyString, newValue);
        }
      }
    }
    return obj;
  }
}

