/*
 * jQuery Plugin : jConfirmAction
 * 
 * by Hidayat Sagita http://www.webstuffshare.com Licensed Under GPL version 2
 * license.
 * 
 */
(function ($) {
    
    jQuery.fn.jConfirmAction = function (options) {
        var theOptions = jQuery.extend({
            question: "你确定要删除吗?",
            yesAnswer: "确定",
            cancelAnswer: "取消"
        }, options);
        
        return this.each(function () {
            
            $(this).bind('click', function (e) {
                e.preventDefault();
                thisHref = $(this).attr('href');
                if ($(this).next('.question').length <= 0)
                    $(this).after('<div class="question">' +
                        theOptions.question + '<br/> <span class="yes">' +
                        theOptions.yesAnswer +
                        '</span><span class="cancel">' +
                        theOptions.cancelAnswer + '</span></div>');
                
                $(this).next('.question').animate({
                    opacity: 1
                }, 300);
                
                $('.yes').bind('click', function (id) {
                    /* 点击确认后执行的方法 */
                    var xmlhttp;
                    if (window.XMLHttpRequest) { // code for IE7+, Firefox,
                        // Chrome, Opera, Safari
                        xmlhttp = new XMLHttpRequest();
                    } else { // code for IE6, IE5
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    
                    var appId = thisHref.toString();
                    if (appId.match("@delApp@")) {
                        appId = appId.substr(8);
                        xmlhttp.onreadystatechange = function () {
                            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                                if (xmlhttp.responseText == "true") {
                                    var el = document.getElementById("@fzc@_" + appId)
                                    el.parentNode.removeChild(el);
                                    //location.reload(true);
                                } else {
                                    alert("删除失败！");
                                }
                            }
                        }
                        xmlhttp.open("POST", "/appPublishSystem/appAndCustomerAction", true);
                        xmlhttp.setRequestHeader("Content-type",
                            "application/x-www-form-urlencoded");
                        xmlhttp.send("method=delApp&appId=" + appId);
                    } else {
                        xmlhttp.onreadystatechange = function () {
                            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                                if (xmlhttp.responseText == "true") {
                                    var el = document.getElementById("@fzc@_" + thisHref)
                                    el.parentNode.removeChild(el);
                                    //location.reload(true);
                                } else {
                                    alert("删除失败！");
                                }
                            }
                        }
                        xmlhttp.open("POST", "/appPublishSystem/appAndCustomerAction", true);
                        xmlhttp.setRequestHeader("Content-type",
                            "application/x-www-form-urlencoded");
                        xmlhttp.send("method=deleteCust&id=" + thisHref);
                    }
                    
                    $(this).parents('.question').fadeOut(300, function () {
                        $(this).remove();
                    });
                });
                
                $('.cancel').bind('click', function () {
                    $(this).parents('.question').fadeOut(300,
                        function () {
                            $(this).remove();
                        });
                });
                
            });
            
        });
    }
    
})(jQuery);