<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.HashMap,com.monitoring.entity.Manager" pageEncoding="utf-8" %>
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
	<link href="css/index2.css" rel="stylesheet">
    <script src="js/video.js"></script>
	<link href="css/video-js.css" rel="stylesheet" type="text/css" />

</head>
<body class="cbp-spmenu-push">
<!-- head content start-->
<jsp:include page="user_header.jsp"/>
<!-- head content end-->

<!-- main content start-->
<div id="page-wrapper">
    <div class="main-page">

        <h3 class="title1">视频列表:</h3>
        <div class="grid_3 grid_5 widget-shadow">
        <div class="form-title">
            <h4>视频查询 :</h4>
            
            <c:choose>
            	<c:when test="${startTime!=null && endTime!=null}">
            		<form action="toVideo_list" method="post">
		            	<input type="text" name="monitoringId" class="hidden" value="${monitoringId}"/>
		            	开始时间:<input type="text" name="startTime" value="${startTime}"/>
		            	结束时间:<input type="text" name="endTime" value="${endTime}"/>
		            	<input class="hidden" name="pageNum" value="1" />
		            	<input type="submit" value="查询">
            		</form>
            	</c:when>
            	<c:otherwise>
            		<form action="toVideo_list" method="post">
		            	<input type="text" name="monitoringId" class="hidden" value="${monitoringId}"/>
		            	开始时间:<input type="text" name="startTime"/>
		            	结束时间:<input type="text" name="endTime"/>
		            	<input class="hidden" name="pageNum" value="1" />
		            	<input type="submit" value="查询">
            		</form>
            	</c:otherwise>
            </c:choose>
            
        </div>
            <div>
                <div class="clearfix"></div>
            </div>
            <div class="bs-example widget-shadow"
                 data-example-id="contextual-table">
                 <div class="section_class">
                 <c:choose>
                 	<c:when test="${videoList.size()>0}">
                            <c:forEach items="${videoList}" var="item" varStatus="status">
								<section class="section_class1">
									<a href="toVideo_play?id=${item.id}"><img src="images/touming.png"></a>
									<div class="section_class1_div1">
										${item.videoName}
					 				</div>
									<video id="${item.id}" class="video-js" preload="auto" width="300" height="260" data-setup="{}" style="margin: 0 atuo">
										<source src="${item.videoInformation}" type="video/mp4">
									</video>
								</section>
							</c:forEach>
                    </c:when>
                 	<c:otherwise>
                 		<h3 align="center">没有历史视频</h3>
                 	</c:otherwise>
                 </c:choose>
				</div>

			<c:choose>
				<c:when test="${startTime!=null && endTime!=null}">
				
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
 								<li><a href="toVideo_list?pageNum=1&monitoringId=${monitoringId}&startTime=${startTime}&endTime=${endTime}">首页</a></li>
                                <li>
                                    <a href="toVideo_list?pageNum=${pageNum-1}&monitoringId=${monitoringId}&startTime=${startTime}&endTime=${endTime}"
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
                                        <a href="toVideo_list?pageNum=${k}&monitoringId=${monitoringId}&startTime=${startTime}&endTime=${endTime}">${k}</a>
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
                                    <a href="toVideo_list?pageNum=${pageNum+1}&monitoringId=${monitoringId}&startTime=${startTime}&endTime=${endTime}" aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li>
                                	<a href="toVideo_list?pageNum=${totalPage}&monitoringId=${monitoringId}&startTime=${startTime}&endTime=${endTime}">尾页</a>
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
 								<li><a href="toVideo_list?pageNum=1&monitoringId=${monitoringId}">首页</a></li>
                                <li>
                                    <a href="toVideo_list?pageNum=${pageNum-1}&monitoringId=${monitoringId}"
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
                                        <a href="toVideo_list?pageNum=${k}&monitoringId=${monitoringId}">${k}</a>
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
                                    <a href="toVideo_list?pageNum=${pageNum+1}&monitoringId=${monitoringId}" aria-label="Next">
                                        <span aria-hidden="true">»</span>
                                    </a>
                                </li>
                                <li>
                                	<a href="toVideo_list?pageNum=${totalPage}&monitoringId=${monitoringId}">尾页</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
				</c:otherwise>
			</c:choose>



                



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