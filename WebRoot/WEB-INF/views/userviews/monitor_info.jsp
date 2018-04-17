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
     <link href="css/index.css" rel="stylesheet">
     <script src="js/video.js"></script>
	<link href="css/video-js.css" rel="stylesheet" type="text/css" /> 
    <!--//Metis Menu -->


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

        <h3 class="title1">监控器详情:</h3>
        <div class="grid_3 grid_5 widget-shadow">
            <div>
                <div class="clearfix"></div>
            </div>
            <div class="bs-example widget-shadow"
                 data-example-id="contextual-table">
                 <div class="section_class">
                 	<div class="section_class1_div1">
						${monitoring.monitorName}
					</div>
					<div style="margin: 0 auto; width: 900px;height: 650px;">
					<video id="123" class="video-js" controls autoplay
					preload="auto" width="900" height="650" data-setup="{}" style="margin: 0 atuo">
						<source src="${monitoring.monitorIp}" type="rtmp/flv">
					</video>
                 	</div>
				</div>
				<div style="margin: 0 auto; width: 900px;height: 100px;font-size: 30px;text-align: center;">
					<a href="toVideo_list?pageNum=1&monitoringId=${monitoring.id}"><span class="label label-success">查看视频</span></a>
					<a href=""><span class="label label-success">截图</span></a>
					<a href="toUpdateMonitor?monitorId=${monitoring.id}"><span class="label label-success">修改信息</span></a>
					<a href="deleteMonitor?pageNum=1&monitorId=${monitoring.id}&groupId=${monitoring.monitorGroup.groupId}"><span class="label label-danger">删除</span></a>
				</div>
				<div style="margin: 0 auto; width: 900px;height: 400px;font-size: 24px;text-align: center;">
					           <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>监控器名称：</label>
                                    <label>${monitoring.monitorName}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>监控器Ip地址：</label>
                                    <label>${monitoring.monitorIp}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>安装位置：</label>
                                    <label>${monitoring.monitorLocation}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>安装时间：</label>
                                    <label>${monitoring.locatedTime}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>监控器型号：</label>
                                    <label>${monitoring.monitorType}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>监控器状态：</label>
                                    <label>${monitoring.monitorStatus}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>监控器描述：</label>
                                    <label>${monitoring.monitorInfo}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>视频保存位置：</label>
                                    <label>${monitoring.storageLocation}</label>
                                </div>
                                <div class=" form-group-lg">
                                    <label><i class="fa fa-user fa-fw"></i>所属分组：</label>
                                    <label>${monitoring.monitorGroup.groupName}</label>
                                </div>
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

<!-- Classie -->
<script src="js/classie.js"></script>
<script>
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
</script>
<!--scrolling js-->
<script src="js/jquery.nicescroll.js"></script>
<script src="js/scripts.js"></script>
<!--//scrolling js-->
<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.js"></script>
</body>
</html>