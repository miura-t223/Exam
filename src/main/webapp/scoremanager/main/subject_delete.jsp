<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目削除</c:param>
    <c:param name="content">

        <div class="p-2 fw-bold fs-4" style="background-color:#e5e5e5;">
        	科目情報削除
    	</div>
		<br>
		 <div class="mt-3">
        	「${subject.name}（${subject.cd}）」を削除してもよろしいですか？
    	</div>

        <form action="SubjectDeleteExecute.action" method="post">
            <input type="hidden" name="cd" value="${subject.cd}">
            <button type="submit" class="btn btn-danger">削除する</button>
        </form>
        <br>
        <div class="mt-3">
	        <a href="SubjectList.action">戻る</a>
	    </div>

    </c:param>
</c:import>