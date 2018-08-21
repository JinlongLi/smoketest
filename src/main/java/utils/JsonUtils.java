package utils;

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
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
    Object json = this.json;
    for (String key : keys) {
      if (json == null) {
        return null;
      }
      key = key.trim();
      if (StringUtils.isNumeric(key) && (json instanceof JSONArray)) {
        JSONArray jsonArray = (JSONArray) json;
        int ordinal = Integer.parseInt(key);
        if (ordinal >= 0 && ordinal < jsonArray.length()) {
          json = jsonArray.get(ordinal);
        } else {
          return null;
        }
      } else if (json instanceof JSONObject) {
        JSONObject jsonObject = (JSONObject) json;
        json = jsonObject.get(key);
      } else {
        return null;
      }
    }
    return json;
  }

  public void update(String path, String value) {
    JSONObject obj = (JSONObject) this.json;
    String[] keys = path.split("->");
    for (int i = 0; i < keys.length - 1; i++) {
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
    obj.put(keys[keys.length - 1], value);
  }

  public void add(String path, String value) {
    JSONObject obj = (JSONObject) this.json;
    String[] keys = path.split("->");
    for (int i = 0; i < keys.length - 1; i++) {
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
    obj.put(keys[keys.length - 1], value);
  }

  public Object iterate(String path) {
    JSONObject obj = (JSONObject) this.json;
    String[] keys = path.split("->");
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      key = key.trim();
      if (obj.optJSONArray(key) != null) {
        JSONArray jArray = obj.getJSONArray(key);
        key = keys[++i];
        int ordinal = Integer.parseInt(key);
        obj = (JSONObject) jArray.get(ordinal);
      } else if (obj.optJSONObject(key) != null) {
        obj = obj.getJSONObject(key);
      } else {
        return null;
      }
      log.info(obj.toString());
    }
    return obj;
  }


  public void iterate(Object obj, String[] keys, int index) {
    if (index == keys.length - 1) {

    }
    String key = keys[index].trim();
    if (StringUtils.isNumeric(key) && (json instanceof JSONArray)) {
      JSONArray jsonArray = (JSONArray) json;
      int ordinal = Integer.parseInt(key);
      if (ordinal >= 0 && ordinal < jsonArray.length()) {
        iterate(jsonArray.get(ordinal), keys, index + 1);
      }
    } else if (json instanceof JSONObject) {
      JSONObject jsonObject = (JSONObject) json;
      iterate(jsonObject.get(key), keys, index + 1);
    }
  }


  public boolean hasValue(String path) {
    return get(path) != null;
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
