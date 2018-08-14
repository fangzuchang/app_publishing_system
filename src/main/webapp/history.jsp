<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${historyList == null}">
        <c:redirect url="/index.jsp"></c:redirect>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>历史版本</title>
            <link href="apps_res/download/css/his_down.css" rel="stylesheet" type="text/css">
            <style type="text/css">
                .version_dt {
                    font-size: 18px;
                }
                
                .des_dd {
                    font-size: 18px;
                }
                
                .main_cen {
                    height: auto;
                    margin-bottom: 30px;
                }
            </style>
        
        </head>
        <body>
        
        <div class="page" id="show_download">
            <div class="header">
                <a href="javascript:void(0)" class="logo">
                    <img src="<c:url value="${historyList[0].logoUrl}"/>" alt="">
                </a>
                <span class="title">
			软件下载
		</span>
            </div>
            <div class="main">
                <div class="main_cen">
                    <div id="version_div">
                        <p id="version_a">
                            历史版本：
                        </p>
                    </div>
                    <div style="width: 220px;height:auto;text-align: left;margin: 0 auto;">
                        <dl>
                            <c:forEach items="${historyList}" var="history">
                                <dt class="version_dt">${history.appVersion}</dt>
                                <dd class="des_dd" style="word-break:break-all;">描述:${history.appDescription}</dd>
                                <dd class="more_dd">
                                    <a class="more_a"
                                       href="<c:url value="/appAction?method=hisToDown&appVersion=${history.appVersion}&custId=${history.custId}"/>"
                                       style="text-align: right;width:200px;height: 35px;margin: 0 auto;">更多</a>
                                </dd>
                                <hr />
                            </c:forEach>
                        </dl>
                    </div>
                
                </div>
            </div>
        </div>
        <div style="display:none;" id="show_ios">
            <img src="apps_res/download/images/ios.gif" style="width:100%;">
        </div>
        </body>
        </html>
    </c:otherwise>
</c:choose>