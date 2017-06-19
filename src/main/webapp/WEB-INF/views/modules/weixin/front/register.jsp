<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>信息完善</title>
    <link rel="stylesheet" href="${ctxStatic}/mui/css/mui.min.css">
    <link href="${ctxStatic}/jybl/css/mui.picker.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/Information_perfect.css">
</head>

<body>
<div class="mui-card" style="margin: 44px 0 0 0">
    <div class="mui-card-content">
        <div class="mui-card-content-inner">
            <ul class="mui-table-view" style="">
                <li class="mui-table-view-divider">个人信息</li>
                <li id="nameli" class="mui-table-view-cell">
                    <a class="mui-navigate-right">
                        <span id="name" style="max-width: 80px" class="mui-badge mui-badge-primary">SB往哪跑</span>
                        姓名
                    </a>
                </li>
                <li id="sexli" class="mui-table-view-cell">
                    <a class="mui-navigate-right">
                        <span id="sex"  style="max-width: 80px" class="mui-badge mui-badge-primary">女</span>
                        性别
                    </a>
                </li>

                <li style="margin-top: 50px;" class="mui-table-view-divider">其他信息</li>
                <li id="numberli" class="mui-table-view-cell">
                    <a class="mui-navigate-right">
                        <span id="number"  style="max-width: 80px"  class="mui-badge mui-badge-primary">15966072812</span>
                        手机号绑定
                    </a>
                </li>
                <li id="idcardli" class="mui-table-view-cell">
                    <a class="mui-navigate-right">
                        <span id="idcard"  style="max-width: 80px"  class="mui-badge mui-badge-primary">370781199604174572</span>
                        身份证绑定
                    </a>
                </li>
                <li id="mailboxli" class="mui-table-view-cell">
                    <a  class="mui-navigate-right">
                        <span id="mailbox"  style="max-width: 80px"  class="mui-badge mui-badge-primary">1337968347@qq.com</span>
                        邮箱
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="sexdiv" class="mui-popup mui-popup-in" style="display: none;">
    <div class="mui-popup-inner">
        <div class="mui-card">
            <div class="mui-card-header">你的性别是？</div>
            <div class="mui-card-content">
                <ul class="mui-table-view mui-table-view-radio">
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right">
                            男
                        </a>
                    </li>
                    <li class="mui-table-view-cell mui-selected">
                        <a class="mui-navigate-right">
                            女
                        </a>
                    </li>
                </ul>
            </div>

            <div class="mui-card-footer">
                <span id="cancel" style="font-size: 13px" class="mui-popup-button">取消</span>
                <span id="confirm" style="font-size: 13px" class="mui-popup-button mui-popup-button-bold">确定</span>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script src="${ctp}/js/jybl/mui.picker.js"></script>
<script>
    document.getElementById("sexli").onclick=function () {
        document.getElementById("sexdiv").style.display="block";
    };
    var value1="男";
    document.querySelector('.mui-table-view.mui-table-view-radio').addEventListener('selected',function(e){
        value1 = e.detail.el.innerText;
    });
    var sex1=document.getElementById("sex");
    document.getElementById("cancel").onclick=function () {
        document.getElementById("sexdiv").style.display="none";
    };
    document.getElementById("confirm").onclick=function () {
        document.getElementById("sexdiv").style.display="none";
        document.getElementById('sex').innerText=value1;
    };

    var name1=document.getElementById("name");
    document.getElementById("nameli").addEventListener('tap', function(e) {
        e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
        var btnArray = ['取消', '确定'];
        mui.prompt('', '', '姓名', btnArray, function(e) {
            if (e.index == 1) {
                if(e.value!==''){
                    name1.innerText = e.value+'';
                }
            } else {
            }
        })
    });


    var number1=document.getElementById("number");
    document.getElementById("numberli").addEventListener('tap', function(e) {
        e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
        var btnArray = ['取消', '确定'];
        mui.prompt('', '', '手机号', btnArray, function(e) {
            if (e.index == 1) {
                if(e.value!==''){
                    number1.innerText = e.value+'';
                }
            } else {
            }
        })
    });



    var idcard1=document.getElementById("idcard");
    document.getElementById("idcardli").addEventListener('tap', function(e) {
        e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
        var btnArray = ['取消', '确定'];
        mui.prompt('', '', '身份证号', btnArray, function(e) {
            if (e.index == 1) {
                if(e.value!==''){
                    idcard1.innerText = e.value+'';
                }
            } else {
            }
        })
    });

    var mailbox1=document.getElementById("mailbox");
    document.getElementById("mailboxli").addEventListener('tap', function(e) {
        e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
        var btnArray = ['取消', '确定'];
        mui.prompt('', '', '邮箱', btnArray, function(e) {
            if (e.index == 1) {
                if(e.value!==''){
                    mailbox1.innerText = e.value+'';
                }
            } else {
            }
        })
    });
</script>
</body>
</html>