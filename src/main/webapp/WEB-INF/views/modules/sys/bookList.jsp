<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>书籍管理</title>
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
		<li class="active"><a href="${ctx}/sys/book/">书籍列表</a></li>
		<shiro:hasPermission name="sys:book:edit"><li><a href="${ctx}/sys/book/form">书籍添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="book" action="${ctx}/sys/book/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>ISBN：</label>
				<form:input path="bookIsbn" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>书名：</label>
				<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>作者：</label>
				<form:input path="bookAuthor" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>出版社：</label>
				<form:select path="publisherId" class="input-medium">
					<form:option value="" label="请选择"/>
					<c:if test="${publisherList ne null}">
						<c:forEach items="${publisherList}" var="publisher">
							<form:option value="${publisher.publisherId}" label="${publisher.publisherName}"/>
						</c:forEach>
					</c:if>
				</form:select>
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
				<th>图片</th>
				<th>作者</th>
				<th>藏书量</th>
				<th>出版社</th>
				<th>押金(元)</th>
				<shiro:hasPermission name="sys:book:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="book">
			<tr>
				<td><a href="${ctx}/sys/book/form?id=${book.bookId}">
					${book.bookIsbn}
				</a></td>
				<td>
					${book.bookName}
				</td>
				<td>
					${book.bookImage}
				</td>
				<td>
					${book.bookAuthor}
				</td>
				<td>
					${book.bookCollections}
				</td>
				<td>
					${book.bookPublisherid.publisherName}
				</td>
				<td>
					${book.bookCashpledge}
				</td>
				<shiro:hasPermission name="sys:book:edit"><td>
    				<a href="${ctx}/sys/book/form?id=${book.bookId}">修改</a>
					<a href="${ctx}/sys/book/delete?id=${book.bookId}" onclick="return confirmx('确认要删除该书籍吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>