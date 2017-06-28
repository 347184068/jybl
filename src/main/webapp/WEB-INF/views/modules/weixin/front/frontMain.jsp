<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>借阅伴侣</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!--标准mui.css-->
    <link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/tabbar.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/html2.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jybl/css/feedback.css"/>
    <style type="text/css">
        a:link, a:visited {
            color: #444;
            text-decoration: none;
        }

        a:hover {
            color: #007AFF;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            width: 100%;
            height: 40vw;
        }
    </style>

</head>

<body>
<nav class="mui-bar mui-bar-tab">
    <a class="mui-tab-item mui-active" href="#tabbar">
        <span class="mui-icon mui-icon-home"></span>
        <span class="mui-tab-label">首页</span>
    </a>
    <a class="mui-tab-item" href="#tabbar-with-chat">
        <span class="mui-icon iconfont icon-icon02"></span>
        <span class="mui-tab-label">好文推荐</span>
    </a>
    <a class="mui-tab-item" href="#tabbar-with-contact">
        <span class="mui-icon iconfont icon-jinhuodan"></span>
        <span class="mui-tab-label">借书单</span>
    </a>
    <a class="mui-tab-item" href="#tabbar-with-map">
        <span class="mui-icon mui-icon-contact"></span>
        <span class="mui-tab-label">我的</span>
    </a>
