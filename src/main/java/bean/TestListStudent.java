package bean;

import java.io.Serializable;
import java.util.List;

public class TestListStudent implements Serializable {

    // 学生情報
    private Student student;

    // 成績リスト
    private List<Test> testList;

    // getter・setter
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }
}