/**
 *Created by fzc on 2018-5-22
 */
$(document).ready(function () {
    //窗口水平居中
    $(window).resize(function () {
        tc_center();
    });
    
    // 移动拖拽div
    $(".top_nav").mousedown(function (e) {
        $(this).css("cursor", "move");//改变鼠标指针的形状
        var offset = $(this).offset();//DIV在页面的位置
        var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离
        var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离
        $(document).bind("mousemove", function (ev) { //绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
            $(".popup").stop();//加上这个之后
            var _x = ev.pageX - x;//获得X轴方向移动的值
            var _y = ev.pageY - y;//获得Y轴方向移动的值
            $(".popup").animate({left: _x + "px", top: _y + "px"}, 10);
        });
        
    });
    
    $(document).mouseup(function () {
        $(".popup").css("cursor", "default");
        $(this).unbind("mousemove");
    });
    
});
