package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
//
//    // SQL:学校名で絞り込み
//    private String baseSql = "select * from test where school_cd=?";
//    
//    
//    private List<Test> postFilter(ResultSet rs, School school) throws Exception {
//    	List<Test> list = new ArrayList<>();
//    	
//        // SQLの結果を１行ずつ取り出す
//        while (rs.next()) {
//        	
//        	// testインスタンスの作成
//            Test test = new Test();
//            
//            // 学生
//            Student student = new Student();
//            student.setClassNum(rs.getString("class_num"));
//            student.setNo(rs.getString("student_no"));
//            student.setSchool(school);
//            
//            // 科目
//            Subject subject = new Subject();
//            subject.setSchool(school);
//            subject.setCode(rs.getString("subject_cd"));
//            
//            test.setStudent(student);
//            test.setSubject(subject);
//            
//            // テスト回数
//            test.setNo(rs.getInt("no"));
//            // 得点
//            test.setPoint(rs.getInt("point"));
//            
//            // リストに追加
//            list.add(test);
//        }
//        return list;
//    }
    
    
    
    // フィルター（科目別成績一覧検索：年度/クラス/科目）
    public List<Test> filter(School school, int entYear, String classNum, String subjectCd) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        String sql =
        	    "SELECT " +
        	    " t.no AS test_no, t.point, t.class_num, " +
        	    " s.no AS student_no, s.name AS student_name, s.ent_year AS student_ent_year, " +
        	    " sub.cd AS subject_cd, sub.name AS subject_name " +
        	    "FROM test t " +
        	    "JOIN student s ON t.student_no = s.no AND t.school_cd = s.school_cd " +
        	    "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd " +
        	    "WHERE t.school_cd = ? " +
        	    "AND s.ent_year = ? " +
        	    "AND s.class_num = ? " +
        	    "AND t.subject_cd = ? " +
        	    "ORDER BY s.no, t.no";


        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subjectCd);

            rs = statement.executeQuery();

            while (rs.next()) {
                Test test = new Test();

                // 学生
                Student student = new Student();
                student.setNo(rs.getString("student_no"));
                student.setName(rs.getString("student_name"));
                student.setEntYear(rs.getInt("student_ent_year"));
                student.setClassNum(rs.getString("class_num"));
                student.setSchool(school);


                // 科目
                Subject subject = new Subject();
                subject.setCd(rs.getString("subject_cd"));
                subject.setName(rs.getString("subject_name"));
                subject.setSchool(school);
                
                // 得点
                test.setStudent(student);
                test.setSubject(subject);
                test.setNo(rs.getInt("test_no"));
                test.setPoint(rs.getInt("point"));

                list.add(test);
            }
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
    
    
    
    
    
 // フィルター（学生別成績一覧検索）
	public List<Test> filter(School school, String studentNo) throws Exception {
	
	    List<Test> list = new ArrayList<>();
	
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
	    
	    String sql =
	    	    "SELECT " +
	    	    " t.no AS test_no, t.point, t.class_num, " +
	    	    " s.no AS student_no, " +
	    	    " s.name AS student_name, " +
	    	    " s.ent_year AS student_ent_year, " +   // ← これを追加
	    	    " sub.cd AS subject_cd, sub.name AS subject_name " +
	    	    "FROM test t " +
	    	    "JOIN student s ON t.student_no = s.no AND t.school_cd = s.school_cd " +
	    	    "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd " +
	    	    "WHERE t.school_cd = ? AND t.student_no = ? " +
	    	    "ORDER BY t.no";
	    
	    
	    
	    
	    try {
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getCd());
	        statement.setString(2, studentNo);

	        rs = statement.executeQuery();

	        while (rs.next()) {
	            Test test = new Test();

	            // 学生
	            Student student = new Student();
	            student.setNo(rs.getString("student_no"));
	            student.setName(rs.getString("student_name"));
	            student.setEntYear(rs.getInt("student_ent_year"));
	            student.setClassNum(rs.getString("class_num"));
	            student.setSchool(school);


	            // 科目
	            Subject subject = new Subject();
	            subject.setCd(rs.getString("subject_cd"));
	            subject.setName(rs.getString("subject_name"));
	            subject.setSchool(school);
	            
	            // 得点
	            test.setStudent(student);
	            test.setSubject(subject);
	            test.setNo(rs.getInt("test_no"));
	            test.setPoint(rs.getInt("point"));

	            list.add(test);
	        }
	        
	        
	    } finally {
	        if (rs != null) rs.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	
	    return list;
	}
}
	
