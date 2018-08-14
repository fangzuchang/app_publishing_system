/**
 *Created by fzc on 2018-4-11
 */
$(function () {
    function getContextPath() {
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return projectName;
    }
    
    function checkDataFormat(idSelectorStr, tipStr, maxLength, minLength) {
        var dataValue = $(idSelectorStr).val();
        var tips = "";
        var isSuccess = true;
        //检测长度
        if (dataValue.length > maxLength) {
            tips = tipStr + "不能超过" + maxLength + "个字符";
            isSuccess = false;
        } else if (dataValue.length < minLength) {
            tips = tipStr + "至少" + minLength + "个字符";
            isSuccess = false;
        }
        if (!isSuccess) {
            $(idSelectorStr)[0].focus();
            $("#tips").html(tips);
        }
        return isSuccess;
    }
    
    function toLogin() {
        $("#tips").html("");
        //判断数据格式
        if (!checkDataFormat("#username", "登录名", "20", "3")) {
            return false;
        }
        if (!checkDataFormat("#oldpassword", "旧密码", "20", "3")) {
            return false;
        }
        if (!checkDataFormat("#newpassword", "新密码", "20", "3")) {
            return false;
        }
        if (!checkDataFormat("#newpassword_", "新密码", "20", "3")) {
            return false;
        }
        if ($("#newpassword").val() != $("#newpassword_").val()) {
            alert("新密码不一致！");
            return false;
        }
        //提交数据
        $.ajax({
            url: getContextPath() + "/adminAction",
            dataType: "json",
            type: "post",
            data: {
                method: "update",
                username: $("#username").val(),
                oldpassword: $("#oldpassword").val(),
                newpassword: $("#newpassword").val()
            },
            async: false,
            success: function (data) {
                if (data.result == "true") {
                    alert("修改成功，请重新登录！");
                    location.href = getContextPath() + "/adminAction?method=logout";
                } else if (data.result == "false") {
                    $("#tips").html(data.msg);
                } else {
                    alert("修改失败");
                }
            }
        });
    }
    
    $(".submit").bind("click", toLogin);
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键的
            toLogin();
        }
    })
});