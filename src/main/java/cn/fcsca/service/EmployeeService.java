package cn.fcsca.service;

import cn.fcsca.bean.Employee;
import cn.fcsca.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EmployeeService
 *
 * @description
 * @author Fcscanf@樊乘乘
 * @date 下午 14:47 2018-08-06
 */
@Service
public class EmployeeService {

    /**
     * 自动注入EmployeeMapper映射
     */
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工的信息 
     *
     * @param 
     * @return List
     * @author Fcscanf@樊乘乘
     * @date 下午 14:54 2018-08-06
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }
}
