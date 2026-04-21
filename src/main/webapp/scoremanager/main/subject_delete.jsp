<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目削除</c:param>
    <c:param name="content">

        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
            科目削除確認
        </h2>

        <div class="alert alert-danger">
            本当に削除しますか？
        </div>

        <p>科目コード：${subject.cd}</p>
        <p>科目名：${subject.name}</p>

        <form action="SubjectDeleteExecute.action" method="post">
            <input type="hidden" name="cd" value="${subject.cd}">
            <button type="submit" class="btn btn-danger">削除する</button>
            <a href="SubjectList.action" class="btn btn-secondary">キャンセル</a>
        </form>

    </c:param>
</c:import>