</nav>
<div class="mui-content">
    <!--第一个底部导航栏对应的page-->
    <div id="tabbar" class="mui-control-content mui-active">
        <div class="mui-slider">
            <!--图片轮播-->
            <div class="mui-slider-group mui-slider-loop">
                <c:forEach items="${articles}" var="article">
                    <div class="mui-slider-item"><a href="${ctp}/f/weixin/getBookArticle?id=${article.id}">
                        <img src="${article.coverimg}" alt="${article.title}"/></a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!--搜索框-->
        <div id="searchDiv" class="mui-input-row mui-search" style="width: 80%;margin: 15px auto 0 auto">
            <input id="searchInput" type="search" class="mui-input-clear" placeholder="搜一搜">
        </div>

        <!--图书导航-->
        <div class="mui-card " style="width: 100%;margin: 10px 0 0 0;border: none;">
            <div class="mui-card-header">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell">
                                 <span style="color: #007aff;margin-left: 3px;font-size: 20px"
                                       class="mui-icon iconfont icon-daohang"></span>
                        <span class="mui-tab-label">图书导航</span>
                    </li>
                </ul>
            </div>
            <div class="mui-card-content">
                <table class="tabbar_tb">
                    <tr>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=d759478d38744403baf48483bd6aa9eb">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-jisuanji"></i>
                                    <span class="mui-tab-label">计算机</span>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=3eba5c033108454b89f1da012f72d88e">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-wenxue"></i>
                                    <span class="mui-tab-label">文学</span>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=9426d613c87a404ea235edc196693318">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-yishu"></i>
                                    <span class="mui-tab-label">艺术</span>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=8e29abd353604acf957a03a9de2d7012">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-kexue"></i>
                                    <span class="mui-tab-label">科学</span>
                                </div>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=61de94f1583f4a57b4ab3dcac93e1452">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-lishi"></i>
                                    <span class="mui-tab-label">历史</span>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=e84dfe4bf9a54bcd8442d2a1eb187a83">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-yixueziliao"></i>
                                    <span class="mui-tab-label">医学</span>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a href="${ctp}/f/weixin/category?categoryId=c61cf3fe24b2490cb45aba19d7267887">
                                <div class="tb_div">
                                    <i style="" class="mui-icon iconfont icon-junshi"></i>
                                    <span class="mui-tab-label">军事</span>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a href="${ctp}/f/weixin/category">
                                <div class="tb_div">
                                    <i style="color: #2ac845" class="mui-icon iconfont icon-tag22"></i>
                                    <span class="mui-tab-label">更多</span>
                                </div>
                            </a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <!--你懂得-->
        <div class="card_content" style="margin-top: 10px;">
            <!--好书推荐卡片-->
            <div class="mui-card" style="width: 100%;margin: 0 0 0 0;border: none;">
                <div class="mui-card-header">
                    <ul class="mui-table-view">
                        <li class="mui-table-view-cell">
                                 <span style="color: #007aff;margin-left: 3px;font-size: 20px"
                                       class="mui-icon iconfont icon-jisuanji"></span>
                            <span style="" class="mui-tab-label">随机好书</span>
                        </li>
                    </ul>
                </div>
                <div class="mui-card-content" style="padding: 0">
                    <ul class="tabbar_ul">
                        <c:forEach items="${recommendBook}" var="book">
                            <li>
                                <a href="${ctp}/f/weixin/bookDetail?bookId=${book.bookId}">
                                    <div class="tabbar_div">
                                        <img src="${book.bookImage}">
                                    </div>
                                    <span class="mui-tab-label tabbar_span">${book.bookName}</span>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

        </div>

    </div>
    <!--第二个底部导航栏对应的page-->
    <div id="tabbar-with-chat" class="mui-control-content">
        <c:forEach items="${bookarticleList}" var="article">
            <div class="mui-card1">
                <div class="mui-card-header1 mui-card-media1">
                    <img src="${ctxStatic}/jybl/img/articleImg.png"/>
                    <div class="mui-media-body1">
                            ${article.title}
                        <p>${article.date}&nbsp;${article.author}</p>
                    </div>
                </div>
                <div class="mui-card-content1">
                    <img src="${article.coverimg}" alt="" width="100%"/>
                </div>
                <div class="mui-card-footer1">
                    <a class="mui-card-link1"></a>
                    <a class="mui-card-link1" href="${ctp}/f/weixin/getBookArticle?id=${article.id}">查看</a>
                </div>
            </div>
        </c:forEach>
    </div>
    <!--第三个底部导航栏对应的page-->
    <div id="tabbar-with-contact" class="mui-control-content">
        <div class="top_div3">
            <div class="top_div3_left" id="scan">
                <ul class="top_div3_ul">
                    <li><span style="font-size: 12vw;" class="mui-icon iconfont icon-saoyisao"></span></li>
                    <li style="font-size: 2.7vw">扫一扫</li>
                </ul>
            </div>
            <div class="top_div3_middle" id="borrowBook">
                <ul class="top_div3_ul">
                    <li><span style="font-size: 12vw;" class="mui-icon iconfont icon-jie"></span></li>
                    <li style="font-size: 2.7vw">借书</li>
                </ul>
            </div>
            <div class="top_div3_right" id="returnBook">
                <ul class="top_div3_ul">
                    <li><span style="font-size: 12vw;" class="mui-icon iconfont icon-guihuan1"></span></li>
                    <li style="font-size: 2.7vw">还书</li>
                </ul>
            </div>
        </div>

        <ul class="mui-table-view">
            <li class="mui-table-view-cell mui-collapse mui-active">
                <a class="mui-navigate-right">借书栏</a>
                <div style="background-color: #EFEFF4;" class="mui-collapse-content">
                    <ul class="book_ul" id="book_ul">

                    </ul>
                </div>
            </li>
        </ul>


        <div class="qrcode">
            <table class="qrimage-wrap white b-a text-center">
                <tbody>
                <tr>
                    <td id="wrap" width="180px" height="180px">
                        <span id="qrimage" class="f-16 text-darkgrey">点击借书添加图书<br>点击按钮生成二维码</span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div style="margin-top: 20px;width: 100%;text-align: center">
            <button onclick="generate()" style="margin-top: 10px;border-radius: 15px;width: 50%;margin: 0 auto"
                    class="mui-btn mui-btn-green">生成借书码
            </button>
        </div>
    </div>

    <!--第四个底部导航栏对应的page-->
    <div id="tabbar-with-map" class="mui-control-content">

        <div class="mui-views mui-fullscreen mui-control-content mui-active">
            <!--页面主结构开始-->
            <div id="app" class="">
                <div class="">
                    <div class="mui-navbar">
                    </div>
                    <div class="mui-pages">
                    </div>
                </div>
            </div>
            <!--页面主结构结束-->

            <!--单页面开始-->
            <div id="setting" class="mui-page">
                <!--页面标题栏开始-->
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <h1 class="mui-center mui-title">我的</h1>
                </div>
                <!--页面标题栏结束-->
                <!--页面主内容区开始-->
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <!--个人信息-->
                            <ul style="" class="mui-table-view mui-table-view-chevron">
                                <li style="background-color: #fff;" class="mui-table-view-cell mui-media">
                                    <a class="mui-navigate-right" href="#account">
                                        <img class="mui-media-object mui-pull-left head-img" id="head-img"
                                             src="${userInfo.wxImg}">
                                        <div class="mui-media-body">
                                            <span id="personNameS">${userInfo.personName}</span>
                                            <p class='mui-ellipsis' id="phoneNumberP">${userInfo.phoneNumber}</p>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                            <ul class="mui-table-view mui-table-view-chevron">
                                <li style="background-color: #fff;" class="mui-table-view-cell">
                                    <a href="#recommend" class="mui-navigate-right">个性推荐 </a>
                                </li>
                            </ul>
                            <!--借书历史-->
                            <ul style="margin-top: 5vw;" class="mui-table-view mui-table-view-chevron">
                                <li style="background-color: #fff;" class="mui-table-view-cell">
                                    <a href="#borrowhistory" class="mui-navigate-right">借书列表</a>
                                </li>
                                <!--我的超期记录-->
                                <li style="background-color: #fff;" class="mui-table-view-cell">
                                    <a href="#scheduled" class="mui-navigate-right">预定列表</a>
                                </li>
                                <li style="background-color: #fff;" class="mui-table-view-cell">
                                    <a href="#overdue" class="mui-navigate-right">超期列表</a>
                                </li>
                            </ul>
                            <ul style="margin-top: 10vw" class="mui-table-view mui-table-view-chevron">
                                <li style="background-color: #fff;" class="mui-table-view-cell">
                                    <a href="#privacy" class="mui-navigate-right">设置 </a>
                                </li>
                            </ul>
                            <p style="margin-top: 10px;text-align: center;color: #ccc;text-indent: 0;">当前版本：<span
                                    id="version">1.0.0</span></p>
                        </div>
                    </div>
                </div>
                <!--页面主内容区结束-->
            </div>
            <!--单页面结束-->
            <!--个人信息界面-->
            <div id="account" class="mui-page">
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <button type="button"
                            class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
                        <span class="mui-icon mui-icon-left-nav"></span>返回
                    </button>
                    <h1 class="mui-center mui-title">账号设置</h1>
                    <button id="xiugaibtn" onclick="canmodify()" type="button"
                            class="mui-left  mui-btn  mui-btn-link mui-btn-nav mui-pull-right">
                        修改
                    </button>
                </div>
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <form id="userInfo">
                                <input type="hidden" name="id" value="${userInfo.id}">
                                <input type="hidden" name="openid" value="${userInfo.openid}">
                                <ul class="mui-table-view">
                                    <li class="mui-table-view-cell">
                                        <a>姓名:<input readonly="readonly" name="personName"
                                                     value="${userInfo.personName}"
                                                     class="mui-pull-right personal_input"></a>
                                    </li>

                                </ul>
                                <ul class="mui-table-view">
                                    <li class="mui-table-view-cell">
                                        <a>邮箱地址:<input readonly="readonly" name="email" value="${userInfo.email}"
                                                       class="mui-pull-right personal_input"></a>
                                    </li>
                                    <li class="mui-table-view-cell">
                                        <a>手机账号:<input readonly="readonly" name="phoneNumber"
                                                       value="${userInfo.phoneNumber}"
                                                       class="mui-pull-right personal_input"></a>
                                    </li>
                                    <li class="mui-table-view-cell">
                                        <a>身份证号:<input readonly="readonly" name="idCard"
                                                       value="${userInfo.idCard}"
                                                       class="mui-pull-right personal_input"></a>
                                    </li>
                                </ul>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--借书界面-->
            <div id="borrowhistory" class="mui-page">
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <button type="button"
                            class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
                        <span class="mui-icon mui-icon-left-nav"></span>返回
                    </button>
                    <h1 class="mui-center mui-title">借书记录</h1>
                </div>
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper" >
                        <div class="mui-scroll" >
                            <ul class="mui-table-view"id="borrowBookList">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!--预定-->
            <div id="scheduled" class="mui-page">
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <button type="button"
                            class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
                        <span class="mui-icon mui-icon-left-nav"></span>返回
                    </button>
                    <h1 class="mui-center mui-title">预定记录</h1>
                </div>
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll" >
                            <ul class="mui-table-view" id="reserveBookList">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!--设置-->
            <div id="privacy" class="mui-page">
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <button type="button"
                            class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
                        <span class="mui-icon mui-icon-left-nav"></span>设置
                    </button>
                </div>
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <ul class="mui-table-view">
                                <li class="mui-table-view-divider">还书提醒</li>
                                <li class="mui-table-view-cell">
                                    推荐提醒
                                    <div class="mui-switch mui-switch-mini" id="tuijiantixing">
                                        <div class="mui-switch-handle"></div>
                                    </div>
                                </li>
                                <li class="mui-table-view-cell" id="pinglvshijian">
                                    推荐频率
                                    <ul class="mui-table-view mui-table-view-radio">
                                        <li class="mui-table-view-cell">
                                            <a class="mui-navigate-right">
                                                24小时
                                            </a>
                                        </li>
                                        <li class="mui-table-view-cell mui-selected">
                                            <a class="mui-navigate-right">
                                                12小时
                                            </a>
                                        </li>
                                        <li class="mui-table-view-cell">
                                            <a class="mui-navigate-right">
                                                6小时
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                            <ul class="mui-table-view">
                                <li class="mui-table-view-cell">
                                    还书提醒
                                    <div class="mui-switch mui-active mui-switch-mini">
                                        <div class="mui-switch-handle"></div>
                                    </div>
                                </li>
                                <li class="mui-table-view-divider">开启后，将在后台提醒你还书</li>
                            </ul>

                        </div>
                    </div>
                </div>
            </div>
            <div id="overdue" class="mui-page">
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <button type="button"
                            class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
                        <span class="mui-icon mui-icon-left-nav"></span>返回
                    </button>
                    <h1 class="mui-center mui-title">超期记录</h1>
                </div>
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll" >
                            <ul class="mui-table-view" id="overBookList">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div id="recommend" class="mui-page">
                <div class="mui-navbar-inner mui-bar mui-bar-nav">
                    <button type="button"
                            class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
                        <span class="mui-icon mui-icon-left-nav"></span>返回
                    </button>
                    <h1 class="mui-center mui-title">推荐记录</h1>
                </div>
                <div class="mui-page-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll" >
                            <ul class="mui-table-view" id="recommendBookList">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript" src="${ctxStatic}/weixin/jweixin-1.0.0.js"></script>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script src="${ctp}/js/jybl/tabbar.js"></script>
