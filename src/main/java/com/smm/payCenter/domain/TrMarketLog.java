package com.smm.payCenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
* @author  zhaoyutao
* @version 2015年12月9日 下午5:34:52
* 开市闭市日志类
*/

public class TrMarketLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7074635306659748612L;
	
	private Integer id;
	
	private Integer operator;  //操作人
	
	private Integer operType; // 0 闭市
	
	private Date operTime; //操作时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	
	
	
	
}
