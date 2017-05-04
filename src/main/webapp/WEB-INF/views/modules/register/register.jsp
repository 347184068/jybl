<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="${ctxStatic}/register/style/weui.css"/>
    <link rel="stylesheet" href="${ctxStatic}/register/style/weui2.css"/>
    <link rel="stylesheet" href="${ctxStatic}/register/style/weui3.css"/>
    <script src="${ctxStatic}/register/zepto.min.js"></script>
    <script>
        $(function(){
        });
    </script>
    <style>
    </style>
</head>

<body ontouchstart style="background-color: #f8f8f8;">
<div class="weui_cells_title">注册</div>
<form id="form" action="/user/userRegister/register" method="post" modelAttribute="userRegister">

    <input type="text" name="openId" id="openId" value="${openId}"/>

    <%--<input type="text" name="appId" value="${appId}"/>
    <input type="text" name="timestamp" value="${timestamp}"/>
    <input type="text" name="nonceStr" value="${nonceStr}"/>
    <input type="text" name="signature" value="${signature}"/>--%>
    <input type="text" name="type" id="type" value="${type}"/>


    <div class="weui_cells weui_cells_form">
        <div class="weui_cell">
            <div class="weui_cell_hd">
                <label class="weui_label">账号</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input class="weui_input"  id="account" name="account" value="${userRegister.account}"
                       type="text" pattern="^[a-zA-Z0-9]{6,18}$" placeholder="请使用英文数字下划线的形式"/>
            </div>
        </div>

        <div class="weui_cell">
            <div class="weui_cell_hd">
                <label class="weui_label">密码</label>
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <input id="pwd" class="weui_input" name="password" type="password" pattern="[A-Za-z0-9_]+" placeholder="请使用英文数字下划线的形式"/>
            </div>
        </div>

        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">确认密码</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input id="confirmPwd" name="confirmPwd" class="weui_input" type="password" pattern="[A-Za-z0-9_]+" placeholder="请使用英文数字下划线的形式"/>
            </div>
        </div>


    </div>
    <input type="text" name="message" id="msg" value="${msg}">
    <div class="weui_btn_area">
        <a id="formSubmitBtn" href="javascript:" class="weui_btn weui_btn_primary">注册</a>
    </div>
</form>
<script>
    var inputForm = $("#form");
    $(function(){
        $('#formSubmitBtn').on('click', function(e){
            var pairs = $('form').serialize().split(/&/gi);
            var data = {};
            pairs.forEach(function(pair) {
                pair = pair.split('=');
                data[pair[0]] = decodeURIComponent(pair[1] || '');
            });
            if(!data.account || !/^[a-zA-Z0-9]{6,18}$/.test(data.account)){
                $.toptips('请输入正确的账号');
                return;
            }
            if(!data.password || !/[A-Za-z0-9_]{1,20}/.test(data.password)){
                $.toptips('请输入正确的密码');
                return;
            }
            if(!data.confirmPwd || !/[A-Za-z0-9_]{1,20}/.test(data.confirmPwd) || data.confirmPwd != data.password){
                $.toptips('两次输入的密码不一致');
                return;
            }
            inputForm.submit();
           // $.post('<%=request.getContextPath()%>/user/userRegister/register', data);
            });

        var msg = $("#msg").val();
        if(msg && typeof (msg)!="undefined"){
            $.toptips(msg);
        }
 });


</script>
</body>
</html>
