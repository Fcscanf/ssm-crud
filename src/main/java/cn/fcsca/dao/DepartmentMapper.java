package cn.fcsca.dao;

import cn.fcsca.bean.Department;
import cn.fcsca.bean.DepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * DepartmentMapper 部门Dao层接口
 *
 * @author Fcscanf@樊乘乘
 * @date 上午 8:57 2018-08-06
 */
public interface DepartmentMapper {
    
    /**
     * countByExample
     *
     * @param example
     * @return
     * @author Fcscanf@樊乘乘
     * @date 上午 9:03 2018-08-06
     */
    long countByExample(DepartmentExample example);

    /**
     * deleteByExample 
     *
     * @param example
     * @return 
     * @author Fcscanf@樊乘乘
     * @date 下午 12:29 2018-08-06
     */
    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Integer deptId);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}