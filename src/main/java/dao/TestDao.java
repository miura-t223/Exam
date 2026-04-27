package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
 
public class TestDao extends Dao {
 
    // SQL:学校名で絞り込み
    private String baseSql = "select * from test where school_cd=?";

    private List<Test> postFilter(ResultSet rs, School school) throws Exception {
    	List<Test> list = new ArrayList<>();
        // SQLの結果を１行ずつ取り出す
        while (rs.next()) {
        	// testインスタンスの作成
            Test test = new Test();
            // 学生
            Student student = new Student();
            student.setClassNum(rs.getString("class_num"));
            student.setNo(rs.getString("student_no"));
            student.setSchool(school);
            try {
                student.setName(rs.getString("student_name"));
            } catch (SQLException e) {}
            try {
                student.setEntYear(rs.getInt("ent_year"));
            } catch (SQLException e) {}
            // 科目
            Subject subject = new Subject();
            subject.setSchool(school);
            subject.setCd(rs.getString("subject_cd"));
            try {
                subject.setName(rs.getString("subject_name"));
            } catch (SQLException e) {}
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            // テスト回数
            test.setNo(rs.getInt("no"));
            // 得点
            test.setPoint(rs.getInt("point"));
            // クラス番号
            ClassNum cn = new ClassNum();
            cn.setClass_num(rs.getString("class_num"));
            cn.setSchool(school);
            test.setClassNum(cn);
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
 
        try {
            // SQLを組み立て
            String sql =
                    "select " +
                    "t.student_no, t.subject_cd, t.no, t.point, t.class_num, " +
                    "s.name as student_name, s.ent_year, " +
                    "sub.name as subject_name " +
                    "from test t " +
                    "join student s on t.student_no = s.no and t.school_cd = s.school_cd " +
                    "join subject sub on t.subject_cd = sub.cd and t.school_cd = sub.school_cd " +
                    "where t.school_cd = ? " +
                    "and s.ent_year = ? " +
                    "and s.class_num = ? " +
                    "and t.subject_cd = ? " +
                    "and t.is_deleted = false " +
                    "order by t.student_no, t.no";
 
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
	    try {
	        String sql =
	                "select " +
	                "t.student_no, t.subject_cd, t.no, t.point, t.class_num, " +
	                "s.name as student_name, s.ent_year, " +
	                "sub.name as subject_name " +
	                "from test t " +
	                "join student s on t.student_no = s.no and t.school_cd = s.school_cd " +
	                "join subject sub on t.subject_cd = sub.cd and t.school_cd = sub.school_cd " +
	                "where t.school_cd = ? and t.student_no = ? " +
	                "and t.is_deleted = false " +
	                "order by t.subject_cd, t.no";
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
	
	
	public Test get(School school, String studentNo, String subjectCd, int no) throws Exception {
	
	    Test test = null;
	
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	
	
	    String sql =
	            "select " +
	            "t.student_no, t.subject_cd, t.no, t.point, t.class_num, " +
	            "s.name as student_name, s.ent_year, " +
	            "sub.name as subject_name " +
	            "from test t " +
	            "join student s on t.student_no = s.no and t.school_cd = s.school_cd " +
	            "join subject sub on t.subject_cd = sub.cd and t.school_cd = sub.school_cd " +
	            "where t.school_cd = ? " +
	            "and t.student_no = ? " +
	            "and t.subject_cd = ? " +
	            "and t.no = ? " +
	            "and t.is_deleted = false";
	
	
	    try {
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getCd());
	        statement.setString(2, studentNo);
	        statement.setString(3, subjectCd);
	        statement.setInt(4, no);
	
	        rs = statement.executeQuery();
	
	        if (rs.next()) {
	            test = new Test();
	
	            Student student = new Student();
	            student.setNo(rs.getString("student_no"));
	            student.setName(rs.getString("student_name"));
	            student.setEntYear(rs.getInt("ent_year"));
	            student.setClassNum(rs.getString("class_num"));
	            student.setSchool(school);
	
	            Subject subject = new Subject();
	            subject.setCd(rs.getString("subject_cd"));
	            subject.setName(rs.getString("subject_name"));
	            subject.setSchool(school);
	
	            test.setStudent(student);
	            test.setSubject(subject);
	            test.setSchool(school);
	            test.setNo(rs.getInt("no"));
	            test.setPoint(rs.getInt("point"));
	            
	            ClassNum cn = new ClassNum();
	            cn.setClass_num(rs.getString("class_num"));
	            cn.setSchool(school);
	            test.setClassNum(cn);
	        }
	
	
	    } finally {
	        if (rs != null) rs.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	
	    return test;
	}
	
	
	public boolean delete(School school, String studentNo, String subjectCd, int no) throws Exception {
	
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;
	
	    try {
	        statement = connection.prepareStatement(
	                "update test set is_deleted = true " +
	                "where school_cd = ? and student_no = ? and subject_cd = ? and no = ?"
	        );
	        statement.setString(1, school.getCd());
	        statement.setString(2, studentNo);
	        statement.setString(3, subjectCd);
	        statement.setInt(4, no);
	
	        count = statement.executeUpdate();
	
	
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	
	    return count > 0;
	}
	
	
	
	//○○
	public Test get(Student student, Subject subject,
            School school, int no) throws Exception {
        Test test = null;
        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("select * from test where student_no=? and subject_cd=? and school_cd=? and no=? and is_deleted=false");
            st.setString(1, student.getNo());
            st.setString(2, subject.getCd());
            st.setString(3, school.getCd());
            st.setInt(4, no);

            rs = st.executeQuery();

            if (rs.next()) {
                test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(rs.getInt("no"));
                test.setPoint(rs.getInt("point"));
                ClassNum cn = new ClassNum();
                cn.setClass_num(rs.getString("class_num"));
                cn.setSchool(school);
                test.setClassNum(cn);
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (st != null) try { st.close(); } catch (SQLException e) {}
            if (con != null) try { con.close(); } catch (SQLException e) {}
        }
        return test;
    }


    // saveメソッド
    public boolean save(Test test) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = null;
        int count = 0;
     
        try {
            // 既存データがあるか確認
            Test old = get(test.getStudent(), test.getSubject(),
                              test.getSchool(), test.getNo());
     
            if (old == null) {
                // 新規登録
                st = con.prepareStatement(
                    "insert into test (student_no, subject_cd, school_cd, " +
                    "no, point, class_num, is_deleted) values (?, ?, ?, ?, ?, ?, false)"
                );
                st.setString(1, test.getStudent().getNo());
                st.setString(2, test.getSubject().getCd());
                st.setString(3, test.getSchool().getCd());
                st.setInt(4, test.getNo());
                st.setInt(5, test.getPoint());
                st.setString(6, test.getClassNum().getClass_num());

            } else {
                // 更新(pointだけ変える)
                st = con.prepareStatement(
                    "update test set point=?, class_num=?, is_deleted=false where student_no=? and " +
                    "subject_cd=? and school_cd=? and no=?"
                );
                st.setInt(1, test.getPoint());
                st.setString(2, test.getClassNum().getClass_num());
                st.setString(3, test.getStudent().getNo());
                st.setString(4, test.getSubject().getCd());
                st.setString(5, test.getSchool().getCd());
                st.setInt(6, test.getNo());
            }
     
            count = st.executeUpdate();
     
        } finally {
            if (st != null) try { st.close(); } catch (SQLException e) {}
            if (con != null) try { con.close(); } catch (SQLException e) {}
        }
        return count > 0;
    }
     
    /**
     * 複数件まとめて保存する(for文で1件ずつsave)
     */
    public boolean save(List<Test> list) throws Exception {
        boolean allSuccess = true;
        for (Test test : list) {
            if (!save(test)) allSuccess = false;
        }
        return allSuccess;
    }

}