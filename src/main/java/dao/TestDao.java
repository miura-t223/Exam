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

public class TestDao {

    private DataSource ds;

    // baseSql
    private String baseSql = "SELECT * FROM test";

    public TestDao() throws Exception {
        InitialContext ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/comp/env/jdbc/book2");
    }

    // ■ get（1件取得）
    public Test get(Student student, Subject subject, School school, int no) throws Exception {

        Connection con = ds.getConnection();

        String sql = baseSql + " WHERE student_id=? AND subject_code=? AND school_cd=? AND no=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, student.getNo());
        st.setString(2, subject.getCode());
        st.setString(3, school.getCd());
        st.setInt(4, no);

        ResultSet rs = st.executeQuery();

        Test test = null;

        if (rs.next()) {
            test = new Test();

            test.setStudent(student);
            test.setSubject(subject);
            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));
        }

        st.close();
        con.close();

        return test;
    }

    // ■ postFilter（ResultSet → List変換）
    public List<Test> postFilter(ResultSet rs, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        while (rs.next()) {

            Test test = new Test();

            Student student = new Student();
            student.setNo(rs.getString("student_id"));

            Subject subject = new Subject();
            subject.setCode(rs.getString("subject_code"));

            test.setStudent(student);
            test.setSubject(subject);
            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));

            list.add(test);
        }

        return list;
    }

    // ■ filter（条件検索）
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = ds.getConnection();

        String sql = baseSql + " WHERE ent_year=? AND class_num=? AND subject_code=? AND no=? AND school_cd=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setInt(1, entYear);
        st.setString(2, classNum);
        st.setString(3, subject.getCode());
        st.setInt(4, num);
        st.setString(5, school.getCd());

        ResultSet rs = st.executeQuery();

        list = postFilter(rs, school);

        st.close();
        con.close();

        return list;
    }

    // ■ save（List版）
    public boolean save(List<Test> list) throws Exception {

        Connection con = ds.getConnection();

        boolean result = true;

        for (Test test : list) {
            if (!save(test, con)) {
                result = false;
            }
        }

        con.close();

        return result;
    }

    // ■ save（1件版）
    public boolean save(Test test, Connection con) throws Exception {

        String sql = "INSERT INTO test(student_id, subject_code, school_cd, no, point) VALUES(?,?,?,?,?)";

        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, test.getStudent().getNo());
        st.setString(2, test.getSubject().getCode());
        st.setString(3, test.getStudent().getSchool().getCd());
        st.setInt(4, test.getNo());
        st.setInt(5, test.getPoint());

        int line = st.executeUpdate();

        st.close();

        return line > 0;
    }
}