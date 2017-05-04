<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>资产管理</title>
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
<form:form id="inputForm"  enctype="multipart/form-data"  action="/file/fileDeal/uploadFile" method="post" class="form-horizontal">
	<div class="control-group">
		<label class="control-label">txt文件</label>
		<div class="controls">
				<input  type="file" name="file"/>
		</div>
	</div>
	<div class="form-actions">

		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;

		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>

</body>

</html>