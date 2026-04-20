package scoremanager.main;

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
 * 学生変更の実行処理を行うActionクラス
 */
public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションからログイン中の教員情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 未ログインならログイン画面へ
		if (teacher == null || teacher.getSchool() == null) {
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		// 画面から送られてきた値を取得
		String no = request.getParameter("no");
		String entYearStr = request.getParameter("ent_year");
		String name = request.getParameter("name");
		String classNum = request.getParameter("class_num");
		String isAttendStr = request.getParameter("is_attend");

		Map<String, String> errors = new HashMap<>();

		// 入学年度（hiddenで来る想定）
		int entYear = 0;
		if (entYearStr != null && !entYearStr.isBlank()) {
			entYear = Integer.parseInt(entYearStr);
		}

		// 在学中チェックが入っていればtrue
		boolean isAttend = (isAttendStr != null);

		// -------- 入力チェック（ブラウザrequiredがあるので保険） --------
		if (name == null || name.isBlank()) {
			errors.put("name", "氏名を入力してください");
		} else if (name.length() > 10) {
			errors.put("name", "氏名は10文字以内で入力してください");
		}

		if (classNum == null || classNum.isBlank()) {
			errors.put("class_num", "クラスを選択してください");
		}

		// エラーがある場合は元画面へ戻す
		if (!errors.isEmpty()) {
			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());

			Student student = new Student();
			student.setNo(no);
			student.setEntYear(entYear);
			student.setName(name);
			student.setClassNum(classNum);
			student.setIsAttend(isAttend);
			student.setSchool(teacher.getSchool());

			request.setAttribute("errors", errors);
			request.setAttribute("student", student);
			request.setAttribute("class_num_set", classNumSet);

			request.getRequestDispatcher("student_update.jsp").forward(request, response);
			return;
		}

		// -------- Studentインスタンスに値を詰める --------
		Student student = new Student();
		student.setNo(no);
		student.setEntYear(entYear);
		student.setName(name);
		student.setClassNum(classNum);
		student.setIsAttend(isAttend);
		student.setSchool(teacher.getSchool());

		// DBへ保存
		StudentDao sDao = new StudentDao();
		boolean result = sDao.save(student);

		if (result) {
			// 変更成功 → 変更完了画面へ
			response.sendRedirect("StudentUpdateDone.action");
		} else {
			errors.put("common", "更新に失敗しました");

			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());

			request.setAttribute("errors", errors);
			request.setAttribute("student", student);
			request.setAttribute("class_num_set", classNumSet);

			request.getRequestDispatcher("student_update.jsp").forward(request, response);
		}
	}
}