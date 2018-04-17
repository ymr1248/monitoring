
$(function () {
		$("#btn_addtr").click(function () {
		$("#tab11 tbody tr").clone().appendTo("#dynamicTable tbody");   //在表格后面添加一行
		});
});
function deltr(opp) {
		var length = $("#dynamicTable tbody tr").length;
								//alert(length);
		if (length <= 1) {
			alert("至少保留一行");
		} else {
			$(opp).parent().parent().remove();//移除当前行
			changeIndex();
			}
		}

function checkIsExist() {
	var code = $("#barcodeNumber").val();
    $.ajax({
        type:"GET",   //http请求方式
        url:"isExist", //发送给服务器的url
        data:"barcodeNumber="+code, //发送给服务器的参数
        dataType:"json",  //告诉JQUERY返回的数据格式(注意此处数据格式一定要与提交的controller返回的数据格式一致,不然不会调用回调函数complete)
        complete:function(msg) {
			if (eval("(" + msg.responseText + ")")) {
				$("#msg").html("<font color='red'>条形码已存在</font>");
			} else {
				$("#msg").html("");
			}
        }//定义交互完成,并且服务器正确返回数据时调用回调函数 
    });
}

function suborder(){
	var code = document.getElementById("msg").innerText;
	if(code!=""){
		return false;
	}else{
		return true;
	}
}

function checkIsExist2() {
	var code = $("#barcodeNumber").val();
	var code1 = $("#idnotsee").text();
    $.ajax({
        type:"GET",   //http请求方式
        url:"isExist2", //发送给服务器的url
        data:"barcodeNumber="+code+"&id="+code1, //发送给服务器的参数
        dataType:"json",  //告诉JQUERY返回的数据格式(注意此处数据格式一定要与提交的controller返回的数据格式一致,不然不会调用回调函数complete)
        complete:function(msg) {
			if (eval("(" + msg.responseText + ")")) {
				$("#msg").html("<font color='red'>条形码已存在</font>");
			} else {
				$("#msg").html("");
			}
        }//定义交互完成,并且服务器正确返回数据时调用回调函数 
    });
}


