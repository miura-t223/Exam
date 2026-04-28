<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

			<div class="mt-3 text-center p-2"
			     style="background-color:#8CC3A9; width:100%; margin: 0 auto;">
			    変更が完了しました
			</div>
			<br>
			<br>
			<br>

			<div class="mx-3 mt-3">
				<a href="StudentList.action">学生一覧</a>
			</div>
		</section>
	</c:param>
</c:import>