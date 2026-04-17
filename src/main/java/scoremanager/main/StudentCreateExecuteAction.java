package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 学生登録の実行処理を行うActionクラス
 */
public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1) セッションからログイン中ユーザー取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインならログイン画面へ
        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
            return;
        }

        // 2) パラメータ取得
        String entYearStr = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");

        Map<String, String> errors = new HashMap<>();
        int entYear = 0;

        // 3) 入力チェック
        // 入学年度
        if (entYearStr == null || entYearStr.isBlank() || entYearStr.equals("0")) {
            errors.put("ent_year", "入学年度を選択してください");
        } else {
            entYear = Integer.parseInt(entYearStr);
        }

        // 学生番号
        if (no == null || no.isBlank()) {
            errors.put("no", "学生番号を入力してください");
        } else if (no.length() > 10) {
            errors.put("no", "学生番号は10文字以内で入力してください");
        }

        // 氏名
        if (name == null || name.isBlank()) {
            errors.put("name", "氏名を入力してください");
        } else if (name.length() > 10) {
            errors.put("name", "氏名は10文字以内で入力してください");
        }

        // クラス
        if (classNum == null || classNum.isBlank() || classNum.equals("0")) {
            errors.put("class_num", "クラスを選択してください");
        }

        // 4) 重複チェック
        // StudentDao.save() は「存在したらupdate」になっているので、登録画面では事前に弾く
        StudentDao sDao = new StudentDao();
        if (no != null && !no.isBlank()) {
            Student oldStudent = sDao.get(no);
            if (oldStudent != null) {
                // 設計書UI側の文言に合わせる
                errors.put("no", "学生番号が重複しています");
            }
        }

        // 5) エラーがあるなら登録画面へ戻す（プルダウンも再セット）
        if (!errors.isEmpty()) {

            // クラス一覧（ログインユーザーの学校）
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumSet = cDao.filter(teacher.getSchool());

            // 入学年度（今年-10 ～ 今年+1）
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i <= year + 1; i++) {
                entYearSet.add(i);
            }

            request.setAttribute("errors", errors);

            // 入力値を戻す（selectのselected判定用に ent_year は int を渡す）
            request.setAttribute("ent_year", entYear);
            request.setAttribute("no", no);
            request.setAttribute("name", name);
            request.setAttribute("class_num", classNum);

            request.setAttribute("class_num_set", classNumSet);
            request.setAttribute("ent_year_set", entYearSet);

            request.getRequestDispatcher("student_create.jsp").forward(request, response);
            return;
        }

        // 6) 登録するStudentを作る
        Student student = new Student();
        student.setEntYear(entYear);
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);

        // 新規は在学中で登録
        student.setIsAttend(true);

        // 学校はログインユーザーの学校
        student.setSchool(teacher.getSchool());

        // 7) DB保存
        boolean result = sDao.save(student);

        if (result) {
            // 設計書：学生登録完了画面へ
            response.sendRedirect("StudentCreateDone.action");
        } else {
            // 保存失敗
            errors.put("common", "登録に失敗しました");

            // プルダウン再セットして戻す
            ClassNumDao cDao = new ClassNumDao();
            List<String> classNumSet = cDao.filter(teacher.getSchool());

            LocalDate today = LocalDate.now();
            int year = today.getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i <= year + 1; i++) {
                entYearSet.add(i);
            }

            request.setAttribute("errors", errors);
            request.setAttribute("ent_year", entYear);
            request.setAttribute("no", no);
            request.setAttribute("name", name);
            request.setAttribute("class_num", classNum);
            request.setAttribute("class_num_set", classNumSet);
            request.setAttribute("ent_year_set", entYearSet);

            request.getRequestDispatcher("student_create.jsp").forward(request, response);
        }
    }
}