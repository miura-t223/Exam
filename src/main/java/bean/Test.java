package bean;

import java.io.Serializable;

public class Test implements Serializable {

    // 学生
    private Student student;

    // 科目
    private Subject subject;

    // 回数（1回目、2回目など）
    private int no;

    // 点数
    private int point;

    // クラス番号
    private ClassNum classNum;
    
    // 学校
    private School school;
    
//-----------------------------------------------------
    // getter・setter
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ClassNum getClassNum() {
        return classNum;
    }

    public void setClassNum(ClassNum classNum) {
        this.classNum = classNum;
    }
    
    public School getSchool() {
        return school;
    }
    
    public void setSchool(School school) {
        this.school = school;
    }

}