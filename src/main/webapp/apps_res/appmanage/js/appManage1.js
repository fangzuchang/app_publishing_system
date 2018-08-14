/**
 *
 *Created by fzc on 2018-5-13
 */
//******************编辑app start*************
//显示编辑弹窗
function edit(appId, appVersion, appDescription, iphPackageName, iphStoreUrl) {
    //清空数据
    clearEditData();
    //初始化编辑框数据
    initEditData(appId, appVersion, appDescription, iphPackageName, iphStoreUrl);
    //初始化样式
    initEditUI();
}

/**
 * 清空编辑框数据
 */
function clearEditData() {
    $("#appId").val("");
    $("#app_version1").val("");
    $("#app_description1").val("");
    $("#iph_package_name1").val("");
    $("#iph_store_url1").val("");
}

/**
 *初始化编辑框数据
 */
function initEditData(appId, appVersion, appDescription, iphPackageName, iphStoreUrl) {
    $("#appId").val(appId);
    $("#app_version1").val(appVersion);
    $("#app_description1").val(appDescription);
    $("#iph_package_name1").val(iphPackageName);
    $("#iph_store_url1").val(iphStoreUrl);
}

/**
 * 初始化编辑框样式
 */
function initEditUI() {
    //显示div
    $("#edit-app-gray").show();
    $("#edit-popup").show();
    //居中设置
    var _top = ($(window).height() - $(".popup").height()) / 2;
    var _left = ($(window).width() - $(".popup").width()) / 2;
    $(".popup").css({top: _top, left: _left});
}

/**
 *编辑框苹果端发布方式切换
 * @param obj
 */
function edit_div_click(obj) {
    var show = $(obj).find(".pub_style").val();
    switch (show) {
        case 'appstore':
            $("#pack_name_id1").hide();
            $("#iph_app_id1").hide();
            $("#store_url_id1").show();
            break;
        case 'enterprise':
            $("#pack_name_id1").show();
            $("#iph_app_id1").show();
            $("#store_url_id1").hide();
            break;
        default:
            $("#pack_name_id1").hide();
            $("#iph_app_id1").hide();
            $("#store_url_id1").show();
            break;
    }
}

//检测编辑框数据
function checkEditData() {
    var result = {isPass: false, message: ""};
    var andApp = $("#and_app1").val();
    var iphApp = $("#iph_app1").val();
    //发布方式
    var pubStyle = $('input:radio[name="pub_style1"]:checked').val();
    //store地址
    var iphStoreUrl = $("#iph_store_url1").val();
    if ($("#app_version1").val() == "") {
        result.message = "版本号不能为空！";
        return result;
    }
    
    if ($("#app_description1").val() == "") {
        result.message = "安装包描述不能为空！";
        return result;
    }
    //判断后缀
    if (andApp != "") {//安卓安装包不为空
        var suffix1 = andApp.substr(andApp.lastIndexOf("."));
        
        if (suffix1 != ".apk") {//判断安卓安装包后缀
            result.message = "请上传.apk格式的安卓安装包！";
            return result;
        }
        
        if (pubStyle != "appStore") {
            if (iphApp != "") {//iphone安装包不为空
                if ($("#iph_package_name1").val() == "") {
                    result.message = "上传iphone安装包时安装包名不能为空！";
                    return result;
                }
                var suffix2 = iphApp.substr(iphApp.lastIndexOf("."));
                if (suffix2 != ".ipa") {
                    result.message = "请上传.ipa格式的苹果安装包！";
                    return result;
                }
                result.isPass = true;
            } else {//iphone安装包为空
                if ($("#iph_package_name1").val() != "") {
                    result.message = "安装包名与iphone安装包必须同时填写";
                    return result;
                }
                result.isPass = true;
            }
        } else {
            result.isPass = true;
        }
    } else {//安卓安装包为空
        if (pubStyle != "appStore") {
            if (iphApp != "") {//苹果安装包不为空
                var suffix2 = iphApp.substr(iphApp.lastIndexOf("."));
                if (suffix2 != ".ipa") {
                    result.message = "请上传.ipa格式的苹果安装包！";
                    return result;
                }
                if ($("#iph_package_name1").val() == "") {
                    result.message = "上传iphone安装包时安装包名不能为空";
                    return result;
                }
                result.isPass = true;
            } else {//苹果安装包为空
                if ($("#iph_package_name1").val() != "") {
                    result.message = "安装包名与iphone安装包必须同时填写";
                    return result;
                }
                result.isPass = true;
            }
        } else {
            result.isPass = true;
        }
    }
    return result;
}

