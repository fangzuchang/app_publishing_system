/**
 *Created by fzc on 2018-5-22
 */

function refreshCode() {
    $("#captcha-img-div img").attr("src", "captchaAction?rnd=" + Math.random());
}

$(function () {
    /**
     * 获取上下文
     * @returns {string}
     */
    function getContextPath() {
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return projectName;
    }
    
    /**
     * 校验数据
     * @param idSelectorStr
     * @param tipStr
     * @param maxLength
     * @param minLength
     * @returns {boolean}
     */
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
    
    /**
     * 登录
     * @returns {boolean}
     */
    function toLogin() {
        $("#tips").html("");
        //判断数据格式
        if (!checkDataFormat("#username", "用户名", "20", "3") || !checkDataFormat("#password", "密码", "20", "3")) {
            return false;
        }
        if ($("#captcha").val() == "") {
            $("#tips").html("验证码不能为空！");
            return false;
        }
        //提交数据
        $.ajax({
            url: getContextPath() + "/adminAction",
            dataType: "json",
            type: "post",
            data: {
                method: "login",
                username: $("#username").val(),
                password: $("#password").val(),
                captcha: $("#captcha").val()
            },
            async: false,
            success: function (data) {
                if (data.result == "true") {
                    location.href = getContextPath() + "/customerAction?method=queryAllCustAndApp";
                } else if (data.result == "false") {
                    $("#tips").html(data.msg);
                } else {
                    alert("登录失败");
                }
            }
        });
    }
    
    $(".submit").bind("click", toLogin);
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            toLogin();
        }
    })
});