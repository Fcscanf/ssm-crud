package cn.fcsca.bean;

/**
  * 部门
  *
  * @author Fcscanf@樊乘乘
  * @date 下午 22:37 2018-08-05
  */
public class Department {

    /**
     * deptId 部门的ID
     */
    private Integer deptId;

    /**
     * deptName 部门名称
     */
    private String deptName;

    public Department() {
        super();
    }

    public Department(Integer deptId, String deptName) {
        super();
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}