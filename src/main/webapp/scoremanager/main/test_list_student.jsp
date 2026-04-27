<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績参照（学生別）</c:param>
	<c:param name="content">

		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績参照（学生別）</h2>

			<div class="mb-2 text-end">
				<a href="TestRegist.action">成績登録</a>
			</div>

			<div class="card mb-3">
				<div class="card-body">
					<div>学生番号：${f4}</div>
				</div>
			</div>

			<c:choose>
				<c:when test="${not empty tests}">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>科目コード</th>
								<th>科目名</th>
								<th>回数</th>
								<th>点数</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${tests}">
								<tr>
									<td>${test.subject.cd}</td>
									<td>${test.subject.name}</td>
									<td>${test.no}</td>
									<td>${test.point}</td>
									<td>
										<a href="TestDelete.action?student_no=${test.student.no}&subject_cd=${test.subject.cd}&no=${test.no}">削除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="alert alert-secondary">該当する成績情報がありませんでした</div>
				</c:otherwise>
			</c:choose>

			<div class="mt-2">
				<a href="TestList.action">戻る</a>
			</div>
		</section>

	</c:param>
</c:import>