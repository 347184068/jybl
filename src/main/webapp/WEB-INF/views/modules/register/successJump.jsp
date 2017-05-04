<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>注册成功</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="${ctxStatic}/register/style/weui.css"/>
    <link rel="stylesheet" href="${ctxStatic}/register/style/weui2.css"/>
    <link rel="stylesheet" href="${ctxStatic}/register/style/weui3.css"/>
    <script src="${ctxStatic}/register/zepto.min.js"></script>
</head>
<body>



<div style="text-align: center;margin-top: 20%">
  <span class="f32">注册成功</span>
</div>
<div style="text-align: center;margin-top: 20px">
  <span id="time">5</span>秒之后自动跳转至防伪扫描页面
</div>
<form id="form" action="/user/userRegister/gotoScanPage" modelAttribute="userRegister">

    <input type="text" name="openId" id="openId" value="${openId}"/>

    <input type="text" name="appId" value="${appId}"/>
    <input type="text" name="timestamp" value="${timestamp}"/>
    <input type="text" name="nonceStr" value="${nonceStr}"/>
    <input type="text" name="signature" value="${signature}"/>

  <div class="weui_btn_area">
    <a id="formSubmitBtn" onclick="btnSubmit()" href="javascript:" class="weui_btn weui_btn_primary">前往扫描页面</a>
  </div>
</form>
<script>
  var t = 5;
  var time = document.getElementById("time");
  function fun(){
    t--;
    time.innerHTML = t;
    if(t<=0){
     btnSubmit();clearInterval(inter);
    }
  }
  var inter = setInterval(fun,1000);

  function btnSubmit(){
      var scanForm = $("#form");
      scanForm.submit();
  }


</script>
</body>
</html>
