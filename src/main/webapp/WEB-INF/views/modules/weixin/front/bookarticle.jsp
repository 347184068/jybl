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
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/html2.css">
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/iconfont.css">
    <link rel="stylesheet" href="{ctxStatic}/jybl/css/recommended_details.css">
</head>
<body>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">好文</h1>
</header>
<div class="mui-content">
    <div class="mui-card1">
        <div class="mui-card-header1 mui-card-media1">
            <img src="${ctxStatic}/jybl/img/articleImg.png" />
            <div class="mui-media-body1">
                ${bookarticle.title}
                <p>${bookarticle.date}&nbsp;${bookarticle.author}</p>
            </div>
        </div>
        <div class="mui-card-content1" >
            <img src="${bookarticle.coverimg}" alt="" width="100%"/>
            ${bookarticle.content}
        </div>
    </div>
</div>
<script src="${ctxStatic}/mui/mui.min.js"></script>
</body>
</html>