<%--
  根据分类查询图书
  Created by IntelliJ IDEA.
  User: coco
  Date: 2017/5/9
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
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
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>ISBN</th>
        <th>书名</th>
        <th>图片</th>
        <th>作者</th>
        <th>藏书量</th>
        <th>出版社</th>
        <th>押金(元)</th>
        <shiro:hasPermission name="sys:book:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="book">
        <tr>
            <td>
                    ${book.bookIsbn}
            </td>
            <td>
                    ${book.bookName}
            </td>
            <td>
                    ${book.bookImage}
            </td>
            <td>
                    ${book.bookAuthor}
            </td>
            <td>
                    ${book.bookCollections}
            </td>
            <td>
                    ${book.bookPublisherid.publisherName}
            </td>
            <td>
                    ${book.bookCashpledge}
            </td>
            <shiro:hasPermission name="sys:book:edit"><td>
                <a href="${ctx}/sys/book/form?id=${book.bookId}">修改</a>
                <a href="${ctx}/sys/book/delete?id=${book.bookId}" onclick="return confirmx('确认要删除该书籍吗？', this.href)">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
