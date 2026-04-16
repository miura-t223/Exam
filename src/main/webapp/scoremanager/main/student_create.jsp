<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- base.jspを読み込み -->
<c:import url="/common/base.jsp">

	<%-- 追加のJavaScriptがあれば scripts に書く。今回は空 --%>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生登録</h2>

			<%-- 全体エラー表示 --%>
			<c:if test="${not empty errors.common}">
				<div class="alert alert-danger mx-3">${errors.common}</div>
			</c:if>
			
			<!-- 学生登録フォーム -->
			<form action="StudentCreateExecute.action" method="post">
				<div class="mx-3">

					<div class="mb-3">
						<!-- 入学年度選択 -->
						<label class="form-label" for="ent-year">入学年度</label>
						<select class="form-select" id="ent-year" name="ent_year">
							<option value="0">----</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>
									${year}
								</option>
							</c:forEach>
						</select>
						<!-- エラー時の文章表示 -->
						<div class="text-warning">${errors.ent_year}</div>
					</div>

					<div class="mb-3">
						<!-- 学生番号入力 -->
						<label class="form-label" for="no">学生番号</label>
						<input class="form-control" type="text" id="no" name="no" value="${no}">
						<!-- エラー時の文章表示 -->
						<div class="text-warning">${errors.no}</div>
					</div>

					<div class="mb-3">
						<!-- 氏名入力 -->
						<label class="form-label" for="name">氏名</label>
						<input class="form-control" type="text" id="name" name="name" value="${name}">
						<!-- エラー時の文章表示 -->
						<div class="text-warning">${errors.name}</div>
					</div>

					<div class="mb-3">
						<!-- クラス番号選択 -->
						<label class="form-label" for="class-num">クラス</label>
						<select class="form-select" id="class-num" name="class_num">
							<option value="0">----</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>
									${num}
								</option>
							</c:forEach>
						</select>
						<!-- エラー時の文章表示 -->
						<div class="text-warning">${errors.class_num}</div>
					</div>

					<div class="mt-4">
						<!-- 登録終了ボタン -->
						<input class="btn btn-primary" type="submit" value="登録して終了" name="end">
						<!-- 前の画面に戻るボタン -->
						<a class="btn btn-secondary" href="StudentList.action">戻る</a>
					</div>

				</div>
			</form>
		</section>
	</c:param>
</c:import>