<script src="${ctp}/js/mui/mui.view.js "></script>
<script src="${ctp}/js/jybl/qrcode.min.js"></script>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script>
    mui.init();
    //初始化单页view
    var viewApi = mui('#app').view({
        defaultPage: '#setting'
    });
    //初始化单页的区域滚动
    mui('.mui-scroll-wrapper').scroll();

    var view = viewApi.view;
    (function ($) {
        //处理view的后退与webview后退
        var oldBack = $.back;
        $.back = function () {
            if (viewApi.canBack()) { //如果view可以后退，则执行view的后退
                viewApi.back();
            } else { //执行webview后退
                oldBack();
            }

        };
        //监听页面切换事件方案1,通过view元素监听所有页面切换事件，目前提供pageBeforeShow|pageShow|pageBeforeBack|pageBack四种事件(before事件为动画开始前触发)
        //第一个参数为事件名称，第二个参数为事件回调，其中e.detail.page为当前页面的html对象
        view.addEventListener('pageBeforeShow', function (e) {
            //				console.log(e.detail.page.id + ' beforeShow');
            switch (e.detail.page.id){
                case "borrowhistory":
                    getBookList("1");
                    break;
                case "scheduled":
                    getBookList("2");
                    break;
                case "overdue":
                    getBookList("3");
                    break;
                case "recommend":
                    getBookList("4");
                    break;
            }
        });
        view.addEventListener('pageShow', function (e) {
            //				console.log(e.detail.page.id + ' show');
        });
        view.addEventListener('pageBeforeBack', function (e) {
            //				console.log(e.detail.page.id + ' beforeBack');
            refreshPersonInfo();


        });
        view.addEventListener('pageBack', function (e) {
            //				console.log(e.detail.page.id + ' back');
        });

        var btnArray = ['确认', '取消'];
        //第一个demo，拖拽后显示操作图标，点击操作图标删除元素；
        $('.book_ul').on('tap', '.mui-btn', function (event) {
            var elem = this;
            var li = elem.parentNode.parentNode;
            mui.confirm('', '确定删除？', btnArray, function (e) {
                if (e.index == 0) {
                    var num = (li.getElementsByClassName("right_sort")[0].innerText) * 1 - 0;
                    if (num >= 2) {
                        li.getElementsByClassName("right_sort")[0].innerText = li.getElementsByClassName("right_sort")[0].innerText - 1;
                        setTimeout(function () {
                            $.swipeoutClose(li);
                        }, 0);
                    } else if (num === 1) {
                        li.parentNode.removeChild(li);
                    }
                } else {
                    setTimeout(function () {
                        $.swipeoutClose(li);
                    }, 0);
                }
            });
        });
    })(mui);
    function refreshPersonInfo() {
        $.ajax({
            url: '${ctp}/f/weixin/getUserInfo',
            dataType: 'json',
            success: function (data) {
                $("#head-img").attr("src", data.wxImg);
                $("#personNameS")[0].innerText = data.personName;
                $("#phoneNumberP")[0].innerText = data.phoneNumber;
            }
        });
    }


    function getBookList(type) {
        var url = "";
        var divId = "";
        if(type == "1"){
            //借书历史
            url = "${ctp}/f/weixin/getBorrowBook";
            divId = "borrowBookList";
        }else if(type == "2"){
            //预定记录
            url = "${ctp}/f/weixin/getReserveBook";
            divId = "reserveBookList";
        }else if(type=="3"){
            //超期记录
            url = "${ctp}/f/weixin/getOverTimeBook";
            divId = "overBookList";
        }else{
            url = "${ctp}/f/weixin/recommend";
            divId = "recommendBookList";
        }
        var $divContent = $("#"+divId);
        $divContent.empty();
        $.ajax({
            url : url,
            type : "post",
            dataType : "json",
            success : function (bookList) {
                if(bookList.length == 0){
                    $divContent.parent(".mui-scroll").append('<div style="width: 100%;text-align:center ; margin: 10px auto 0 auto">无记录</div>');
                }
                for(var i = 0 ; i < bookList.length ;i++){
                    var b = bookList[i].book;
                    var message = bookList[i].msg
                    var str ='<a href=${ctp}/f/weixin/bookDetail?bookId='+b.bookId+'><li class="mui-table-view-cell">'+
                            '<div class="right_div">' +
                            '<img class="right_div_img" src="' + b.bookImage + '">' +
                            '<ul style=" position: relative;list-style: none;float: left;padding-left: 70px;">' +
                            '<li style="width: 100%;">' +
                            '<span class="right_title">' + b.bookName + '</span>' +
                            '</li>' +
                            '<li style="width: 100%;margin-top: 7px">' +
                            '<span class="right_content">' + b.bookContents + '</span></li>' +
                            '<li style="width: 100%;margin-top: 1px">' +
                            '<span style="float:left;color: #7d7b80;font-size: 16px;margin-top: 12px;" class="mui-icon iconfont icon-zuozhe"></span>' +
                            '<span class="right_author">' + b.bookAuthor + '</span><span class="right_sort">' + message + '</span>' +
                            '</li>' +
                            '</ul>' +
                            '</div></li></a>';
                    $divContent.append(str);
                }
            }
        });

    }


    document.querySelector("#returnBook").onclick = function () {
        location.href = "${ctp}/f/weixin/returnBook";
    }
