<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<%-- 成績参照表示フォーム --%>
 
 
<c:param name="scripts"></c:param>
 
<c:param name="content">
    <section class="me-4">
        
        
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生別成績一覧</h2>
        <form method="get" action="TestListStudentExecute.action">
            <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                <!-- 学生番号で検索(テキストボックス) -->
                <div class="col-4">
	                <label class="form-label" for="student-no">学生番号</label>
	                <input class="form-control" type="text" id="student-no" name="f4" value="${f4}">
                </div>
                
                
                
                <div class="col-2 text-center">
                    <button class="btn btn-secondary" id="filter-button">検索</button>
                </div>
                <div class="mt-2 text-warning">${errors.get("f4")}</div>
            </div>
        </form>
        
        
        
        <!-- クラス、科目、テスト、得点を表示 -->
		<c:choose>
		    <c:when test="${tests.size() > 0}">
		        <div>検索結果 ${tests.size()} 件</div>
		        <table class="table table-hover">
		            <tr>
		                <th>クラス</th>
		                <th>科目</th>
		                <th>テスト</th>
		                <th>得点</th>
		                <th></th>
		            </tr>
		            <c:forEach var="t" items="${tests}">
					    <tr>
					        <td>${t.student.classNum}</td>
					        <td>${t.subject.name}</td>
					        <td>${t.no}</td>
					        <td>${t.point}</td>
					        <!-- 成績変更 -->
					        <td><a href="TestUpdate.action?studentNo=${t.student.no}&subjectCd=${t.subject.code}&no=${t.no}">変更</a></td>
		                </tr>
		            </c:forEach>
		        </table>
		    </c:when>
  			<c:otherwise>
		        <div>成績情報が存在しませんでした</div>
		    </c:otherwise>
		</c:choose>
	</section>
       <div class="my-2 px-4">
    	<a href="menu.jsp">メニューに戻る</a>
	</div>
</c:param>
</c:import>

