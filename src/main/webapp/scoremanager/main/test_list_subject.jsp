<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績参照（科目別）</c:param>
	<c:param name="content">

		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績参照（科目別）</h2>

			<div class="mb-2 text-end">
				<a href="TestRegist.action">成績登録</a>
			</div>

			<div class="card mb-3">
				<div class="card-body">
					<div>入学年度：${f1}</div>
					<div>クラス：${f2}</div>
					<div>科目コード：${f3}</div>
				</div>
			</div>

			<c:choose>
				<c:when test="${not empty tests}">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>学生番号</th>
								<th>氏名</th>
								<th>入学年度</th>
								<th>クラス</th>
								<th>回数</th>
								<th>点数</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${tests}">
								<tr>
									<td>${test.student.no}</td>
									<td>${test.student.name}</td>
									<td>${test.student.entYear}</td>
									<td>${test.student.classNum}</td>
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