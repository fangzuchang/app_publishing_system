<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${admin == null}">
        <c:redirect url="/login.jsp"></c:redirect>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>安装包管理</title>
            <script type="text/javascript" src="common/js/jquery.min.js"></script>
            <link rel="stylesheet" href="apps_res/appmanage/css/appmanage.css">
            <script type="application/javascript">
                var custId = "${param.custId}";
                var contextPath = "<%=request.getContextPath()%>";
                var contextPath1 = "<%=request.getContextPath()%>";
            </script>
            <script type="text/javascript" src="apps_res/appmanage/js/appManage1.js"></script>
            <script type="text/javascript" src="apps_res/appmanage/js/appManage.js"></script>
        </head>
        <body>
            <%--顶部导航 start--%>
        <div class="head">
                <%--顶部按钮--%>
            <div class="btn-class">
                <div class="btn-item-class">
                    <a href="<c:url value="/adminAction?method=logout"/>">
                        <div class="btn-div">
                            <div class="img-div">
                                <img class="home-img" src="common/images/out.png" />
                            </div>
                            <div class="text-div">退出</div>
                        </div>
                    </a>
                </div>
                <div class="btn-item-class">
                    <a href="<c:url value="/accountSet.jsp"/>">
                        <div class="btn-div">
                            <div class="img-div">
                                <img class="home-img" src="common/images/setting.png" />
                            </div>
                            <div class="text-div">${admin.realName}
                            </div>
                        </div>
                    </a>
                </div>
                <div class="btn-item-class">
                    <a href="<c:url value="/customerAction?method=queryAllCustAndApp"/>">
                        <div class="btn-div">
                            <div class="img-div">
                                <img class="home-img" src="common/images/user.png" />
                            </div>
                            <div class="text-div">客户管理</div>
                        </div>
                    </a>
                </div>
                <div class="btn-item-class">
                    <a href="<c:url value="/index.jsp"/>">
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
            <%--顶部导航 end--%>
            <%--app列表 start--%>
        
        <div class="content">
            <div class="content-top">
                    <%--按钮区--%>
                <div class="content-search">
                    <a href="javascript:void(0);" onclick="newApp()">
                        <div class="content-top-btn">新建</div>
                    </a>
                    <a href="javascript:void(0);" onclick="deleteApps()">
                        <div class="content-top-btn">删除</div>
                    </a>
                </div>
                    <%--客户管理文字--%>
                <div class="content-top-text-div">
                    <img style="width:30px;height: 30px;vertical-align: middle"
                         src="apps_res/appmanage/images/appmanage.png" alt="">
                    APP管理
                </div>
            </div>
            
            <div class="content-foot">
                <table cellspacing="1" id="custer-table">
                    <tr class="table-title">
                        <th style="width: 45px"></th>
                        <th style="width:100px">版本号</th>
                        <th style="width: 90px">安卓端</th>
                        <th style="width: 90px">苹果端</th>
                            <%--<th style="width:190px">创建时间</th>
                            <th style="width:190px">更新时间</th>--%>
                        <th>描述</th>
                        <th style="width: 200px;">操作</th>
                    </tr>
                    <c:forEach items="${appList}" var="app">
                        <tr id="@fzc@_${app.app_id}" class="table-data">
                            <td>
                                <input type="checkbox" name=""
                                       value="${app.app_id}" />
                            </td>
                                <%--版本--%>
                            <td>${app.app_version}</td>
                                <%--安卓--%>
                            <c:choose>
                                <c:when test="${app.android_url} !=null">
                                    <td>无${app.android_url}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>有</td>
                                </c:otherwise>
                            </c:choose>
                                <%--苹果--%>
                            <c:choose>
                                <c:when test="${app.iphone_url != null}">
                                    <td>无</td>
                                </c:when>
                                <c:otherwise>
                                    <td>有</td>
                                </c:otherwise>
                            </c:choose>
                                <%--<td>${app.createTimeDate}</td>
                                <td>${app.updateTime}</td>--%>
                                <%--描述--%>
                            <td style="overflow: hidden;text-overflow:ellipsis;white-space:nowrap;">${app.description}</td>
                                <%--按钮--%>
                            <td>
                                <a class="edit-a" href="javascript:void(0)"
                                   onclick="edit('${app.app_id}','${app.app_version}','${app.description}','${app.iphPackageName}','${app.iphStoreUrl}');">
                                    <img class="btn-img" src="common/images/edit.png">
                                    <span class="btn-span edit-btn-text">编辑</span>
                                </a>
                                &nbsp;&nbsp;
                                <a href="javascript:void(0);" onclick="deleteApp('${app.app_id}')">
                                    <img class="btn-img" src="common/images/delete.png">
                                    <span class="btn-span delete-btn-text">删除</span>
                                </a>
                                &nbsp;&nbsp;
                                <a href="javascript:void(0)"
                                   onclick="openQRcode('${app.qrcode}')">
                                    <img class="btn-img" src="common/images/qrcode.png">
                                    <span class="btn-span qr-btn-text">二维码</span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
            <%--app列表 end--%>
            
            <%--*********************弹窗*******--%>
            <%--新建appdiv--%>
        <div id="new_app_div">
                <%--遮光罩--%>
            <div id="gray"></div>
            <div class="popup" id="popup">
                <div class="top_nav" id='edit-app-nav'>
                        <%--<div align="center">--%>
                    <span>新建APP</span>
                        <%--</div>--%>
                </div>
                <div class="min">
                    <form name="formdata" id="formdata" target="ifr" method="post"
                          action="<%=request.getContextPath()%>/appAction?method=save"
                          enctype="multipart/form-data">
                        <fieldset>
                            <table id="new_table">
                                <input type="hidden" name="custId" value="${param.custId}">
                                <tr>
                                    <td width="120px"><label for="app_version">版本号</label></td>
                                    <td><input type="text" name="app_version" id="app_version"
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr>
                                    <td width="120px"><label for="and_app">安卓端</label></td>
                                    <td>
                                        <input type="file" name="and_app" id="and_app" value=""
                                               class="text ui-widget-content ui-corner-all">
                                    </td>
                                </tr>
                                <!-- 选择上传方式 -->
                                <tr>
                                    <td width="120px">
                                        iphone端
                                    </td>
                                    <td>
                                        <label class="new-toggle-iphone" onclick="new_div_click(this)">
                                            <input name="pub_style" class="pub_style" type="radio" value="appstore"
                                                   checked="true" />AppStore发布
                                        </label>
                                        <label class="new-toggle-iphone" onclick="new_div_click(this)"><input
                                                name="pub_style" class="pub_style"
                                                type="radio"
                                                value="enterprise" />企业版发布</label>
                                    </td>
                                </tr>
                                
                                <!-- 商店下载 -->
                                <tr id="store_url_id">
                                    <td width="120px">appStore地址</td>
                                    <td><input type="text" name="iph_store_url" id="iph_store_url" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <!-- 企业下载 -->
                                <tr id="pack_name_id" style="display: none;">
                                    <td width="120px">iphone安装包名</td>
                                    <td><input type="text" name="iph_package_name" id="iph_package_name" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr id="iph_app_id" style="display: none;">
                                    <td width="120px">iphone端</td>
                                    <td><input type="file" name="iph_app" id="iph_app" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr>
                                    <td width="120px"><label for="app_description">描述</label></td>
                                    <td>
                        <textarea rows="6" cols="40" name="app_description" id="app_description"
                                  class="text ui-widget-content ui-corner-all"></textarea>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </form>
                        <%--进度条--%>
                    <div id="new-parent">
                        <div id="new-son"></div>
                    </div>
                    <div class="app-foot">
                        <button class="hidden-div-btn new-cancel-btn" onclick="new_Cancel()">取消</button>
                        <button class="hidden-div-btn new-submit-btn" onclick="new_OK()">确认</button>
                    </div>
                </div>
            </div>
        </div>
            
            <%--********************************编辑app div****************--%>
        <div id="edit-app-div">
            <div id="edit-app-gray"></div>
            <div class="popup" id="edit-popup">
                <div class="top_nav" id='top_nav'>
                    <div align="center">
                        <span>编辑APP</span>
                    </div>
                </div>
                <div class="min">
                    <form name="formdata1" id="formdata1" target="ifr" method="post"
                          action="<%=request.getContextPath()%>/appAction?method=update"
                          enctype="multipart/form-data">
                        <fieldset>
                            <table id="edit_table">
                                <input type="hidden" name="custId" value="${param.custId}">
                                <tr>
                                    <td width="120px"><label for="app_version">版本号</label></td>
                                    <td><input type="text" name="app_version1" id="app_version1"
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <input type="hidden" name="appId" id="appId" value="">
                                <tr>
                                    <td width="120px"><label for="and_app">安卓端</label></td>
                                    <td><input type="file" name="and_app1" id="and_app1" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                
                                <!-- 选择上传方式 -->
                                <tr>
                                    <td width="120px">
                                        iphone端
                                    </td>
                                    <td>
                                        <label onclick="edit_div_click(this);"><input name="pub_style1"
                                                                                      class="pub_style"
                                                                                      type="radio"
                                                                                      value="appstore" checked="true" />AppStore发布
                                        </label>
                                        <label onclick="edit_div_click(this);"><input name="pub_style1"
                                                                                      class="pub_style"
                                                                                      type="radio"
                                                                                      value="enterprise" />企业版发布</label>
                                    </td>
                                </tr>
                                
                                <tr id="store_url_id1">
                                    <td width="120px"><label for="iph_store_url1">appStore地址</label></td>
                                    <td><input type="text" name="iph_store_url1" id="iph_store_url1" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                
                                <tr id="pack_name_id1" style="display: none;">
                                    <td width="120px"><label for="iph_package_name">iphone安装包名</label></td>
                                    <td><input type="text" name="iph_package_name1" id="iph_package_name1" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr id="iph_app_id1" style="display: none;">
                                    <td width="120px"><label for="iph_app">iphone端</label></td>
                                    <td><input type="file" name="iph_app1" id="iph_app1" value=""
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr>
                                    <td width="120px">
                                        <label for="app_description">描述</label>
                                    </td>
                                    <td>
                        <textarea rows="6" cols="40" name="app_description1" id="app_description1"
                                  class="text ui-widget-content ui-corner-all">
                        </textarea>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </form>
                    
                    
                    <div id="parent">
                        <div id="son"></div>
                    </div>
                    <div class="app-foot">
                        <button class="hidden-div-btn edit-cancel-btn" onclick="edit_cancel()">取消</button>
                        <button class="hidden-div-btn edit-submit-btn" onclick="edit_OK()">确认</button>
                    </div>
                </div>
            </div>
        
        </div>
            <%--****************************app二维码div********************--%>
        <div id="qrcode-div">
            <div id="qrcode-gray"></div>
            <div class="popup" id="qrcode-popup">
                <div class="top_nav" id='qrcode-top_nav'>
                    <div align="center">
                        <span>二维码</span>
                    </div>
                </div>
                <div class="min">
                    <img id="qrcode_img" src="" alt="二维码" style="width: 250px;height: 250px;">
                    <div class="qrcode-cancel-btn" onclick="closeQRcode()">
                        关闭
                    </div>
                </div>
            </div>
        </div>
        </body>
        </html>
    </c:otherwise>
</c:choose>
