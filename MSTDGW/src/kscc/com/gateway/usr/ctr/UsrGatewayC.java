package kscc.com.gateway.usr.ctr;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;

import javax.servlet.http.HttpServletRequest;

import kscc.com.gateway.usr.vo.UsrGatewayV;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value="usr-act")
@RequestMapping(value="/usr", method=RequestMethod.GET)
public class UsrGatewayC {
	
	@ResponseBody
	@ApiOperation(value="get usr info", notes="note of readUsr")
	@RequestMapping(value="{usrName}/{phoneNum}/{deptName}", method=RequestMethod.GET)
	public /*String*/ UsrGatewayV readUsr(ModelMap model, @PathVariable String usrName, @PathVariable String phoneNum, @PathVariable String deptName, HttpServletRequest req) throws Exception{

		UsrGatewayV resultUsrGatewayV = new UsrGatewayV();
		resultUsrGatewayV.setUsrName(usrName);
		resultUsrGatewayV.setPhoneNum(phoneNum);
		resultUsrGatewayV.setDeptName(deptName);

		return resultUsrGatewayV;
	}
}
