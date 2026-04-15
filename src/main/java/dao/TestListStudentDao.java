package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestListStudentDao {

    private DataSource ds;

    public TestListStudentDao() throws Exception {
        InitialContext ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/comp/env/jdbc/book2");
    }

    // ■ 学生ごとの成績一覧取得
    public List<Test> filter(Student student, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = ds.getConnection();

        String sql =
            "SELECT t.student_id, t.subject_code, t.no, t.point, " +
            "s.name AS subject_name " +
            "FROM test t " +
            "JOIN subject s ON t.subject_code = s.code AND t.school_cd = s.school_cd " +
            "WHERE t.student_id = ? AND t.school_cd = ? " +
            "ORDER BY t.subject_code, t.no";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, student.getNo());
        st.setString(2, school.getCd());

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            Test test = new Test();

            // Student
            test.setStudent(student);

            // Subject（名前も入れるのがポイント）
            Subject subject = new Subject();
            subject.setCode(rs.getString("subject_code"));
            subject.setName(rs.getString("subject_name"));
            test.setSubject(subject);

            // 成績
            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));

            list.add(test);
        }

        st.close();
        con.close();

        return list;
    }
}