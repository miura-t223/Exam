package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // SQL:学校名で絞り込み
    private String baseSql = "select * from test where school_cd=?";

<<<<<<< HEAD
    // baseSql
    private String baseSql = "SELECT * FROM test";

    public TestDao() throws Exception {
        InitialContext ic = new InitialContext();
        ds = (DataSource) ic.lookup("java:/comp/env/jdbc/miurasystem");
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

=======
    
    private List<Test> postFilter(ResultSet rs, School school) throws Exception {
    	List<Test> list = new ArrayList<>();
    	
        // SQLの結果を１行ずつ取り出す
>>>>>>> branch 'master' of https://github.com/miura-t223/Exam.git
        while (rs.next()) {
        	
        	// testインスタンスの作成
            Test test = new Test();
            
            // 学生
            Student student = new Student();
            student.setClassNum(rs.getString("class_num"));
            student.setNo(rs.getString("student_id"));
            student.setSchool(school);
            
            // 科目
            Subject subject = new Subject();
            subject.setSchool(school);
            subject.setCode(rs.getString("subject_code"));
            
            test.setStudent(student);
            test.setSubject(subject);
            
            // テスト回数
            test.setNo(rs.getInt("no"));
            // 得点
            test.setPoint(rs.getInt("point"));
            
            // リストに追加
            list.add(test);
        }
        return list;
    }
    
    
    
    
    // フィルター（科目別成績一覧検索：年度/クラス/科目）
    public List<Test> filter(School school, int entYear, String classNum, String subjectCd) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        // 条件と並び順
        String condition = " and ent_year=? and class_num=? and subject_code=? ";
        String order = " order by student_id, no ";

        try {
            // SQLを組み立て
            String sql = baseSql + condition + order;

            // SQLの実行準備
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subjectCd);

            // 実行
            rs = statement.executeQuery();

            // 結果をTestのリストに変換
	            list = postFilter(rs, school);

	        } catch (Exception e) {
	            throw e;

	        } finally {
	            if (rs != null) {
	                try { rs.close(); } catch (SQLException sqle) { throw sqle; }
	            }
	            if (statement != null) {
	                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
	            }
	            if (connection != null) {
	                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
	            }
	        }

	        return list;
    }
    
    
    
 // フィルター（学生別成績一覧検索）
	public List<Test> filter(School school, String studentNo) throws Exception {
	
	    List<Test> list = new ArrayList<>();
	
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	
	    String condition = " and student_id=? ";
	    String order = " order by no ";
	
	    try {
	        String sql = baseSql + condition + order;
	
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getCd());
	        statement.setString(2, studentNo);
	
	        rs = statement.executeQuery();
	        list = postFilter(rs, school);
	
	    } finally {
	        if (rs != null) rs.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	
	    return list;
	}
}
	
