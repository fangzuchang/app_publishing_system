<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${admin == null}">
        <c:redirect url="/login.jsp"></c:redirect>
    </c:when>
    <c:otherwise>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>客户管理</title>
            <script type="application/javascript">
                var contextPath = "<%=request.getContextPath()%>";
            </script>
            <script type="text/javascript" src="common/js/jquery.min.js"></script>
            <link rel="stylesheet" type="text/css" href="apps_res/custmanage/css/custmanage.css">
            <script type="text/javascript" src="apps_res/custmanage/js/custmanage1.js"></script>
            <script type="text/javascript" src="apps_res/custmanage/js/custmanage.js"></script>
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
                    <a href="<%=request.getContextPath()%>/accountSet.jsp">
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
            <%--app列表 start--%>
        
        <div class="content">
            <div class="content-top">
                    <%--按钮区--%>
                <div class="content-search">
                    <a href="javascript:void(0);" onclick="newCust()">
                        <div class="content-top-btn">新建</div>
                    </a>
                    <a href="javascript:void(0);" onclick="deleteCusts()">
                        <div class="content-top-btn">删除</div>
                    </a>
                </div>
                    <%--客户管理文字--%>
                <div class="content-top-text-div">
                    <img style="width:30px;height: 30px;vertical-align: middle"
                         src="apps_res/custmanage/images/custmanage.png" alt="">
                    客户管理
                </div>
            </div>
            
            <div class="content-foot">
                <table cellspacing="1" id="custer-table">
                    <tr class="table-title">
                        <th style="width: 45px"></th>
                        <th style="width: 120px">客户名称</th>
                        <th style="width: 150px">Logo</th>
                        <th style="width: 90px">最新版本</th>
                        <th>描述</th>
                        <th style="width: 200px;">操作</th>
                    </tr>
                    <c:forEach items="${appAndCustomerList}" var="appAndCustomer">
                        <tr id="@fzc@_${appAndCustomer.id}" class="table-data">
                            <td>
                                <input type="checkbox" name="" value="${appAndCustomer.id}" />
                            </td>
                            <td>${appAndCustomer.custName}</td>
                            <td><img style="width: 100px;height: 50px;"
                                     src="<c:url value="${appAndCustomer.logoUrl}" />" alt=""></td>
                            <td>${appAndCustomer.app_version}</td>
                            <td style="overflow: hidden;text-overflow:ellipsis;white-space:nowrap;">${appAndCustomer.description}</td>
                            <td>
                                <a class=" edit-a" href="javascript:void(0)"
                                   onclick="edit('${appAndCustomer.id}','${appAndCustomer.custName}','${appAndCustomer.logoUrl}','${appAndCustomer.appLogoUrl}');">
                                    <img class="btn-img" src="common/images/edit.png">
                                    <span class="btn-span edit-btn-text">编辑</span>
                                </a>
                                &nbsp;&nbsp;
                                <a href="javascript:void(0);" onclick="deleteCust('${appAndCustomer.id}')">
                                    <img class="btn-img" src="common/images/delete.png">
                                    <span class="btn-span delete-btn-text">删除</span>
                                </a>
                                &nbsp;&nbsp;
                                <a href="<c:url value="/appAction?method=queryApp&custId=${appAndCustomer.id}"/>">
                                    <img class="btn-img" src="common/images/appmanage.png">
                                    <span class="btn-span qr-btn-text">安装包</span>
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
                    <span>新建客户</span>
                        <%--</div>--%>
                </div>
                <div class="min">
                    <form name="formdata" id="formdata" target="ifr" method="post"
                          action="<%=request.getContextPath()%>/customerAction?method=addCust"
                          enctype="multipart/form-data">
                        <fieldset>
                            <table id="new_table">
                                <tr>
                                    <td width="100px"><label for="cust_name">客户名称</label></td>
                                    <td><input type="text" name="cust_name" id="cust_name"
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr>
                                    <td width="100px"><label for="cust_logo">客户Logo</label></td>
                                    <td>
                                        <input type="file" name="cust_logo" id="cust_logo" value=""
                                               class="text ui-widget-content ui-corner-all">
                                    </td>
                                </tr>
                                <tr>
                                    <td width="100px"><label for="app_logo">AppLogo</label></td>
                                    <td>
                                        <input type="file" name="app_logo" id="app_logo" value=""
                                               class="text ui-widget-content ui-corner-all">
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
                        <span>编辑客户</span>
                    </div>
                </div>
                <div class="min">
                    <form name="formdata1" id="formdata1" target="ifr" method="post"
                          action="<%=request.getContextPath()%>/customerAction?method=editCust"
                          enctype="multipart/form-data">
                        <fieldset>
                            <table id="edit_table">
                                <input id="custId" type="hidden" name="custId" value="" />
                                <tr>
                                    <td width="100px"><label for="cust_name1">客户名称</label></td>
                                    <td><input type="text" name="cust_name1" id="cust_name1"
                                               class="text ui-widget-content ui-corner-all"></td>
                                </tr>
                                <tr>
                                    <td width="100px"><label for="cust_logo1">客户Logo</label></td>
                                    <td>
                                        <input type="file" name="cust_logo1" id="cust_logo1" value=""
                                               class="text ui-widget-content ui-corner-all">
                                    </td>
                                </tr>
                                <tr>
                                    <td width="100px"><label for="app_logo1">AppLogo</label></td>
                                    <td>
                                        <input type="file" name="app_logo1" id="app_logo1" value=""
                                               class="text ui-widget-content ui-corner-all">
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
            <%--<div id="main_container">
                <div class="main_content">
                    <div class="right_content">
                        <div>
                            <button id="newCustButton" onclick="add()">新建</button>
                            <button id="deleteCustButton" onclick="dels()">删除</button>
                        </div>
                            
                            &lt;%&ndash;<table id="rounded-corner" style="text-align: center;">
                                <thead>
                                <tr>
                                    <th scope="col" class="rounded-company"></th>
                                    <th scope="col" class="rounded">客户</th>
                                    <th scope="col" class="rounded">logo</th>
                                    <th scope="col" class="rounded">最新版本</th>
                                    <th scope="col" class="rounded">版本描述</th>
                                    <th scope="col" class="rounded">安装包</th>
                                    <th scope="col" class="rounded">编辑</th>
                                    <th scope="col" class="rounded-q4">删除</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="app_cust" items="${appAndCustomerList}">
                                    <tr id="@fzc@_${app_cust.id}">
                                        <td>
                                            <input class="id_class" type="checkbox" name=""
                                                   value="${app_cust.id}" />
                                        </td>
                                        <td>
                                            <a href="#">${app_cust.custName}
                                            </a>
                                        </td>
                                        <td><img src="${app_cust.logoUrl}"
                                                 height="50px" width="100px"></td>
                                        <td>${app_cust.app_version}
                                        </td>
                                        <td>${app_cust.description}
                                        </td>
                                        <td>
                                            <a href="<%=request.getContextPath()%>/appAction?method=queryApp&custId=${app_cust.id}">查看</a>
                                        </td>
                                        <td>
                                            <a href="javascript:edit('${app_cust.id}','${app_cust.custName}','${app_cust.logoUrl}','${app_cust.appLogoUrl}');"><img
                                                    src="common/images/edit.png" alt="" title="" border="0" /></a></td>
                                        
                                        <td><a href="${app_cust.id}" class="ask"><img
                                                src="common/images/delete.png" alt="" title="" border="0" /></a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>&ndash;%&gt;
                        <table id="app-table" cellspacing="1">
                            <tr id="table-title">
                                <th style="width: 45px;"></th>
                                <th style="width: 100px;">客户名</th>
                                <th style=" width: 100px;">Logo
                                </th>
                                <th style="width: 80px;">最新版本</th>
                                <th>描述</th>
                                <th style=" width: 300px;">操作
                                </th>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </div>
                    
                    <div class="clear"></div>
                </div>
                <!--end of main content-->
            </div>
            
            <!-- 弹窗 -->
                &lt;%&ndash;<iframe src="" name="ifr" id="ifr" frameborder="0" width="0" height="0" style="width:0px;height:0px;"></iframe>
                <div id="dialog-form" title="新建客户">
                    <!-- <p class="validateTips"></p> -->
                    <form name="formdata" id="formdata" target="ifr" method="post"
                          action="<%=request.getContextPath()%>/appAndCustomerAction?method=addCust"
                          enctype="multipart/form-data">
                        <fieldset>
                            <table>
                                <tr>
                                    <td><label for="cust_name">客户名</label></td>
                                    <td><input type="text" name="cust_name" id="cust_name"
                                               class="text ui-widget-content ui-corner-all">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="logo_url">客户logo地址</label></td>
                                    <td>
                                        <input type="file" name="logo_url" id="logo_url" value=""
                                               class="text ui-widget-content ui-corner-all" onchange="readURL(this);">
                                    
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="logo_url">软件logo地址</label></td>
                                    <td>
                                        <input type="file" name="app_logo_url" id="app_logo_url"
                                               class="text ui-widget-content ui-corner-all" onchange="readURL1(this);">
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </form>
                    <div>
                        <div><img id="img_prev" src="#" alt="your image" style="margin: 0 auto;" /></div>
                        <div><img id="img_prev1" src="#" alt="your image" style="margin: 0 auto;" /></div>
                    </div>
                    <script type="text/javascript">
                        $(function () {
                            $('#img_prev').css('display', 'none');
                            $('#logo_url').change(function () {
                                $('#img_prev').css('display', 'block');
                            });
                        })
                    </script>
                    <script type="text/javascript">
                        function readURL(input) {
                            if (input.files && input.files[0]) {
                                var reader = new FileReader();
                                reader.onload = function (e) {
                                    $('#img_prev')
                                        .attr('src', e.target.result)
                                        .width(150)
                                        .height(120);
                                };
                                reader.readAsDataURL(input.files[0]);
                            }
                        }
                    </script>
                    <script type="text/javascript">
                        $(function () {
                            $('#img_prev1').css('display', 'none');
                            $('#app_logo_url').change(function () {
                                $('#img_prev1').css('display', 'block');
                            });
                        })
                    </script>
                    <script type="text/javascript">
                        function readURL1(input) {
                            if (input.files && input.files[0]) {
                                var reader = new FileReader();
                                reader.onload = function (e) {
                                    $('#img_prev1')
                                        .attr('src', e.target.result)
                                        .width(90)
                                        .height(90);
                                };
                                reader.readAsDataURL(input.files[0]);
                            }
                        }
                    </script>
                </div>
                <div id="dialog-edit" title="编辑客户">
                    <!-- <p class="validateTips"></p> -->
                    <form name="formdata1" id="formdata1" target="ifr" method="post"
                          action="<%=request.getContextPath()%>/appAndCustomerAction?method=editCust"
                          enctype="multipart/form-data">
                        <fieldset>
                            <table id="edit_table">
                                <tr>
                                    <td><label for="cust_name">客户名</label></td>
                                    <td><input type="text" name="cust_name1" id="cust_name1"
                                               class="text ui-widget-content ui-corner-all" value=""></td>
                                </tr>
                                <input type="hidden" name="custId" id="custId" value="">
                                <tr>
                                    <td><label for="logo_url">客户logo地址</label></td>
                                    <td>
                                        <input type="file" name="logo_url1" id="logo_url1" value=""
                                               class="text ui-widget-content ui-corner-all" onchange="readURL2(this);">
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="logo_url">软件logo地址</label></td>
                                    <td><input type="file" name="app_logo_url1" id="app_logo_url1"
                                               class="text ui-widget-content ui-corner-all" onchange="readURL3(this);"></td>
                                </tr>
                            
                            </table>
                        </fieldset>
                    </form>
                    <div>
                        <div><img id="img_prev2" src="#" alt="your image" style="margin: 0 auto;" /></div>
                        <div><img id="img_prev3" src="#" alt="your image" style="margin: 0 auto;" /></div>
                    </div>
                    <script type="text/javascript">
                        $(function () {
                            $('#img_prev2').css('display', 'none');
                            $('#logo_url1').change(function () {
                                $('#img_prev2').css('display', 'block');
                            });
                        })
                    </script>
                    <script type="text/javascript">
                        function readURL2(input) {
                            if (input.files && input.files[0]) {
                                var reader = new FileReader();
                                reader.onload = function (e) {
                                    $('#img_prev2')
                                        .attr('src', e.target.result)
                                        .width(150)
                                        .height(120);
                                };
                                reader.readAsDataURL(input.files[0]);
                            }
                        }
                    </script>
                    <script type="text/javascript">
                        $(function () {
                            $('#img_prev3').css('display', 'none');
                            $('#app_logo_url1').change(function () {
                                $('#img_prev3').css('display', 'block');
                            });
                        })
                    </script>
                    <script type="text/javascript">
                        function readURL3(input) {
                            if (input.files && input.files[0]) {
                                var reader = new FileReader();
                                reader.onload = function (e) {
                                    $('#img_prev3')
                                        .attr('src', e.target.result)
                                        .width(90)
                                        .height(90);
                                };
                                reader.readAsDataURL(input.files[0]);
                            }
                        }
                    </script>
                </div>&ndash;%&gt;--%>
        </body>
        
        </html>
    </c:otherwise>
</c:choose>