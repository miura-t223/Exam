package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class TestListStudentExecuteAction {
	public abstract void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception;
}