//提交编辑app数据
function edit_OK() {
    var result = checkEditData();
    if (result.isPass) {
        //提交表单
        var formData = new FormData(document.getElementById("formdata1"));
        $.ajax({
            url: contextPath + "/appAction?method=update",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (result) {
                if (result.result = "true") {
                    alert("修改成功");
                    //隐藏编辑app弹窗
                    $("#edit-app-gray").hide();
                    $("#edit-popup").hide();
                    location.reload(true);
                } else if (result.result == "false") {
                    alert("修改失败,错误信息：" + result.message);
                } else {
                    alert("修改失败！");
                }
            },
            xhr: function () {
                var xhr = $.ajaxSettings.xhr();
                if (onprogress && xhr.upload) {
                    xhr.upload.addEventListener("progress", onprogress, false);
                    return xhr;
                }
            }
        });
    } else {
        alert(result.message);
    }
}

/**
 * 文件上传进度条
 * @param evt
 */
function onprogress(evt) {
    var loaded = evt.loaded;     //已经上传大小情况
    var tot = evt.total;      //附件总大小
    var per = Math.floor(100 * loaded / tot);  //已经上传的百分比
    $("#son").html("已上传" + per + "%");
    $("#son").css("width", per + "%");
}

//关闭编辑弹窗
function edit_cancel() {
    $("#edit-popup").hide();
    $("#edit-app-gray").hide();
}

//*******************编辑app end**********
//*******************新建app start**********
/**
 * 新建app
 */
function new_OK() {
    var result = checkNewData();
    if (result.isPass) {
        //提交表单
        var formData = new FormData(document.getElementById("formdata"));
        $.ajax({
            url: contextPath + "/appAction?method=save",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (result) {
                if (result.result = "true") {
                    alert("新建成功");
                    //隐藏新建app弹窗
                    $("#gray").hide();
                    $("#popup").hide();
                    //清空新建app弹窗数据
                    $("#cust_name").val("");
                    $("#iph_package_name").val("");
                    $("#iph_store_url").val("");
                    $("#app_description").val("");
                    location.reload(true);
                } else if (result.result == "false") {
                    alert("新建失败,错误信息：" + result.message);
                } else {
                    alert("新建失败！");
                }
            },
            xhr: function () {
                var xhr = $.ajaxSettings.xhr();
                if (onprogress1 && xhr.upload) {
                    xhr.upload.addEventListener("progress", onprogress1, false);
                    return xhr;
                }
            }
        });
    } else {
        alert(result.message);
    }
}

/**
 * 关闭新建app窗口
 */
function new_Cancel() {
    $("#gray").hide();
    $("#popup").hide();//查找ID为popup的DIV hide()隐藏
}

//进度条
function onprogress1(evt) {
    var loaded = evt.loaded;     //已经上传大小情况
    var tot = evt.total;      //附件总大小
    var per = Math.floor(100 * loaded / tot);  //已经上传的百分比
    $("#new-son").html("已上传" + per + "%");
    $("#new-son").css("width", per + "%");
}

/**
 * 校验新建app数据
 * @returns {{isPass: boolean, message: string}}
 */
