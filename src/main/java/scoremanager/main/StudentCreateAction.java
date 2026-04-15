package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 学生登録画面を表示するActionクラス
 */
public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションからログイン中の教員情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// クラス番号プルダウン用のデータ取得
		ClassNumDao cDao = new ClassNumDao();
		List<String> classNumSet = cDao.filter(teacher.getSchool());

		// 入学年度プルダウン用のデータ作成
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		List<Integer> entYearSet = new ArrayList<>();

		// 10年前から1年後までを候補にする
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		// JSPへ渡す
		request.setAttribute("class_num_set", classNumSet);
		request.setAttribute("ent_year_set", entYearSet);

		// 学生登録画面へフォワード
		request.getRequestDispatcher("student_create.jsp").forward(request, response);
	}
}