<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.HashMap,com.monitoring.entity.Manager" pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.monitoring.util.CyptoUtils" %>
<%@ page import="com.monitoring.entity.User" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords"
          content="Novus Admin Panel Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
SmartPhone Compatible web template, free WebDesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design"/>
    <script type="application/x-javascript">addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    }
    </script>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <!-- Custom CSS -->
    <link href="css/style.css" rel='stylesheet' type='text/css'/>
    <!-- font CSS -->
    <!-- font-awesome icons -->
    <link href="css/font-awesome.css" rel="stylesheet">
    <!-- //font-awesome icons -->
    <!-- js-->
    <!-- num -->
    <link href="css/num.css" rel="stylesheet">
    <script src="js/jquery-1.11.1.min.js"></script>

    <!--webfonts-->
    <link
            href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic'
            rel='stylesheet' type='text/css'>
    <!--//webfonts-->
    <!--animate-->
    <link href="css/animate.css" rel="stylesheet" type="text/css"
          media="all">
    <script src="js/wow.min.js"></script>
    <script>
        new WOW().init();
    </script>
    <!--//end-animate-->
    <!-- chart -->
    <script src="js/Chart.js"></script>
    <!-- //chart -->
    <!--Calender-->
    <link rel="stylesheet" href="css/clndr.css" type="text/css"/>
    <script src="js/underscore-min.js" type="text/javascript"></script>
    <script src="js/moment-2.2.1.js" type="text/javascript"></script>
    <script src="js/clndr.js" type="text/javascript"></script>
    <script src="js/site.js" type="text/javascript"></script>
    <!--End Calender-->
    <!-- Metis Menu -->
    <script src="js/metisMenu.min.js"></script>
    <script src="js/custom.js"></script>
    <link href="css/custom.css" rel="stylesheet">
    <!--//Metis Menu -->
	<link rel="stylesheet" href="css/piliang_delete.css"/>
    


</head>
<body class="cbp-spmenu-push">
<%
	User user = (User) request.getSession().getAttribute("USER");
    String userId = CyptoUtils.encode(String.valueOf(user.getId()));
    String account =user.getUserAccount();
    String userName =user.getUserName();
%>
<!-- head content start-->
<jsp:include page="user_header.jsp"/>
<!-- head content end-->

