package kscc.com.gateway.ctr;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import kscc.com.gateway.vo.UsrGatewayV;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/usr", method=RequestMethod.GET)
public class UsrGatewayC {
	
	@ResponseBody
	@RequestMapping(value="readUsrList.do", method=RequestMethod.GET)
	public UsrGatewayV readUsrList(ModelMap model, @Valid UsrGatewayV usrGatewayV, BindingResult result, HttpServletRequest req) throws Exception{
		
		//1. Validator Factory를 이용한 validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UsrGatewayV>> constraintViolations = validator.validate(usrGatewayV);
		if(constraintViolations.size()==0){
			System.out.println("Validation Check finished without error");
		}else{
			System.out.println(constraintViolations.toString());
		}
		
		//2. Binding Result를 이용한 validation
		if(result.hasErrors()){
			System.out.println("Validation error");
		}else{
			System.out.println("Validation Check finished without error");
		}
		UsrGatewayV resultUsrGatewayV = new UsrGatewayV();
		resultUsrGatewayV.setUsrName("조준");
		resultUsrGatewayV.setPhoneNum("01067882423");
		resultUsrGatewayV.setDeptName("서울시서초구반포2동");
		
		//return new Gson().toJson(new ErrorMessageV("500","validation Error"));
		return resultUsrGatewayV;
	}
}
