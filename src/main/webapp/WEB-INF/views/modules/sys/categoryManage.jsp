<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>分类管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <style type="text/css">
        .ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
    </style>
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
    <li><a href="${ctx}/sys/category/">分类列表</a></li>
    <shiro:hasPermission name="sys:category:edit"><li><a href="${ctx}/sys/category/form">分类添加</a></li></shiro:hasPermission>
    <li class="active"><a href="${ctx}/sys/category/manage">图书分类管理</a></li>
</ul><br/>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
    <form:form id="searchForm" modelAttribute="book" action="${ctx}/sys/category/manage?categoryId=${categoryId eq null ? '': categoryId}" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    </form:form>
    <div id="left" class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle">图书分类管理<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
        </div>
        <div id="ztree" class="ztree"></div>
    </div>
    <div id="openClose" class="close">&nbsp;</div>
    <div id="right">
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
            <c:forEach items="${page.list}" var="book">
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

                    </td></shiro:hasPermission>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="pagination">${page}</div>
    </div>
</div>
<script type="text/javascript">
    var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
        callback:{onClick:function(event, treeId, treeNode){
            //var id = treeNode.pId == '0' ? '' :treeNode.pId;
            var id = treeNode.id;
            $('#searchForm').attr("action","${ctx}/sys/category/manage?categoryId="+id);
            $('#searchForm').submit();
        }
        }
    };

    function refreshTree(){
        $.getJSON("${ctx}/sys/category/treeData",function(data){
            console.log(data);
            $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
        });
    }
    refreshTree();

    var leftWidth = 180; // 左侧窗口大小
    var htmlObj = $("html"), mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize(){
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
        mainObj.css("width","auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>