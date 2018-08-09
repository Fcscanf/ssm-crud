package cn.fcsca.service;

import cn.fcsca.bean.Department;
import cn.fcsca.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * DepartmentService
 *
 * @author Fcscanf@樊乘乘
 * @description 处理部门相关的业务
 * @date 下午 16:26 2018-08-08
 */
@Service
public class DepartmentService {

    /**
     * 自动注入DepartmentMapper映射 
     */
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 调用Dao层的Mapper接口查询所有的部门信息 
     *
     * @param 
     * @return
     * @author Fcscanf@樊乘乘
     * @date 下午 16:35 2018-08-08 
     */
    public List<Department> getDepts() {
        List<Department> departmentList = departmentMapper.selectByExample(null);
        return departmentList;
    }
}
