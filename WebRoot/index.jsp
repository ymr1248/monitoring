<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords"
          content="Novus Admin Panel Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
SmartPhone Compatible web template, free WebDesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
    <script type="application/x-javascript">addEventListener("load", function() {
        setTimeout(hideURLbar, 0);
    }, false);
    function hideURLbar() {
        window.scrollTo(0, 1);
    }
    </script>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
    <!-- Custom CSS -->
    <link href="css/style.css" rel='stylesheet' type='text/css' />
    <!-- font CSS -->
    <!-- font-awesome icons -->
    <link href="css/font-awesome.css" rel="stylesheet">
    <!-- //font-awesome icons -->
    <!-- js-->
    <!-- num -->
    <link href="css/num.css" rel="stylesheet"/>
    <script src="js/jquery-1.11.1.min.js"></script>

    <!--<script src="js/jquery-1.11.1.min.js"></script>-->
    <script type="text/javascript">
        $(function() {
            var h = $(window).height();
            $('body').height(h);
            $('.mianBox').height(h);
            centerWindow(".tipInfo");
        });

        //2.将盒子方法放入这个方，方便法统一调用
        function centerWindow(a) {
            center(a);
            //自适应窗口
            $(window).bind('scroll resize',
                    function() {
                        center(a);
                    });
        }

        //1.居中方法，传入需要剧中的标签
        function center(a) {
            var wWidth = $(window).width();
            var wHeight = $(window).height();
            var boxWidth = $(a).width();
            var boxHeight = $(a).height();
            var scrollTop = $(window).scrollTop();
            var scrollLeft = $(window).scrollLeft();
            var top = scrollTop + (wHeight - boxHeight) / 2;
            var left = scrollLeft + (wWidth - boxWidth) / 2;
            $(a).css({
                "top": top,
                "left": left
            });
        }
    </script>
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
    <link rel="stylesheet" href="css/clndr.css" type="text/css" />
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
    Cookie cookie=null;
    Cookie[] cookies=null;
    cookies=request.getCookies();
    String userId = "";
    String account = "";
    String username = "";
    String userType = "";
    String userTypeName = "";
	boolean isLogin = false;
	if (cookies != null) {
		for (int i = 0; i < cookies.length - 1; i++) {
			System.out.println("cookies==="+cookies.length+" "+cookies[i].getValue());
			cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				userId = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
			if (cookie.getName().equals("account")) {
				account = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
			if (cookie.getName().equals("username")) {
				username = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
			if (cookie.getName().equals("userType")) {
				userType = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
			if (cookie.getName().equals("userTypeName")) {
				userTypeName = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
		}
	}
//	if (cookies.length<=3||cookies.length==5||cookies.length==6||cookies.length==7||cookies.length==8){
//		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("map");
//		if (map != null) {
//			System.out.println(map.toString());
//			userId = map.get("userId");
//			account = map.get("userAccount");
//			username = map.get("userName");
//			userType = map.get("userType");
//			userTypeName = map.get("userTypeName");
//		}
//	}
	if (account.equals("")||account==null){
//    if (false){
%>
<div class="mianBox">
    <img src="images/yx403.png" alt=""  class="num403"/>
    <div class="tipInfo tiao">
        <div class="in">
            <div class="textThis">
                <h2>你没有权限访问此页面!请先登录<a href="/monitoring/login"><span class="label label-success">登录</span></a></h2>
                <!-- <h2>You do not have permission to access this content</h2>
                <p><span>请<a href="/detectionSystem">登录</a></span></p>-->
            </div>
        </div>
    </div>
</div>
<%
}else {
%>
<div class="main-content">
    <!--left-fixed -navigation-->
    <div class=" sidebar" role="navigation">
        <div class="navbar-collapse">
            <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left"
                 id="cbp-spmenu-s1">
                <ul class="nav" id="side-menu">
                    <li><a href="index" class="active"><i
                            class="fa fa-home nav_icon"></i>首页</a></li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>用户管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="#">添加用户</a></li>
                            <li><a href="#">用户信息管理</a></li>
                        </ul></li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>监控器管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="#">供应商信息管理</a></li>
                        </ul></li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>空间管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toAddSpaceJsp">添加空间</a></li>
                            <li><a href="toSpaceUseListJsp?pageNum=1">管理空间使用情况</a></li>
                        </ul></li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>视频管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="#">添加订单</a></li>
                        </ul></li>
                </ul>
                <!-- //sidebar-collapse -->
            </nav>
        </div>
    </div>
    <!--left-fixed -navigation-->
    <!-- header-starts -->
     <div class="sticky-header header-section ">
        <div class="header-left">
            <!--toggle button start-->
            <button id="showLeftPush">
                <i class="fa fa-bars"></i>
            </button>
            <!--toggle button end-->
            <!--logo -->
            <div class="logo">
                <a href="index.html">
                    <h1>管理员</h1> <span>管理面板</span>
                </a>
            </div>
            <!--//logo-->
            <!--search-box
            <div class="search-box">
                <form class="input">
                    <input class="sb-search-input input__field--madoka"
                        placeholder="查询..." type="search" id="input-31" /> <label
                        class="input__label" for="input-31"> <svg class="graphic"
                            width="100%" height="100%" viewBox="0 0 404 77"
                            preserveAspectRatio="none">
                            <path d="m0,0l404,0l0,77l-404,0l0,-77z" />
                        </svg>
                    </label>
                </form>
            </div>-->
            <!--//end-search-box-->
            <div class="clearfix"></div>
        </div>
        <div class="header-right">

            <div class="profile_details">
                <ul>
                    <li class="dropdown profile_details_drop">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <div class="profile_img">
									<span class="prfil-img"><img src="images/mm.jpg" alt="">
									</span>
                                <div class="user-name">
                                    <p>管理员</p>
                                    <span>类型：管理员</span>
                                </div>
                                <i class="fa fa-angle-down lnr"></i> <i
                                    class="fa fa-angle-up lnr"></i>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                        <ul class="dropdown-menu drp-mnu">
                            <li><a href="#"><i class="fa fa-cog"></i> 设置</a></li>
                            <li><a href="#"><i class="fa fa-user"></i> 个人信息</a></li>
                            <li><a href="#"><i class="fa fa-sign-out"></i> 退出</a></li>
                        </ul></li>
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="clearfix"></div>
    </div>
    <!-- //header-ends -->
    <div class="copyrights">
    </div>
    </div><!-- main content start-->
    <div id="page-wrapper">
        <div class="main-page">

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
<%
    }
%>
<!-- Classie -->
<script src="js/classie.js"></script>
<script>
    var menuLeft = document.getElementById('cbp-spmenu-s1'),
            showLeftPush = document.getElementById('showLeftPush'),
            body = document.body;

    showLeftPush.onclick = function() {
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
<script src="js/bootstrap.js"> </script>

</body>
</html>