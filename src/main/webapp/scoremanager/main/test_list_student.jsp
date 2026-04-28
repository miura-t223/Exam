<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
	
	<%-- 成績参照表示ページ --%>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（学生）
             </h2>


<%--------------------------------------- 枠線 ---------------------------------------%>
	        <%-- プルダウン --%>
	        <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
		        
		        <%-- 科目情報 --%>
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
		                            <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
		                        </c:forEach>
		                    </select>
		                </div>
		                
		                <%-- 検索ボタン --%>
		                <div class="col-2 text-center">
		                    <button class="btn btn-secondary" id="filter-button">検索</button>
		                </div>
		                <%-- 未入力エラー処理 --%>
		                <div class="mt-2 text-warning">
		                	${errors.get("f1")}
		                </div>
		            </div>
		        </form>



<%--------------------------------------- 横線 ---------------------------------------%>
		        <div style="border-bottom: 1px solid #ccc;width: 95%; margin: 10px auto;"></div>
		        
		        <%-- 学生情報 --%>
		        <form method="get" action="TestListStudentExecute.action">
		            <div class="row  mx-3 mb-3 py-2 align-items-center rounded" id="filter">
		                <%-- 科目情報(見出し) --%>
		                <p class="col-2 d-flex justify-content-center align-items-center">
		                <label class="form-label" for="student-f1-select">学生情報</label>
		                </p>
		                
		                <%-- 学生番号で検索(テキストボックス) --%>
		                <div class="col-4">
			                <label class="form-label" for="student-no">学生番号</label>
			                <%-- 未入力 --%>
							<c:choose>
							    <c:when test="${not empty errors.get('f4')}">
							        <%-- エラー時 --%>
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
							        <%-- 通常時 --%>
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
		                
		                <%-- 検索ボタン --%>
		                <div class="col-2 text-center">
		                    <button class="btn btn-secondary" id="filter-button">検索</button>
		                </div>
		                <%-- 未入力エラー処理 --%>
		                <div class="mt-2 text-warning">${errors.get("f4")}</div>
		            </div>
		        </form>
	       </div>
<%--------------------------------------- 枠線 ---------------------------------------%>









			<%-- 氏名：学生名(学生番号) --%>
			<%-- student表に学生情報が存在する場合 --%>
			<c:if test="${not empty student}">
			    <div>氏名：${student.name}（${student.no}）</div>
			</c:if>
			<%-- student表に登録されていない学生番号を検索した場合 --%>
			<c:if test="${empty student}">
			    <div style="color:red; margin-bottom: 1rem;">※学生番号(${f4})の学生は登録されていません。学生管理画面から登録してください。</div>
			</c:if>
			
			
			
			
			
			<c:choose>
				<c:when test="${not empty tests}">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>科目名</th>
								<th>科目コード</th>
								<th>回数</th>
								<th>点数</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${tests}">
								<tr>
									<td>${test.subject.name}</td>
									<td>${test.subject.cd}</td>
									<td>${test.no}</td>
									<td>${test.point}</td>
									<td>
										<a href="TestDelete.action?student_no=${test.student.no}&subject_cd=${test.subject.cd}&no=${test.no}">削除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				
				<%-- 登録された成績情報がなかった場合 --%>
				<c:otherwise>
					<div>成績情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>

	</c:param>
</c:import>