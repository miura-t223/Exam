package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/scoremanager/login.jsp");
            return;
        }

        String cd = request.getParameter("cd");
        if (cd == null || cd.isEmpty()) {
            response.sendRedirect("SubjectList.action");
            return;
        }

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, teacher.getSchool());

        if (subject == null) {
            response.sendRedirect("SubjectList.action");
            return;
        }

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}