<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>图书超期管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/bookBorrow/overTimeList">超期列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="bookBorrow" action="${ctx}/sys/bookBorrow/overTimeList" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>微信ID：</label>
            <form:input path="bookUserWechatId" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>ISBN：</label>
            <form:input path="bookIsbn" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>书名：</label>
            <form:input path="bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>用户名：</label>
            <form:input path="bookUserName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>借阅时间：</label>
            <input type="date" id="borrowTime" name="borrowTime" class="input-medium" placeholder="借阅时间">
        </li>
        <li><label>归还时间：</label>
            <input type="date" id="returnTime" name="returnTime" class="input-medium" placeholder="归还时间">
        </li>
        <li><label>是否归还：</label>
            <form:select path="isReturn" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>是否续借：</label>
            <form:select path="isRenew" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>微信ID</th>
        <th>用户名</th>
        <th>ISBN</th>
        <th>书名</th>
        <th>是否归还</th>
        <th>借阅时间</th>
        <th>归还时间</th>
        <th>是否续借</th>
        <th>是否超期</th>
        <shiro:hasPermission name="sys:bookBorrow:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="bookBorrow">
        <tr>
            <td>
                    ${bookBorrow.bookUserWechatId}
            </td>
            <td>
                    ${bookBorrow.bookUserName}
            </td>
            <td>
                    ${bookBorrow.bookIsbn}
            </td>
            <td>
                    ${bookBorrow.bookName}
            </td>
            <td>
                    ${fns:getDictLabel(bookBorrow.isReturn, 'yes_no', '')}
            </td>
            <td>
                    ${bookBorrow.borrowTime}
            </td>
            <td>
                    ${bookBorrow.returnTime}
            </td>
            <td>
                    ${fns:getDictLabel(bookBorrow.isRenew, 'yes_no', '')}
            </td>
            <td>
                    ${fns:getDictLabel(bookBorrow.isOvertime, 'yes_no', '')}
            </td>
            <shiro:hasPermission name="sys:bookBorrow:edit">
                <td>
                    <c:if test="${bookBorrow.isReturn eq 0}">
                        <font color="red">未还书</font>
                        &nbsp;
                        <a href="${ctx}/sys/bookBorrow/overTimeProess?borrowId=${bookBorrow.borrowId}" onclick="return confirmx('确认用户已缴纳罚金么？', this.href)">处理</a>
                    </c:if>
                    <c:if test="${bookBorrow.isReturn eq 1}">
                        <font color="green">已还书</font>
                    </c:if>
                    &nbsp;
                    <a href="${ctx}/sys/bookBorrow/renew?borrowId=${bookBorrow.borrowId}" onclick="return confirmx('确认要加入不良记录么？', this.href)">加入不良记录</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>