</script>
<script>
    function canmodify() {
        var xiugaibtn = document.getElementById("xiugaibtn");
        if (xiugaibtn.innerText === '修改') {
            xiugaibtn.innerText = "完成"
            var inputlist = document.getElementsByClassName("personal_input");
            for (var i = 0; i < inputlist.length; i++) {
                inputlist[i].readOnly = "";
                inputlist[i].style.backgroundColor = "#b0ffee"
            }
        } else if (xiugaibtn.innerText === '完成') {
            xiugaibtn.innerText = "修改";
            var inputlist = document.getElementsByClassName("personal_input");
            for (var i = 0; i < inputlist.length; i++) {
                inputlist[i].readOnly = "readonly";
                inputlist[i].style.backgroundColor = "#efeff4"
            }
            var btnArray = ['否', '是'];
            mui.confirm('', '是否保存', btnArray, function (e) {
                if (e.index == 1) {
                    updatePersonInfo();
                    mui.back();
                } else {

                }
            })


        }
    }

    document.querySelector('.mui-table-view.mui-table-view-radio').addEventListener('selected', function (e) {
        var value = "当前选中的为：" + e.detail.el.innerText;
    });


    function updatePersonInfo() {
        $.ajax({
            url: '${ctp}/f/weixin/updateInfo',
            data: $("#userInfo").serialize(),
            dataType: 'json',
            success: function (data) {
                mui.alert(data.msg);
            }
        });
    }

