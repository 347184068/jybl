<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信菜单管理</title>
	<meta name="decorator" content="default"/>

</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxMenu?menuType=1">微信菜单列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxMenu/form?id=${wxMenu.id}&parent.id=${wxMenuparent.id}&menuType=1">微信菜单<shiro:hasPermission name="weixin:wxMenu:edit">${not empty wxMenu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxMenu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxMenu" action="${ctx}/weixin/wxMenu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuType"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">父级：</label>
			<div class="controls">
					<form:select path="parent.id" cssClass="input-xlarge">
							<form:option value="">请选择</form:option>
							<form:options items="${list}" itemValue="id" itemLabel="name"/>
					</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜单名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">层级：</label>
			<div class="controls">
				<form:select path="level"  class="input-xlarge">
						<form:option value="1">1级</form:option>
						<form:option value="2">2级</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" cssClass="input-xlarge" >
							<form:option value="">请选择</form:option>
							<form:option value="click">点击事件</form:option>
							<form:option value="view">跳转URL</form:option>
							<form:option value="scancode_push">扫码事件</form:option>
							<form:option value="scancode_waitmsg">扫码推事件且弹出“消息接收中”提示框</form:option>
							<form:option value="pic_sysphoto">弹出系统拍照发图</form:option>
							<form:option value="pic_photo_or_album">弹出拍照或者相册发图</form:option>
							<form:option value="pic_weixin">弹出微信相册发图器</form:option>
							<form:option value="pic_weixin">弹出地理位置选择器</form:option>
							<form:option value="media_id">跳转图文消息URL</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">url：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">key_value：</label>
			<div class="controls">
				<form:input path="keyValue" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sort：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="64" class="required number input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxMenu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">

	</script>
</body>
</html>