

function showprompt(){
			$(".alter-s").show("slow");
			$("#img2").hide("slow");
			$("#words2").hide("slow");
			$("#words2").html("");
		}
		
		function hideprompt(){
			$(".alter-s").hide("slow");
			$("#img2").show();
			$("#words2").show();
		}
	function checkpwd(){
		var i = 0;	//包含几种符号
		var j = 1;  //安全等级
		var k = 1;  //判断验证结果，小于1则验证不通过
		var reg  = /[a-zA-z0-9\W_]{1,}/;
		var reg1 = /[a-z]{1,}/;
		var reg2 = /[A-Z]{1,}/;
		var reg3 = /[0-9]{1,}/;
		var reg4 = /[\W_]{1,}/;
		var reg5 = /\s/;
		var newpwd = $("#pwd1").val();
		if(newpwd.length<6 || newpwd.length>20){//6-20位字符
			$("#ck1").attr("class","fa fa-close red");
			k--;
		}else{
			$("#ck1").attr("class","fa fa-check green");
		}
		if(!reg.test(newpwd) || reg5.test(newpwd)){//只能包含大小写字母、数字以及标点符号（除空格）
			$("#ck2").attr("class","fa fa-close red");
			k--;
		}else{				
			$("#ck2").attr("class","fa fa-check green");					
		}
	
		if(reg1.test(newpwd)){ i++; }
		if(reg2.test(newpwd)){ i++; }
		if(reg3.test(newpwd)){ i++; }
		if(reg4.test(newpwd)){ i++; }
		
		if(i < 2){//大写字母、小写字母、数字和标点符号至少包含2种
			$("#ck3").attr("class","fa fa-close red");
			k--;
		}else{
			$("#ck3").attr("class","fa fa-check green");
		}
		if(i > 2){ j++; }
		if(newpwd.length > 10 && newpwd.length < 21){ j++; }
		if (j == 1){
			$("#safelv1").attr("class","block safe-g safe-r");
			$("#safelv2").attr("class","block safe-g");
			$("#safelv3").attr("class","block safe-g");
			$("#safeprompt").attr("class","red");
			$("#safeprompt").html("低");
		}
		if (j == 2){
			$("#safelv1").attr("class","block safe-g safe-o");
			$("#safelv2").attr("class","block safe-g safe-o");
			$("#safelv3").attr("class","block safe-g");
			$("#safeprompt").attr("class","orange");
			$("#safeprompt").html("中");
		}
		if (j == 3){
			$("#safelv1").attr("class","block safe-g safe-gr");
			$("#safelv2").attr("class","block safe-g safe-gr");
			$("#safelv3").attr("class","block safe-g safe-gr");
			$("#safeprompt").attr("class","green");
			$("#safeprompt").html("高");
		}
	}
	
	function testpwd2(){
		
		 var password2 = $('#pwd2').val();
		 var password =$("#pwd1").val();
		 
		 if(null==password2||""==password2||password2!=password){
			 $("#pwd2msg").text("2次密码输入不一致，请重新输入");
			 $("#pwd2msg").show();
			 $("#pwd2flag").show();
			 
		 }else if(password2==password){
			 $("#pwd2msg").hide();
			 $("#pwd2flag").hide();
		 }
	}
	

	function uploadfile(path,filename){
		if($("#"+filename).val()==null || $("#"+filename).val()==""){
			alert("不能上传空文件");
			return ;
		}
		    $.ajaxFileUpload(
                    {
                 url:path+'/rest/register/uploadmethod',            //需要链接到服务器地址
                secureuri:false,
                 fileElementId:filename,                        //文件选择框的id属性
                dataType: 'json',                                     //服务器返回的格式，可以是json
                 success: function (data, status)            //相当于java中try语句块的用法
                {      
                     alert(data.info);
                     if(data.info=="success"){
                    	  document.getElementById(filename+"Url").setAttribute("href",data.fileurl); 
                     }else{
                    	 document.getElementById(filename+"Url").setAttribute("href","#"); 
                     }
                     
                 },
                 error: function (data, status, e)            //相当于java中catch语句块的用法
                {
                     alert(e);
                 }
             }
                    				
                );
               
	}
	
	
	function getDepositBank(path){
		
		var bankshortName=$("#bankshortName").val();//隐藏id的值
		var bankName=$("#bankName").val();
		var bankBlongs=$("#bankBlongs").val();
		var selCity=$("#selCity").val();
		if(bankName.length>=4){
			  $.ajax({  
				  type : "POST",
			        url : path+"/rest/register/getDepositBank",   
			    	data :{
						"bankName":bankName,
						"bankBlongs":bankBlongs,
						"selCity":selCity
					},
			        dataType : 'json', 
			        success : function(data) 
			        {  
			        	if(data!=null){
			        		var array = eval(data.info) ;
			        		$("#bankName").val(array[0].shortName)
			        		$("#bankshortName").val(array[0].id)
			        	}else{
			        		$("#bankName").val("");
			        		$("#bankshortName").val("");
			        	}
			        },  
			        error : function(data)//服务器响应失败处理函数  
			        {  
			            $("#bankName").val("");
			            $("#bankshortName").val("");
			        }  
			    });  
			
		}
	}
	
	function subFrom(){
		
		var ck1=$("#ck1").attr("class"); 
		var ck2=$("#ck2").attr("class"); 
		var ck3=$("#ck3").attr("class"); 
		
		
		if($("#pwd1").val()==null || $("#pwd1").val()==""){
			$("#pwdflag").show();
			return;
		}else{
			$("#pwdflag").hide();
		}
		
		if($("#pwd2").val()==null || $("#pwd2").val()==""){
			$("#pwd2flag").show();
			return;
		}else{
			$("#pwd2flag").hide();
		}
		
		if(ck1=="fa fa-close red" || ck2=="fa fa-close red" || ck3=="fa fa-close red"){
			showprompt();
			return;
		}
		
		if(!$("#pwd2msg").is(":hidden")){
			//alert("两次密码输入不一致");
			return;
		}
		
		
		if($("#companyName").val()==null || $("#companyName").val()==""){
			$("#companyNameflag").show();
			return;
		}else{
			$("#companyNameflag").hide();
		}

		if($("#BusinessNo").val()==null || $("#BusinessNo").val()==""){
			$("#BusinessNoflag").show();
			return;
		}else{
			$("#BusinessNoflag").hide();
		}
		
		if($("#companyAdd").val()==null || $("#companyAdd").val()==""){
			$("#companyAddflag").show();
			return;
		}else{
			$("#companyAddflag").hide();
		}
		
		if($("#ContactName").val()==null || $("#ContactName").val()==""){
			$("#ContactNameflag").show();
			return;
		}else{
			$("#ContactNameflag").hide();
		}
		

		if($("#ContactTel").val()==null || $("#ContactTel").val()==""){
			$("#ContactTelflag").show();
			return;
		}else{
			$("#ContactTelflag").hide();
		}

		if($("#ContactPhone").val()==null || $("#ContactPhone").val()==""){
			$("#ContactPhoneflag").show();
			return;
		}else{
			$("#ContactPhoneflag").hide();
		}			

		if($("#postcode").val()==null || $("#postcode").val()==""){
			$("#postcodeflag").show();
			return;
		}else{
			$("#postcodeflag").hide();
		}	
		
		if($("#bankName").val()==null || $("#bankName").val()==""){
			$("#bankNameflag").show();
			return;
		}else{
			$("#bankNameflag").hide();
		}	
		
		if($("#bankNo").val()==null || $("#bankNo").val()==""){
			$("#bankNoflag").show();
			return;
		}else{
			$("#bankNoflag").hide();
		}	
		
		$('#usrForm').submit();

	}
	
