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

		// --------------------------------------------------
		// 1. セッションからログイン中の教員情報を取得
		// --------------------------------------------------
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 未ログイン状態で直接URLを叩かれた場合はログイン画面へ戻す
		if (teacher == null || teacher.getSchool() == null) {
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		// --------------------------------------------------
		// 2. フォームから送られてきた値を取得
		// --------------------------------------------------
		String entYearStr = request.getParameter("ent_year");
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String classNum = request.getParameter("class_num");

		// エラーメッセージを入れるMap
		Map<String, String> errors = new HashMap<>();

		// 入学年度をintで使うための変数
		int entYear = 0;

		// --------------------------------------------------
		// 3. 入力チェック
		// --------------------------------------------------

		// 入学年度チェック
		if (entYearStr == null || entYearStr.isBlank() || entYearStr.equals("0")) {
			errors.put("ent_year", "入学年度を選択してください");
		} else {
			entYear = Integer.parseInt(entYearStr);
		}

		// 学生番号チェック
		if (no == null || no.isBlank()) {
			errors.put("no", "学生番号を入力してください");
		} else if (no.length() > 10) {
			errors.put("no", "学生番号は10文字以内で入力してください");
		}

		// 氏名チェック
		if (name == null || name.isBlank()) {
			errors.put("name", "氏名を入力してください");
		} else if (name.length() > 10) {
			errors.put("name", "氏名は10文字以内で入力してください");
		}

		// クラス番号チェック
		if (classNum == null || classNum.isBlank() || classNum.equals("0")) {
			errors.put("class_num", "クラスを選択してください");
		}

		// --------------------------------------------------
		// 4. 学生番号の重複チェック
		//    save() は既存番号だと update してしまうため、
		//    登録画面では事前に重複を弾く
		// --------------------------------------------------
		StudentDao sDao = new StudentDao();

		// 学生番号が入力されている場合のみ重複確認
		if (no != null && !no.isBlank()) {
			Student oldStudent = sDao.get(no);

			if (oldStudent != null) {
				errors.put("no", "その学生番号は既に登録されています");
			}
		}

		// --------------------------------------------------
		// 5. エラーがある場合は元の画面に戻す
		// --------------------------------------------------
		if (!errors.isEmpty()) {

			// クラス番号プルダウン再表示用データ
			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());

			// 入学年度プルダウン再表示用データ
			LocalDate today = LocalDate.now();
			int year = today.getYear();
			List<Integer> entYearSet = new ArrayList<>();
			for (int i = year - 10; i <= year + 1; i++) {
				entYearSet.add(i);
			}

			// 入力値とエラーをJSPへ戻す
			request.setAttribute("errors", errors);
			request.setAttribute("ent_year", entYearStr);
			request.setAttribute("no", no);
			request.setAttribute("name", name);
			request.setAttribute("class_num", classNum);
			request.setAttribute("class_num_set", classNumSet);
			request.setAttribute("ent_year_set", entYearSet);

			request.getRequestDispatcher("student_create.jsp").forward(request, response);
			return;
		}

		// --------------------------------------------------
		// 6. Studentインスタンスに値を詰める
		// --------------------------------------------------
		Student student = new Student();

		student.setEntYear(entYear);
		student.setNo(no);
		student.setName(name);
		student.setClassNum(classNum);

		// 新規登録時は在学中で登録
		student.setIsAttend(true);

		// ログイン中教員の学校をセット
		student.setSchool(teacher.getSchool());

		// --------------------------------------------------
		// 7. DBへ保存
		// --------------------------------------------------
		boolean result = sDao.save(student);

		if (result) {
			// 登録成功 → 一覧画面へ戻す
			response.sendRedirect("StudentList.action");
		} else {
			// 保存失敗 → エラーを表示して元画面へ戻す
			errors.put("common", "登録に失敗しました");

			// クラス番号プルダウン再表示用データ
			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());

			// 入学年度プルダウン再表示用データ
			LocalDate today = LocalDate.now();
			int year = today.getYear();
			List<Integer> entYearSet = new ArrayList<>();
			for (int i = year - 10; i <= year + 1; i++) {
				entYearSet.add(i);
			}

			// 入力値とエラーをJSPへ戻す
			request.setAttribute("errors", errors);
			request.setAttribute("ent_year", entYearStr);
			request.setAttribute("no", no);
			request.setAttribute("name", name);
			request.setAttribute("class_num", classNum);
			request.setAttribute("class_num_set", classNumSet);
			request.setAttribute("ent_year_set", entYearSet);

			request.getRequestDispatcher("student_create.jsp").forward(request, response);
		}
	}
}