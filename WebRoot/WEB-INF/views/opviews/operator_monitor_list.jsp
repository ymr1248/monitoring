<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.HashMap,com.monitoring.entity.Manager" pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.monitoring.util.CyptoUtils" %>
<%@ page import="com.monitoring.entity.User" %>
<%@ page import="com.monitoring.entity.Operator" %>
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
<%
    Operator operator = (Operator) request.getSession().getAttribute("OPERATOR");
    String operatorId = CyptoUtils.encode(String.valueOf(operator.getOperatorId()));
%>
<!-- head content start-->
<jsp:include page="op_header.jsp"/>
<!-- head content end-->

<!-- main content start-->
<div id="page-wrapper">
    <div class="main-page">

        <h3 class="title1">监控器列表:</h3>
        <div class="grid_3 grid_5 widget-shadow">
            <div>
                <h3>
                    <a href="toOperatorAddMonitor?operatorId=<%=operatorId%>"><span class="label label-success">添加监控器</span></a>
                </h3>
            </div>
            <div>
                <div class="clearfix"></div>
            </div>
            <div class="bs-example widget-shadow"
                 data-example-id="contextual-table">
                <table class="table">
                    <c:if test="${list!=null}">
                        <thead>
                        <tr>
                            <th class="manager-border">序号</th>
                            <th class="manager-border">监控器名称</th>
                            <th class="manager-border">监控器组</th>
                            <th class="manager-border">监控器状态</th>
                            <th class="manager-border">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="item" varStatus="status">
                            <tr class="manager-bg input-lg">
                                <td class="manager-border2">${status.index+1}</td>
                                <td class="manager-border2">${item.monitorName}</td>
                                <td class="manager-border2 manager-phone">${item.monitorGroup.groupName}</td>
                                <td class="manager-border2 manager-phone">${item.monitorStatus}</td>
                                <td class="manager-border2">
                                    <a href="showMonitorInfo?monitorId=${item.id}&&userId=<%=operatorId%>&pageNum=${pageNum}">
                                        <span class="label label-success">查看详情</span></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </c:if>
                    <c:if test="${list==null}">
                        <h3 align="center">没有机器组数据</h3>
                    </c:if>
                </table>


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
                                <li><a href="toOperatorSeeMonitor?pageNum=1&operatorId=<%=operatorId%>">首页</a></li>
                                <li>
                                    <a href="toOperatorSeeMonitor?pageNum=${pageNum-1}&operatorId=<%=operatorId%>"
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
                                        <a href="toOperatorSeeMonitor?pageNum=${k}&operatorId=<%=operatorId%>">${k}</a>
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
                                    <a href="toOperatorSeeMonitor?pageNum=${pageNum+1}&operatorId=<%=operatorId%>" aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="toOperatorSeeMonitor?pageNum=${totalPage}&operatorId=<%=operatorId%>">尾页</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>




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