function checkNewData() {
    var result = {isPass: false, message: ""};
    var andApp = $("#and_app").val();
    var iphApp = $("#iph_app").val();
    //发布方式
    var pubStyle = $('input:radio[name="pub_style"]:checked').val();
    //store地址
    var iphStoreUrl = $("#iph_store_url").val();
    if ($("#cust_name").val() == "") {
        result.message = "版本号不能为空！";
        return result;
    }
    if ($("#app_description").val() == "") {
        result.message = "安装包描述不能为空！";
        return result;
    }
    if (andApp == "" && iphApp == "" && iphStoreUrl == "") {
        result.message = "请至少选择一个上传安装包";
        return result;
    }
    //判断后缀
    if (andApp != "") {
        var suffix1 = andApp.substr(andApp.lastIndexOf("."));
        if (suffix1 != ".apk") {
            result.message = "请上传.apk格式的安卓安装包！";
            return result;
        }
        if (pubStyle != "appStore") {
            //企业app
            if (iphApp != "") {
                if ($("#iph_package_name").val() == "") {
                    result.message = "上传iphone安装包时安装包名不能为空！";
                    return result;
                }
                var suffix2 = iphApp.substr(iphApp.lastIndexOf("."));
                if (suffix2 != ".ipa") {
                    result.message = "请上传.ipa格式的苹果安装包！";
                    return result;
                }
                result.isPass = true;
            } else {
                result.isPass = true;
            }
        } else {
            if (iphStoreUrl == "") {
                result.message = "AppStore发布appStore地址不能为空！";
                return result;
            }
            result.isPass = true;
        }
    } else {
        if (pubStyle != "appstore") {
            if (iphApp != "") {
                if ($("#iph_package_name").val() == "") {
                    result.message = "上传iphone安装包时安装包名不能为空！";
                    return result;
                }
                var suffix2 = iphApp.substr(iphApp.lastIndexOf("."));
                if (suffix2 != ".ipa") {
                    result.message = "请上传.ipa格式的苹果安装包！";
                    return result;
                }
                result.isPass = true;
            }
        } else {
            //商店发布
            if (iphStoreUrl == "") {
                result.message = "请上传.ipa格式的苹果安装包！";
                return result;
            }
            result.isPass = true;
        }
    }
    return result;
}

/**
 * 新建app,切换苹果发布方式
 * @param obj
 */
function new_div_click(obj) {
    var show = $(obj).find(".pub_style").val();
    switch (show) {
        case 'appstore':
            $("#pack_name_id").hide();
            $("#iph_app_id").hide();
            $("#store_url_id").show();
            break;
        case 'enterprise':
            $("#pack_name_id").show();
            $("#iph_app_id").show();
            $("#store_url_id").hide();
            break;
        default:
            $("#pack_name_id").hide();
            $("#iph_app_id").hide();
            $("#store_url_id").show();
            break;
    }
}

//*******************新建app end**********

//删除单个app
function deleteApp(appId) {
    if (confirm("是否确定删除？")) {
        if (appId) {
            $.ajax({
                url: contextPath + "/appAction",
                type: "post",
                dataType: "json",
                data: {
                    method: "delApps",
                    ids: appId
                },
                async: false,
                success: function (res) {
                    if (res.result == "true") {
                        alert("删除成功");
                        location.reload(true);
                    } else {
                        alert(res.msg);
                    }
                }
            });
        }
    }
}

//窗口居中
function tc_center() {
    var _top = ($(window).height() - $(".popup").height()) / 2;
    var _left = ($(window).width() - $(".popup").width()) / 2;
    $(".popup").css({top: _top, left: _left});
}

/**
 * 新建app
 */
function newApp() {
    $("#gray").show();
    $("#popup").show();//查找ID为popup的DIV show()显示#gray
    tc_center();
}

/**
 * 批量删除app
 */
function deleteApps() {
    //选择的数据
    var deletedAppList = $("#custer-table input:checked");
    if (deletedAppList.length == 0) {
        alert("请选择数据！");
    } else {
        if (confirm("是否确定删除？")) {
            var idsString = "";
            for (var i = 0; i < deletedAppList.length; i++) {
                idsString += deletedAppList.get(i).defaultValue + "@fzc@";
            }
            $.ajax({
                url: contextPath + "/appAction",
                type: "post",
                dataType: "json",
                data: {
                    method: "delApps",
                    ids: idsString
                },
                async: false,
                success: function (res) {
                    if (res.result == "true") {
                        alert(res.msg);
                        location.reload(true);
                    } else {
                        alert(res.msg);
                    }
                }
            });
        }
    }
}

//*******************二维码 start***********
//显示二维码
function openQRcode(qrcodeUrl) {
    $("#qrcode_img").attr("src", qrcodeUrl);
    $("#qrcode-gray").show();
    $("#qrcode-popup").show();//查找ID为popup的DIV show()显示#gray
    var _top = ($(window).height() - $("#qrcode-popup").height()) / 2;
    var _left = ($(window).width() - $("#qrcode-popup").width()) / 2;
    $("#qrcode-popup").css({top: _top, left: _left});
}

//关闭二维码
function closeQRcode() {
    $("#qrcode-gray").hide();
    $("#qrcode-popup").hide();
}

//*******************二维码 end***********