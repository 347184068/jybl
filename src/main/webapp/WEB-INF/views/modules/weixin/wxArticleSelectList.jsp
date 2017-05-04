<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		 	var	mainFrame=parent.window;
			$(document).ready(function() {
				$("input[name=id]").each(function(){
					var articleSelect =mainFrame.articleSelect;
					for (var i=0; i<articleSelect.length; i++){
						if (articleSelect[i][0]==$(this).val()){
							this.checked = true;
						}
					}
					$(this).click(function(){
						var id = $(this).val(), title = $(this).attr("title");
						mainFrame.articleSelectAddOrDel(id, title);
					});

				});
			});
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
		<li class="active"><a href="${ctx}/weixin/wxArticle/">文章列表</a></li>
		<shiro:hasPermission name="weixin:wxArticle:edit"><li><a href="${ctx}/weixin/wxArticle/form">文章添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxArticle" action="${ctx}/weixin/wxArticle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>title：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>标题</th>
				<td>作者</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxArticle">
			<tr>
				<td>
				<input type="checkbox" name="id" value="${wxArticle.id}" title="${fns:abbr(wxArticle.title,40)}" />
				</td>
				<td>
					<a href="${ctx}/weixin/wxArticle/form?id=${wxArticle.id}">
					${wxArticle.title}
				</a>
				</td>
				<td>${wxArticle.author}</td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>