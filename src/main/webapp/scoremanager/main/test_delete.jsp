<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績削除</c:param>
	<c:param name="content">

		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績削除</h2>

			<div class="mx-3">
				<p>以下の成績情報を削除しますか？</p>

				<table class="table table-bordered">
					<tr>
						<th>学生番号</th>
						<td>${test.student.no}</td>
					</tr>
					<tr>
						<th>氏名</th>
						<td>${test.student.name}</td>
					</tr>
					<tr>
						<th>科目コード</th>
						<td>${test.subject.cd}</td>
					</tr>
					<tr>
						<th>科目名</th>
						<td>${test.subject.name}</td>
					</tr>
					<tr>
						<th>回数</th>
						<td>${test.no}</td>
					</tr>
					<tr>
						<th>点数</th>
						<td>${test.point}</td>
					</tr>
				</table>

				<form action="TestDeleteExecute.action" method="post">
					<input type="hidden" name="student_no" value="${test.student.no}">
					<input type="hidden" name="subject_cd" value="${test.subject.cd}">
					<input type="hidden" name="no" value="${test.no}">

					<button type="submit" class="btn btn-danger">削除</button>
				</form>

				<div class="mt-2">
					<a href="javascript:history.back()">戻る</a>
				</div>
			</div>
		</section>

	</c:param>
</c:import>