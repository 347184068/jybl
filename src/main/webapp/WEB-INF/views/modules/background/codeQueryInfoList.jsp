<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防伪码查询情况报表（汇总）</title>
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
	<li class="active"><a href="${ctx}/background/manage/codeQueryInfoList">防伪码查询情况报表（汇总）</a></li>
</ul>
<form:form id="searchForm" modelAttribute="codeQueryInfo" action="${ctx}/background/manage/codeQueryInfoList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	<label class="control-label">码类型：</label>
	<form:select   path="codeType" htmlEscape="false" maxlength="11" class="input-xlarge  ">
		<form:option value="" label="全部"/>
		<form:option value="合格证" label="合格证"/>
		<form:option value="防伪码" label="防伪码"/>
		<form:option value="二维码" label="二维码"/>
	</form:select>
	<label class="control-label">收货单位：</label>
	<form:input path="receiveName" htmlEscape="false" maxlength="64" class="input-xlarge required" />

	<label class="control-label">油品供应商：</label>
	<form:input path="supplierName" htmlEscape="false" maxlength="64" class="input-xlarge required" />
	<br/><br/>
	<label class="control-label">包材厂商：</label>
	<form:select path="baoCaiType" htmlEscape="false" maxlength="11" class="input-xlarge  ">
		<form:option value="" label="全部"/>
		<form:option value="01" label="01"/>
		<form:option value="02" label="02"/>
	</form:select>

	扫描日期（开始）：<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
	value="<fmt:formatDate value="${codeQueryInfo.startTime}" pattern="yyyy-MM-dd"/>"
	onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

	扫描日期（结束）：<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
	value="<fmt:formatDate value="${codeQueryInfo.endTime}" pattern="yyyy-MM-dd"/>"
	onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

	<ul class="ul-form">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>码类型</th>
		<th>收货单位</th>
		<th>油品供应商</th>
		<th>包材供应商</th>
		<th>查询频次</th>
		<th>查询防伪码条数</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="info" varStatus="item">
		<tr>
			<td>${item.index+1}</td>
			<td>${info.codeType}</td>
			<td>${info.receiveName}</td>
			<td>${info.supplierName}</td>
			<td>${info.baoCaiType}</td>
			<td>${info.queryNum}</td>
			<td>${info.querySum}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>