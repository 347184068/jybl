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
    <!--App自定义的css-->
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/book_details.css">
    <link href="${ctxStatic}/jybl/css/mui.picker.css" rel="stylesheet"/>
    <link href="${ctxStatic}/jybl/css/mui.poppicker.css" rel="stylesheet"/>
</head>
<body>
<!--底部-->
<div class="bottom_click_div">
    <button id="reserve" type="button" class="mui-btn">预定</button>
    <button id="showstate" type="button">${borrowInfo eq null ? '未' :'已'}借</button>
    <button id="collection" type="button" class="mui-btn">续借</button>
</div>
<header class="mui-bar mui-bar-nav">
    <a class="mui-icon mui-icon-left-nav mui-pull-left mui-action-back"></a>
    <h1 class="mui-title">书籍详情</h1>
</header>
<!--内容-->
<div class="mui-content">
    <div class="com-item-list mt_5">
        <div class="horizontal-box">
            <div class="com-item-thumb">
                <img id="bookImage" style="width: 84px;height: 120px;" src="${book.bookImage}"/>
            </div>
            <div class="com-item-list-details xq_tit">
                <span id="bookName" style="margin-left: 25px;" class="x_t">${book.bookName}</span>
                <p class="tit_2" id="bookAuthor"><span>作者：</span>${book.bookAuthor}</p>
                <p class="tit_3" id="bookCategory"><span>类别：</span>${book.categoryCustomer.categoryName}</p>
                <p id="bookIsbn" class="tit_3"><span>ISBN：</span>${book.bookIsbn}</p>
                <p class="tit_3" id="bookPublisher"><span>出版社</span><em id="publish">${book.bookPublisher.publisherName}</em>藏书量:<em
                        id="shengyu">${book.bookCollections}</em></p>
            </div>
        </div>
    </div>

</div>

<!--简介-->
<div class="mui-card" style="border: none">
    <div class="mui-card-header">简介:</div>
    <div class="mui-card-content">
        <div class="mui-card-content-inner">
            ${book.bookContents}
        </div>
    </div>
</div>

<!--导读-->
<div class="mui-card" style="border: none">
    <div class="mui-card-header">导读:</div>
    <div class="mui-card-content">
        <div class="mui-card-content-inner">
            ${book.bookGuide}
        </div>
    </div>
    <!--<div class="mui-card-footer">页脚</div>-->
</div>

<!--目录-->
<div class="mui-card" style="border: none">
    <div class="mui-card-header">目录:</div>
    <div class="mui-card-content">
        <p id="mulutext">
            ${book.bookDirectory}
        </p>
    </div>
</div>

<div class="mui-card" style="border: none">
    <div class="mui-card-header" style="font-size: 18px">相关</div>
    <div class="mui-card-content">
        <ul class="tabbar_ul">
            <c:forEach items="${bookList}" var="book">
                <li>
                    <a href="${ctp}/f/weixin/bookDetail?bookId=${book.bookId}">
                        <div class="tabbar_div"><img src="${book.bookImage}" alt=""></div>
                        <span class="tabbar_ul_span">${book.bookName}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <!--<div class="mui-card-footer">页脚</div>-->
</div>

<div class="seat"></div>
</div>

<script src="${ctxStatic}/mui/mui.min.js"></script>
<%--<script src="${ctp}/js/jybl/book_details.js"></script>--%>
<script src="${ctp}/js/jybl/mui.picker.js"></script>
<script src="${ctp}/js/jybl/mui.poppicker.js"></script>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script>
    $(function () {
        mui.init();
        mui.ready(function() {
            var _getParam = function(obj, param) {
                return obj[param] || '';
            };
            //普通示例
            var userPicker = new mui.PopPicker();
            userPicker.setData([{
                value: '1',
                text: '一天'
            }, {
                value: '2',
                text: '两天'
            }, {
                value: '3',
                text: '三天'
            }]);
            var showUserPickerButton = document.getElementById('reserve');
            showUserPickerButton.addEventListener('tap', function(event) {
                userPicker.show(function(items) {
                    $.ajax({
                        url : '${ctp}/f/weixin/bookReserve',
                        data :{bookId:'${book.bookId}',borrowTime:items[0].value},
                        type :'post',
                        dataType :'json',
                        success : function (data) {
                            mui.alert(data.msg);
                        }
                    });
                });
            }, false);
        });
        $("#collection").click(function () {
            $.ajax({
                url : '${ctp}/f/weixin/bookRenew',
                data :{bookId:'${book.bookId}'},
                type :'post',
                dataType :'json',
                success : function (data) {
                    mui.alert(data.msg);
                }
            });
        });
    });
</script>
<script>

</script>
</body>
</html>