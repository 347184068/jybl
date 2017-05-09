<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出版社管理</title>
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
		<li class="active"><a href="${ctx}/sys/bookPublisher/">出版社列表</a></li>
		<shiro:hasPermission name="sys:bookPublisher:edit"><li><a href="${ctx}/sys/bookPublisher/form">出版社添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookPublisher" action="${ctx}/sys/bookPublisher/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>出版社：</label>
				<form:input path="publisherName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>ID</th>
				<th>出版社</th>
				<shiro:hasPermission name="sys:bookPublisher:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookPublisher">
			<tr>
				<td><a href="${ctx}/sys/bookPublisher/form?id=${bookPublisher.publisherId}">
					${bookPublisher.publisherId}
				</a></td>
				<td>
					${bookPublisher.publisherName}
				</td>
				<shiro:hasPermission name="sys:bookPublisher:edit"><td>
    				<a href="${ctx}/sys/bookPublisher/form?id=${bookPublisher.publisherId}">修改</a>
					<a href="${ctx}/sys/bookPublisher/delete?id=${bookPublisher.publisherId}" onclick="return confirmx('确认要删除该出版社及出版社下所有图书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>