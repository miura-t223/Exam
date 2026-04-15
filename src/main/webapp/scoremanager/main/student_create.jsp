<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<%-- 追加のJavaScriptがあれば scripts に書く。今回は空でOK --%>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生登録</h2>

			<%-- 全体エラー表示 --%>
			<c:if test="${not empty errors.common}">
				<div class="alert alert-danger mx-3">${errors.common}</div>
			</c:if>

			<form action="StudentCreateExecute.action" method="post">
				<div class="mx-3">

					<div class="mb-3">
						<label class="form-label" for="ent-year">入学年度</label>
						<select class="form-select" id="ent-year" name="ent_year">
							<option value="0">----</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>
									${year}
								</option>
							</c:forEach>
						</select>
						<div class="text-warning">${errors.ent_year}</div>
					</div>

					<div class="mb-3">
						<label class="form-label" for="no">学生番号</label>
						<input class="form-control" type="text" id="no" name="no" value="${no}">
						<div class="text-warning">${errors.no}</div>
					</div>

					<div class="mb-3">
						<label class="form-label" for="name">氏名</label>
						<input class="form-control" type="text" id="name" name="name" value="${name}">
						<div class="text-warning">${errors.name}</div>
					</div>

					<div class="mb-3">
						<label class="form-label" for="class-num">クラス</label>
						<select class="form-select" id="class-num" name="class_num">
							<option value="0">----</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>
									${num}
								</option>
							</c:forEach>
						</select>
						<div class="text-warning">${errors.class_num}</div>
					</div>

					<div class="mt-4">
						<input class="btn btn-primary" type="submit" value="登録して終了" name="end">
						<a class="btn btn-secondary" href="StudentList.action">戻る</a>
					</div>

				</div>
			</form>
		</section>
	</c:param>
</c:import>