<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防伪码订单关联情况报表</title>
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
	<li class="active"><a href="${ctx}/background/manage/codeMaterialList">防伪码订单关联情况</a></li>
</ul>
<form:form id="searchForm" modelAttribute="codeMaterialInfo" action="${ctx}/background/manage/codeMaterialList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	<label class="control-label">油品供应商：</label>

	<form:input path="supplierName" htmlEscape="false" maxlength="64" class="input-xlarge required" />

	<%--<form:select items="${supplierList}"  itemLabel="supplierName" itemValue="supplierName"
				 path="supplierName" htmlEscape="false" maxlength="11" class="input-xlarge  "/>--%>

	发货时间开始时间：<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
	value="<fmt:formatDate value="${codeMaterialInfo.startTime}" pattern="yyyy-MM-dd"/>"
	onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

	发货时间结束时间：<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
	value="<fmt:formatDate value="${codeMaterialInfo.endTime}" pattern="yyyy-MM-dd"/>"
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
		<th>油品供应商</th>
		<th>发运单号</th>
		<th>物料编码</th>
		<th>物料名称</th>
		<th>应传<br>合格证数量</th>
		<th>已传<br>合格证数量</th>
		<th>合格证上传率</th>
		<th>应传<br>二维码数量</th>
		<th>已传<br>二维码数量</th>
		<th>二维码上传率</th>
		<th>应传<br>防伪码数量</th>
		<th>已传<br>防伪码数量</th>
		<th>防伪码上传率</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="info" varStatus="item">
		<tr>
			<td>${item.index+1}</td>
			<td>${info.supplierName}</td>
			<td>${info.shippingOrder}</td>
			<td>${info.materialCode}</td>
			<td>${info.materialName}</td>
			<td>${info.productIdNum}</td>
			<td>${info.realProductIdNum}</td>
			<td>${info.productIdPercent}</td>
			<td>${info.outCodeNum}</td>
			<td>${info.realOutCodeNum}</td>
			<td>${info.outCodePercent}</td>
			<td>${info.securityNum}</td>
			<td>${info.realSecurityNum}</td>
			<td>${info.securityPercent}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>