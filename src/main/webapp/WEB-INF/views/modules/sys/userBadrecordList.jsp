<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>不良记录管理</title>
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
    <li class="active"><a href="${ctx}/sys/userBadrecord/">不良记录列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="userBadrecord" action="${ctx}/sys/userBadrecord/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>ISBN：</label>
            <form:input path="bookBorrow.bookIsbn" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>书名：</label>
            <form:input path="bookBorrow.bookName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>用户名：</label>
            <form:input path="bookBorrow.bookUserName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>借阅时间：</label>
            <input type="date" id="borrowTime" name="bookBorrow.borrowTime" class="input-medium" placeholder="借阅时间">
        </li>
        <li><label>归还时间：</label>
            <input type="date" id="returnTime" name="bookBorrow.returnTime" class="input-medium" placeholder="归还时间">
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
        <th>借阅时间</th>
        <th>归还时间</th>
        <shiro:hasPermission name="sys:userBadrecord:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="userBadrecord">
        <tr>
            <td>
                    ${userBadrecord.bookBorrow.bookUserName}
            </td>
            <td>
                    ${userBadrecord.bookBorrow.bookIsbn}
            </td>
            <td>
                    ${userBadrecord.bookBorrow.bookName}
            </td>
            <td>
                    ${userBadrecord.bookBorrow.borrowTime}
            </td>
            <td>
                    ${userBadrecord.bookBorrow.returnTime}
            </td>
            <shiro:hasPermission name="sys:userBadrecord:edit">
                <td>
                    <a href="${ctx}/sys/userBadrecord/delete?id=${userBadrecord.id}"
                       onclick="return confirmx('确认要删除该不良记录吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>