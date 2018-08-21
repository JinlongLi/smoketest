package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseEntity {
  final ObjectMapper mapper = new ObjectMapper();

  public String toJsonString() throws JsonProcessingException {
    return mapper.writeValueAsString(this);
  }
}
