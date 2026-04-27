<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 
  
 
<c:import url="/common/base.jsp">
 
    <c:param name="title">科目変更完了</c:param>
 
    <c:param name="content">
 
        <div class="p-2 fw-bold" style="background-color:#e5e5e5; font-size:26px;">
	        科目情報変更
    	</div>
 
		<div class="mt-3 text-center p-2"
	         style="background-color:#cfe2d6;">
	        変更が完了しました
    	</div>
 		<br>
 		<br>
 		<br>
 		<br>
 		<div class="mt-3">
            <a href="${pageContext.request.contextPath}/scoremanager/main/SubjectList.action">
                科目一覧
            </a>
 		</div>
 		
    </c:param>
 
</c:import>
 