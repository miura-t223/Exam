<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<%-- 成績参照表示フォーム --%>
 
 
<c:param name="scripts"></c:param>
 
<c:param name="content">
    <section class="me-4">
        
        
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目別成績一覧</h2>
        <form method="get" action="TestListSubjectExecute.action">
            <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                <!-- 入学年度、クラス、科目で検索(テキストボックス) -->
				<div class="col-3">
				    <label class="form-label" for="student-f1-select">入学年度</label>
				    <select class="form-select" id="student-f1-select" name="f1">
				        <option value="0">----</option>
				        <c:forEach var="year" items="${ent_year_set}">
				            <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
				        </c:forEach>
				    </select>
				</div>
				
				<div class="col-3">
				    <label class="form-label" for="student-f2-select">クラス</label>
				    <select class="form-select" id="student-f2-select" name="f2">
				        <option value="0">----</option>
				        <c:forEach var="num" items="${class_num_set}">
				            <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
				        </c:forEach>
				    </select>
				</div>
				
				<div class="col-3">
				    <label class="form-label" for="student-f3-select">科目</label>
				    <select class="form-select" id="student-f3-select" name="f3">
				        <option value="0">----</option>
				        <c:forEach var="subject" items="${class_subject_set}">
				            <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
				                ${subject.name}
				            </option>
				        </c:forEach>
				    </select>
				</div>

                
                
                <div class="col-2 text-center">
                    <button class="btn btn-secondary" id="filter-button">検索</button>
                </div>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
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
					        <td><a href="TestUpdate.action?studentNo=${t.student.no}&subjectCd=${t.subject.cd}&no=${t.no}">変更</a></td>
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



