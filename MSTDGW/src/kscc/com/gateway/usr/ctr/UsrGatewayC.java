package kscc.com.gateway.usr.ctr;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import kscc.com.gateway.code.Constants;
import kscc.com.gateway.response.CommonResV;
import kscc.com.gateway.response.util.ResParsingUtil;
import kscc.com.gateway.usr.vo.UsrGatewayV;
import kscc.com.gateway.util.http.HttpSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@Api(value="usr-act")
@RequestMapping(value="/api/gw/usr", method=RequestMethod.GET)
public class UsrGatewayC {
	
	private static final Logger LOG = LoggerFactory.getLogger(UsrGatewayC.class);
	
	@Value("${common.child.url}")
	String childUrl;
	
	@Autowired
	private HttpSender httpSender;
	
	@ResponseBody
	@ApiOperation(value="get usr info", notes="note of readUsr")
	@RequestMapping(value="{usrName}/{phoneNum}/{deptName}", method=RequestMethod.GET)
	public /*String*/ ResponseEntity<CommonResV> readUsr(ModelMap model, @PathVariable String usrName, @PathVariable String phoneNum, @PathVariable String deptName, HttpServletRequest req) throws Exception{
		JsonParser jsonParser = new JsonParser();
		String result = httpSender.send(childUrl + "/api/fchld/usr/" + usrName + "/" + phoneNum + "/" + deptName, null, Constants.HTTP_GET);
		//IE 에서 json response 다운로드 현상 발생
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		JsonElement jsonTree = jsonParser.parse(result);
		if(jsonTree == null || jsonTree.getAsJsonObject().get("success").toString().equals("false") ){
			LOG.debug("err");
		}

		UsrGatewayV resultUsrGatewayV = ((UsrGatewayV)ResParsingUtil.convertResponsetoObject(result, UsrGatewayV.class));
		
		//UsrGatewayV resultUsrGatewayV = gson.fromJson(httpSender.send(childUrl + "/api/fchld/usr/chojuhn/01067882423/QA", null, Constants.HTTP_GET), UsrGatewayV.class);
		CommonResV commonResV = new CommonResV("true", null, resultUsrGatewayV);
		return new ResponseEntity<CommonResV>(commonResV, headers, HttpStatus.OK);
	}
}
