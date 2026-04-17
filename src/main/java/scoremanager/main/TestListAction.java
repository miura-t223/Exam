package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
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
		
		
		
		
		// リクエストパラメータ
		// 入学年度
		String entYearStr = request.getParameter("f1");
		// クラス
		String classNum = request.getParameter("f2");
		// 科目
		String subjectCd = request.getParameter("f3");
		// 学生番号
		String studentNo = request.getParameter("f4");
		
		
		
		
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
		// 学生番号が未入力orスペースの時は空白に揃える
		if (studentNo == null || studentNo.isBlank()) {
		    studentNo = "";
		}

		
		
		
		
		School school = teacher.getSchool();
		
		
		
		
		//クラス情報を取得するための DAO インスタンス
		ClassNumDao cNumDao = new ClassNumDao();

		//クラス
		List<String> classList = cNumDao.filter(school);
		
		//エラーメッセージを入れるための箱
		Map<String, String> errors = new HashMap<>();
		
		
		
		// 入学年度検索用プルダウン
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		//科目検索用プルダウン
		SubjectDao subDao = new SubjectDao();
		List<Subject> subjectList = subDao.filter(teacher.getSchool());
		
		
		
		
		
		TestDao tDao = new TestDao();
		List<Test> tests = new ArrayList<>();
		// 条件に応じて検索
		if (studentNo != null && !studentNo.isBlank()) {
		    tests = tDao.filter(teacher.getSchool(), studentNo);
		} else if (entYear != 0 && !classNum.equals("0") && subjectCd != null && !subjectCd.isBlank()) {
		    // 年度、クラス、科目を全て指定→実行
		    tests = tDao.filter(teacher.getSchool(), entYear, classNum, subjectCd);
		//未入力があった場合 → エラー
		}else {
			// 学生別検索がエラーの場合
	    	if (studentNo == null || studentNo.isBlank()) {
	    	    errors.put("f4", "このフィールドを入力してください");
		    // 科目別検索がエラー野場合
		    } else {
		        errors.put("f1", "入学年度とクラスと科目を指定してください");
		    }
		 // 空のリストを返す or 全件表示(どちらでもOK)
		    tests = new ArrayList<>();
		}
		
		
		
		
		// JSPへ値を渡す
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);
		request.setAttribute("f4", studentNo);

		request.setAttribute("tests", tests);

		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classList);
		request.setAttribute("class_subject_set", subjectList);

		request.setAttribute("errors", errors);

		request.getRequestDispatcher("test_list.jsp").forward(request, response);
		}
}