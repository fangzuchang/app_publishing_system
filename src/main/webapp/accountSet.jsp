<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${admin == null}">
        <c:redirect url="/login.jsp"></c:redirect>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>账户设置</title>
            <script type="text/javascript" src="common/js/jquery.min.js">
            </script>
            <link rel="stylesheet" type="text/css" href="apps_res/accountset/css/accountset.css" />
            <script type="text/javascript" src="apps_res/accountset/js/accountset.js"></script>
        </head>
        <body>
        <div class="head">
            <div class="btn-class">
                <div class="btn-item-class">
                    <a href="<%=request.getContextPath()%>/adminAction?method=logout">
                        <div class="btn-div">
                            <div class="img-div">
                                <img class="home-img" src="common/images/out.png" />
                            </div>
                            <div class="text-div">退出</div>
                        </div>
                    </a>
                </div>
                <div class="btn-item-class">
                    <a href="<%=request.getContextPath()%>/customerAction?method=queryAllCustAndApp">
                        <div class="btn-div">
                            <div class="img-div">
                                <img class="home-img" src="common/images/user.png" />
                            </div>
                            <div class="text-div">客户管理</div>
                        </div>
                    </a>
                </div>
                <div class="btn-item-class">
                    <a href="<%=request.getContextPath()%>/index.jsp">
                        <div class="btn-div">
                            <div class="img-div">
                                <img class="home-img" src="common/images/home.png" />
                            </div>
                            <div class="text-div">首页</div>
                        </div>
                    </a>
                </div>
            
            
            </div>
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
                    账户设置
                </div>
                <div class="name">
                    <div class="name-img-wrap">
                        <div class="name-img">
                        </div>
                    </div>
                    <div class="name-input-div">
                        <input placeholder="登录名" type="text" name="username" id="username" value=""
                               onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" />
                    </div>
                </div>
                <div class="pass">
                    <div class="pass-img-wrap">
                        <div class="pass-img">
                        </div>
                    </div>
                    <div class="pass-input-div">
                        <input placeholder="原密码" type="password" name="oldpassword" id="oldpassword" value="" />
                    </div>
                </div>
                <div class="pass">
                    <div class="pass-img-wrap">
                        <div class="pass-img">
                        </div>
                    </div>
                    <div class="pass-input-div">
                        <input placeholder="新密码" type="password" name="newpassword" id="newpassword" value="" />
                    </div>
                </div>
                <div class="pass">
                    <div class="pass-img-wrap">
                        <div class="pass-img">
                        </div>
                    </div>
                    <div class="pass-input-div">
                        <input placeholder="确认新密码" type="password" name="newpassword_" id="newpassword_" value="" />
                    </div>
                </div>
                <div class="tips">
                    <span id="tips"></span>
                </div>
                <div class="submit">
                    确&nbsp;&nbsp;认
                </div>
            </form>
        </div>
        </body>
        </html>
    </c:otherwise>
</c:choose>