<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息列表管理</title>
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
		<li class="active"><a href="${ctx}/weixin/wxArticleCollect/">消息列表列表</a></li>
		<shiro:hasPermission name="weixin:wxArticleCollect:edit"><li><a href="${ctx}/weixin/wxArticleCollect/form">消息列表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxArticleCollect" action="${ctx}/weixin/wxArticleCollect/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>消息：</label>
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
				<th>消息</th>
				<shiro:hasPermission name="weixin:wxArticleCollect:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxArticleCollect">
			<tr>
				<td><a href="${ctx}/weixin/wxArticleCollect/form?id=${wxArticleCollect.id}">
					${wxArticleCollect.name}
				</a></td>
				<shiro:hasPermission name="weixin:wxArticleCollect:edit"><td>
					<a href="${ctx}/weixin/wxArticleCollect/sendWeixn?id=${wxArticleCollect.id}">群发</a>
					<a href="${ctx}/weixin/wxArticleCollect/form?id=${wxArticleCollect.id}">修改</a>
					<a href="${ctx}/weixin/wxArticleCollect/delete?id=${wxArticleCollect.id}" onclick="return confirmx('确认要删除该消息列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>