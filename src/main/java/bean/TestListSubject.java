package bean;

import java.io.Serializable;
import java.util.List;

public class TestListSubject implements Serializable {

    // 科目
    private Subject subject;

    // クラス
    private String classNum;

    // 入学年度
    private int entYear;

    // 回数
    private int no;

    // 成績リスト
    private List<Test> testList;

    // getter・setter
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }
}