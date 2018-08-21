package cn.fcsca.controller;

import cn.fcsca.bean.Employee;
import cn.fcsca.service.EmployeeService;
import cn.fcsca.utils.MsgUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 自动注入EmployeeService组件，Service层需要有@Service注解
     */
    @Autowired
    EmployeeService employeeService;

    /**
     * 根据员工id删除员工信息 ——单个删除复合多个删除
     * 批量删除：1-2-3
     * 单个删除：1
     *
     * @param ids
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 21:47 2018-08-09 
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public MsgUtil deleteEmpById(@PathVariable("ids") String ids) {
        if (ids.contains("-")) {
            List<Integer> del_ids = new ArrayList<Integer>();
            String[] str_ids = ids.split("-");
            //组装id的集合
            for (String s : str_ids) {
                del_ids.add(Integer.parseInt(s));
            }
            employeeService.deleteBatch(del_ids);
        } else {
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmpById(id);
        }
        return MsgUtil.success();
    }

    /**
     * 更新员工信息的处理器
     *
     * 问题：如果直接发送ajax=PUT形式的请求，前台可以取到值，后台封装的数据全部为null
     * 原因：Tomcat的处理机制：
     *      1、将请求体中的数据，封装一个Map
     *      2、request.getParameter（“empName”）就会从这个map中取值
     *      3、SpringMVC封装POJO对象的时候，会把POJO中每个属性的值，request.getParameter（“email”）
     * AJAX发送PUT请求：
     *      PUT请求体中的数据，request.getParameter（“empName”）拿不到
     *      Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体
     *
     * @param employee
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 20:20 2018-08-09 
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public MsgUtil updateEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return MsgUtil.success();
    }

    /**
     * 根据id查询员工信息 
     *
     * @param id
     * @return MsgUtil
     * @author Fcscanf@樊乘乘
     * @date 下午 19:25 2018-08-09 
     */
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public MsgUtil getEmpById(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmpById(id);
        return MsgUtil.success().add("emp", employee);
    }

    /**
     * 校验用户名的可用性
     *
     * @param empName
     * @return null
     * @author Fcscanf@樊乘乘
     * @date 上午 10:24 2018-08-09 
     */
    @ResponseBody
    @RequestMapping("/checkempname")
    public MsgUtil checkEmpName(@RequestParam("empName") String empName) {
        //先判断用户名是否是合法的表达式
        String regEmpName = "(^[a-zA-Z0-9_-]{6,16})|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regEmpName)) {
            return MsgUtil.fail().add("va_msg","用户名可以是2-5位中文或者6-16位英文和数字的组合");
        }

        //再进行数据库用户名校验
        boolean b = employeeService.checkEmpName(empName);
        if (b) {
            return MsgUtil.success();
        } else {
            return MsgUtil.fail().add("va_msg","用户名不可用!");
        }
    }

    /**
     * 员工新增保存
     * 添加hibernate-validator依赖，就可以支持后台操作的JSR303验证
     * @Valid给对象添加JSR303验证
     *
     * @param employee
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 19:24 2018-08-08 
     */
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    public MsgUtil saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            //校验失败，在模态框返回校验失败的信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println("错误的字段名：" + fieldError.getField());
                System.out.println("错误的信息" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return MsgUtil.fail().add("errorFileds",map);
        } else {
            employeeService.saveEmp(employee);
            return MsgUtil.success();
        }
    }

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
     * @RequestMapping("/emps")
     *
     * @param pn
     * @return list 返回到员工信息列表页面
     * @author Fcscanf@樊乘乘
     * @date 下午 14:42 2018-08-06
     */

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
