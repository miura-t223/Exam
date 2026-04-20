package scoremanager.main;

import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/**
 * 科目登録完了画面を表示するAction
 */
public class SubjectCreateDoneAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ログインチェック（直URL対策）
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		if (teacher == null || teacher.getSchool() == null) {
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
	}
}