<%@page import="com.monitoring.entity.Manager"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.HashMap" pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.monitoring.util.CyptoUtils" %>
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


</head>
<body class="cbp-spmenu-push">
<!-- head content start-->
<jsp:include page="user_header.jsp"/>
<!-- head content end-->
</div><!-- main content start-->
<div id="page-wrapper">
    <div class="main-page">
        <div class="tables">
            <div class="bs-example widget-shadow"
                 data-example-id="hoverable-table">

                <div class="row">
                    <div class="form-title">
                        <h4>添加视频 :</h4>
                    </div>
                    <div class="form-body">
                        <form action="video_add" method="post" enctype="multipart/form-data"
                              class="bs-example bs-example-form" role="form" name="touxiangform">
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>视频名称：</label>
                                <input type="text" class="form-control" maxlength="16"
                                       id="videoName" name="videoName" placeholder="视频名称" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>视频大小：</label>
                                <input type="text" class="form-control" maxlength="100"
                                       id="videoSize" name="videoSize" placeholder="视频大小" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>视频地址：</label>
                                <input type="text" class="form-control" maxlength="200"
                                       id="videoInformation" name="videoInformation" placeholder="视频地址" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>开始时间：</label>
                                <input type="text" class="form-control" maxlength="100"
                                       id="videoStart" name="videoStart1" placeholder="开始时间" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>结束时间：</label>
                                <input type="text" class="form-control" maxlength="100"
                                       id="videoEnd" name="videoEnd1" placeholder="结束时间" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>用户名称：</label>
                                <select class="form-control" id="userId" name="userId1">
                                <c:choose>
                                	<c:when test="${userList.size()<1 }">
                                		<option value="0">没有用户列表</option>
                                	</c:when>
                                	<c:otherwise>
                                		<c:forEach items="${userList }" var="item">
                                			<option value="${item.id }">${item.userName }</option>
                               			</c:forEach>
                                	</c:otherwise>
                                </c:choose>
                                </select>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>机器名称：</label>
                                <select class="form-control" id="MonitoringId" name="MonitoringId1">
                               	<c:choose>
                                	<c:when test="${monitoringList.size()<1 }">
                                		<option value="0">没有机器列表</option>
                                	</c:when>
                                	<c:otherwise>
                                		<c:forEach items="${monitoringList}" var="item">
                                			<option value="${item.id }">${item.monitorName }</option>
                               			</c:forEach>
                                	</c:otherwise>
                                </c:choose>
                                </select>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>视频时长：</label>
                                <input type="text" class="form-control" maxlength="100"
                                       id="videoTime" name="videoTime" placeholder="视频时长" required>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn bottom-up">提交</button>
                            </div>
                            <div class="form-group">
                                <a class="btn btn-primary" href="javascript:history.back();">取消</a>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="clearfix"></div>
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