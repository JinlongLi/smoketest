package utils;

public enum Service {
  CARD("card", 8081), CHIP("chip", 8082);

  private String name;
  private int port;

  Service(String name, int port) {
    this.name = name;
    this.port = port;
  }

  public String path() {
    return name + "-service";
  }

  public int port() {
    return port;
  }
}
