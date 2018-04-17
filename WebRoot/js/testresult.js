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