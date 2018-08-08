package cn.fcsca.test;

import cn.fcsca.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * SpringMvcTest
 *
 * @description 使用Spring测试模块提供的测试请求功能，测试curd请求的正确性
 * SpringMVC测试还需要引入SpringMVC配置文件
 *
 * @author Fcscanf@樊乘乘
 * @date 下午 15:28 2018-08-06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class SpringMvcTest {
    
    /**
     * 虚拟SpringMVC请求，获取到处理的结果
     */
    MockMvc mockMvc;

    /**
     * 传入SpringMVC的IOC控制器
     */
    @Autowired
    WebApplicationContext webApplicationContext;

    /**
     * 初始化MockMvc 
     *
     * @param
     * @return void
     * @author Fcscanf@樊乘乘
     * @date 下午 15:36 2018-08-06
     */
    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 测试分页的效果 
     *
     * @param
     * @return void
     * @author Fcscanf@樊乘乘
     * @date 下午 15:43 2018-08-06
     */
    @Test
    public void testPage() throws Exception {
//        模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
//        请求成功后请求域中会有pageInfo；我们可以取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码:" + pageInfo.getPageNum());
        System.out.println("总页码:" + pageInfo.getPages());
        System.out.println("总记录数:" + pageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码");
        int[] nums = pageInfo.getNavigatepageNums();
        for (int i : nums) {
            System.out.println(" " + i);
        }

//        获取员工数据
        List<Employee> list = pageInfo.getList();
        for (Employee employee : list) {
            System.out.println("ID:" + employee.getEmpId() + "==>Name:" + employee.getEmpName());
        }
    }
}
