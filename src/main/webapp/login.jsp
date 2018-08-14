<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>管理员登录</title>
    <script type="text/javascript" src="common/js/jquery.min.js"></script>
    <link rel="stylesheet" href="apps_res/login/css/login.css">
    <script type="text/javascript" src="apps_res/login/js/login.js"></script>
</head>
<body>
<div class="head">
    <a href="<%=request.getContextPath()%>/index.jsp">
        <div class="home-wrap-div">
            <div class="home-img-div">
                <img class="home-img" src="common/images/home.png" />
            </div>
            <div class="login-text-div">
                首页
            </div>
        </div>
    </a>
    <!--公司名-->
    <div class="account-name-div">
        <span>
            APP发布系统
        </span>
    </div>
</div>
<div class="form-div-class">
    <form id="login-form">
        <input type="hidden" name="method" value="login" />
        <div class="title">
            管理员登录
        </div>
        <div class="name">
            <%--<div class="name-img-wrap">
                <div class="name-img">
                </div>
            </div>--%>
            <div class="name-input-div">
                <input placeholder="用户名" type="text" name="username" id="username" value=""
                       onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" />
            </div>
        </div>
        <div class="pass">
            <%--<div class="pass-img-wrap">
                <div class="pass-img">
                </div>
            </div>--%>
            <div class="pass-input-div">
                <input placeholder="密码" type="password" name="password" id="password" value="" />
            </div>
        </div>
        <div class="captcha">
            <div class="captcha-input-div">
                <div style="float: left">
                    <input placeholder="验证码" type="text" name="captcha" id="captcha" value="" /></div>
                <div id="captcha-img-div" style="display: inline-block;float: right">
                    <img class="captcha-img" alt="" src="captchaAction"
                         style="margin-top:6px;width: 120px;height: 30px;" onclick="refreshCode(this);">
                </div>
            </div>
        </div>
        <div class="tips">
            <span id="tips"></span>
        </div>
        <div class="submit">
            登&nbsp;&nbsp;录
        </div>
    </form>
</div>
</body>
</html>