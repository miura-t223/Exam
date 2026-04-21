<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<%-- 成績参照フォーム --%>
 
 
<c:param name="scripts"></c:param>
 
<c:param name="content">
    <section class="me-4">
<!-- 
        <div class="my-2 text-end px-4">
            <a href="StudentCreate.action">新規登録</a>
        </div>
-->

        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目別成績一覧</h2>
        <form method="get" action="TestListSubjectExecute.action">
            <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                <div class="col-3">
                    <label class="form-label" for="student-f1-select">入学年度</label>
                    <select class="form-select" id="student-f1-select" name="f1">
                        <option value="0">----</option>
                        <c:forEach var="year" items="${ent_year_set}">
                            <%-- 現在のyearと選択されていた値が一致していればselectedを追記 --%>
                            <option value="${year}" <c:if test="${year==f1}"> selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-3">
                    <label class="form-label" for="student-f2-select">クラス</label>
                    <select class="form-select" id="student-f2-select" name="f2">
                        <option value="">----</option>
                        <c:forEach var="num" items="${class_num_set}">
                            <%-- 現在のnumと選択されていた値が一致していればselectedを追記 --%>
                            <option value="${num}" <c:if test="${num==f2}"> selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-3">
                    <label class="form-label" for="student-f3-select">科目</label>
                    <select class="form-select" id="student-f3-select" name="f3">
                        <option value="">----</option>
                        <c:forEach var="subject" items="${class_subject_set}">
                            <%-- 現在のnumと選択されていた値が一致していればselectedを追記 --%>
                            <!-- <option value="${subject.code}" <c:if test="${subject.code == f3}">selected</c:if>></option> -->
                            <option value="${subject.code}" <c:if test="${subject.code == f3}">selected</c:if>>${subject.name}</option>
                            	
                            	
                        </c:forEach>
                    </select>
                </div>
                
                
                <div class="col-2 text-center">
                    <button class="btn btn-secondary" id="filter-button">検索</button>
                </div>
                <div class="mt-2 text-warning">${errors.get("f1")}</div>
            </div>
        </form>
        
        
        
        
        
        
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


	</section>
       <div class="my-2 px-4">
    	<a href="menu.jsp">メニューに戻る</a>
	</div>
</c:param>
</c:import>