</script>
<script>
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '${appId}', // 必填，公众号的唯一标识
        timestamp:${timestamp}, // 必填，生成签名的时间戳
        nonceStr: '${nonceStr}', // 必填，生成签名的随机串
        signature: '${signature}',// 必填，签名，见附录1
        jsApiList: [
            'scanQRCode',
            'chooseWXPay'] // 必填，需要使用的JS接口记录，所有JS接口列表见附录2
    });
    wx.ready(function () {

        document.querySelector('#scan').onclick = function scan() {
            wx.scanQRCode({
                needResult: 1,
                desc: '扫一扫获取书籍详情',
                success: function (res) {
                    queryBook(res.resultStr);
                }
            });
        };

        document.querySelector('#borrowBook').onclick = function () {
            wx.scanQRCode({
                needResult: 1,
                desc: '添加到借书栏',
                success: function (data) {
                    addBook(data.resultStr);
                }
            });
        };


        function queryBook(id) {
            $.ajax({
                url: '${ctp}/f/weixin/isBookExist',
                type: 'post',
                data: {bookId: id},
                datatype: 'json',
                success: function (data) {
                    if (data.status == "false") {
                        mui.alert(data.msg);
                    } else {
                        window.location.href = "${ctp}/f/weixin/bookDetail?bookId=" + id;
                    }
                }
            });
        }

        function addBook(id) {
            var $ulDiv = $("#book_ul");
            var bookLength = 0;
            var $children = $ulDiv.children('li');
            bookLength = $children.length;
            var bookCountList = document.querySelectorAll(".right_sort");
            var sum = 0;
            for (var i = 0; i < bookCountList.length; i++) {
                sum += (bookCountList[i].innerText * 1 - 0);
            }
            if (bookLength >= 2 || sum >= 2) {
                mui.alert("最大可借阅两本书");
                return;
            }
            var isBookContians = false;
            var $li = $ulDiv.children('li[name=' + id + ']');
            var len = $li.length;
            if (len !== 0) {
                isBookContians = true;
            }
            if (isBookContians) {
                var $countSpan = $li.find('#' + id);
                var currentCount = $countSpan.text();
                var newCount = parseInt(currentCount) + 1;
                $countSpan.html(newCount);
                return;
            }
            $.ajax({
                url: '${ctp}/f/weixin/addBook',
                type: 'post',
                data: {bookId: id},
                datatype: 'json',
                success: function (data) {
                    if (data.status == "false") {
                        //添加图书失败
                        mui.alert(data.msg);
                    } else {
                        var book = data.entity;
                        var str =
                                '<div class="right_div">' +
                                '<img class="right_div_img" src="' + book.bookImage + '">' +
                                '<ul style=" position: relative;list-style: none;float: left;padding-left: 70px;">' +
                                '<li style="width: 100%;">' +
                                '<span class="right_title">' + book.bookName + '</span>' +
                                '</li>' +
                                '<li style="width: 100%;margin-top: 7px">' +
                                '<span class="right_content">' + book.bookContents + '</span></li>' +
                                '<li style="width: 100%;margin-top: 1px">' +
                                '<span style="float:left;color: #7d7b80;font-size: 16px;margin-top: 12px;" class="mui-icon iconfont icon-zuozhe"></span>' +
                                '<span class="right_author">' + book.bookAuthor + '</span><span class="right_sort" id="' + book.bookId + '">' + 1 + '</span>' +
                                '</li>' +
                                '</ul>' +
                                '</div>';
                        var ss = '<li style="padding: 0" class="mui-table-view-cell" name = "' + book.bookId + '">' +
                                '<div class="mui-slider-right mui-disabled">' +
                                '<a class="mui-btn mui-btn-red">删除</a>' +
                                '</div>' +
                                '<div style="background-color:transparent;" class="mui-slider-handle">' +
                                str +
                                '</div>' +
                                '</li>';

                        $ulDiv.append(ss);
                        mui.alert(data.msg);
                    }

                }
            });
        }
    });
