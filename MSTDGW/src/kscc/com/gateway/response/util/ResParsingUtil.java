package kscc.com.gateway.response.util;

import kscc.com.gateway.usr.vo.UsrGatewayV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ResParsingUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResParsingUtil.class);
	
	public static Boolean isSuccess(String json) throws Exception {
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonTree = jsonParser.parse(json);
		
		if(jsonTree == null){
			LOG.debug("json parameter is null");
			return null;
		}
		
		if(jsonTree.getAsJsonObject().get("success").equals("false") )
			return false;
		else
			return true;
	}
	
	public static String readError(String json) throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonTree = jsonParser.parse(json);
		
		if(jsonTree == null || jsonTree.getAsJsonObject().get("error") == null){
			LOG.debug("json parameter is null");
			return null;
		}
		
		return jsonTree.getAsJsonObject().get("error").toString();
	}
	
	public static String readResponse(String json) throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonTree = jsonParser.parse(json);
		
		if(jsonTree == null){
			LOG.debug("json parameter is null");
			return null;
		}
		
		return jsonTree.getAsJsonObject().get("response").toString();
	}
	
	public static Object convertResponsetoObject (String json, Class classOfT) throws Exception {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonTree = jsonParser.parse(json);
		
		if(jsonTree == null){
			LOG.debug("json parameter is null");
			return null;
		}
		
		Gson gson = new Gson();
		UsrGatewayV usrGatewayV = gson.fromJson(jsonTree.getAsJsonObject().get("response"), classOfT);
		
		return usrGatewayV;
	}
}
