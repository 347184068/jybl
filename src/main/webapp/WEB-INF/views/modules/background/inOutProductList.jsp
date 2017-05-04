<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内外码扫描次数管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/background/manage/list">内外码列表</a></li>

</ul>
<form:form id="searchForm" modelAttribute="inOutProduct" action="${ctx}/background/manage/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	<label class="control-label">内码：</label>
	<form:input path="inCode" htmlEscape="false" maxlength="32" class="input-xlarge " />

	<label class="control-label">外码：</label>
	<form:input path="outCode" htmlEscape="false" maxlength="32" class="input-xlarge " />

	<br><br>
	<label class="control-label">产品码：</label>
	<form:input path="productId" htmlEscape="false" maxlength="32" class="input-xlarge " />

	<label class="control-label">一维码：</label>
	<form:input path="security" htmlEscape="false" maxlength="32" class="input-xlarge " />

    <ul class="ul-form">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>内码</th>
		<th>外码</th>
		<th>产品码</th>
		<th>普通码</th>
		<th>内码扫描次数</th>
		<th>外码扫描次数</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="inOutProduct" varStatus="item">
		<tr>
			<td>${item.index+1}</td>
			<td>${inOutProduct.inCode}</td>
			<td>${inOutProduct.outCode}</td>
			<td>${inOutProduct.productId}</td>
			<td>${inOutProduct.security}</td>
			<td>${inOutProduct.inNum}</td>
			<td>${inOutProduct.outNum}</td>
			<td>
				<a href="${ctx}/background/manage/getMaterial?productId=${inOutProduct.productId}">查看产品信息</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>