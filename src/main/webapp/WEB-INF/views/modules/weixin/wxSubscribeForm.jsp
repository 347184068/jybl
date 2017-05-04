<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#msgType").bind("change",function(){
				alert(this.value);
				if(this.value=='view'){
					$("#selectDiv").css("display","");
				}else{
					$("#selectDiv").css("display","none");
				}
			});
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/wxSubscribe/">消息列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxSubscribe/form?id=${wxSubscribe.id}">消息<shiro:hasPermission name="weixin:wxSubscribe:edit">${not empty wxSubscribe.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxSubscribe:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxSubscribe" action="${ctx}/weixin/wxSubscribe/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="msgType">
					<form:option value="text">text</form:option>
					<form:option value="view">view</form:option>
				</form:select>
			</div>
		</div>

		<div id="selectDiv" class="control-group"  <c:if test="${wxSubscribe.msgType ne 'view'}">style="display:none"</c:if>">
			<label class="control-label">图文消息：</label>
			<div class="controls">
				<form:select path="msgId">
					<form:option value="" >请选择</form:option>
					<form:options items="${collectList}"  itemLabel="name"  itemValue="id"/>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxSubscribe:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>