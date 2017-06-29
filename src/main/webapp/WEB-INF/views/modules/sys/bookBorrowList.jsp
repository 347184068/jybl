<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>借阅信息管理</title>
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
    <li class="active"><a href="${ctx}/sys/bookBorrow/">借阅信息列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="bookBorrow" action="${ctx}/sys/bookBorrow/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
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
        <li><label>是否超期：</label>
            <form:select path="isOvertime" class="input-medium">
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
                        <c:choose>
                            <c:when test="${bookBorrow.isOvertime eq 0 && bookBorrow.isRenew eq 0}">
                                <a href="${ctx}/sys/bookBorrow/renew?borrowId=${bookBorrow.borrowId}" onclick="return confirmx('确认要续借么？', this.href)">续借</a>
                                &nbsp;
                                <font color="orange">(未还书)</font>
                            </c:when>
                            <c:when test="${bookBorrow.isOvertime eq 0 && bookBorrow.isRenew eq 1}">
                                <font color="green">已续借</font><font color="orange">(未还书)</font>
                                <a href="${ctx}/sys/bookBorrow/returnBook?borrowId=${bookBorrow.borrowId}" onclick="return confirmx('确认要还书么？', this.href)">还书</a>
                            </c:when>
                            <c:otherwise>
                                <font color="red">已超期</font>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${bookBorrow.isReturn eq 1 && bookBorrow.isConfirm eq 0}">
                        <font color="green">已归还</font><font color="red">(管理员未确认)</font>
                        &nbsp;
                        <a href="${ctx}/sys/bookBorrow/confirm?borrowId=${bookBorrow.borrowId}" onclick="return confirmx('是否确认无误？', this.href)">点击确认</a>
                    </c:if>
                    <c:if test="${bookBorrow.isReturn eq 1 && bookBorrow.isConfirm eq 1}">
                        <font color="green">已归还(管理员已确认)</font>
                    </c:if>
                    <a href="${ctx}/sys/bookBorrow/delete?borrowId=${bookBorrow.borrowId}" onclick="return confirmx('确定要删除么？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>