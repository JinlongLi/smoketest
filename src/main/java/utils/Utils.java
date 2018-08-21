package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

public class Utils {
  public final static String streamAsString(CloseableHttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    if (entity == null) {
      return null;
    }
    InputStream inputStream = entity.getContent();
    StringWriter writer = new StringWriter();
    IOUtils.copy(inputStream, writer, "UTF-8");
    return writer.toString();
  }
}
