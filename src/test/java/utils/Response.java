package utils;

import java.util.Collections;
import java.util.List;
import org.apache.http.Header;
import lombok.Getter;

@Getter
public class Response {

  private final int responseCode;
  private final String responseBody;
  private String requestUrl;
  private String postData;
  private final List<Header> headers;

  public Response(int responseCode, String responseBody) {
    this(responseCode, responseBody, Collections.emptyList());
  }

  public Response(int responseCode, String responseBody, String requestUri) {
    this(responseCode, responseBody, requestUri, null, Collections.emptyList());
  }

  public Response(int responseCode, String responseBody, List<Header> headers) {
    this(responseCode, responseBody, null, null, headers);
  }

  public Response(int responseCode, String responseBody, String requestUrl, String postData,
      List<Header> headers) {
    this.responseCode = responseCode;
    this.responseBody = responseBody;
    this.headers = headers;
    this.requestUrl = requestUrl;
    this.postData = postData;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Header location =
        this.headers.stream().filter(h -> h.getName().equals("Location")).findFirst().orElse(null);
    Header traceId =
        this.headers.stream().filter(h -> h.getName().equals("trace-id")).findFirst().orElse(null);
    if (location != null) {
      this.requestUrl = location.getValue();
    }
    if (this.requestUrl != null) {
      sb.append("\nRequest Url: ").append(this.requestUrl);
    }
    if (this.postData != null) {
      sb.append("\nPost data: ").append(this.postData);
    }
    if (traceId != null) {
      sb.append("\ntrace-id: ").append(traceId.getValue());
    }
    sb.append("\nResponse code: ").append(responseCode);
    sb.append("\nResponse body:").append(responseBody);
    return sb.toString();
  }
}
