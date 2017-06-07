<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐图书管理</title>
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
		<li class="active"><a href="${ctx}/sys/bookRecommend/">推荐图书列表</a></li>
		<shiro:hasPermission name="sys:bookRecommend:edit"><li><a href="${ctx}/sys/bookRecommend/form">推荐图书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookRecommend" action="${ctx}/sys/bookRecommend/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>ISBN：</label>
				<form:input path="isbn" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>书名：</label>
				<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>作者：</label>
				<form:input path="bookAuthor" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>ISBN</th>
				<th>书名</th>
				<th>作者</th>
				<shiro:hasPermission name="sys:bookRecommend:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookRecommend">
			<tr>
				<td>
					${bookRecommend.isbn}
				</td>
				<td>
					${bookRecommend.bookName}
				</td>
				<td>
					${bookRecommend.bookAuthor}
				</td>
				<shiro:hasPermission name="sys:bookRecommend:edit"><td>
					<a href="${ctx}/sys/bookRecommend/delete?id=${bookRecommend.id}" onclick="return confirmx('确认要删除该推荐图书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>