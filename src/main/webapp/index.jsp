<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>APP发布系统</title>
    <meta name="Keywords" content="app发布系统" />
    <meta name="description" content="成都模块科技app发布系统" />
    <script src="common/js/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="apps_res/homepage/css/index.css" />
    <script type="text/javascript" src="apps_res/homepage/js/index.js"></script>
</head>
<body>
<div class="head">
    <c:if test="${admin == null}">
        <a class="login-text-span" href="<%=request.getContextPath()%>/login.jsp">
            <div class="login-wrap-div">
                <div class="login-img-div">
                    <img class="login-img" src="common/images/login-btn.png" />
                </div>
                <div class="login-text-div">
                    登录${admin}
                </div>
            </div>
        </a>
    </c:if>
    <c:if test="${admin != null}">
        <a class="login-text-span" href="<%=request.getContextPath()%>/customerAction?method=queryAllCustAndApp">
            <div class="login-wrap-div">
                <div class="login-img-div">
                    <img class="login-img" src="common/images/user.png" />
                </div>
                <div class="login-text-div">
                    客户管理
                </div>
            </div>
        </a>
    </c:if>
    <!--公司名-->
    <div class="account-name-div">
        <span>
            APP发布系统
        </span>
    </div>
</div>
<div id="content">
    <div id="live_box">
        <dl>
            <dd>
                <ul id="custer-ul">
                </ul>
            </dd>
        </dl>
    </div>
</div>
</body>

</html>