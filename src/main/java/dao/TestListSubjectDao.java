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

public class TestListSubjectDao {

    private DataSource ds;

    public TestListSubjectDao() throws Exception {
        InitialContext ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/comp/env/jdbc/miurasystem");
    }

    // ■ 科目ごとの成績一覧
    public List<Test> filter(Subject subject, String classNum, int entYear, int no, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = ds.getConnection();

        String sql =
            "SELECT t.student_id, t.no, t.point, " +
            "st.name AS student_name, st.class_num, st.ent_year " +
            "FROM test t " +
            "JOIN student st ON t.student_id = st.id " +
            "WHERE t.subject_code = ? AND t.school_cd = ? " +
            "AND st.class_num = ? AND st.ent_year = ? AND t.no = ? " +
            "ORDER BY st.class_num, st.no";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, subject.getCd());
        st.setString(2, school.getCd());
        st.setString(3, classNum);
        st.setInt(4, entYear);
        st.setInt(5, no);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            Test test = new Test();

            // Subject
            test.setSubject(subject);

            // Student（名前も入れる）
            Student student = new Student();
            student.setNo(rs.getString("student_id"));
            student.setName(rs.getString("student_name"));
            test.setStudent(student);

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