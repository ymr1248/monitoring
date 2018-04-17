<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>login</title>
    <link rel="stylesheet" href="css/jq22.css"><!-- 加特效，可我会像 -->
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/font-awesome.css">
    <script src="js/jquery.min.js"></script>
</head>
<body class="bgcolor">
<div  id="login">
    <div>
        <h1 class="head-bb">欢迎使用V380监控系统</h1>
        <div class="login">
            <form action="userLogin" method="post" class="container offset1 loginform">

                <div class="pad">
                    <div class="control-group">
                        <div class="controls">
                            <label for="userAccount" class="control-label fa fa-user"></label>
                            <input id="userAccount" type="text" name="userAccount" maxlength="16" placeholder="账户" tabindex="1" required autofocus="autofocus" class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="password" class="control-label fa fa-heart"></label>
                            <input id="password" type="password" name="password"maxlength="16" placeholder="密码" tabindex="2" class="form-control input-medium" required>
                        </div>
                    </div>
                </div>
                <div class="form-actions"><a href="#" tabindex="5" class="btn pull-left btn-link text-muted">忘记密码？</a>
                    <a href="register" tabindex="6" class="btn btn-link text-muted">注册</a>
                    <button type="submit" id="loginbutton" tabindex="4" class="btn btn-primary">登录</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function() {
            $('#username').focusout(function(){
                var loginName = $.trim($('#username').val());
                if(loginName == ''){
                    //$("#username").empty().append('*账户名不能为空').show();
//                    alert("*账户名不能为空");
                    return false;
                }
            });
            $('#password').focusout(function(){
                var loginPsw = $.trim($('#password').val());
                if(loginPsw == ''){
                    //$("#password").empty().append('*密码不能为空').show();
//                    alert("*密码不能为空");
                    return false;
                }
            });
        });
    </script>
</div>
</body>
</html>