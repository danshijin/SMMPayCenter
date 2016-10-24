var Login = {

	doLogin : function() {
		if (!Login.validate()) {
			return;
		}
		
		var user_name = $('#account').val();
		var password = hex_md5($('#pwd').val());
		var vCode=$('#vCode').val().toLocaleLowerCase();
		
		$.ajax({
			type : "GET",
			url : BASE_URL + "/user/userLogin.do",
			data :{
				"account":user_name,
				"pwd":password,
				"vCode":vCode
			},
			dataType : "json",
			success : function(res) {
				if (res.code == "SUCC") {
					// 成功
					
					delCookie("parentMenu");
					delCookie("childMenu"); 
					
					var account = $("#account").val();
					var password = $("#password").val();
					$.cookie("rmbUser", "true", { expires: 366 }); // 存储一个带7天期限的 cookie
					$.cookie("account", account, { expires: 366 }); // 存储一个带7天期限的 cookie
					$.cookie("password", password, { expires: 366 }); // 存储一个带7天期限的 cookie
					
					window.location.href = BASE_URL + "/user/loginSucc.do";
				} else if (res.code == "EXE") {
					alert(res.msg);
					
				} else if (res.code == "VCODE_ERROR") {
					alert(res.msg);
					
				} else if (res.code == "VCODE_NULL") {
					alert(res.msg);
					
				}else if (res.code == "VCODE_INVALID") {
					Login.verifyCode();
					alert(res.msg);
				} else {
					alert(res.msg);
					
				}
			}
		});
	},
	validate : function() {
		var user_namea = $('#account').val();
		var passworda = $('#pwd').val();
		var vCode=$('#vCode').val();
		var flag = false;
		if (!user_namea) {
			alert("请输入用户名");
			return false;
		}
		if (!passworda) {
			alert("请输入密码");
			return false;
		}
		if (!vCode) {
			alert("请输入验证码");
			return false;
		}
		return true;
	},

	verifyCode : function() {

		$("#verifyCode").attr("src", BASE_URL + "/user/verifyCode.do?time=" + new Date().getTime());

	}
}
