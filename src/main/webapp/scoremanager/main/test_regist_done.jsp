<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			
			<!-- 登録完了メッセージ -->
			<div class="mb-3 text-center"
			     style="background-color: #8cc3a9; color: black; padding: 6px; border-radius: 0;">
			    登録が完了しました
			</div>
			<br><br><br><br><br>
			
			
			
			
			<div class="mx-3">
				<a href="TestRegist.action">戻る</a>
				<span class="mx-2">|</span>
				<a href="TestList.action">成績参照</a>
			</div>
			
		</section>
	</c:param>
</c:import>