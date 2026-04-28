<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目削除完了</c:param>
    <c:param name="content">

        <h2>科目削除完了</h2>

		<div class="alert alert-success text-center" role="alert">
            削除が完了しました。
        </div>

        <a href="SubjectList.action">科目一覧</a>

    </c:param>
</c:import>