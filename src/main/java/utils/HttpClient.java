package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class HttpClient {
  private static final String DELETE = "DELETE";
  private static final String EMPTY_JSON_BODY = "{}";
  private static final String APPLICATION_JSON = "application/json";
  private static final String POST = "POST";
  private static final String PUT = "PUT";
  private static final String PATCH = "PATCH";
  private static final String UTF_8 = "UTF-8";
  private static final String ACCEPT = "Accept";
  private static final String CONTENT_TYPE = "Content-type";
  private static final String AUTHORIZATION = "Authorization";
  private static final String TEXT_URI_LIST = "text/uri-list";

  private final CloseableHttpClient httpClient;
  private final int timeout = 60; // seconds
  private TestEnvironment testEnv;
  private String serviceToServiceToken;
  private String adminToken;
  private String operatorToken;

  // make this class singleton
  private static HttpClient instance = null;

  public static HttpClient getInstance() {
    if (instance == null) {
      instance = new HttpClient();
    }
    return instance;
  }

  private HttpClient() {
    RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
        .setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
    this.httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
  }

  public Response doPost(String uri, String jsonBody, String contentType)
      throws ClientProtocolException, IOException {
    return doPost(uri, jsonBody, contentType, false);
  }

  public Response doPost(String uri, String jsonBody, String contentType, boolean useAdminToken)
      throws ClientProtocolException, IOException {
    return doRequest(uri, jsonBody, contentType, POST,
        useAdminToken ? serviceToServiceToken : adminToken);
  }

  public Response doPost(String uri, String jsonBody, String contentType, String accessToken)
      throws ClientProtocolException, IOException {
    return doRequest(uri, jsonBody, contentType, POST, accessToken);
  }

  public Response doPost(Service service, String uri, String jsonBody, String contentType)
      throws ClientProtocolException, IOException {
    return doPost(service, uri, jsonBody, contentType, false);
  }

  public Response doPost(Service service, String uri, String jsonBody, String contentType,
      boolean useAdminToken) throws ClientProtocolException, IOException {
    String url = testEnv.getBaseUrlForService(service) + uri;
    return doPost(url, jsonBody, contentType, useAdminToken);
  }

  public Response doPut(String uri, String jsonBody, String contentType)
      throws ClientProtocolException, IOException {
    return doRequest(uri, jsonBody, contentType, PUT);
  }

  public Response doPut(String uri, String jsonBody) throws ClientProtocolException, IOException {
    return doPut(uri, jsonBody, TEXT_URI_LIST);
  }

  public Response doPut(Service service, String uri, String jsonBody)
      throws ClientProtocolException, IOException {
    return doPut(service, uri, jsonBody, APPLICATION_JSON);
  }

  public Response doPut(Service service, String uri, String jsonBody, String contentType)
      throws ClientProtocolException, IOException {
    String url = testEnv.getBaseUrlForService(service) + uri;
    return doPut(url, jsonBody, contentType);
  }

  public Response doPatch(String uri, String jsonBody, String contentType)
      throws ClientProtocolException, IOException {
    return doPatch(uri, jsonBody, contentType, false);
  }

  public Response doPatch(String uri, String jsonBody, String contentType,
      boolean useServiceToServiceToken) throws ClientProtocolException, IOException {
    return doRequest(uri, jsonBody, contentType, PATCH, useServiceToServiceToken);
  }

  public Response doPatch(Service service, String uri, String jsonBody, String contentType)
      throws ClientProtocolException, IOException {
    return doPatch(service, uri, jsonBody, contentType, false);
  }

  public Response doPatch(Service service, String uri, String jsonBody, String contentType,
      boolean useServiceToServiceToken) throws ClientProtocolException, IOException {
    String url = testEnv.getBaseUrlForService(service) + uri;
    return doPatch(url, jsonBody, contentType, useServiceToServiceToken);
  }

  public Response doDelete(Service service, String uri)
      throws ClientProtocolException, IOException {
    String url = testEnv.getBaseUrlForService(service) + uri;
    return doDelete(url, EMPTY_JSON_BODY, APPLICATION_JSON, adminToken);
  }

  public Response doDelete(Service service, String uri, String jsonBody)
      throws ClientProtocolException, IOException {
    String url = testEnv.getBaseUrlForService(service) + uri;
    return doRequest(url, jsonBody, APPLICATION_JSON, DELETE, true);
  }

  public Response doDelete(String url, String jsonBody, String contentType, String accessToken)
      throws ClientProtocolException, IOException {
    HttpDelete request = new HttpDelete(url);
    request.setHeader(AUTHORIZATION, accessToken);
    request.setHeader(CONTENT_TYPE, contentType);
    request.addHeader(ACCEPT, contentType);
    CloseableHttpResponse closeResponse = httpClient.execute(request);
    Response response = convertToResponse(closeResponse, url, jsonBody);
    closeResponse.close();
    log.debug("DELETE: url=" + url + ": postData = " + jsonBody);
    return response;
  }

  private Response doRequest(String uri, String jsonBody, String contentType, String verb)
      throws ClientProtocolException, IOException {
    return doRequest(uri, jsonBody, contentType, verb, false);
  }

  private Response doRequest(String uri, String jsonBody, String contentType, String verb,
      boolean useAdminToken) throws ClientProtocolException, IOException {
    return doRequest(uri, jsonBody, contentType, verb,
        useAdminToken ? serviceToServiceToken : adminToken);
  }

  private Response doRequest(String uri, String jsonBody, String contentType, String verb,
      String accessToken) throws ClientProtocolException, IOException {
    HttpEntityEnclosingRequestBase request =
        constructRequest(uri, jsonBody, contentType, verb, accessToken);
    CloseableHttpResponse closeResponse = httpClient.execute(request);
    Response response = convertToResponse(closeResponse, uri, jsonBody);
    closeResponse.close();
    return response;
  }

  private HttpEntityEnclosingRequestBase constructRequest(String uri, String jsonBody,
      String contentType, String verb, String accessToken)
      throws ClientProtocolException, IOException {
    HttpEntityEnclosingRequestBase request = null;
    String url = uri.contains("http") ? uri : testEnv.getBaseUrl() + uri;
    log.debug(verb + ": url=" + url + ": postData = " + jsonBody);
    if (POST.equals(verb)) {
      request = new HttpPost(url);
    } else if (PUT.equals(verb)) {
      request = new HttpPut(url);
    } else if (PATCH.equals(verb)) {
      request = new HttpPatch(url);
    } else if (DELETE.equals(verb)) {
      request = new HttpDeleteWithBody(url);
    }
    StringEntity entity = new StringEntity(jsonBody);
    request.setEntity(entity);
    request.setHeader(AUTHORIZATION, accessToken);
    request.setHeader(CONTENT_TYPE, contentType);
    request.addHeader(ACCEPT, contentType);
    return request;
  }

  // post with form data
  public Response doPost(String url, List<BasicNameValuePair> params)
      throws ClientProtocolException, IOException, ParseException {
    HttpPost httppost = new HttpPost(url);
    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, UTF_8);
    httppost.setEntity(urlEncodedFormEntity);
    CloseableHttpResponse httpResponse = httpClient.execute(httppost);
    Response response = convertToResponse(httpResponse, url, params.toString());
    httpResponse.close();
    return response;
  }

  public Response doGet(Service service, String uri) throws ClientProtocolException, IOException {
    return doGet(service, uri, true);
  }

  public Response doGet(Service service, String uri, boolean hasToken)
      throws ClientProtocolException, IOException {
    return doGet(testEnv.getBaseUrlForService(service) + uri, hasToken);
  }

  public Response doGet(String uri) throws ClientProtocolException, IOException {
    return doGet(uri, true);
  }

  public Response doGet(String uri, boolean hasToken) throws ClientProtocolException, IOException {
    String url = uri.contains("http") ? uri : testEnv.getBaseUrl() + uri;
    log.debug("GET:" + url);
    HttpGet httpGet = new HttpGet(url);
    if (hasToken) {
      httpGet.addHeader(AUTHORIZATION, adminToken);
    }
    CloseableHttpResponse closeResponse = httpClient.execute(httpGet);
    Response response = convertToResponse(closeResponse, url, null);
    closeResponse.close();
    return response;
  }

  public Response doSimplePost(String strUrl, String body, String contentType) throws IOException {
    URL url = new URL(strUrl);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

    httpURLConnection.setRequestMethod(POST);
    httpURLConnection.setRequestProperty(CONTENT_TYPE, contentType);
    httpURLConnection.setRequestProperty(AUTHORIZATION, adminToken);
    httpURLConnection.setConnectTimeout(timeout * 1000);
    httpURLConnection.setReadTimeout(timeout * 2000);

    httpURLConnection.setDoOutput(true);
    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
    dataOutputStream.write(body.getBytes(UTF_8));
    dataOutputStream.flush();
    dataOutputStream.close();

    return getHttpResponse(httpURLConnection);
  }

  public Response doLink(String linkUrl, String linkedURl) throws IOException {
    return doSimplePost(linkUrl, linkedURl, TEXT_URI_LIST);
  }

  public static Response convertToResponse(CloseableHttpResponse closeableHttpResponse, String url,
      String postData) throws IOException {

    Header[] headers = closeableHttpResponse.getAllHeaders();

    List<Header> responseHeaders = Arrays.asList(headers);

    return new Response(closeableHttpResponse.getStatusLine().getStatusCode(),
        Utils.streamAsString(closeableHttpResponse), url, postData, responseHeaders);
  }

  private static Response getHttpResponse(HttpURLConnection httpURLConnection) throws IOException {
    int responseCode = httpURLConnection.getResponseCode();
    StringBuffer sbResponse = new StringBuffer();
    BufferedReader bufferedReader = null;

    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
    String inputLine;

    while ((inputLine = bufferedReader.readLine()) != null) {
      sbResponse.append(inputLine);
    }
    bufferedReader.close();

    return new Response(responseCode, sbResponse.toString());
  }

  public void close() throws IOException {
    httpClient.close();
  }

  class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = DELETE;

    @Override
    public String getMethod() {
      return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
      super();
      setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
      super();
      setURI(uri);
    }

    public HttpDeleteWithBody() {
      super();
    }
  }
}

