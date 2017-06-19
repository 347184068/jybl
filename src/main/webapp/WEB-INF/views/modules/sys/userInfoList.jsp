<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/userInfo/">用户信息列表</a></li>
		<shiro:hasPermission name="sys:userInfo:edit"><li><a href="${ctx}/sys/userInfo/form">用户信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/sys/userInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="personName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>身份证：</label>
				<form:input path="idCard" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>电话号码：</label>
				<form:input path="phoneNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>是否删除：</label>
				<form:radiobuttons path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>身份证</th>
				<th>电话号码</th>
				<th>邮箱</th>
				<shiro:hasPermission name="sys:userInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInfo">
			<tr>
				<td><a href="${ctx}/sys/userInfo/form?id=${userInfo.id}">
					${userInfo.personName}
				</a></td>
				<td>
					${userInfo.idCard}
				</td>
				<td>
					${userInfo.phoneNumber}
				</td>
				<td>
					${userInfo.email}
				</td>
				<shiro:hasPermission name="sys:userInfo:edit"><td>
    				<a href="${ctx}/sys/userInfo/form?id=${userInfo.id}">修改</a>
					<a href="${ctx}/sys/userInfo/delete?id=${userInfo.id}" onclick="return confirmx('确认要删除该用户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>