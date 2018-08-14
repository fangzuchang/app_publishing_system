<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${download == null}">
        <c:redirect url="/index.jsp"></c:redirect>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>软件下载</title>
            <link href="apps_res/download/css/his_down.css" rel="stylesheet" type="text/css">
            <script type="text/javascript">
                function download() {
                    var u = navigator.userAgent,
                        app = navigator.appVersion;
                    var versions = { //移动终端浏览器版本信息
                        trident: u.indexOf('Trident') > -1, //IE内核
                        presto: u.indexOf('Presto') > -1, //opera内核
                        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                        mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                        iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                        iPad: u.indexOf('iPad') > -1, //是否iPad
                        webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                        weixin: u.indexOf('MicroMessenger') > -1 //是否为微信浏览器
                    };
                    var language = (navigator.browserLanguage || navigator.language).toLowerCase();
                    if (versions.ios || versions.iPhone || versions.iPad) {
                        if (versions.weixin) {
                            document.getElementById("iosdow").style.display = "";
                        }
                    } else if (versions.android) {
                        var url = "<c:url value="${download.android_url}" />";
                        if (versions.weixin) {
                            document.getElementById("shadow").style.display = "";
                        }
                        try {
                            <c:if test="${!download.android_url == ''}">
                            document.location.href = url;
                            </c:if>
                        } catch (e) {
                            try {
                                <c:if test="${!download.android_url == ''}">
                                document.location.href = url;
                                </c:if>
                            } catch (e) {
                            
                            }
                        }
                    }
                }
                
                function download_an() {
                    try {
                        document.location.href = "<c:url value="${download.android_url}" />";
                    } catch (e) {
                        try {
                            document.location.href = "<c:url value="${download.android_url}" />";
                        } catch (e) {
                        
                        }
                    }
                }
                
                function download_ios() {
                    var url = "";
                    
                    <c:choose>
                    <c:when test="${download.plistUrl != ''}">
                    url = "itms-services:///?action=download-manifest&url=" + "https://www.cdmkkj.cn:8843<%=request.getContextPath() %>${download.plistUrl}";
                    </c:when>
                    <c:when test="${download.iphStoreUrl != ''}">
                    url = "${download.iphStoreUrl}";
                    </c:when>
                    </c:choose>
                    try {
                        window.location = url;
                    } catch (e) {
                        try {
                            window.location = url;
                        } catch (e) {
                        
                        }
                    }
                }
                
                function show() {
                    download_an();
                    document.getElementById("shadow").style.display = "none";
                }
            </script>
        </head>
        <body onload="download();">
        <div class="ly_shadow" style="display:none;" id="shadow">
            <img src="apps_res/download/images/arrow.png" alt="">
            <h1>如何下载中原证券移动协同</h1>
            <div class="det">
                <p><em>1</em> 点击右上角 · · · 按钮</p>
                <p><em>2</em> 选择“在浏览器中打开”</p>
            </div>
        </div>
        
        <!--ios微信用户  start-->
        <div class="ly_shadow" style="display:none;" id="iosdow">
            <img src="apps_res/download/images/arrow.png" alt="">
            <h1>如何下载中原证券移动协同</h1>
            <div class="det">
                <p><em>1</em> 点击右上角 · · · 按钮</p>
                <p><em>2</em> 选择“在Safari中打开”</p>
            </div>
        </div>
        <!--ios微信用户  end-->
        
        <div class="page" id="show_download">
            <div class="header">
                <a href="javascript:void(0)" class="logo">
                    <!-- <img src="./im/zyzq.jpg" alt=""> -->
                    <img src="<c:url value="${download.logoUrl}" />" alt="">
                </a>
                <span class="title">
			软件下载
		</span>
            </div>
            
            <div class="main">
                <div class="main_cen">
                    <img src="<c:url value="${download.appLogoUrl}" />" alt="">
                    <p style="word-break:break-all;width: 230px;margin: 0 auto;">版本描述：${download.app_version}
                        <br />${download.description}
                    </p>
                    <c:choose>
                        <c:when test="${download.android_url != ''}">
                            <a href="javascript:void(0)" class="android" onclick="download_an();">android下载</a>
                        </c:when>
                        <c:when test="${download.iphStoreUrl != '' || download.plistUrl}">
                            <a href="javascript:void(0)" class="apple" onclick="download_ios();">iPhone下载</a>
                        </c:when>
                    </c:choose>
                    <div id="his_version">
                        <a href="<%=request.getContextPath() %>/appAction?method=history&custId=${download.id}"
                           id="history">历史版本</a>
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