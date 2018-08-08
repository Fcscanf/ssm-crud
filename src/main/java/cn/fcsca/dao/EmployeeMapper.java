package cn.fcsca.dao;

import cn.fcsca.bean.Employee;
import cn.fcsca.bean.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer empId);

    /**
     *查询员工及其部门信息
     *
     * @param example
     * @return Employee
     * @author:Fcscanf@樊乘乘
     * @date: 下午 22:32 2018-08-05
     */
    List<Employee> selectByExampleWithDept(EmployeeExample example);

    /**
     * 根据主键查询员工和部门信息
     *
     * @param empId
     * @return
     * @author:Fcscanf@樊乘乘
     * @date: 下午 22:36 2018-08-05
     */
    Employee selectByPrimaryKeyWithDept(Integer empId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}