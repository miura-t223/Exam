package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目登録の実行処理を行うActionクラス
 */
public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// --------------------------------------------------
		// 1. ログインユーザー取得（直URL対策）
		// --------------------------------------------------
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		if (teacher == null || teacher.getSchool() == null) {
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		// --------------------------------------------------
		// 2. 画面入力値を取得
		// --------------------------------------------------
		String cd = request.getParameter("cd");     // 科目コード
		String name = request.getParameter("name"); // 科目名

		// エラーメッセージ格納用
		Map<String, String> errors = new HashMap<>();

		// --------------------------------------------------
		// 3. 入力チェック
		// --------------------------------------------------
		// ※必須チェックはブラウザ(required)でも止まるが、
		//   直叩き対策でサーバ側でもチェックしておく
		if (cd == null || cd.isBlank()) {
			errors.put("cd", "科目コードを入力してください");
		} else if (cd.length() != 3) {
			// ★設計書：3文字固定（2文字はNG）
			errors.put("cd", "科目コードは3文字で入力してください");
		}

		if (name == null || name.isBlank()) {
			errors.put("name", "科目名を入力してください");
		} else if (name.length() > 20) {
			errors.put("name", "科目名は20文字以内で入力してください");
		}

		// --------------------------------------------------
		// 4. 重複チェック（同一学校内で科目コードが重複）
		// --------------------------------------------------
		SubjectDao sDao = new SubjectDao();
		if (cd != null && !cd.isBlank() && cd.length() == 3) {
			Subject old = sDao.get(cd, teacher.getSchool());
			if (old != null) {
				// ★設計書の文言寄せ
				errors.put("cd", "科目コードが重複しています");
			}
		}

		// --------------------------------------------------
		// 5. エラーがある場合は元の画面に戻す
		// --------------------------------------------------
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);

			// 入力値を戻す（再入力を楽にする）
			request.setAttribute("cd", cd);
			request.setAttribute("name", name);

			request.getRequestDispatcher("subject_create.jsp").forward(request, response);
			return;
		}

		// --------------------------------------------------
		// 6. Subjectインスタンスに詰める
		// --------------------------------------------------
		Subject subject = new Subject();
		subject.setCode(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());

		// --------------------------------------------------
		// 7. DBへ保存
		// --------------------------------------------------
		boolean result = sDao.save(subject);

		if (result) {
			// 登録成功 → 完了画面へ
			response.sendRedirect("SubjectCreateDone.action");
		} else {
			// 保存失敗 → エラー表示して元画面へ
			errors.put("common", "登録に失敗しました");
			request.setAttribute("errors", errors);
			request.setAttribute("cd", cd);
			request.setAttribute("name", name);

			request.getRequestDispatcher("subject_create.jsp").forward(request, response);
		}
	}
}