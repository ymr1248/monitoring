function monitoring_click(id){
	if(document.getElementById("section_"+id)) {
		$("#img_"+id).attr("src","images/left.png");
		$("#section_"+id).remove();
		
	}else{
		var w = '<div class="test_div" id="test_div_'+id+'"></div>';
		$("#img_"+id).attr("src","images/down.png");
		$("#insert_"+id).append(w);
		
		
		
		
		$.ajax({
			type : "POST",
			url: "getMonitoringByGroupId?partsId=" + id,
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			error : function() {
				alert("请求失败，请重试！");
			},
			success:function (data) {
				if (data!=null) {
					$.each(data,function(i,item){
						var text ='<section class="section_class" id="section_${id}">'
								 +'<c:forEach items="${monitoringList}" var="monitoring" varStatus="status">'
								 +'<c:if test="${monitoring.monitorGroup.groupId==monitorGroup.groupId }">'
								 
								 
								 ;
						
						
						
						
						
						/*<section class="section_class" id="section_${status.index}">
					 	<c:forEach items="${monitoringList}" var="monitoring" varStatus="status">
							<c:if test="${monitoring.monitorGroup.groupId==monitorGroup.groupId }">
							<section class="section_class1">
							<div class="section_class1_div1">
								${monitoring.monitorName}
							</div>	
							 <video id="${monitoring.id}" class="video-js" controls autoplay preload="auto" width="600" height="460" data-setup="{}" style="margin: 0 atuo">
								<source src="${monitoring.monitorIp}" type="rtmp/flv">
							</video>
							</section>
							</c:if>
						</c:forEach> 
					</section>*/
						
						
						
						
						
						
						
						
						
						
					})
					
				}
			}
		});
		
		
		
		
	}
}