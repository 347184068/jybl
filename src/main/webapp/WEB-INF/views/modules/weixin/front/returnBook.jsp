<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>借阅伴侣</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!--标准mui.css-->
    <link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">
    <link href="${ctxStatic}/jybl/css/mui.indexedlist.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/borrow_books.css">
    <style>
        .qrcode {
            position: relative;
            border-radius: 3px;
            width: 180px;
            margin: 10px auto 8px;
            border: 1px solid rgba(120,130,140,.25);
        }
        .qrimage-wrap {
            width: 180px;
            height: 180px;
            margin: auto;
        }
        .qrimage-wrap td {
            margin: 0;
            text-align: center;
        }
        .qrimage-wrap img {
            max-width: 180px;
            max-height: 180px;
            display: inline-block;
            margin: auto;
            vertical-align: middle;
        }

    </style>
</head>
<body>
<div class="mui-content">
    <header class="mui-bar mui-bar-nav">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">归还图书</h1>
        <a id='done' class="mui-btn mui-btn-link mui-pull-right mui-btn-blue mui-disabled">还书</a>
    </header>
    <div class="mui-content">
        <div id='list' class="mui-indexed-list">
            <div class="mui-indexed-list-inner">
                <div class="mui-scroll-wrapper" >
                    <div class="mui-scroll" >
                        <ul id="ulid" class="mui-table-view">
                            <c:forEach items="${borrowList}" var="book">
                                <li data-value="AKU" data-tags=""
                                    class="mui-table-view-cell mui-indexed-list-item mui-checkbox mui-left">
                                    <input style="line-height: 90px" type="checkbox"/>
                                    <div class="right_div"><img class="right_div_img" src="${book.bookImage}">
                                        <ul style=" position: relative;list-style: none;float: left;padding-left: 70px;">
                                            <li style="width: 100%;"><span
                                                    class="right_title">${book.bookName}</span></li>
                                            <li style="width: 100%;margin-top: 7px"><span class="right_content">
                                                    ${book.bookContents}</span></li>
                                            <li style="width: 100%;margin-top: 1px"><span
                                                    style="float:left;color: #7d7b80;font-size: 16px;margin-top: 12px;"
                                                    class="mui-icon iconfont icon-zuozhe"></span><span class="right_author">${book.bookAuthor}</span><span
                                                    class="right_sort" id="${book.bookId}">${book.categoryCustomer.categoryName}</span></li>
                                        </ul>
                                    </div>
                                </li>
                            </c:forEach>
                            <div class="qrcode">
                                <table class="qrimage-wrap white b-a text-center">
                                    <tbody>
                                    <tr>
                                        <td id="wrap" width="180px" height="180px">
                                            <span id="qrimage" class="f-16 text-darkgrey">点击图书选中<br>点击还书按钮生成二维码</span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script src="${ctp}/js/jybl/mui.indexedlist.js"></script>
<script src="${ctp}/js/jybl/qrcode.min.js"></script>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script>
    var $ulDiv = $("#ulid");
    var $children = $ulDiv.children('li');
    bookLength = $children.length;
    if(bookLength == 0){
        $ulDiv.html('<li class="mui-table-view-cell mui-indexed-list-item mui-center">无借阅记录</li>');
    }
</script>
<script type="text/javascript" charset="utf-8">
    mui.init();
    mui.ready(function () {
        var header = document.querySelector('header.mui-bar');
        var list = document.getElementById('list');
        var done = document.getElementById('done');
        //calc hieght
        list.style.height = (document.body.offsetHeight - header.offsetHeight) + 'px';
        //done event
        done.addEventListener('tap', function () {
            var checkboxArray = [].slice.call(list.querySelectorAll('input[type="checkbox"]'));
            var checkedValues = [];
            var openid = '${userInfo.openid}';
            var str = '{"userId":"' + openid + '","bookList": [';
            checkboxArray.forEach(function (box) {
                if (box.checked) {
                    var right_span = box.parentNode.querySelectorAll('.right_sort');
                    checkedValues.push(box.parentNode.querySelectorAll('.right_title')[0].innerText);
                    str = str + '{"bookId": "' + right_span[0].id + '"}';
                    str = str + ",";
                }
            });
            str = str.substr(0,str.length-1);
            str = str + ']}';
//            mui.alert(str);
            if (checkedValues.length > 0) {
//                mui.alert('你选择了: ' + checkedValues);
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
            } else {
                mui.alert('你没选择任何图书');
            }
        }, false);
        mui('.mui-indexed-list-inner').on('change', 'input', function () {
            var count = list.querySelectorAll('input[type="checkbox"]:checked').length;
            var value = count ? "还书(" + count + ")" : "还书";
            done.innerHTML = value;
            if (count) {
                if (done.classList.contains("mui-disabled")) {
                    done.classList.remove("mui-disabled");
                }
            } else {
                if (!done.classList.contains("mui-disabled")) {
                    done.classList.add("mui-disabled");
                }
            }
        });
    });
</script>

<script>
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
        var str = '{"userId":' + openid + ',"bookList": [';
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
</script>
</body>
</html>