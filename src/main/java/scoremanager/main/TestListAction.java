package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
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
		// クラスが未選択時は "0" にそろえる
		if (classNum == null || classNum.isBlank()) {
			classNum = "0";
		}
		
		
		
		
		School school = teacher.getSchool();
		
		
		
		
		//検索結果を入れるための箱
		List<Test> scores = null;
		//学生情報を取得するためのDAOインスタンス
		StudentDao sDao = new StudentDao();
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
		// クラス検索用プルダウン
		List<String> list = cNumDao.filter(teacher.getSchool());
		
		//科目検索用プルダウン
		List<String> subjectList = SubjectDao.filter(teacher.getSchool());
		
		
		
		
		
		
		
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
        request.setAttribute("f1", entYearStr);
        request.setAttribute("f2", classNum);
        request.setAttribute("f3", subjectCd);
        request.setAttribute("f4", studentNo);
		//request.setAttribute("students", students);
		//request.setAttribute("class_num_set", list);
		//request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("scores", scores);
        request.setAttribute("classList", classList);
        //request.setAttribute("subjectList", subjectList);
        request.setAttribute("errors", errors);
        
        // JSPへフォワード
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}