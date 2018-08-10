package cn.fcsca.service;

import cn.fcsca.bean.Employee;
import cn.fcsca.bean.EmployeeExample;
import cn.fcsca.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EmployeeService
 *
 * @description 处理员工相关的业务
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
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 14:54 2018-08-06
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 员工信息保存
     *
     * @param employee
     * @return 
     * @author Fcscanf@樊乘乘
     * @date 下午 19:28 2018-08-08 
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 查找统计前台传过来的用户名在后台有多少个这样的记录数，如果记录数为0则该用户名可用 
     *
     * @param empName
     * @return boolean
     * @author Fcscanf@樊乘乘
     * @date 上午 10:33 2018-08-09 
     */
    public boolean checkEmpName(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(employeeExample);
        return count == 0;
    }

    /**
     * 按照员工id查询员工信息
     *
     * @param id
     * @return employee
     * @author Fcscanf@樊乘乘
     * @date 下午 19:26 2018-08-09 
     */
    public Employee getEmpById(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 根据页面提交的信息有选择的更新 
     *
     * @param employee
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 20:21 2018-08-09 
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 根据员工ID进行有选择的删除 
     *
     * @param id
     * @return void
     * @author Fcscanf@樊乘乘
     * @date 下午 21:49 2018-08-09 
     */
    public void deleteEmpById(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 对员工进行批量删除操作 
     *
     * @param ids
     * @return void
     * @author Fcscanf@樊乘乘
     * @date 下午 23:19 2018-08-09 
     */
    public void deleteBatch(List<Integer> ids) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        //delete from *** where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(employeeExample);
    }
}
