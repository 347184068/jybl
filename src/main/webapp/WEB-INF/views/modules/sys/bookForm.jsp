<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/sys" %>
<html>
<head>
	<title>书籍管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
	<li><a href="${ctx}/sys/book/">书籍列表</a></li>
	<li class="active"><a href="${ctx}/sys/book/form?id=${book.id}">书籍<shiro:hasPermission name="sys:book:edit">${not empty book.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:book:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="book" action="${ctx}/sys/book/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">ISBN：</label>
		<div class="controls">
			<form:input path="bookIsbn" htmlEscape="false" maxlength="13" minlength = "13" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">书名：</label>
		<div class="controls">
			<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">图片：</label>
		<div class="controls">
			<form:hidden path="bookImage" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<tags:ckfinder input="bookImage" type="files" uploadPath="/bookImg "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">作者：</label>
		<div class="controls">
			<form:input path="bookAuthor" htmlEscape="false" maxlength="255" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">藏书量：</label>
		<div class="controls">
			<form:input path="bookCollections" htmlEscape="false" maxlength="255" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">序：</label>
		<div class="controls">
			<form:textarea id="bookIntroduction" path="bookIntroduction" htmlescape="true" rows="4" maxlength="1024" class="input-xxlarge "></form:textarea>
			<tags:ckeditor replace="bookIntroduction"></tags:ckeditor>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">目录：</label>
		<div class="controls">
			<form:textarea id="bookDirectory" path="bookDirectory" htmlescape="true" rows="4" maxlength="1024" class="input-xxlarge "></form:textarea>
			<tags:ckeditor replace="bookDirectory"></tags:ckeditor>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">内容：</label>
		<div class="controls">
			<form:textarea id="bookContents" path="bookContents" htmlescape="true" rows="4" maxlength="1024" class="input-xxlarge "></form:textarea>
			<tags:ckeditor replace="bookContents"></tags:ckeditor>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">导读：</label>
		<div class="controls">
			<form:textarea id="bookGuide" path="bookGuide" htmlescape="true" rows="4" maxlength="1024" class="input-xxlarge "></form:textarea>
			<tags:ckeditor replace="bookGuide"></tags:ckeditor>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">出版社：</label>
		<div class="controls">
			<form:select path="bookPublisherid" class="input-medium">
				<form:option value="" label="请选择"/>
				<c:if test="${publisherList ne null}">
					<c:forEach items="${publisherList}" var="publisher">
						<form:option value="${publisher.publisherId}" label="${publisher.publisherName}"/>
					</c:forEach>
				</c:if>
			</form:select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">出版社：</label>
		<div class="controls">
			<form:select path="bookCategoryid" class="input-medium">
				<form:option value="" label="请选择"/>
				<c:if test="${categoryList ne null}">
					<c:forEach items="${categoryList}" var="category">
						<form:option value="${category.id}" label="${category.name}"/>
					</c:forEach>
				</c:if>
			</form:select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">押金(元)：</label>
		<div class="controls">
			<form:input path="bookCashpledge" htmlEscape="false" maxlength="255" class="input-xlarge "/>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="sys:book:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>