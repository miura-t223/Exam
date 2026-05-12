<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:set var="user" value="${sessionScope.user}" />
<c:import url="/common/base.jsp">
    <c:param name="title">エラーページ</c:param>
<c:param name="scripts"></c:param>



<%-- エラーページ --%>

    <c:param name="content">
        <div>エラーが発生しました</div>
    </c:param>
</c:import>