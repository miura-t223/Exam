package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;


public class TestListAction extends Action {
	
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
		
		
		
		
		School school = teacher.getSchool();
		//クラス情報を取得するための DAO インスタンス
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classList = cNumDao.filter(school);
		//エラーメッセージを入れるための箱
		Map<String, String> errors = new HashMap<>();
		
		
		
		// リクエストパラメータ
		// 入学年度
		String entYearStr = request.getParameter("f1");
		// クラス
		String classNum = request.getParameter("f2");
		// 科目
		String subjectCd = request.getParameter("f3");
		// 学生番号
		String studentNo = request.getParameter("f4");
		
		
		
		
		// ★ 初回アクセス判定（ここでは forward しない）
		boolean isFirstAccess =
		    entYearStr == null &&
		    classNum == null &&
		    subjectCd == null &&
		    studentNo == null;
		
		
		
		
		//初期値の設定
		int entYear = 0;//指定なし(int型は初期化必須/コンパイルエラーになる)
		// 入学年度が未選択時(null / "" / "0")の時は"０"にそろえる
		if (entYearStr != null && !entYearStr.isBlank() && !entYearStr.equals("0")) {
			entYear = Integer.parseInt(entYearStr);
		}
		// クラスが未選択の時は "0" に揃える
		if (classNum == null || classNum.isBlank()) {
			classNum = "0";
		}
		// 科目が未選択の時は空白に揃える
		if (subjectCd == null || subjectCd.isBlank()) {
		    subjectCd = "";
		}
		// 学生番号が未入力の時は空白に揃える
		if (studentNo == null) {
		    studentNo = "";
		}
		
		
		
		
		// 入学年度検索用プルダウン
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
		    entYearSet.add(i);
		}

		// 科目検索用プルダウン
		SubjectDao subDao = new SubjectDao();
		List<Subject> subjectList = subDao.filter(teacher.getSchool());
		
		
		
		
		
        // 初回アクセス時用フォワード
        if (isFirstAccess) {
            request.setAttribute("tests", new ArrayList<>());
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("class_num_set", classList);
            request.setAttribute("class_subject_set", subjectList);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }
		
		
		
		
		
		
		// JSPへ値を渡す
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);
		request.setAttribute("f4", studentNo);
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classList);
		request.setAttribute("class_subject_set", subjectList);
		// フォワード先を設定
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}
