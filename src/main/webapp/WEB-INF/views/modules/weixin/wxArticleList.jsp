<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxArticle/">文章列表</a></li>
		<shiro:hasPermission name="weixin:wxArticle:edit"><li><a href="${ctx}/weixin/wxArticle/form">文章添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxArticle" action="${ctx}/weixin/wxArticle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>title：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>作者</th>
				<th>时间</th>
				<shiro:hasPermission name="weixin:wxArticle:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxArticle">
			<tr>
				<td><a href="${ctx}/weixin/wxArticle/form?id=${wxArticle.id}">
					${wxArticle.title}
				</a></td>
				<td>${wxArticle.author}</td>
				<td>${wxArticle.createDate}</td>
				<shiro:hasPermission name="weixin:wxArticle:edit"><td>
    				<a href="${ctx}/weixin/wxArticle/form?id=${wxArticle.id}">修改</a>
					<a href="${ctx}/weixin/wxArticle/delete?id=${wxArticle.id}" onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>