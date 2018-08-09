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

<!-- ===================================Modal员工修改的模态框================================== -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">员工修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <%--员工姓名empName--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工姓名empName</label>
                        <div class="col-sm-12">
                            <p class="form-control-static" id="empName_update_static"></p>
                        </div>
                    </div>
                    <%--员工邮箱Email--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工邮箱Email</label>
                        <div class="col-sm-12">
                            <input type="text" name="email" class="form-control" id="email_update_input" placeholder="email@fcsca.cn">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <%--员工性别Gender--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工性别Gender</label>
                        <div class="col-sm-12">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_update_input" value="M"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <%--员工部门Department--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工部门Department</label>
                        <div class="col-sm-12">
                            <%--部门提交部门Id即可--%>
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="emp_update_btn">Save Updates</button>
            </div>
        </div>
    </div>
</div>

<!-- ===================================Modal员工添加的模态框================================== -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <%--员工姓名empName--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工姓名empName</label>
                        <div class="col-sm-12">
                            <input type="text" name="empName" class="form-control" id="empName_add_input" placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <%--员工邮箱Email--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工邮箱Email</label>
                        <div class="col-sm-12">
                            <input type="text" name="email" class="form-control" id="email_add_input" placeholder="email@fcsca.cn">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <%--员工性别Gender--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工性别Gender</label>
                        <div class="col-sm-12">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <%--员工部门Department--%>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">员工部门Department</label>
                        <div class="col-sm-12">
                            <%--部门提交部门Id即可--%>
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">Save changes</button>
            </div>
        </div>
    </div>
</div>

