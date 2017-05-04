<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息列表管理</title>
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
		<li><a href="${ctx}/weixin/wxArticleCollect/">消息列表列表</a></li>
		<li class="active"><a href="${ctx}/weixin/wxArticleCollect/form?id=${wxArticleCollect.id}">消息列表<shiro:hasPermission name="weixin:wxArticleCollect:edit">${not empty wxArticleCollect.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:wxArticleCollect:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxArticleCollect" action="${ctx}/weixin/wxArticleCollect/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">消息：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div>
			<label class="control-label">文章列表</label>
			<div class="controls">
				<form:hidden path="articleList" />
				<form:hidden path="articleTitles" />
				<ol id="articleSelectList">
					<c:forEach items="${wxArticleCollect.artcleList}" var="m" >
						<li>${m.title}&nbsp;&nbsp;<a href="javascript:" onclick="articleSelectAddOrDel('${m.id}','${m.title}');">×</a></li>
					</c:forEach>
				</ol>
				<a id="relationButton" href="javascript:" class="btn">添加文章</a>
				<script type="text/javascript">
					var articleSelect = [];
					var artcleSelectTitles=[]
					<c:if	test="${not empty wxArticleCollect.articleList}">
							<c:forEach items="${fn:split(wxArticleCollect.articleList, ',')}" var="id">
								articleSelect.push('${id}');
							</c:forEach>
							<c:forEach items="${fn:split(wxArticleCollect.articleTitles, ',')}" var="title">
								artcleSelectTitles.push('${title}');
							</c:forEach>
					</c:if>
					function articleSelectAddOrDel(id,title){
						var isExtents = false, index = 0;
						console.log(articleSelect);
						for (var i=0; i<articleSelect.length; i++){
							if (articleSelect[i][0]==id){
								isExtents = true;
								index = i;
							}
						}
						if(isExtents){
							articleSelect.splice(index,1);
						}else{
							articleSelect.push([id,title]);
						}
						articleSelectRefresh();
					}
					function articleSelectRefresh(){
						$("#articleList").val("");
						$("#articleTitles").val("");
						$("#articleSelectList").children().remove();
						for (var i=0; i<articleSelect.length; i++){
							$("#articleSelectList").append("<li>"+articleSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"articleSelectAddOrDel('"+articleSelect[i][0]+"','"+articleSelect[i][1]+"');\">×</a></li>");
							$("#articleList").val($("#articleList").val()+articleSelect[i][0]+",");
							$("#articleTitles").val($("#articleTitles").val()+articleSelect[i][1]+",");
						}
					}
					$("#relationButton").click(function(){
							$.jBox.open("iframe:${ctx}/weixin/wxArticle/selectList", "添加相关",$(document).width()-220,$(document).height()-180,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</div>

		</div>

		<div class="form-actions">
			<shiro:hasPermission name="weixin:wxArticleCollect:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>