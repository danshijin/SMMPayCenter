var reg = /[a-zA-z0-9\W_]{1,}/;// 只能包含大小写字母、数字以及标点符号
var reg1 = /[a-z]{1,}/;// 小写字母
var reg2 = /[A-Z]{1,}/;// 大写字母
var reg3 = /[0-9]{1,}/;// 数字
var reg4 = /[\W_]{1,}/;
var reg5 = /\s/;// 空格
var reg6 = /^[A-Za-z0-9]+$/;// 只能包含大小写字母、数字

// 验证标志
var flag_checkpwd = "true";
var two_password = "true";
var password_count = "true";//密码位数
var password_specialCharacter = "true";//密码含有特殊字符

function showprompt() {
	if($("#pwd1").val().length == 0){
		$(".tips1").show("slow");
		return false;
	}
	
	checkpwd();
}

function hideprompt() {
	$(".tips1").hide("slow");
}
/**
 * 原始密码校验
 */
function checkpwd() {
	var oldpwd = $("#pwd1").val();
	if(oldpwd.length > 0){
		$(".tips1").hide("slow");
	}
	if(oldpwd.length == 0){
		$(".tips1").show("slow");
		$(".tips3").hide("slow");
		return false;
	}
	$.ajax({
		type : "POST",
		url : getRootPath() + "/sysManager/validatePassword.do",
		data : {
			"password" : oldpwd
		},
		dataType : "json",
		success : function(res) {
			if (res.code == "success") {
				$(".tips3").hide("slow");

				$("#ck2").attr("class", "fa fa-check green");
				$(".tips2").show("slow");
				flag_checkpwd = "true";
			} else {
				$(".tips2").hide("slow");

				$("#ck3").attr("class", "fa fa-close red");
				$(".tips3").show("slow");
				flag_checkpwd = "false";
			}
		}
	});

}

function hidepwd() {
	$(".tips1").hide("slow");
	$(".tips2").hide("slow");
	$(".tips3").hide("slow");
}
/**
 * 新密码校验
 */

function showpwd2() {
	$(".tips4").show("slow");
	checkpwd2();
}
function checkpwd2() {
	var i = 0; // 包含几种符号
	var j = 1; // 安全等级
	var k = 1; // 判断验证结果，小于1则验证不通过

	var newpwd = $("#pwd2").val();

	if (newpwd.length >= 6 && newpwd.length <= 20) {// 6-20位字符
		$("#ck4").attr("class", "fa fa-check green");
		showpwd3();
		password_count = "true";
	} else {
		$("#ck4").attr("class", "fa fa-close red");
		showpwd3();
		password_count = "false";
	}
	if (reg6.test(newpwd)) {// 只能包括大小写字母，且不能有空格
		$("#ck5").attr("class", "fa fa-check green");
		showpwd3();
		password_specialCharacter = "true";
	} else {
		$("#ck5").attr("class", "fa fa-close red");
		showpwd3();
		password_specialCharacter = "false";
	}

	// 包含集中字符，大小写等
	if (reg1.test(newpwd)) {
		i++;
	}// 含有小写字母
	if (reg2.test(newpwd)) {
		i++;
	}// 含有大写字母
	if (reg3.test(newpwd)) {
		i++;
	}// 含有数字
	if (i >= 2) {
		j++;
	}
	if (newpwd.length > 10 && newpwd.length < 21) {
		j++;
	}
	if (j == 1) {
		$("#safelv1").attr("class", "block safe-g safe-r");
		$("#safelv2").attr("class", "block safe-g");
		$("#safelv3").attr("class", "block safe-g");
		$("#safeprompt").attr("class", "red");
		$("#safeprompt").html("低");
	}
	if (j == 2) {
		$("#safelv1").attr("class", "block safe-g safe-o");
		$("#safelv2").attr("class", "block safe-g safe-o");
		$("#safelv3").attr("class", "block safe-g");
		$("#safeprompt").attr("class", "orange");
		$("#safeprompt").html("中");
	}
	if (j == 3) {
		$("#safelv1").attr("class", "block safe-g safe-gr");
		$("#safelv2").attr("class", "block safe-g safe-gr");
		$("#safelv3").attr("class", "block safe-g safe-gr");
		$("#safeprompt").attr("class", "green");
		$("#safeprompt").html("高");
	}
}
function hidepwd2() {
	$(".tips4").hide("slow");
}
// 新密码再次输入校验
function showpwd3() {
	var newpwd = $("#pwd2").val();
	var newpwd_repeat = $("#pwd3").val();
	if (newpwd_repeat == '') {
		return false;
	}

	if (newpwd_repeat != newpwd) {
		$(".tips6").hide("slow");

		$("#ck6").attr("class", "fa fa-close red");
		$(".tips5").show("slow");
		two_password = "false";
	} else {
		$(".tips5").hide("slow");

		$("#ck7").attr("class", "fa fa-check green");
		$(".tips6").show("slow");
		two_password = "true";
	}
}
function hidepwd3() {
	$(".tips5").hide("slow");
	$(".tips6").hide("slow");
}
// 确认提交
function subFrom() {
	if ($("#pwd1").val() == "" || $("#pwd2").val() == ""|| $("#pwd3").val() == "") {
		layer.alert('原密码或者新密码不能空!', 0, '提示', function(index) {
			layer.close(index);
		});
		return false;
	}
	if (flag_checkpwd != "true" ) {
		layer.alert('原密码输入错误!', 0, '提示', function(index) {
			layer.close(index);
		});
		return false;
	}
	if (two_password != "true") {
		layer.alert('新密码两次输入不一致!', 0, '提示', function(index) {
			layer.close(index);
		});
		return false;
	}
	if (password_count != "true") {
		layer.alert('新密码至少6个字符!', 0, '提示', function(index) {
			layer.close(index);
		});
		return false;
	}
	if (password_specialCharacter != "true") {
		layer.alert('新密码只能包含大小写字母、数字!', 0, '提示', function(index) {
			layer.close(index);
		});
		return false;
	}
	
	$.ajax({
		type : "POST",
		url : getRootPath() + "/sysManager/updatePassword.do",
		data : {
			"newPassword" : $("#pwd2").val()
		},
		dataType : "json",
		success : function(res) {
			if (res.code == "success") {
				confirmAndRefresh(res);
			}
		}
	});
	
}


function getRootPath() {

	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}


function confirmAndRefresh(result) {
	if (result.code == "success") {
		//重新加载
		delCookie("parentMenu");
		delCookie("childMenu"); 
		window.location.href = getRootPath() + "/user/loginOut.do";
	} else {
		pandora.dialog({
			wrapClass : "dialog-mini",
			content : result.message,
			okValue : "确定",
			ok : function() {
				
			}
		});
	}
}
