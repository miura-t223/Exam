package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//学生別成績一覧用（検索結果表示）
public class TestListStudentExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //エラーメッセージを入れるための箱
        Map<String, String> errors = new HashMap<>();
        
        
        
        // リクエストパラメータ取得(学生番号)
        String studentNo = request.getParameter("f4");
        
        
        
        
        
        // 検索フィールドが未入力or空白の場合
        if (studentNo == null || studentNo.isBlank()) {
        	//エラー時のメッセージ内容
            errors.put("f4", "　　このフィールドを入力してください");
            
            
            // プルダウン用データをセット
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            
            // 入学年度
            List<Integer> entYearSet = new ArrayList<>();
            int year = java.time.LocalDate.now().getYear();
            for (int i = year - 10; i <= year + 1; i++) {
                entYearSet.add(i);
            }
            
            // クラス
            dao.StudentDao sDao = new dao.StudentDao();
            List<String> classList = sDao.getClassNumSet(teacher.getSchool().getCd());
            
            // 科目
            dao.SubjectDao subDao = new dao.SubjectDao();
            List<bean.Subject> subjectList = subDao.filter(teacher.getSchool());
            
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classList);
            request.setAttribute("class_subject_set", subjectList);
            
            request.setAttribute("errors", errors);
            request.setAttribute("f4", studentNo);
            request.setAttribute("tests", new ArrayList<>());
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }
        
        
        // 検索処理
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        TestDao tDao = new TestDao();
        List<Test> tests = tDao.filter(teacher.getSchool(), studentNo);
        
        
        // プルダウン用データをセット
        dao.StudentDao sDao = new dao.StudentDao();
        dao.SubjectDao subDao = new dao.SubjectDao();

        List<Integer> entYearSet = new ArrayList<>();
        int year = java.time.LocalDate.now().getYear();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        
        
        List<String> classList = sDao.getClassNumSet(teacher.getSchool().getCd());
        List<bean.Subject> subjectList = subDao.filter(teacher.getSchool());
        
        
        // 学生情報を取得
        Student student = sDao.get(studentNo);
        
        
        // JSPに値を渡す
        request.setAttribute("student", student);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classList);
        request.setAttribute("class_subject_set", subjectList);
        request.setAttribute("tests", tests);
        request.setAttribute("f4", studentNo);
        
        // フォワード先を設定
        request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
    }
}



