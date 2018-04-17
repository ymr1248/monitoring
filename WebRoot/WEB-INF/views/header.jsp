<%@ page import="java.util.HashMap" %>
<%@ page import="com.monitoring.util.CyptoUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                            <li><a href="userGradeList?pageNum=1">用户等级管理</a></li>
                            <li><a href="userList?pageNum=1">用户信息管理</a></li>
                            <li><a href="userRegisterList?pageNum=1">待审核列表</a></li>
                        </ul>
                    </li>

                    <li><a href="#"><i class="fa fa-users nav_icon"></i>空间管理 <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                            <li><a href="toSpaceUseListJsp?pageNum=1">管理空间使用情况</a></li>
                            <li><a href="toSpaceOrderListJsp?pageNum=1">查看空间充值订单</a></li>
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
                    <h1>管理员</h1> <span>管理面板</span>
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
