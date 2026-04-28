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

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> errors = new HashMap<>();

        // パラメータ
        String entYear = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        TestDao tDao = new TestDao();

        // 入学年度セット
        List<Integer> entYearSet = new ArrayList<>();
        int year = java.time.LocalDate.now().getYear();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        // クラス
        List<String> classList = sDao.getClassNumSet(teacher.getSchool().getCd());

        // 科目
        List<Subject> subjectList = subDao.filter(teacher.getSchool());

        // 入力チェック
        if (entYear == null || entYear.equals("0")
                || classNum == null || classNum.equals("0") || classNum.isBlank()
                || subjectCd == null || subjectCd.equals("0") || subjectCd.isBlank()) {
            errors.put("f1", "　　入学年度とクラスと科目を選択してください");

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

        
        

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // JSPへ値を渡す
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classList);
        request.setAttribute("class_subject_set", subjectList);

        request.setAttribute("tests", tests);
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);

        request.getRequestDispatcher("test_list_subject.jsp").forward(request, response);
    }
}
