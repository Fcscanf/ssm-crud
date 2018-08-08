package cn.fcsca.bean;

/**
  * 员工
  *
  * @author Fcscanf@樊乘乘
  * @date 下午 22:38 2018-08-05
  */
public class Employee {

    /**
     * empId 员工ID
     */
    private Integer empId;

    /**
     * empName 员工姓名
     */
    private String empName;

    /**
     * gender
     */
    private String gender;

    /**
     * email 员工邮箱
     */
    private String email;

    /**
     * dId 员工部门的ID
     */
    private Integer dId;

    /**
     * department 员工的部门信息
     */
    private Department department;

    public Employee() {
        super();
    }

    public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.dId = dId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", dId=" + dId +
                '}';
    }
}