<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生変更</h2>

			<c:if test="${not empty errors.common}">
				<div class="alert alert-danger mx-3">${errors.common}</div>
			</c:if>

			<form action="StudentUpdateExecute.action" method="post">
				<div class="mx-3">

					<%-- 変更画面では学生番号は変更させないため readonly にして送る --%>
					<div class="mb-3">
						<label class="form-label" for="no">学生番号</label>
						<input class="form-control" type="text" id="no" name="no" value="${student.no}" readonly>
					</div>

					<%-- 入学年度も変更させない想定で readonly にする --%>
					<div class="mb-3">
						<label class="form-label" for="ent-year">入学年度</label>
						<input class="form-control" type="text" id="ent-year" name="ent_year" value="${student.entYear}" readonly>
					</div>

					<div class="mb-3">
						<label class="form-label" for="name">氏名</label>
						<input class="form-control" type="text" id="name" name="name" value="${student.name}">
						<div class="text-warning">${errors.name}</div>
					</div>

					<div class="mb-3">
						<label class="form-label" for="class-num">クラス</label>
						<select class="form-select" id="class-num" name="class_num">
							<option value="0">----</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>
									${num}
								</option>
							</c:forEach>
						</select>
						<div class="text-warning">${errors.class_num}</div>
					</div>

					<div class="mb-3 form-check">
						<input class="form-check-input" type="checkbox" id="is-attend" name="is_attend"
							<c:if test="${student.isAttend}">checked</c:if>>
						<label class="form-check-label" for="is-attend">在学中</label>
					</div>

					<div class="mt-4">
						<input class="btn btn-primary" type="submit" value="変更して終了">
						<a class="btn btn-secondary" href="StudentList.action">戻る</a>
					</div>

				</div>
			</form>
		</section>
	</c:param>
</c:import>