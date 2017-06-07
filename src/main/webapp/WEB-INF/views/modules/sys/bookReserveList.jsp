<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预定信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/bookReserve/">预定信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="bookReserve" action="${ctx}/sys/bookReserve/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>书名：</label>
				<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>预定时间：</label>
				<input type="date" id="reserveTime" name="reserveTime" class="input-medium" placeholder="预定时间">
			</li>
			<li><label>取书时间：</label>
				<input type="date" id="pickTime" name="pickTime" class="input-medium" placeholder="取书时间">
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>图书名</th>
				<th>预定时间</th>
				<th>取书时间</th>
				<th>可借状态</th>
				<shiro:hasPermission name="sys:bookReserve:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookReserve">
			<tr>
				<td>
					${bookReserve.userName}
				</td>
				<td>
					${bookReserve.bookName}
				</td>
				<td>
					${bookReserve.reserveTime}
				</td>
				<td>
					${bookReserve.pickTime}
				</td>
				<td>
					<c:if test="${bookReserve.bookCollections>0}">
						<font color="green">可借</font>
					</c:if>
					<c:if test="${bookReserve.bookCollections<=0}">
						<font color="red">不可借</font>
					</c:if>
				</td>
				<shiro:hasPermission name="sys:bookReserve:edit"><td>
					<c:if test="${bookReserve.isOvertime eq 1}">
						<font color="red">已过期</font>
					</c:if>
					<c:if test="${bookReserve.isOvertime eq 0 && bookReserve.isPick eq 0}">
						<font color="orange">未取书</font>
					</c:if>
					<c:if test="${bookReserve.isOvertime eq 0 && bookReserve.isPick eq 1}">
						<font color="green">已取书</font>
					</c:if>
					<c:if test="${bookReserve.bookCollections>0 && bookReserve.isOvertime eq 0 && bookReserve.isPick eq 0}">
						&nbsp;
						<a href="" onclick="return confirmx('确认提醒取书吗？', this.href)">提醒</a>
					</c:if>
					&nbsp;
					<a href="${ctx}/sys/bookReserve/delete?id=${bookReserve.id}" onclick="return confirmx('确认要删除该预定信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>