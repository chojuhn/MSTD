package kscc.com.error.vo;

public class ErrorMessageV {
	private String status;
	private String message;

	public ErrorMessageV(){
		
	}
	
	public ErrorMessageV(String status, String message){
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
