<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/wxMenu?menuType=1">微信菜单列表</a></li>
		<shiro:hasPermission name="weixin:wxMenu:edit">
			<li><a href="${ctx}/weixin/wxMenu/form?menuType=1">微信菜单添加</a>
			</li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMenu" action="${ctx}/weixin/wxMenu/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>菜单名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input  id="syn" class="btn btn-primary" type="button" value="同步微信服务器"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>菜单名</th>
				<th>类型</th>
				<th>层级</th>
				<shiro:hasPermission name="weixin:wxMenu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList">
			<c:forEach items="${list}" var="wx">
				<tr>
					<td>
					<c:if test="${not empty wx.parent && not empty wx.parent.id}">
						 &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;
					</c:if>
					${wx.name}</td>
					<td>${wx.type}</td>
					<td>${wx.level}</td>
					<shiro:hasPermission name="weixin:wxMenu:edit">
						<td>
							<a href="${ctx}/weixin/wxMenu/form?id=${wx.id}&menuType=${wx.menuType}">编辑</a>
							<a href="${ctx}/weixin/wxMenu/delete?id=${wx.id}">删除</a>
							<c:if test="${wx.level eq 1}">
								<a href="${ctx}/weixin/wxMenu/form?parentid=${wx.id}&menuType=${wx.menuType}">添加下级</a>
							</c:if>
						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script  type="text/javascript">
		$(function () {
			$("#syn").bind("click",function(){
				$("#syn").attr("disabled",true);
				$.jBox.tip("1");
				$.get("${ctx}/weixin/wxMenu/sync?menuType=1",function(data){
					if("0"==data){
						$("#syn").attr("disabled",false);
						$.jBox.tip("微信菜单同步成功");
					}else{
						$("#syn").attr("disabled",false);
						$.jBox.tip("微信菜单同步失败:"+data);
					}
				})
			});
		})


	</script>
</body>
</html>