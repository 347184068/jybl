<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
	<li ><a href="${ctx}/background/manage/list">内外码列表</a></li>
	<li class="active"><a href="">产品信息</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="material" action="" method="post" class="form-horizontal">
	<div class="control-group">
		<label class="control-label">物料编码：</label>
		<div class="controls">
			<label class="control-label">${materialCode}</label>
		 </div>
	</div>
	<div class="control-group">
		<label class="control-label">物料名称：</label>
		<div class="controls">
			<label class="control-label">${materialName}</label>
		</div>
	</div>
</form:form>
</body>
</html>