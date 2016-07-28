package kscc.com.gateway.response;

public class CommonResV {
	String success;
	private CommonErrV err;
	private Object response;
	
	public CommonResV(){
		
	}
	public CommonResV(String success, CommonErrV err, Object response) {
		super();
		this.success = success;
		this.err = err;
		this.response = response;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public CommonErrV getErr() {
		return err;
	}
	public void setErr(CommonErrV err) {
		this.err = err;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
}
