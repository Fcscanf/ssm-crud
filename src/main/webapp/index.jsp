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
    <%--引入Jquery--%>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.3.1.min.js"></script>
    <%--引入bootstrap样式--%>
    <link href="${APP_PATH}/static/bootstrap-4.1.3-dist/css/bootstrap.css" rel="stylesheet">
    <script href="${APP_PATH}/static/bootstrap-4.1.3-dist/js/bootstrap.js" rel="stylesheet"></script>
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
                <table class="table table-hover" id="emps_table">
                    <thead>
                    <tr>
                        <th>员工ID</th>
                        <th>员工姓名Empname</th>
                        <th>员工性别Gender</th>
                        <th>员工邮箱Email</th>
                        <th>员工部门Department</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

    <%--显示分页的信息--%>

    <div class="row">
        <%--分页文字信息--%>
        <div class="col-md-6" id="page_info_area">
        </div>
        <%--分页条信息--%>
        <div class="col-md-6" id="page_nav_area">
        </div>
    </div>
</div>

<script type="text/javascript">

    <%--页面加载完成从后台请求数据到前台--%>
    $(function () {
        to_page(1);
    });

    function to_page(pn) {
        $.ajax({
            url: "${APP_PATH}/emps",
            data: "pn="+pn,
            type: "get",
            success: function (result) {
                // console.log(result);
                // 解析并显示员工数据
                build_emps_table(result);
                // 解析并显示分页信息
                build_page_info(result);
                // 解析并显示分页信息
                build_page_nav(result);
            }
        });
    }

    // 解析并显示员工数据
    function build_emps_table(result) {
        //清空TABLE表格
        $("#emps_table tbody").empty();
        var emps = result.extend.pageInfo.list;
        $.each(emps,function (index, item) {
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderTd = $("<td></td>").append(item.gender == 'M'?"男":"女");
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            var editBtn = $("<button></button>").addClass("btn btn-success btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("Edit"));
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("Delete"));
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
            $("<tr></tr>").append(empIdTd)
                .append(empNameTd)
                .append(genderTd)
                .append(emailTd)
                .append(deptNameTd)
                .append(btnTd)
                .appendTo("#emps_table tbody");
        })
    }

    //解析显示分页信息
    function build_page_info(result) {
        $("#page_info_area").empty();
        $("#page_info_area").append("当前第"+ result.extend.pageInfo.pageNum +"页,总共"
            + result.extend.pageInfo.pages + "页,共计"
            + result.extend.pageInfo.total + "条记录")
    }

    // 解析并显示分页条信息
    function build_page_nav(result) {
        // $("#page_nav_area")
        $("#page_nav_area").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        //构建元素
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if (result.extend.pageInfo.hasPreviousPage == false) {
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        }else {
            //为元素添加点击翻页事件
            firstPageLi.click(function () {
                to_page(1);
            });
            prePageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum-1);
            });
        }

        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
        if (result.extend.pageInfo.hasNextPage == false) {
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        }else {
            //为元素添加点击翻页事件
            nextPageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum+1);
            });
            lastPageLi.click(function () {
                to_page(result.extend.pageInfo.pages);
            });
        }

        //构造首页和前一页提示按钮
        ul.append(firstPageLi).append(prePageLi);
        $.each(result.extend.pageInfo.navigatepageNums,function (index,item) {
            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if (result.extend.pageInfo.pageNum == item) {
                numLi.addClass("active");
            }
            numLi.click(function () {
                to_page(item);
            })
            ul.append(numLi);
        });
        ul.append(nextPageLi).append(lastPageLi);
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }

</script>
</body>
</html>