<%--=====================================搭建显示页面====================================--%>
<div class="container">

    <%--标题行--%>
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>

        <%--按钮行--%>
    <div class="row">
        <div class="col-md-8 col-md-offset-10">
            <button class="btn btn-success" id="emp_add_modal_btn">新增</button>
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

    var totalRecord,currentPage;
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
            var editBtn = $("<button></button>").addClass("btn btn-success btn-sm edit_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("Edit"));
            //为编辑按钮添加一个自定义的属性，来表示当前员工id
            editBtn.attr("edit-id", item.empId);
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
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
        //清空数据
        $("#page_info_area").empty();
        $("#page_info_area").append("当前第" + result.extend.pageInfo.pageNum + "页,总共"
            + result.extend.pageInfo.pages + "页,共计"
            + result.extend.pageInfo.total + "条记录");
        totalRecord = result.extend.pageInfo.total;
        currentPage = result.extend.pageInfo.pageNum;
    }

    // 解析并显示分页条信息
    function build_page_nav(result) {
        // $("#page_nav_area")
        //清空数据
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

    //清空表单样式及内容
    function reset_form(ele) {
        $(ele)[0].reset();
        //清空表单样式
        $(ele).find("*").removeClass("has-error has-success");
        $(ele).find(".help-block").text("");
    }

    //点击新增按钮给新增按钮添加弹出事件进而弹出新增员工的模态框
    $("#emp_add_modal_btn").click(function () {
        //清空表单，以防重复提交(进行完整的重置——表单数据和表单样式)
        reset_form("#empAddModal form")
        // $("#empAddModal form")[0].reset();
        //在弹出模态框之前发送ajax请求得到部门信息显示到员工新增的模态框的下拉列表
        getDepts("#empAddModal select");
        $("#empAddModal").modal({
            backdrop:"static"
        });
    });

    //查询所有部门信息
    function getDepts(ele) {
        //清空下拉列表的数据
        $(ele).empty();
        $.ajax({
            url: "${APP_PATH}/depts",
            type: "GET",
            success:function (result) {
                //测试时显示后台请求的数据
                // console.log(result);
                //将得到的数据在页面解析到下拉列表
                // 命名id $("#dept_add_select") $("#empAddModal select")
                $.each(result.extend.depts,function () {
                    var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                    optionEle.appendTo(ele)
                })
            }
        })
    }

    //校验表单数据
    function validate_add_form() {
        //校验用户名
        var empName = $("#empName_add_input").val();
        var regName = /(^[a-zA-Z0-9_-]{6,16})|(^[\u2E80-\u9FFF]{2,5})/;
        if (!regName.test(empName)) {
            // alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
            // $("#empName_add_input").parent().addClass("has-error");
            // $("#empName_add_input").next("span").text("用户名可以是2-5位中文或者6-16位英文和数字的组合");
            show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
            return false;
        } else {
            // $("#empName_add_input").parent().addClass("has-success");
            // $("#empName_add_input").next("span").text("");
            show_validate_msg("#empName_add_input", "success", "");
        }
        //校验邮箱格式
        var email = $("#email_add_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!regEmail.test(email)) {
            // alert("邮箱格式不正确！");
            // $("#email_add_input").parent().addClass("has-error");
            // $("#email_add_input").next("span").text("邮箱格式不正确！");
            show_validate_msg("#email_add_input", "error", "邮箱格式不正确！请重新输入：（示例：fcscaf@126.com）");
            return false;
        } else {
            // $("#email_add_input").parent().addClass("has-success");
            // $("#email_add_input").next("span").text("");
            show_validate_msg("#email_add_input", "success", "");
        }
        return true;
    }

    //显示校验结果的提示信息
    function show_validate_msg(ele, status, msg) {
        //清除当前元素状态，以免叠加
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");
        if ("success" == status) {
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        }else if ("error" == status) {
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }

    //校验用户名可用性
    $("#empName_add_input").change(function () {
        //发送ajax请求检验是否和后台用户名重复
        var empName = this.value;
        $.ajax({
            url:"${APP_PATH}/checkempname",
            data:"empName="+empName,
            type:"POST",
            success:function (result) {
                if (result.code == 100) {
                    show_validate_msg("#empName_add_input","success","用户名可用!");
                    $("#emp_save_btn").attr("ajax-va", "success");
                } else {
                    show_validate_msg("#empName_add_input","error",result.extend.va_msg);
                    $("#emp_save_btn").attr("ajax-va", "error");
                }
            }
        })
    });

    // 保存按钮新增员工
    $("#emp_save_btn").click(function () {
        //前台Jquery的信息校验
        if (!validate_add_form()) {
            return false;
        };
        //判断用户名的校验状态，如果ajax后台请求校验成功正常执行
        if ($(this).attr("ajax-va") == "error") {
            return false;
        }
        //得到模态框的数据发送到后台保存
        $.ajax({
            url: "${APP_PATH}/emp",
            type: "POST",
            data: $("#empAddModal form").serialize(),
            success:function (result) {
                if (result.code == 100) {
                    //弹出窗口提示保存成功
                    alert(result.msg);
                    //关闭模态框
                    $("#empAddModal").modal('hide');
                    //查询最新添加的数据
                    to_page(totalRecord);
                } else {
                    //显示失败信息
                    if (undefined != result.extend.errorFields.email) {
                        show_validate_msg("#email_add_input", "error", result.extend.errorFields.email);
                    }
                    if (undefined != result.extend.errorFields.empName) {
                        show_validate_msg("#empName_add_input", "error", result.extend.errorFields.empName);
                    }
                }
            }
        });
    });

    //给员工修改按钮绑定单击事件
    //分析：
    //JS绑定是在页面加载进行绑定的，要绑定单击事件的按钮是页面加载完成已经ajax请求完成后才显示的，所以一般的cilck绑定不了
    //1.可以在创建按钮时绑定
    //2.绑定点击.live()——jquery新版没有live，可以使用on代替-注意按钮id之前加“.”
    //给按钮添加点击绑定事件
    $(document).on("click",".edit_btn",function () {
        // alert("edit");
        //查询部门信息并显示到模态框的下拉列表
        getDepts("#empUpdateModal select");
        //查询员工信息并显示到模态框
        getEmp($(this).attr("edit-id"));
        //把员工的id传递给模态框的更新按钮
        $("#emp_update_btn").attr("edit-id", $(this).attr("edit-id"));
        $("#empUpdateModal").modal({
            backdrop:"static"
        })
    });

    //根据ID查询员工信息
    function getEmp(id) {
        $.ajax({
            url: "${APP_PATH}/emp/" + id,
            type: "GET",
            success: function (result) {
                // console.log(result);
                var empData = result.extend.emp;
                $("#empName_update_static").text(empData.empName);
                $("#email_update_input").val(empData.email);
                $("#empUpdateModal input[name=gender]").val([empData.gender]);
                $("#empUpdateModal select").val([empData.dId]);
            }
        });
    }

    //点击更新，更新员工信息
    $("#emp_update_btn").click(function () {
        //校验邮箱格式
        var email = $("#email_update_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!regEmail.test(email)) {
            show_validate_msg("#email_update_input", "error", "邮箱格式不正确！请重新输入：（示例：fcscaf@126.com）");
            return false;
        } else {
            show_validate_msg("#email_update_input", "success", "");
        }

        //发送ajax请求保存更新的员工数据
        $.ajax({
            url: "${APP_PATH}/emp/"+$(this).attr("edit-id"),
            type: "PUT",
            data: $("#empUpdateModal form").serialize(),
            success: function (result) {
                alert(result.msg);
                //关闭对话框
                $("#empUpdateModal").modal("hide");
                //回到当前修改信息的页面
                to_page(currentPage);
            }
        });
    });

</script>
</body>
</html>
