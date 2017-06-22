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
    <link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">
    <link href="${ctxStatic}/jybl/css/mui.picker.css" rel="stylesheet"/>
    <link href="${ctxStatic}/jybl/css/mui.poppicker.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/iconfont.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/search.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/classification.css">
</head>
<body>

<header class="top-bar on" id="J_Header">
    <div class="top-bar-w">
        <div class="top-bar-c">
            <a style="margin-top: 5px;padding: 0 5px" class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
            <div class="s-input-select">
                <div class="s-input-tab">
                    <div id="showtext" class="s-input-tab-txt">书名</div>
                </div>
                <div class="s-input-frame">
                    <form class="c-form-suggest" id="J_Search" onkeydown="if(event.keyCode==13){ showSearchResult(); return false;}">
                        <div class="s-form-search search-form">
                            <input id="selectType" name="type" type="hidden" value="bookName">
                            <input id="showinput" type="search" name="keyWord" class="J_autocomplete"
                                   autocomplete="off" value="">
                            <button style="display: none;"><span></span></button>
                        </div>
                        <span onclick="set_storage_record()" name="search"
                              class="mui-icon mui-icon-search icon_search"></span>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </div>
</header>
<div id="can_hidden" class="can_hidden">
    <div class="history_search_box">
        <span class="left_title">历史搜索</span>
        <span style="font-size: 20px" class="mui-icon mui-icon-trash right_delete" onclick="deletebtn()"></span>
    </div>

    <ul id="suggest-hotkey" class="suggest-hotkey" data-sugg-type="2">

    </ul>
</div>
<ul id="segmentedControlContents" class="mui-col-xs-12" style="display: none;list-style: none;padding-left: 10px;">

</ul>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script src="${ctp}/js/jybl/mui.picker.js"></script>
<script src="${ctp}/js/jybl/mui.poppicker.js"></script>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script>
    (function ($, doc) {
        $.init();
        $.ready(function () {
            /**
             * 获取对象属性的值
             * @param {Object} obj 对象
             * @param {String} param 属性名
             */
            var _getParam = function (obj, param) {
                return obj[param] || '';
            };
            //普通示例
            var userPicker = new $.PopPicker();
            userPicker.setData([{
                value: 'bookName',
                text: '书名'
            }, {
                value: 'bookIsbn',
                text: 'isbn'
            }, {
                value: 'bookAuthor',
                text: '作者'
            }, {
                value: 'publisher',
                text: '出版社'
            }]);
            var showinput = doc.getElementById('showinput');
            var showtext = doc.getElementById('showtext');
            var typeInput = doc.getElementById('selectType');
            showtext.addEventListener('tap', function (event) {
                userPicker.show(function (items) {
                    showtext.innerText = items[0].text;
                    typeInput.value = items[0].value;
                    //返回 false 可以阻止选择框的关闭
                    //return false;
                });
            }, false);

        });
    })(mui, document);
</script>
<script>
    var localstroage = window.localStorage;
    //    存储搜索记录
    function set_storage_record() {
        var input = document.getElementById('showinput');
        var stritem = input.value;
        if (stritem !== "") {
//            获得的字符串的个数
            var lenitem = (localstroage.getItem("length") - 0 * 1);
            var arrlist = new Array();
            for (var i = 0; i < lenitem; i++) {
                arrlist.push(localstroage.getItem("item" + i));
            }
            if (arrlist.indexOf(stritem) === -1) {
                localStorage.setItem("item" + lenitem, stritem);
                localstroage.setItem("length", (localstroage.getItem("length") - 0 * 1) + 1);
            }
        }
        showSearchResult();
    }
    window.onload = function () {
//        localstroage.removeItem("length");
        localstroage.getItem("length") ? {} : localstroage.setItem("length", 0);
        var localshow = document.getElementById("suggest-hotkey");
        var length = (localstroage.getItem("length") - 0 * 1);
        if(length == 0){
            $("#can_hidden").hide();
        }
        html = [];
        for (var i =  length - 1; i >= 0; i--) {
            console.log(localstroage.getItem("item" + i));
            html.push('<li class="append_div" onclick="li_click(this.innerText)">' + localstroage.getItem("item" + i) + '</li>');
        }
        localshow.innerHTML = html.join('');
    };
    function li_click(str) {
        var input = document.getElementById('showinput');
        input.value = str;
        showSearchResult();
    }

    //    删除历史记录事件
    function deletebtn() {
        var localshow = document.getElementById("suggest-hotkey");
        var btnArray = ['否', '是'];
        mui.confirm('是否删除所有搜索记录？', '', btnArray, function (e) {
            if (e.index == 1) {
                localstroage.removeItem("length");
                while (localshow.hasChildNodes()) {
                    localshow.removeChild(localshow.firstChild)
                }
            } else {

            }
        })
    }


    function showSearchResult(){
        $("#can_hidden").hide();
        var typeValue = $("#selectType").val();
        var keyWordValue = $("#showinput").val();
        $.ajax({
            url : '${ctp}/f/weixin/searchBook',
            type : 'post',
            data : {type : typeValue ,keyWord : keyWordValue},
            dataType : 'json',
            success : function (bookList) {
                $("#segmentedControlContents").show();

                mui.init({
                    swipeBack: false //启用右滑关闭功能
                });
                var contents = document.getElementById("segmentedControlContents");
                var html = [];
                for (var j = 0; j < bookList.length ; j++) {
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
                if(bookList.length == 0 ) {
                    html.push('<span class="none_info">没有搜索到相关记录</span>');
                }
                contents.innerHTML = html.join('');
            }
        });
    }
</script>
</body>
</html>