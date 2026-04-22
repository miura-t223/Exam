<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<c:param name="scripts">
<script></script>
</c:param>


<%-- 成績参照表示フォーム --%> 
 
<c:param name="content">
    <section class="me-4">
    	<h2 class="h2 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
        
        
        
        <!-- 枠線 -->
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
	        
	        <!-- 科目情報 -->
	        <form method="get" action="TestListSubjectExecute.action">
	            <div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
	                <p class="col-2 d-flex justify-content-center align-items-center">
	                	<label class="form-label" for="student-f1-select">科目情報</label>
	                </p>
	                
	                <div class="col-2">
	                    <label class="form-label" for="student-f1-select">入学年度</label>
	                    <select class="form-select" id="student-f1-select" name="f1">
	                        <option value="0">----</option>
	                        <c:forEach var="year" items="${ent_year_set}">
	                            <%-- 現在のyearと選択されていた値が一致していればselectedを追記 --%>
	                            <option value="${year}" <c:if test="${year==f1}"> selected</c:if>>${year}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	                
	                <div class="col-2">
	                    <label class="form-label" for="student-f2-select">クラス</label>
	                    <select class="form-select" id="student-f2-select" name="f2">
	                        <option value="">----</option>
	                        <c:forEach var="num" items="${class_num_set}">
	                            <%-- 現在のnumと選択されていた値が一致していればselectedを追記 --%>
	                            <option value="${num}" <c:if test="${num==f2}"> selected</c:if>>${num}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	                
	                <div class="col-4">
	                    <label class="form-label" for="student-f3-select">科目</label>
	                    <select class="form-select" id="student-f3-select" name="f3">
	                        <option value="">----</option>
	                        <c:forEach var="subject" items="${class_subject_set}">
	                            <%-- 現在のnumと選択されていた値が一致していればselectedを追記 --%>
	                            <!-- <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>></option> -->
	                            <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
	                        </c:forEach>
	                    </select>
	                </div>
	                
	                <!-- 検索ボタン -->
	                <div class="col-2 text-center">
	                    <button class="btn btn-secondary" id="filter-button">検索</button>
	                </div>
	                <!-- 未入力エラー処理 -->
	                <div class="mt-2 text-warning">
	                	${errors.get("f1")}
	                </div>
	            </div>
	        </form>
	        
	        
	        
	        
	        <!-- 横線 -->
	        <div style="border-bottom: 1px solid #ccc;width: 95%; margin: 10px auto;"></div>
	        
	        
	        
	        
	        <!-- 学生情報 -->
	        <form method="get" action="TestListStudentExecute.action">
	            <div class="row  mx-3 mb-3 py-2 align-items-center rounded" id="filter">
	                <!-- 科目情報(見出し) -->
	                <p class="col-2 d-flex justify-content-center align-items-center">
	                <label class="form-label" for="student-f1-select">学生情報</label>
	                </p>
	                
	                <!-- 学生番号で検索(テキストボックス) -->
	                <div class="col-4">
		                <label class="form-label" for="student-no">学生番号</label>
		                <!-- 未入力 -->
						<c:choose>
						    <c:when test="${not empty errors.get('f4')}">
						        <!-- エラー時（赤枠） -->
						        <input class="form-control"
						               type="text"
						               id="student-no"
						               name="f4"
						               maxlength="10"
						               value="${f4}"
						               placeholder="学生番号を入力してください"
						               required
						               data-bs-toggle="tooltip"
						               data-bs-placement="right"
						               title="${errors.get('f4')}"
						               style="border-color:#dc3545;">
						    </c:when>
						    <c:otherwise>
						        <!-- 通常時 -->
						        <input class="form-control"
						               type="text"
						               id="student-no"
						               name="f4"
						               maxlength="10"
						               value="${f4}"
						               placeholder="学生番号を入力してください"
						               required
						               data-bs-toggle="tooltip"
						               data-bs-placement="right"
						               title="${errors.get('f4')}">
						    </c:otherwise>
						</c:choose>
	                </div>
	                
	                <!-- 検索ボタン -->
	                <div class="col-2 text-center">
	                    <button class="btn btn-secondary" id="filter-button">検索</button>
	                </div>
	                <!-- 未入力エラー処理 -->
	                <div class="mt-2 text-warning">${errors.get("f4")}</div>
	            </div>
	        </form>
       </div>
        
        
        
        <!-- クラス、科目、テスト、得点を表示 -->
		<c:choose>
		    <c:when test="${tests.size() > 0}">
		        <!--<div>検索結果 ${tests.size()} 件</div>-->
		        <div><label>科目：${tests[0].subject.name}</label></div>
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
		        <div>学生情報が存在しませんでした</div>
		    </c:otherwise>
		</c:choose>
	</section>
       <div class="my-2 px-4">
    	<a href="menu.jsp">メニューに戻る</a>
	</div>
</c:param>
</c:import>



