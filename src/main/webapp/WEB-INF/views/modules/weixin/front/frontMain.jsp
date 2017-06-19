<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
    <style type="text/css">
        a:link,a:visited{color:#444;text-decoration:none;}
        a:hover{color:#007AFF;}
    </style>
</head>

<body>
<nav class="mui-bar mui-bar-tab">
    <a class="mui-tab-item mui-active" href="#tabbar">
        <span class="mui-icon mui-icon-home"></span>
        <span class="mui-tab-label">首页</span>
    </a>
    <a class="mui-tab-item" href="#tabbar-with-chat">
        <span class="mui-icon iconfont icon-icon02"><span class="mui-badge">9</span></span>
        <span class="mui-tab-label">推荐</span>
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
                <!--支持循环，需要重复图片节点-->
                <div class="mui-slider-item mui-slider-item-duplicate"><a href="#"><img src="images/demo1.png"/></a>
                </div>
                <div class="mui-slider-item"><a href="#"><img
                        src="${ctxStatic}/images/swiper.png"/></a></div>
                <div class="mui-slider-item"><a href="#"><img
                        src="${ctxStatic}/images/swiper.png"/></a></div>
                <div class="mui-slider-item"><a href="#"><img
                        src="${ctxStatic}/images/swiper.png"/></a></div>
                <div class="mui-slider-item"><a href="#"><img
                        src="${ctxStatic}/images/swiper.png"/></a></div>
                <!--支持循环，需要重复图片节点-->
                <div class="mui-slider-item mui-slider-item-duplicate"><a href="#"><img src="images/demo1.png"/></a>
                </div>
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
        <div class="title">这是div模式选项卡中的第2个子页面，该页面展示一个消息列表</div>

        <ul class="mui-table-view mui-table-view-chevron">
            <li class="mui-table-view-cell"><a href="" class="mui-navigate-right">Item 1</a></li>
        </ul>

    </div>
    <!--第三个底部导航栏对应的page-->
    <div id="tabbar-with-contact" class="mui-control-content">
        <div class="title">这是div模式选项卡中的第3个子页面，该页面展示一个通讯录示例.</div>
        <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
            <li class="mui-table-view-cell">
                <div class="mui-slider-cell">
                    <div class="oa-contact-cell mui-table">
                        <div class="oa-contact-avatar mui-table-cell">
                            <img src="images/60x60.gif"/>
                        </div>
                        <div class="oa-contact-content mui-table-cell">
                            <div class="mui-clearfix">
                                <h4 class="oa-contact-name">叶文洁</h4>
                                <span class="oa-contact-position mui-h6">董事长</span>
                            </div>
                            <p class="oa-contact-email mui-h6">
                                yewenjie@sina.com
                            </p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="mui-table-view-cell">
                <div class="mui-slider-cell">
                    <div class="oa-contact-cell mui-table">
                        <div class="oa-contact-avatar mui-table-cell">
                            <img src="images/60x60.gif"/>
                        </div>
                        <div class="oa-contact-content mui-table-cell">
                            <div class="mui-clearfix">
                                <h4 class="oa-contact-name">艾AA</h4>
                                <span class="oa-contact-position mui-h6">总经理</span>
                            </div>
                            <p class="oa-contact-email mui-h6">
                                aaa@163.com
                            </p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="mui-table-view-cell">
                <div class="mui-slider-cell">
                    <div class="oa-contact-cell mui-table">
                        <div class="oa-contact-avatar mui-table-cell">
                            <img src="images/60x60.gif"/>
                        </div>
                        <div class="oa-contact-content mui-table-cell">
                            <div class="mui-clearfix">
                                <h4 class="oa-contact-name">罗辑</h4>
                                <span class="oa-contact-position mui-h6">员工</span>
                            </div>
                            <p class="oa-contact-email mui-h6">
                                luoji@126.com
                            </p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="mui-table-view-cell">
                <div class="mui-slider-cell">
                    <div class="oa-contact-cell mui-table">
                        <div class="oa-contact-avatar mui-table-cell">
                            <img src="images/60x60.gif"/>
                        </div>
                        <div class="oa-contact-content mui-table-cell">
                            <div class="mui-clearfix">
                                <h4 class="oa-contact-name">云天明</h4>
                                <span class="oa-contact-position mui-h6">员工</span>
                            </div>
                            <p class="oa-contact-email mui-h6">
                                ytm@163.com
                            </p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="mui-table-view-cell">
                <div class="mui-slider-cell">
                    <div class="oa-contact-cell mui-table">
                        <div class="oa-contact-avatar mui-table-cell">
                            <img src="images/60x60.gif"/>
                        </div>
                        <div class="oa-contact-content mui-table-cell">
                            <div class="mui-clearfix">
                                <h4 class="oa-contact-name">史强</h4>
                                <span class="oa-contact-position mui-h6">员工</span>
                            </div>
                            <p class="oa-contact-email mui-h6">
                                shiqiang@gmail.com
                            </p>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <!--第四个底部导航栏对应的page-->
    <div id="tabbar-with-map" class="mui-control-content">
        <div class="title">这是div模式选项卡中的第4个子页面，该页面展示一个常见的设置示例.</div>
        <ul class="mui-table-view">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    新消息通知
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    隐私
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    通用
                </a>
            </li>
        </ul>
        <ul class="mui-table-view" style="margin-top: 25px;">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    关于mui
                </a>
            </li>
        </ul>
        <ul class="mui-table-view" style="margin-top: 25px;">
            <li class="mui-table-view-cell">
                <a style="text-align: center;color: #FF3B30;">
                    退出登录
                </a>
            </li>
        </ul>
    </div>
</div>

</body>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script src="${ctp}/js/jybl/tabbar.js"></script>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script type="application/javascript">
    $(function () {
        var $search = $("#searchDiv").find("#searchInput");
        $search.focus(function () {
            window.location.href = "${ctp}/f/weixin/searchView";
        });
    });
</script>
</html>