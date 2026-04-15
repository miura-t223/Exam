package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class TestListAction {
	public abstract void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception;
}
