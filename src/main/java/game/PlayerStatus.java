package game;

public enum PlayerStatus {
  ACTIVE("active"),
  SITOUT("sitout"),
  NOT_CONNECTED("not connected");

  private String prettyName;

  public String getPrettyName() {
    return prettyName;
  }

  PlayerStatus(String prettyName) {
    this.prettyName = prettyName;
  }
}
