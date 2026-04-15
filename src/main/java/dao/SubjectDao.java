package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.School;
import bean.Subject;

public class SubjectDao {

    private DataSource ds;

    // baseSql
    private String baseSql = "SELECT * FROM subject";

    public SubjectDao() throws Exception {
        InitialContext ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/comp/env/jdbc/miurasystem");
    }

    // ■ get（1件取得）
    public Subject get(String code, School school) throws Exception {

        Connection con = ds.getConnection();

        String sql = baseSql + " WHERE cd=? AND school_cd=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, code);
        st.setString(2, school.getCd());

        ResultSet rs = st.executeQuery();

        Subject subject = null;

        if (rs.next()) {
            subject = new Subject();

            subject.setCode(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            subject.setSchool(school);
        }

        st.close();
        con.close();

        return subject;
    }

    // ■ postFilter（ResultSet → List変換）
    public List<Subject> postFilter(ResultSet rs, School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        while (rs.next()) {

            Subject subject = new Subject();

            subject.setCode(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            subject.setSchool(school);

            list.add(subject);
        }

        return list;
    }

    // ■ filter（一覧取得：学校ごと）
    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        Connection con = ds.getConnection();

        String sql = baseSql + " WHERE school_cd=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, school.getCd());

        ResultSet rs = st.executeQuery();

        list = postFilter(rs, school);

        st.close();
        con.close();

        return list;
    }

    // ■ save（1件）
    public boolean save(Subject subject) throws Exception {

        Connection con = ds.getConnection();

        String sql = "INSERT INTO subject(school_cd, cd, name) VALUES(?,?,?)";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, subject.getSchool().getCd());
        st.setString(2, subject.getCode());
        st.setString(3, subject.getName());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line > 0;
    }

    // ■ update（更新）
    public boolean update(Subject subject) throws Exception {

        Connection con = ds.getConnection();

        String sql = "UPDATE subject SET name=? WHERE cd=? AND school_cd=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, subject.getName());
        st.setString(2, subject.getCode());
        st.setString(3, subject.getSchool().getCd());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line > 0;
    }

    // ■ delete（削除）
    public boolean delete(String code, School school) throws Exception {

        Connection con = ds.getConnection();

        String sql = "DELETE FROM subject WHERE cd=? AND school_cd=?";
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, code);
        st.setString(2, school.getCd());

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line > 0;
    }
}