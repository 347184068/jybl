<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图书用户信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/bookUser/">图书用户信息列表</a></li>
		<shiro:hasPermission name="sys:bookUser:edit"><li><a href="${ctx}/sys/bookUser/form">图书用户信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bookUser" action="${ctx}/sys/bookUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>微信号：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>用户类型：</label>
				<form:input path="userType" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>微信号</th>
				<th>用户名</th>
				<th>密码</th>
				<th>用户类型</th>
				<shiro:hasPermission name="sys:bookUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bookUser">
			<tr>
				<td><a href="${ctx}/sys/bookUser/form?id=${bookUser.userId}">
					${bookUser.wechatId}
				</a></td>
				<td>
					${bookUser.userName}
				</td>
				<td>
					${bookUser.password}
				</td>
				<td>
					${bookUser.userType}
				</td>
				<shiro:hasPermission name="sys:bookUser:edit"><td>
    				<a href="${ctx}/sys/bookUser/form?id=${bookUser.userId}">修改</a>
					<a href="${ctx}/sys/bookUser/delete?id=${bookUser.userId}" onclick="return confirmx('确认要删除该图书用户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>