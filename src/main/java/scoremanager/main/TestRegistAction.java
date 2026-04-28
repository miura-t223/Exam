package scoremanager.main;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;




public class TestRegistAction extends Action {
	@Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        // ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            response.sendRedirect(request.getContextPath()
                + "/scoremanager/login.jsp");
            return;
        }
        
        
        
        // 絞り込みパラメータ取得
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String noStr = request.getParameter("f4");
        
        
        
        // プルダウン用データ生成
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        
        
        StudentDao sDao = new StudentDao();
        List<String> classNumSet = sDao.getClassNumSet(teacher.getSchool().getCd());
        SubjectDao subDao = new SubjectDao();
        List<Subject> subjects = subDao.filter(teacher.getSchool());
        // エラーメッセージを入れるための箱
        Map<String, String> errors = new HashMap<>();
        
        
        
        
        // 入力チェック（入学年度/クラス/科目/回数プルダウンのいずれかが未選択の場合）
        if (entYearStr == null || entYearStr.equals("0") ||
            classNum == null || classNum.equals("0") ||
            subjectCd == null || subjectCd.equals("0") ||
            noStr == null || noStr.equals("0")) {
        	
            errors.put("filter", "入学年度とクラスと科目と回数を選択してください");

            // エラーをJSPに渡す
            request.setAttribute("errors", errors);

            // プルダウン用データをセット
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classNumSet);
            request.setAttribute("subject_set", subjects);

            // 入力値を戻す
            request.setAttribute("f1", entYearStr);
            request.setAttribute("f2", classNum);
            request.setAttribute("f3", subjectCd);
            request.setAttribute("f4", noStr);

            // 検索結果は空
            request.setAttribute("students", new ArrayList<>());
            // JSPへフォワード
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
            return;
        }
        int entYear = Integer.parseInt(entYearStr);
        int no = Integer.parseInt(noStr);
        
        // 学生一覧取得
        List<Student> students = sDao.filter(teacher.getSchool(), entYear, classNum, false);
        // 検索結果がなかった場合(該当学生なし)の
        if (students.isEmpty()) {
            request.setAttribute("message", "学生情報が存在しませんでした");
        }

        
        // 科目
        Subject subject = subDao.get(subjectCd, teacher.getSchool());
        
        // 既存点数
        TestDao tDao = new TestDao();
        Map<String, Integer> pointMap = new HashMap<>();
        
        for (Student s : students) {
        	Test existing = tDao.get(s, subject, teacher.getSchool(), no);
        	if (existing != null) {
        		pointMap.put(s.getNo(), existing.getPoint());
        		}
        	}
        
        
        // JSPに渡す
        request.setAttribute("students", students);
        request.setAttribute("subject", subject);
        request.setAttribute("point_map", pointMap);
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        request.setAttribute("f4", no);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subject_set", subjects);
        // JSPへフォワード
        request.getRequestDispatcher("test_regist.jsp").forward(request, response);
	}
}