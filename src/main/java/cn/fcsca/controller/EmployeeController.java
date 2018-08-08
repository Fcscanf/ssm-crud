package cn.fcsca.controller;

import cn.fcsca.bean.Employee;
import cn.fcsca.service.EmployeeService;
import cn.fcsca.utils.MsgUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * EmployeeController
 *
 * @description 处理员工CRUD请求
 * @author Fcscanf@樊乘乘
 * @date  下午 14:40 2018-08-06
 */
@Controller
public class EmployeeController {

    /**
     * 指定EmployeeService 员工业务层
     */
    @Autowired
    EmployeeService employeeService;

    /**
     * 把分页信息拿到的PageInfo封装信息在这里封装成对象，转换成json格式，以json格式返回到不同的客户端
     * 需要导入Jackson包将对象转换成json
     *
     * @param pn
     * @return pageInfo
     * @author Fcscanf@樊乘乘
     * @date 下午 12:46 2018-08-07
     */
    @RequestMapping("/emps")
    @ResponseBody
    public MsgUtil getEmpaWithJson(@RequestParam(value = "pn", defaultValue = "1")Integer pn) {
        /*
        分页查询-引入分页插件方便操作
        1.引入PageHelper分页插件-pom文件引入相关依赖
        2.在Mybatis配置文件注册该插件
        3.在查询之前只需要调用，传入页码，以及每页的大小,然后执行查询的操作
        4.查询完成将结果封装到pageInfo交付至页面
        5.通过一个变量Model给页面传值
        */

//        进行分页的页码，页大小的设计
        PageHelper.startPage(pn, 5);
//        进行查询
        List<Employee> emps = employeeService.getAll();
//        使用pageInfo包装查询的结果，将其交付至页面，并传入连续显示的页数的值
        PageInfo pageInfo = new PageInfo(emps,5);
//        model添加交付给页面的值
        return MsgUtil.success().add("pageInfo",pageInfo);
    }

//    ==================上面的处理是将数据转发出去，然后页面得到数据进行处理==================
//    ================================================================================
//    ===================下面的是将数据封装转发到固定的页面，业务拓展性低====================
    /**
     * 获取所有员工的信息 分页显示==========这里的处理只能发生到浏览器页面，不能很好的适应多客户端IOS，Android等
     * 上面的处理以json数据发送到页面，可以适应不同的需求
     *
     * @param pn
     * @return list 返回到员工信息列表页面
     * @author Fcscanf@樊乘乘
     * @date 下午 14:42 2018-08-06
     */

//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1")Integer pn, Model model) {
        /*
        分页查询-引入分页插件方便操作
        1.引入PageHelper分页插件-pom文件引入相关依赖
        2.在Mybatis配置文件注册该插件
        3.在查询之前只需要调用，传入页码，以及每页的大小,然后执行查询的操作
        4.查询完成将结果封装到pageInfo交付至页面
        5.通过一个变量Model给页面传值
        */

//        进行分页的页码，页大小的设计
        PageHelper.startPage(pn, 5);
//        进行查询
        List<Employee> emps = employeeService.getAll();
//        使用pageInfo包装查询的结果，将其交付至页面，并传入连续显示的页数的值
        PageInfo pageInfo = new PageInfo(emps,5);
//        model添加交付给页面的值
        model.addAttribute("pageInfo", pageInfo);
        return "list";
    }
}
