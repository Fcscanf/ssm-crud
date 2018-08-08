package cn.fcsca.test;

import cn.fcsca.bean.Department;
import cn.fcsca.bean.Employee;
import cn.fcsca.dao.DepartmentMapper;
import cn.fcsca.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * MapperTest
 * Spring项目使用单元测试，可以注入组件
 * 1.导入springTest的依赖
 * 2.在测试类添加@ContextConfiguration注解指定配置文件的位置
 * 3.通过Junit的注解@RunWith指定当前使用的单元测试模块的SpringJUnit4ClassRunner.class的
 * 4.通过注解@Autowired指定要使用的组件
 *
 * @description 测试Mapper运行没问题以及Dao接口没问题
 * @author Fcscanf@樊乘乘
 * @date 上午 8:32 2018-08-06
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;
    /**
     * 测试DepartMentMapper
     *
     * @param
     * @return void
     * @author Fcscanf@樊乘乘
     * @date 上午 8:35 2018-08-06
     */
    @Test
    public void testCRUD() {

        System.out.println(departmentMapper);

        /**
         * 插入几个部门
         */
        departmentMapper.insertSelective(new Department(null, "调研部"));
        departmentMapper.insertSelective(new Department(null, "宣传部"));

        /**
         * 插入员工数据
         */

        employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@fcsca.cn",1));

        /**
         * 插入多个员工数据；批量，使用可以执行批量操作的sqlSession
         */

        EmployeeMapper sqlSessionMapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 10; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            sqlSessionMapper.insertSelective(new Employee(null, uid, "M", uid+"@fcsca.cn", 2));
        }
        System.out.println("批量添加操作完成");

       /* for (){
             employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@fcsca.cn",1));
        }*/

     /*
     ============================过时的操作==============================
     **************Spring项目使用单元测试，可以注入组件进行测试***************
//        1.创建SpringIOC容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        2.从容器获取Mapper
        DepartmentMapper bean = applicationContext.getBean(DepartmentMapper.class);*/

    }
}
