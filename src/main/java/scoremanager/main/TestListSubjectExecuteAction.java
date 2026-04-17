package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//科目別成績一覧用（検索結果表示）
public class TestListSubjectExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
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
		
		
		
		request.setAttribute("tests", tests);
		request.setAttribute("errors", errors);
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
		
		
	}
}