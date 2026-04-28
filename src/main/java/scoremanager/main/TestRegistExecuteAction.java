package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.ClassNum;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

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

 
        // パラメータ取得
        String subjectCd = request.getParameter("f3");
        int no = Integer.parseInt(request.getParameter("f4"));
        String classNum = request.getParameter("f2");
 
        String[] studentNos = request.getParameterValues("student_no");
        String[] points = request.getParameterValues("point");
        
        
        
     // 科目情報を取得
        SubjectDao subDao = new SubjectDao();
        Subject subject = subDao.get(subjectCd, teacher.getSchool());
 
     // 学生数分のTestをfor文で作ってリストに詰める
        Map<String, String> errors = new HashMap<>();

        List<Test> testList = new ArrayList<>();
        for (int i = 0; i < studentNos.length; i++) {

        	// 空欄はエラー&登録なし
        	if (points[i] == null || points[i].isBlank()) {
        	    continue;
        	}
        	
        	
        	// 点数入力時のエラー処理
            int p = 0;
            try {
                p = Integer.parseInt(points[i]);
                if (p < 0 || p > 100) {
                    errors.put(studentNos[i], "0～100の範囲で入力してください");
                    continue;
                }
            } catch (NumberFormatException e) {
                errors.put(studentNos[i], "点数は数値で入力してください");
                continue;
            }

            // 正常データだけTestに入れる
            Student student = new Student();
            student.setNo(studentNos[i]);
            student.setSchool(teacher.getSchool());

            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(teacher.getSchool());
            test.setNo(no);
            test.setPoint(p);

            ClassNum cn = new ClassNum();
            cn.setClass_num(classNum);
            test.setClassNum(cn);
            
            
         // 既存の点数と同じなら変更なし
            String oldPoint = request.getParameter("old_point_" + studentNos[i]);
            if (oldPoint != null && oldPoint.equals(points[i])) {
                continue;
            }
            testList.add(test);
        }
        
        
        
     // 登録＆変更をせずに登録ボタンを押した場合
     if (errors.isEmpty() && testList.isEmpty()) {
         errors.put("global", "登録・変更された内容がありません");
     }

     // エラーがあればJSPに戻す
     if (!errors.isEmpty()) {
    	 // エラーと入力値をJSPへ
         request.setAttribute("errors", errors);
         request.setAttribute("f1", request.getParameter("f1"));
         request.setAttribute("f2", request.getParameter("f2"));
         request.setAttribute("f3", request.getParameter("f3"));
         request.setAttribute("f4", request.getParameter("f4"));
         // JSPへフォワード
         request.getRequestDispatcher("TestRegist.action").forward(request, response);
         return;
     }

     // 保存
     TestDao tDao = new TestDao();
     tDao.save(testList);
     
     
        // 完了画面へフォワード
        request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
    }
}