package com.smm.payCenter.tools.Message;

import java.io.Serializable;



import org.apache.log4j.Logger;

import com.smm.payCenter.controller.accountAudit.AccountController;
public class ResultHandle implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8088409315308751253L;
	
	private static Logger logger = Logger.getLogger(AccountController.class.getName());
	private boolean success = true;
	private String msg;

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.success = false;
		this.msg = msg;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	public boolean isFail() {
		return !isSuccess();
	}

	public ResultHandle() {
	}

	public ResultHandle(String msg) {
		setMsg(msg);
	}
	
	public void setMsg(Exception ex){
		setMsg(ex.getMessage());
		if(logger.isDebugEnabled()){
			ex.printStackTrace();
		}else if(ex instanceof NullPointerException||ex instanceof IllegalArgumentException){
			ex.printStackTrace();
		}
	}
}
