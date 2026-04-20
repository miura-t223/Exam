package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            errors.put("f4", "このフィールドを入力してください");
            request.setAttribute("errors", errors);
            request.setAttribute("f4", studentNo);
            request.setAttribute("tests", new ArrayList<>());
            request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
            return;
        }
        
        
        
        // 検索処理
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        TestDao tDao = new TestDao();
        List<Test> tests = tDao.filter(teacher.getSchool(), studentNo);
        
        
        
        // JSPに値を渡す
        request.setAttribute("tests", tests);
        request.setAttribute("f4", studentNo);
        
        // フォワード先を設定
        request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
    }
}
