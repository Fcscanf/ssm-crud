package cn.fcsca.controller;

import cn.fcsca.bean.Department;
import cn.fcsca.service.DepartmentService;
import cn.fcsca.utils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * DepartmentController
 *
 * @author Fcscanf@樊乘乘
 * @description 处理和部门有关的请求
 * @date 下午 16:23 2018-08-08
 */
@Controller
public class DepartmentController {

    /**
     * 自动注入DepartmentService组件，Service层需要有@Service注解
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询所有部门的处理器 
     *
     * @param
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 16:37 2018-08-08 
     */
    @RequestMapping("/depts")
    @ResponseBody
    public MsgUtil getDepts() {
        List<Department> departmentList = departmentService.getDepts();
        return MsgUtil.success().add("depts",departmentList);
    }
}
