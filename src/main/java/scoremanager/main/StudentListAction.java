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

public class StudentListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションからログインユーザーを取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// ログイン未実装中の仮対応
		// 今のSQLでは tes 側に school / class_num / teacher がそろっている
		if (teacher == null || teacher.getSchool() == null) {
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		// リクエストパラメータ取得
		String entYearStr = request.getParameter("f1");
		String classNum = request.getParameter("f2");
		String isAttendStr = request.getParameter("f3");

		int entYear = 0;
		boolean isAttend = false;

		// 入学年度
		if (entYearStr != null && !entYearStr.isBlank() && !entYearStr.equals("0")) {
			entYear = Integer.parseInt(entYearStr);
		}

		// クラス未選択時は "0" にそろえる
		if (classNum == null || classNum.isBlank()) {
			classNum = "0";
		}

		// 在学中チェックは検索前に判定する
		if (isAttendStr != null) {
			isAttend = true;
		}

		List<Student> students = null;
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();

		// 入学年度プルダウン用データ
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		// クラス番号プルダウン用データ
		List<String> list = cNumDao.filter(teacher.getSchool());

		// 条件に応じて検索
		if (entYear != 0 && !classNum.equals("0")) {
			// 入学年度とクラス番号を指定
			students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

		} else if (entYear != 0 && classNum.equals("0")) {
			// 入学年度のみ指定
			students = sDao.filter(teacher.getSchool(), entYear, isAttend);

		} else if (entYear == 0 && classNum.equals("0")) {
			// 指定なし
			students = sDao.filter(teacher.getSchool(), isAttend);

		} else {
			// クラスだけ指定された場合
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			students = sDao.filter(teacher.getSchool(), isAttend);
		}

		// JSPへ値を渡す
		request.setAttribute("f1", entYear);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", isAttendStr);
		request.setAttribute("students", students);
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("errors", errors);

		request.getRequestDispatcher("student_list.jsp").forward(request, response);
	}
}