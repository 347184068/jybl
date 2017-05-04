<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防伪码申请及打印情况报表</title>
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
	<li class="active"><a href="${ctx}/background/manage/codeGrantAndPrint">防伪码申请及打印情况</a></li>
</ul>
<form:form id="searchForm" modelAttribute="codeStatisticInfo" action="${ctx}/background/manage/codeGrantAndPrint" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	<label class="control-label">包材厂商：</label>
	<form:select path="baoCaiType">
		<form:option value="">全部</form:option>
		<form:option value="01">01</form:option>
		<form:option value="02">02</form:option>
	</form:select>

	开始时间：<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
	value="<fmt:formatDate value="${codeStatisticInfo.startTime}" pattern="yyyy-MM-dd"/>"
	onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

	结束时间：<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
	value="<fmt:formatDate value="${codeStatisticInfo.endTime}" pattern="yyyy-MM-dd"/>"
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
		<th>包材厂商</th>
		<th>申请频次</th>
		<th>申请数量</th>
		<th>打印回传频次</th>
		<th>回传合格数量</th>
		<th>回传报废数量</th>
		<th>尚未打印量</th>
		<th>打印比率</th>
		<th>打印合格比率</th>
	</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td>
				${not empty codeStatisticInfo.baoCaiType?codeStatisticInfo.baoCaiType:"全部"}
			</td>
			<td>${codeStatisticInfo.grantNum}</td>
			<td>${codeStatisticInfo.grantSum}</td>
			<td>${codeStatisticInfo.uploadNum}</td>
			<td>${codeStatisticInfo.uploadQualifiedSum}</td>
			<td>${codeStatisticInfo.uploadScrapSum}</td>
			<td>${codeStatisticInfo.noPrintSum}</td>
			<td>${codeStatisticInfo.printPercentage}</td>
			<td>${codeStatisticInfo.qualifyPrintPercentage}</td>

		</tr>

	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>