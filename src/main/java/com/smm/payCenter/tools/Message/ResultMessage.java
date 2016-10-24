package com.smm.payCenter.tools.Message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultMessage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7339461273795844294L;

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";
	
	public static final String NULL = "null";
	
	public static final String SYS_ERROR = "sysError";

	public static final ResultMessage ADD_SUCCESS_RESULT = new ResultMessage(SUCCESS, "新增成功");
	public static final ResultMessage ADD_FAIL_RESULT = new ResultMessage(ERROR, "新增失败");

	public static final ResultMessage UPDATE_SUCCESS_RESULT = new ResultMessage(SUCCESS, "修改成功");
	public static final ResultMessage UPDATE_FAIL_RESULT = new ResultMessage(ERROR, "修改失败");
	
	public static final ResultMessage EXPORT_SUCCESS_RESULT = new ResultMessage(SUCCESS, "导出成功");
	public static final ResultMessage EXPORT_FAIL_RESULT = new ResultMessage(ERROR, "导出失败");
	public static final ResultMessage EXPORT_NULL_RESULT = new ResultMessage(NULL, "很抱歉，这段时间没有数据");
	
	public static final ResultMessage IMPORT_SUCCESS_RESULT = new ResultMessage(SUCCESS, "导入成功");
	public static final ResultMessage IMPORT_FAIL_RESULT = new ResultMessage(ERROR, "导入失败");

	public static final ResultMessage SET_SUCCESS_RESULT = new ResultMessage(SUCCESS, "设置成功");
	public static final ResultMessage CHECK_SUCCESS_RESULT = new ResultMessage(SUCCESS, "审核通过");
	public static final ResultMessage REJECT_SUCCESS_RESULT = new ResultMessage(SUCCESS, "驳回成功");
	public static final ResultMessage REJECT_ERROR_RESULT = new ResultMessage(ERROR, "必须保留一个银行账户");
	public static final ResultMessage GUANBI_SUCCESS_RESULT = new ResultMessage(SUCCESS, "关闭成功");

	public static final ResultMessage DELETE_SUCCESS_RESULT = new ResultMessage(SUCCESS, "删除成功");
	public static final ResultMessage DELETE_FAIL_RESULT = new ResultMessage(ERROR, "删除失败");
	
	public static final ResultMessage SMS_SEND_SUCCESS_RESULT = new ResultMessage(SUCCESS, "短信发送成功");
	public static final ResultMessage SMS_SEND_FAIL_RESULT = new ResultMessage(ERROR, "短信发送失败");

	public static final ResultMessage ID_UNEXITE_RESULT = new ResultMessage(ERROR, "编号ID不存在");
	
	public static final ResultMessage JOB_START_SUCCESS = new ResultMessage(SUCCESS, "JOB启动成功");
	public static final ResultMessage JOB_START_FAIL = new ResultMessage(ERROR, "JOB启动失败");
	public static final ResultMessage JOB_PARAM_ERROR = new ResultMessage(ERROR, "JOB启动参数错误,请检查该JOB对应的参数值！");
	public static final ResultMessage JOB_STOP_SUCCESS = new ResultMessage(SUCCESS, "JOB关闭成功");
	public static final ResultMessage JOB_STOP_FAIL = new ResultMessage(ERROR, "JOB关闭失败");

	public static final ResultMessage ID_EXITE_RESULT = new ResultMessage(ERROR, "编号ID已存在");
	
	public static final ResultMessage NO_EXITE_FAIL = new ResultMessage(ERROR, "存在");
	public static final ResultMessage NO_EXITE_SUCCESS = new ResultMessage(SUCCESS, "不存在");
	
	public static final ResultMessage UPDATE_WORKTIMESET_SUCCESS = new ResultMessage(SUCCESS, "编辑排班计划成功");
	public static final ResultMessage UPDATE_WORKTIMESET_FAIL = new ResultMessage(ERROR, "编辑排班计划失败");
	
	public static final ResultMessage TRANSFER_MONEY_SUCCESS = new ResultMessage(SUCCESS, "付款成功");
	public static final ResultMessage TRANSFER_MONEY_FAIL = new ResultMessage(ERROR, "付款失败");
	
	//密码匹配
	public static final ResultMessage COMPARE_PASSWORD_SUCCESS = new ResultMessage(SUCCESS, "原始密码输入正确");
	public static final ResultMessage COMPARE_PASSWORD_FAIL = new ResultMessage(ERROR, "原始密码输入错误");
	
	private String code;

	private String message;

	private Map<String, Object> attributes;

	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultMessage(Map<String, Object> attributes, String code, String message) {
		this.code = code;
		this.message = message;
		this.attributes = attributes;
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

	public boolean isSuccess() {
		return SUCCESS.equalsIgnoreCase(code);
	}

	@Override
	public String toString() {
		return "ResultMessage [code=" + code + ", message=" + message + "]";
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void addObject(String key, Object value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
	}

	/**
	 * 调用当前方法把状态改为异常
	 * 
	 * @param msg
	 */
	public void raise(String msg) {
		code = ERROR;
		message = msg;
	}

	public void raise(ResultHandle handle) {
		if (handle.isFail()) {
			raise(handle.getMsg());
		}
	}
	
	public static ResultMessage createResultMessage(){
		ResultMessage msg = new ResultMessage(SUCCESS, "操作成功");
		return msg;
	}
}
