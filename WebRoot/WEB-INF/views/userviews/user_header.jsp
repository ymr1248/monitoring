<%@ page import="java.util.HashMap" %>
<%@ page import="com.monitoring.util.CyptoUtils" %>
<%@ page import="com.monitoring.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) request.getSession().getAttribute("USER");
    String userId = CyptoUtils.encode(String.valueOf(user.getId()));
%>
<div class="main-content">
    <!--left-fixed -navigation-->
    <div class=" sidebar" role="navigation">
        <div class="navbar-collapse">
            <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left"
                 id="cbp-spmenu-s1">
                <ul class="nav" id="side-menu">
                    <li><a href="userIndex" class="active"><i
                            class="fa fa-home nav_icon"></i>首页</a></li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>监控器管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toMonitorgroup_list?pageNum=1">监控器组</a></li>
                            <%-- <li><a href="toShowMonitorJSP?pageNum=1&&userId=<%=userId%>">查看监控器</a></li> --%>
                        </ul>
                    </li>
                    <%-- <li><a href="#"><i class="fa fa-users nav_icon"></i>视频管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toVideo_add">添加视频</a></li>
                            <li><a href="toVideo_list?pageNum=1">视频列表</a></li>
                        </ul>
                    </li> --%>
                    
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>操作员管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <%--<li><a href="toAddOperator?userId=<%=userId%>">添加操作员</a></li>--%>
                            <li><a href="userOperatorList?pageNum=1&userId=<%=userId%>">操作员列表</a></li>
                        </ul>
                    </li>
                    <li><a href="#"><i class="fa fa-users nav_icon"></i>个人信息管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toUserSelfUpdate">修改个人信息</a></li>
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
                    <h1>用户</h1> <span>管理面板</span>
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
                                    <p>用户</p>
                                    <span>类型：用户</span>
                                </div>
                                <i class="fa fa-angle-down lnr"></i> <i
                                    class="fa fa-angle-up lnr"></i>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                        <ul class="dropdown-menu drp-mnu">
                            <li><a href="#"><i class="fa fa-cog"></i> 设置</a></li>
                            <li><a href="userToCheckInfo"><i class="fa fa-user"></i> 个人信息</a></li>
                            <li><a href="toUpdateMyPsw"><i class="fa fa-user"></i> 修改密码</a></li>
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