<!-- main content start-->
<div id="page-wrapper">
    <div class="main-page">

        <h3 class="title1">监控器组列表:</h3>
        <div class="grid_3 grid_5 widget-shadow">
            <div style="float: left;">
                <h3>
                    <a href="toMonitorgroup_add?"><span class="label label-success">添加监控器组</span></a>
                </h3>
            </div>
            <div style="float: left;margin-left: 25%">
            	<c:choose>
            		<c:when test="${search!=null }">
            			<form action="toMonitorgroup_list" method="post">
            			<input type="text" name="pageNum" value="1" class="hidden"/> 
		            	<input type="text" name="search" value="${search}"/> 
		            	<input type="submit" value="搜索"> 
		            	</form>
            		</c:when>
            		<c:otherwise>
            			<form action="toMonitorgroup_list" method="post">
		            	<input type="text" name="search"/> 
		            	<input type="text" name="pageNum" value="1" class="hidden"/> 
		            	<input type="submit" value="搜索"> 
		            	</form>
            		</c:otherwise>
            	</c:choose>
            </div>
            <div>
                <div class="clearfix"></div>
            </div>
            <div class="bs-example widget-shadow"
                 data-example-id="contextual-table">
                 
			<form action="" method="post" id="myForm">
                 
                <table class="table">
                    <c:if test="${list.size()>0}">
                        <thead>
                        <tr>
                            <th class="manager-border">序号</th>
                            <th class="manager-border">监控器组名</th>
                            <th class="manager-border">监控器类型</th>
                            <th class="manager-border">操作</th>
                        </tr>
                        </thead>
                            <tbody>
                            <c:forEach items="${list}" var="item" varStatus="status">
                            <tr class="manager-bg input-lg">
                                <c:choose>
                                	<c:when test="${defaultGroupId == item.groupId}">
                                		<td class="manager-border2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${status.index+1}</td>
                                	</c:when>
                                	<c:otherwise>
                                		<td class="manager-border2"><input type="checkbox" name="groupId" value="${item.groupId}" />&nbsp;&nbsp;${status.index+1}</td>
                                	</c:otherwise>
                                </c:choose>
                                <td class="manager-border2">${item.groupName}</td>
                                <td class="manager-border2 manager-phone">${item.groupType}</td>
                                <td class="manager-border2">
                                	<a href="toMonitor_list?groupId=${item.groupId}&pageNum=1">
                                        <span class="label label-success">查看监控器</span></a>
                                    <a href="toMonitorgroup_update?id=${item.groupId}">
                                        <span class="label label-success">修改</span></a>
                                        
                                     <%-- <c:choose>
                                     	<c:when test="${pageNum==1 &&status.index==0}">
                                     	</c:when>
                                     	<c:otherwise>
                                     		<c:choose>
	                                			<c:when test="${status.index==0 && pageNum==1}">
	                                 				<a href="monitorgroup_delete?id=${item.groupId}&pageNum=${pageNum-1}"><span class="label label-danger">删除</span></a>
	                                 			</c:when>
			                                 	<c:otherwise>
			                                 		<a href="monitorgroup_delete?id=${item.groupId}&pageNum=${pageNum}"><span class="label label-danger">删除</span></a>
			                                 	</c:otherwise>
			                                 </c:choose>
                                     	</c:otherwise>
                                     </c:choose> --%>
	                                 
                                </td>
                            </tr>
                            </c:forEach>
                            </tbody>
                    </c:if>
                    <c:if test="${list.size()==0}">
                        <h3 align="center">没有监控器组数据</h3>
                    </c:if>
                </table>
				<button type="button" style="margin-left:20px;" id="button">批量删除</button>
			</form>
               
		<c:choose>
			<c:when test="${search!=null }">
				<nav class="cbp-spmenu-right bbbb">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${pageNum==1||totalPage==0}">
                            	<li class="disabled"><a>首页</a></li>
                                <li class="disabled">
                                    <a aria-label="Previous">
                                        <span aria-hidden="true">«</span>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
 								<li><a href="toMonitorgroup_list?pageNum=1&search=${search}">首页</a></li>
                                <li>
                                    <a href="toMonitorgroup_list?pageNum=${pageNum-1}&search=${search}"
                                       aria-label="Previous">
                                        <span aria-hidden="true">«</span>
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="k" begin="1" end="${totalPage}">
                            <c:choose>
                                <c:when test="${k==(pageNum-4) || k == (pageNum + 4)}">
                                    <li><a>…</a></li>
                                </c:when>
                                <c:when test="${k==pageNum}">
                                    <li class="active"><a>${k}<span class="sr-only"></span></a></li>
                                </c:when>
                                <c:when test="${k < pageNum - 4 || k > pageNum + 4}">

                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="toMonitorgroup_list?pageNum=${k}&search=${search}">${k}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${pageNum == totalPage || totalPage == 0}">
                                <li class="disabled">
                                    <a aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li class="disabled"><a>尾页</a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="toMonitorgroup_list?pageNum=${pageNum+1}&search=${search}" aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li>
                                	<a href="toMonitorgroup_list?pageNum=${totalPage}&search=${search}">尾页</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
			</c:when>
			<c:otherwise>
				<nav class="cbp-spmenu-right bbbb">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${pageNum==1||totalPage==0}">
                            	<li class="disabled"><a>首页</a></li>
                                <li class="disabled">
                                    <a aria-label="Previous">
                                        <span aria-hidden="true">«</span>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
 								<li><a href="toMonitorgroup_list?pageNum=1">首页</a></li>
                                <li>
                                    <a href="toMonitorgroup_list?pageNum=${pageNum-1}"
                                       aria-label="Previous">
                                        <span aria-hidden="true">«</span>
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="k" begin="1" end="${totalPage}">
                            <c:choose>
                                <c:when test="${k==(pageNum-4) || k == (pageNum + 4)}">
                                    <li><a>…</a></li>
                                </c:when>
                                <c:when test="${k==pageNum}">
                                    <li class="active"><a>${k}<span class="sr-only"></span></a></li>
                                </c:when>
                                <c:when test="${k < pageNum - 4 || k > pageNum + 4}">

                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="toMonitorgroup_list?pageNum=${k}">${k}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${pageNum == totalPage || totalPage == 0}">
                                <li class="disabled">
                                    <a aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li class="disabled"><a>尾页</a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="toMonitorgroup_list?pageNum=${pageNum+1}" aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li>
                                	<a href="toMonitorgroup_list?pageNum=${totalPage}">尾页</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
			</c:otherwise>
		</c:choose>	
		
		<div id="dialogBg"></div>
		<div id="dialog" class="animated">
			<div class="dialogTop">
				<a href="javascript:;" class="claseDialogBtn">关闭</a>
			</div>
			<div class="dialogContent">
				请选择删除方式
			</div>
			<button class="submitBtn" id="only">仅删除组</button>
			<button class="submitBtn" id="both">删除组及数据</button>
		</div>
                



            </div>
        </div>
        <div class="row calender widget-shadow" style="display: none">
            <h4 class="title">Calender</h4>
            <div class="cal1"></div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!--footer-->
<div class="footer">
    <p>
        Copyright &copy; yongxin.com版权所有
    </p>
</div>
<!--//footer-->
</div>

<!-- Classie -->
<script src="js/classie.js"></script>
<script>
	var w,h;
	function getSrceenWH(){
		w = $(window).width();
		h = $(window).height();
		$('#dialogBg').width(w).height(h);
	}

    var menuLeft = document.getElementById('cbp-spmenu-s1'),
        showLeftPush = document.getElementById('showLeftPush'),
        body = document.body;

    showLeftPush.onclick = function () {
        classie.toggle(this, 'active');
        classie.toggle(body, 'cbp-spmenu-push-toright');
        classie.toggle(menuLeft, 'cbp-spmenu-open');
        disableOther('showLeftPush');
    };

    function disableOther(button) {
        if (button !== 'showLeftPush') {
            classie.toggle(showLeftPush, 'disabled');
        }
    }
    
    $("#button").click(function(){
    	if($("input:checkbox[name='groupId']:checked").length==0){
    		alert('请选择删除对象');
    	}else{
    		$('#dialogBg').fadeIn(300);
			$('#dialog').removeAttr('class').addClass('animated bounceInDown').fadeIn();
    	}
    });
    
	$("#only").click(function(){
		var newUrl = 'monitorgroup_deleteOnly';
		$("#myForm").attr('action',newUrl);
		$("#myForm").submit();
	});
    
    $("#both").click(function(){
		var newUrl = 'monitorgroup_deleteBoth';
		$("#myForm").attr('action',newUrl);
		$("#myForm").submit();
	});
    
    $('.claseDialogBtn').click(function(){
			$('#dialogBg').fadeOut(300,function(){
			$('#dialog').addClass('bounceOutUp').fadeOut();
		});
	});
    
</script>
<!--scrolling js-->
<script src="js/jquery.nicescroll.js"></script>
<script src="js/scripts.js"></script>
<!--//scrolling js-->
<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.js"></script>
</body>
</html>