</script>

<script type="application/javascript">
    $(function () {
        var $search = $("#searchDiv").find("#searchInput");
        $search.focus(function () {
            window.location.href = "${ctp}/f/weixin/searchView";
        });
    });

    function generate() {
        var $ulDiv = $("#book_ul");
        var bookLength = 0;
        var $children = $ulDiv.children('li');
        bookLength = $children.length;
        if (bookLength == 0) {
            mui.alert("请添加书籍");
            return;
        }
        var openid = '${userInfo.openid}';
        var str = '{"userId":"' + openid + '","bookList": [';
        var length = bookLength;
        var right_span = document.querySelectorAll(".right_sort");
        for (var i = 0; i < length; i++) {
            str = str + '{"bookId": "' + right_span[i].id + '","count": "' + right_span[i].innerText + '"}'
            if (i !== length - 1) {
                str = str + ",";
            }
        }
        str = str + ']}';
        var wrap = document.getElementById("wrap");
        var qrImg = document.getElementById("qrimage");
        if (qrImg != null) {
            wrap.removeChild(qrImg);
        }
        var $wrap = $("#wrap");
        $wrap.empty();
        var qrcode = new QRCode(wrap, {
            width: 180,
            height: 180,
            colorDark: '#000000',
            colorLight: '#ffffff',
            correctLevel: QRCode.CorrectLevel.H
        });
        qrcode.clear();
        qrcode.makeCode(str);
    }

    //    推荐频率显示
    document.getElementById("tuijiantixing").addEventListener("toggle", function (event) {
        if (event.detail.isActive) {
            document.getElementById("pinglvshijian").style.display = "none";
        } else {
            document.getElementById("pinglvshijian").style.display = "block";
        }
    });

</script>
</html>