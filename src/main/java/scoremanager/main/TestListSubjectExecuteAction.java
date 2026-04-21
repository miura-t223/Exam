package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//学生別成績一覧用（検索結果表示）
public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //エラーメッセージを入れるための箱
        Map<String, String> errors = new HashMap<>();
        
        
        
        // リクエストパラメータ取得(学生番号)
        String entYear = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        
        
        // DAO
	    StudentDao sDao = new StudentDao();
	    SubjectDao subDao = new SubjectDao();
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        TestDao tDao = new TestDao();
        
        
        // 入学年度
	    List<Integer> entYearSet = new ArrayList<>();
	    int year = java.time.LocalDate.now().getYear();
	    for (int i = year - 10; i <= year + 1; i++) {
	        entYearSet.add(i);
	    }
	    // クラス
        List<String> classList = sDao.getClassNumSet(teacher.getSchool().getCd());
        // 科目
        List<Subject> subjectList = subDao.filter(teacher.getSchool());
        
        
        
        // 検索フィールドが未入力or空白の場合
        if (entYear == null || entYear.equals("0")
        	    || classNum == null || classNum.isBlank()
        	    || subjectCd == null || subjectCd.isBlank()) {
        	
        	    errors.put("f1", "入学年度・クラス・科目をすべて選択してください");
        	    
        	    
        	    
        	    request.setAttribute("ent_year_set", entYearSet);
        	    request.setAttribute("class_num_set", classList);
        	    request.setAttribute("class_subject_set", subjectList);
        	    request.setAttribute("errors", errors);
        	    request.setAttribute("tests", new ArrayList<>());
        	    request.setAttribute("f1", entYear);
        	    request.setAttribute("f2", classNum);
        	    request.setAttribute("f3", subjectCd);

        	    request.getRequestDispatcher("test_list.jsp").forward(request, response);
        	    return;
        	}
        
        
        
        
        // 検索処理
       
        int entYearInt = Integer.parseInt(entYear);
        List<Test> tests = tDao.filter(teacher.getSchool(), entYearInt, classNum, subjectCd);
	    
        
        
        // JSPに値を渡す
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classList);
        request.setAttribute("class_subject_set", subjectList);
        
        request.setAttribute("tests", tests);
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        
        // フォワード先を設定
        request.getRequestDispatcher("test_list_subject.jsp").forward(request, response);
    }
}
