/**
 *Created by fzc on 2018-4-11
 */
$(document).ready(function () {
    //js获取项目根路径，如： http://localhost:8083/uimcardprj
    function getRootPath() {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht + projectName);
    }
    
    function getContextPath() {
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return projectName;
    }
    
    var custList = {};
    //获取数据
    $.ajax(
        {
            url: getContextPath() + "/homePageAction",
            dataType: "json",
            async: false,
            data: {method: "home"},
            type: "post",
            success: function (data) {
                if (data.result == "true") {
                    custList = data.data;
                } else {
                    alert(data.data);
                }
            }
        }
    );
    //创建元素并填充数据
    var elementText = "";
    var custer = {};
    for (var i = 0; i < custList.length; i++) {
        custer = custList[i];
        if (i % 4 == 0) {
            elementText += "<li class='one'>";
        } else if (i % 4 == 3) {
            elementText += "<li class='four'>";
        } else {
            elementText += "<li>";
        }
        elementText += "<a href=\"" + getContextPath() + "/appAction?method=download&custId=" + custer.id + "\">";
        elementText += "<img src = \"" + custer.logoUrl + "\" width=\"240\" height=\"120\" alt=\"" + custer.custName + "\"></a><span><a href=\"#\">" + custer.custName + "</a></span></li>";
    }
    console.log(elementText);
    $(elementText).appendTo("#custer-ul");
    //设置样式
});