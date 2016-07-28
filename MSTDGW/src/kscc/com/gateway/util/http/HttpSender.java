package kscc.com.gateway.util.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpSender {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpSender.class);
	
	@Autowired
	private HttpClient httpClient;
	
	public String send(String url, String paramJson, String httpMethod) throws Exception {
		return sendGet(url);
	}
	
	public String sendGet(String url) throws Exception {
		LOG.debug("request url : >>> " + url);
		HttpGet httpGet = new HttpGet(url);
		ResponseHandler <String> responseHandler = new ResponseHandler<String>(){
			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if ( status >= 200 && status < 300){
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}else{
					throw new ClientProtocolException("Unexpected response status :" + status);
				}
			}
		};
		String responseBody = httpClient.execute(httpGet, responseHandler);
		LOG.debug("response from child : >>> " + responseBody);
		return responseBody;
	}
	
	public String sendPost(String url, String param) throws Exception {
		String result = "";
		return result;		
	}
	
	public String sendPut(String url, String param) throws Exception {
		String result = "";
		return result;
	}
	
	public String sendDelete(String url) throws Exception {
		String result = "";
		return result;		
	}
}
 