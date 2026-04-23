<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

			<c:if test="${not empty errors.common}">
				<div class="alert alert-danger mx-3">${errors.common}</div>
			</c:if>

			<form action="StudentUpdateExecute.action" method="post">
				<div class="mx-3">

					<%-- 入学年度も変更させない想定で readonly にする --%>
					<div class="mb-3">
						<label class="form-label" >入学年度</label>
						<div class="form-control-plaintext">${student.entYear}</div>
						<input type="hidden" name="ent_year" value="${student.entYear}">
					</div>

					<%-- 変更画面では学生番号は変更させないため readonly にして送る --%>
					<div class="mb-3">
						<label class="form-label">学生番号</label>
						<div class="form-control-plaintext">${student.no}</div>
						<input type="hidden" name="no" value="${student.no}">
					</div>

					<div class="mb-3">
						<label class="form-label" for="name">氏名</label>
						<input class="form-control" type="text" id="name" name="name" 
							value="${student.name}"
							placeholder="氏名を入力してください"
							maxlength="10"
							required>
						<div class="text-warning">${errors.name}</div>
					</div>

					<div class="mb-3">
						<label class="form-label" for="class-num">クラス</label>
						<select class="form-select" id="class-num" name="class_num" required>
							<option value="">----</option>
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

					<div class="mt-3">
						<input class="btn btn-primary" type="submit" value="変更">
					</div>
					<%-- 学生管理一覧へ戻る --%>
					<div class="mt-2">
					    <a href="StudentList.action">戻る</a>
					</div>
					</form>

				</div>
			</form>
		</section>
	</c:param>
</c:import>