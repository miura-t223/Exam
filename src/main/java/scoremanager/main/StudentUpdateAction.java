package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 学生変更画面を表示するActionクラス
 */
public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションからログイン中の教員情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 一覧画面から渡ってきた学生番号を取得
		String no = request.getParameter("no");

		// 学生番号をもとに学生情報を1件取得
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(no);

		// クラス番号プルダウン用データを取得
		ClassNumDao cDao = new ClassNumDao();
		List<String> classNumSet = cDao.filter(teacher.getSchool());

		// JSPへ渡す
		request.setAttribute("student", student);
		request.setAttribute("class_num_set", classNumSet);

		// 変更画面へフォワード
		request.getRequestDispatcher("student_update.jsp").forward(request, response);
	}
}