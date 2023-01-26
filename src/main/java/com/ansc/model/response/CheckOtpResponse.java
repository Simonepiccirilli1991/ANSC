package com.ansc.model.response;

public class CheckOtpResponse {

	private Boolean autenticationSucc;
	private String msg;
	private String errorMsg;
	
	public Boolean getAutenticationSucc() {
		return autenticationSucc;
	}
	public void setAutenticationSucc(Boolean autenticationSucc) {
		this.autenticationSucc = autenticationSucc;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
