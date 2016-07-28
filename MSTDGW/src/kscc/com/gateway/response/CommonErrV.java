package kscc.com.gateway.response;

public class CommonErrV {
	private String code;
	private String message;
	
	public CommonErrV(){
		
	}
	public CommonErrV(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
