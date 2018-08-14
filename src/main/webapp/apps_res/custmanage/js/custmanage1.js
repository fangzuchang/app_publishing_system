/**
 *
 *Created by fzc on 2018-5-13
 */
//******************编辑客户 start*************
//显示编辑弹窗
function edit(custId, custName, custLogo, appLogo) {
    //清空数据
    clearEditData();
    //初始化编辑框数据
    initEditData(custId, custName, custLogo, appLogo);
    //初始化样式
    initEditUI();
}

/**
 * 清空编辑框数据
 */
function clearEditData() {
    $("#cust_name1").val("");
    $("#cust_logo1").val("");
    $("#app_logo1").val("");
}

/**
 *初始化编辑框数据
 */
function initEditData(custId, custName, custLogo, appLogo) {
    $("#custId").val(custId)
    $("#cust_name1").val(custName);
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

//检测编辑框数据
function checkEditData() {
    var result = {isPass: false, message: ""};
    var cust_name = $("#cust_name1").val();
    var app_logo = $("#app_logo1").val();
    var cust_logo = $("#cust_logo1").val();
    if (cust_name && cust_name.replace(/ /g, '')) {
        result.isPass = true;
        if (app_logo && cust_logo) {
            var suffix = app_logo.toUpperCase().endsWith(".PNG") && app_logo.toUpperCase().endsWith(".JPG") && app_logo.toUpperCase().endsWith(".JPEG");
            var suffix2 = cust_logo.toUpperCase().endsWith(".PNG") && cust_logo.toUpperCase().endsWith(".JPG") && cust_logo.toUpperCase().endsWith(".JPEG");
            if (!(suffix && suffix2)) {
                result.isPass = false;
                result.msg = "图片格式应为.jpg, .png, .jpeg";
            }
        }
        
        
    }
    return result;
}

//提交编辑客户数据
function edit_OK() {
    var result = checkEditData();
    if (result.isPass) {
        //提交表单
        var formData = new FormData(document.getElementById("formdata1"));
        $.ajax({
            url: contextPath + "/customerAction?method=editCust",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (result) {
                if (result.result = "true") {
                    alert("修改成功");
                    //隐藏编辑客户弹窗
                    $("#edit-app-gray").hide();
                    $("#edit-popup").hide();
                    location.reload(true);
                } else if (result.result == "false") {
                    alert("修改失败,错误信息：" + result.msg);
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
        alert(result.msg);
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
    //            $("#son").html(per + "%");
    $("#son").css("width", per + "%");
}

//关闭编辑弹窗
function edit_cancel() {
    $("#edit-popup").hide();
    $("#edit-app-gray").hide();
}

//*******************编辑客户 end**********
//*******************新建客户 start**********
/**
 * 新建客户
 */
function new_OK() {
    var result = checkNewData();
    if (result.isPass) {
        //提交表单
        var formData = new FormData(document.getElementById("formdata"));
        $.ajax({
            url: contextPath + "/customerAction?method=addCust",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (result) {
                if (result.result = "true") {
                    alert("新建成功");
                    //隐藏新建客户弹窗
                    $("#gray").hide();
                    $("#popup").hide();
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
        alert(result.msg);
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
    $("#new-son").css("width", per + "%");
}

/**
 * 校验新建客户数据
 * @returns {{isPass: boolean, message: string}}
 */
function checkNewData() {
    var result = {isPass: false, message: ""};
    var cust_name = $("#cust_name").val();
    var app_logo = $("#app_logo").val();
    var cust_logo = $("#cust_logo").val();
    if (cust_name && cust_name.replace(/ /g, '')) {
        if (app_logo && cust_logo) {
            var suffix = app_logo.toUpperCase().endsWith(".PNG") && app_logo.toUpperCase().endsWith(".JPG");
            var suffix2 = cust_logo.toUpperCase().endsWith(".PNG") && cust_logo.toUpperCase().endsWith(".JPG");
            if (suffix && suffix2) {
                result.isPass = true;
            } else {
                result.msg = "图片格式应为.jpg, .png, .jpeg";
            }
        }
    }
    return result;
}


//*******************新建app end**********

//删除单个app
function deleteCust(custId) {
    if (confirm("是否确定删除？")) {
        if (custId) {
            $.ajax({
                url: contextPath + "/customerAction",
                type: "post",
                dataType: "json",
                data: {
                    method: "deleteCust",
                    custId: custId
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
function newCust() {
    //清空新建客户弹窗数据
    $("#gray").show();
    $("#popup").show();//查找ID为popup的DIV show()显示#gray
    tc_center();
}

/**
 * 批量删除客户
 */
function deleteCusts() {
    //选择的数据
    var deletedCustList = $("#app-table input:checked");
    if (deletedCustList.length == 0) {
        alert("请选择数据！");
    } else {
        if (confirm("是否确定删除？")) {
            var idsString = "";
            for (var i = 0; i < deletedCustList.length; i++) {
                idsString += deletedCustList.get(i).defaultValue + "@fzc@";
            }
            $.ajax({
                url: contextPath + "/customerAction",
                type: "post",
                dataType: "json",
                data: {
                    method: "delCusts",
                    ids: idsString
                },
                async: false,
                success: function (res) {
                    if (res.result == "true") {
                        alert("删除成功");
                        location.reload(true);
                    } else {
                        alert("删除失败，报错信息：" + res.msg);
                    }
                }
            });
        }
    }
}
