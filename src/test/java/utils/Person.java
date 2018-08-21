package utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Person extends BaseEntity {
  private String name;
  private double height;
  private boolean isStudent;
}
