package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// --- 1. セッションからユーザー情報を取得 ---
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// --- 2. ログインチェック ---
		// ログイン済みでなければログイン画面へ戻す
		if (teacher == null || teacher.getSchool() == null) {
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		// --- 3. 科目一覧を取得（ログインユーザーの学校で絞る） ---
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjects = subjectDao.filter(teacher.getSchool());

		// --- 4. JSPへ渡して表示 ---
		request.setAttribute("subjects", subjects);
		request.getRequestDispatcher("subject_list.jsp").forward(request, response);
	}
}