package game;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Table {
  private List<Player> players;
  private int dealerPosition;
  private int id;
}
