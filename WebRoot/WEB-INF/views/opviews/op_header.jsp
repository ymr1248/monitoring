<%@ page import="java.util.HashMap" %>
<%@ page import="com.monitoring.util.CyptoUtils" %>
<%@ page import="com.monitoring.entity.User" %>
<%@ page import="com.monitoring.entity.Operator" %>
<%@ page import="com.monitoring.entity.Permission" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Operator operator = (Operator) request.getSession().getAttribute("OPERATOR");
    int operatorId = operator.getOperatorId();
    Permission permission = operator.getPermission();
%>
<div class="main-content">
    <!--left-fixed -navigation-->
    <div class=" sidebar" role="navigation">
        <div class="navbar-collapse">
            <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left"
                 id="cbp-spmenu-s1">
                <ul class="nav" id="side-menu">
                    <li><a href="opIndex" class="active"><i
                            class="fa fa-home nav_icon"></i>首页</a></li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>监控器管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toOperator_monitorGroup_list?pageNum=1&operatorId=<%=operatorId%>">查看监控器</a></li>
                        </ul>
                    </li>
                    <!-- <li><a href="#"><i class="fa fa-users nav_icon"></i>视频管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toShowHistoryVideo?pageNum=1">视频列表</a></li>
                        </ul>
                    </li> -->
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>权限管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="higherOperatorManageLower?pageNum=1">低级权限列表</a></li>
                            <li><a href="manageApplyPermission?pageNum=1">申请权限列表</a></li>
                            <li><a href="toApplyForPermission?pageNum=1">申请权限</a></li>
                        </ul>
                    </li>
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
                <a>
                    <h1>操作员</h1> <span>管理面板</span>
                </a>
            </div>
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
                                    <p>操作员</p>
                                    <span>类型：操作员</span>
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
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="clearfix"></div>
    </div>
    <!-- //header-ends -->
    <div class="copyrights">
    </div>
</div>
