<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关键字管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxAutoKeyword/">关键字列表</a></li>
		<shiro:hasPermission name="weixin:wxAutoKeyword:edit"><li><a href="${ctx}/weixin/wxAutoKeyword/form">关键字添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxAutoKeyword" action="${ctx}/weixin/wxAutoKeyword/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>关键词</th>
				<th>标题</th>
				<shiro:hasPermission name="weixin:wxAutoKeyword:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxAutoKeyword">
			<tr>
				<td>${wxAutoKeyword.keyWord}</td>
				<td><a href="${ctx}/weixin/wxAutoKeyword/form?id=${wxAutoKeyword.id}">
					${wxAutoKeyword.name}
				</a></td>
				<shiro:hasPermission name="weixin:wxAutoKeyword:edit"><td>
    				<a href="${ctx}/weixin/wxAutoKeyword/form?id=${wxAutoKeyword.id}">修改</a>
					<a href="${ctx}/weixin/wxAutoKeyword/delete?id=${wxAutoKeyword.id}" onclick="return confirmx('确认要删除该关键字吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>