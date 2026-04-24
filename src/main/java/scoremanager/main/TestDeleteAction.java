package scoremanager.main;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
            return;
        }

        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("subject_cd");
        String noStr = request.getParameter("no");

        if (studentNo == null || studentNo.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()
                || noStr == null || noStr.isEmpty()) {
            response.sendRedirect("TestList.action");
            return;
        }

        int no = Integer.parseInt(noStr);

        TestDao dao = new TestDao();
        Test test = dao.get(teacher.getSchool(), studentNo, subjectCd, no);

        if (test == null) {
            response.sendRedirect("TestList.action");
            return;
        }

        request.setAttribute("test", test);
        request.getRequestDispatcher("test_delete.jsp").forward(request, response);
    }
}