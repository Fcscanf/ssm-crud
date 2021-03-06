<%--
  Created by IntelliJ IDEA.
  User: fcsca
  Date: 2018-08-06
  Time: 下午 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>
    <%--
    不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
    以/开始的相对路径，找资源，以服务器的路径为标准（http：//localhost：8080）；需要加上项目名
    --%>
    <%--引入bootstrap样式--%>
    <link href="${APP_PATH}/static/bootstrap-4.1.3-dist/css/bootstrap.css" rel="stylesheet">
    <%--引入Jquery--%>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.3.1.min.js"></script>
    <script src="${APP_PATH}/static/bootstrap-4.1.3-dist/js/bootstrap.js" rel="stylesheet"></script>
</head>
<body>

<%--搭建显示页面--%>

<div class="container">

    <%--标题行--%>

    <div class="row">

        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>

    </div>

        <%--按钮行--%>

    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-success">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>

        <%--以表格形式显示数据--%>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover">
                <tr>
                    <th>员工ID</th>
                    <th>员工姓名Empname</th>
                    <th>员工性别Gender</th>
                    <th>员工邮箱Email</th>
                    <th>员工部门Department</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="emp">
                    <tr>
                        <th>${emp.empId}</th>
                        <th>${emp.empName}</th>
                        <th>${emp.gender=="M"?"男":"女"}</th>
                        <th>${emp.email}</th>
                        <th>${emp.department.deptName}</th>
                        <th>
                            <button class="btn btn-success btn-sm">
                                Edit<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            </button>
                            <button class="btn btn-danger btn-sm">
                                Delete<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            </button>
                        </th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

        <%--显示分页的信息--%>

    <div class="row">
        <%--分页文字信息--%>
        <div class="col-md-6">
            当前第${pageInfo.pageNum}页,总共${pageInfo.pages}页,总共${pageInfo.total}条记录.
        </div>
        <%--分页条信息--%>
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">

                    <%--首页按钮的设定--%>
                    <li><a href="${APP_PATH}/emps?pn=1">首页</a></li>

                    <%--前一页按钮显示的判断--%>
                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <%--页码条的显示设定--%>
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                        <c:if test="${page_Num == pageInfo.pageNum}">
                            <li class="active"><a href="#">${pageNum}</a></li>
                        </c:if>
                        <c:if test="${page_Num != pageInfo.pageNum}">
                            <li><a href="${APP_PATH}/emps?pn=${page_Num}">${page_Num}</a></li>
                        </c:if>
                    </c:forEach>

                    <%--下一页按钮显示的判断--%>
                    <c:if test="${pageInfo.hasNextPage}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <%--末页按钮的设定--%>
                    <li><a href="${APP_PATH}/emps?pn=${pageInfo.pages}">末页</a></li>

                </ul>
            </nav>
        </div>
    </div>

</div>
</body>
</html>
