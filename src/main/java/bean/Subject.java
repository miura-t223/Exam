package bean;

import java.io.Serializable;

public class Subject implements Serializable {

    // 科目コード
    private String code;

    // 科目名
    private String name;

    // 所属学校
    private School school;

    // getter・setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}