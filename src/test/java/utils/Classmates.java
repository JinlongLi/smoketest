package utils;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Classmates extends BaseEntity {
  private List<Person> persons = new ArrayList<>();
}
