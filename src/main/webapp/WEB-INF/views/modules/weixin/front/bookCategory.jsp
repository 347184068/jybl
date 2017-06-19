<!DOCTYPE html>
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<head>
    <meta charset="utf-8">
    <title>借阅伴侣</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <!--标准mui.css-->
    <link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/classification.css">
</head>

<body>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">全部分类</h1>
</header>
<div class="mui-content mui-row mui-fullscreen">

    <div class="mui-col-xs-3">
        <div id="segmentedControls"
             class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-vertical">

        </div>
    </div>
    <div id="segmentedControlContents" class="mui-col-xs-9" style="border-left: 1px solid #c8c7cc;">

    </div>

    <div class="spinner" id="loadDiv">
        <div class="rect1"></div>
        <div class="rect2"></div>
        <div class="rect3"></div>
        <div class="rect4"></div>
        <div class="rect5"></div>
    </div>

</div>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script type="application/javascript">
    var leftMenuSize = 0;
    $(function () {
        $("#spinner").hide();
        var currentCategory = '${currentCategory}';
        //获取所有目录
        $.ajax({
            url: '${ctp}/f/weixin/getAllCategory',
            type: 'get',
            dataType: 'json',
            success: function (category) {
                leftMenuSize = category.length;
                mui.init({
                    swipeBack: true //启用右滑关闭功能
                });
                var controls = document.getElementById("segmentedControls");
                var html = [];
                var sort = '';
                var i = 0, j = 1, n = 11; //每个选项卡列表数量+1
                for (; i < category.length; i++) {
                    //创建子项
                    html.push('<a href="javascript:void(0);" class="mui-control-item" name="' + category[i].id + '">' + category[i].name + '</a> ');
                }
                controls.innerHTML = html.join('');
                controls.querySelector('.mui-control-item').classList.add('mui-active');
                var aTags = document.getElementsByClassName('mui-control-item');
                for (var i = 0; i < aTags.length; i++) {
                    aTags[i].addEventListener('tap', function () {
                        var categoryId = this.name;
                        loadBookByCategory(categoryId);
                    });
                }
                if (currentCategory != null && currentCategory != '') {
                    var evObj = new Event('tap');
                    if($('a').hasClass('mui-active')){
                        $('a').removeClass('mui-active');
                    }
                    $('a[name='+currentCategory+']').addClass('mui-active');
                    $('a[name='+currentCategory+']')[0].dispatchEvent(evObj);
                }else{
                    var evObj = new Event('tap');
                    aTags[0].dispatchEvent(evObj);
                }
            }
        });


    });


    function loadBookByCategory(categoryId) {
        $("#segmentedControlContents").hide();
        $("#loadDiv").show();
        $.ajax({
            url: '${ctp}/f/weixin/getBookByCategory?categoryId=' + categoryId,
            type: 'post',
            dateType: 'json',
            success: function (bookList) {
                $("#segmentedControlContents").show();
                $("#loadDiv").hide();
                var contents = document.getElementById("segmentedControlContents");
                var html = [];
                for (var i = 0; i < leftMenuSize; i++) {
                    html.push('<div id="content' + i + '" class="mui-control-content"><ul class="mui-table-view">');
                    for (var j = 0; j < bookList.length; j++) {
                        html.push();
                        html.push('<a href=${ctp}/f/weixin/bookDetail?bookId='+bookList[j].bookId+'><li class="mui-table-view-cell">' +
                                '<div class="right_div_div">' +
                                '<div class="right_div"><img class="right_div_img" src="' + bookList[j].bookImage + '">' +
                                '<ul style="width: 100%;position:relative;list-style: none;float: left;padding-left: 0;padding-right: 20px;">' +
                                '<li><div style="max-width: 100%;word-break: break-all;  text-overflow: ellipsis;  display: -webkit-box;  -webkit-box-orient: vertical;  -webkit-line-clamp: 2;  overflow: hidden;"><span class="right_title"> ' + bookList[j].bookName + '</span></div></li>' +
                                '<li style="margin-top: 7px"> <div class="right_content"> ' + bookList[j].bookContents + ' </div> </li>' +
                                '<li>' +
                                '<span style = "float:left;color: #7d7b80;font-size: 16px;margin-top: 12px;"class = "mui-icon iconfont icon-zuozhe"></span >' +
                                ' <div class = "right_author" >' + bookList[j].bookAuthor + '</div> ' +
                                '<span class = "right_sort" >' + bookList[j].categoryCustomer.categoryName + '</span> ' +
                                '</li>' +
                                '</ul ></div > ' +
                                '</div>' +
                                '</li></a>');
                    }
                    html.push('</ul></div>');
                }


                if(bookList.length == 0){
                    var html = [];
                    html.push('<span>当前分类下没有图书</span>');
                }
                contents.innerHTML = html.join('');
                contents.querySelector('.mui-control-content').classList.add('mui-active');
            }
        });
    }


</script>

</body>

</html>