<%@ page language="java" import="java.util.HashMap" pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.monitoring.util.CyptoUtils,com.monitoring.entity.Manager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%
    Manager manager = (Manager) request.getSession().getAttribute("MANAGER");
    int managerId = manager.getId();
%>
<!-- head content start-->
<jsp:include page="header.jsp"/>
<!-- head content end-->

<!-- main content start-->
<div id="page-wrapper">
    <div class="main-page">
        <div class="tables">
            <div class="bs-example widget-shadow"
                 data-example-id="hoverable-table">
                <div class="row">
                    <div class="form-title">
                        <h4>修改${space.user.userName}的空间 :</h4>
                    </div>
                    <div class="form-body">
                        <form action="updateSpace" method="post" enctype="multipart/form-data"
                              class="bs-example bs-example-form" role="form" name="touxiangform">
                            <input class="hidden" value="${space.id}" name="spaceId">
                            <input class="hidden" value="${pageNum}" name="pageNum">
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>空间名称：</label>
                                <input type="text" class="form-control" maxlength="16"
                                       value="${space.spaceName}"
                                       id="spaceName" name="spaceName" placeholder="空间名称" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>空间大小（g）：</label>
                                <input type="text" class="form-control" maxlength="16"
                                       value="${space.spaceSize}"
                                       onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
                                       id="spaceSize" name="spaceSize" placeholder="空间大小" required>
                            </div>
                            <div class=" form-group-lg">
                                <label><i class="fa fa-user fa-fw"></i>已使用大小（g）：</label>
                                <input type="text" class="form-control" maxlength="10"
                                       value="${space.spaceSize}"
                                       onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
                                       id="useSize" name="useSize" placeholder="已使用大小" required>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn bottom-up">提交</button>
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