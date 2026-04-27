<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績削除完了</c:param>
	<c:param name="content">

		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績削除</h2>

			<div class="mx-3">
				<div class="alert alert-success text-center py-1" role="alert">
					削除が完了しました
				</div>

				<div class="mt-2">
					<a href="TestList.action">成績参照へ戻る</a>
				</div>
			</div>
		</section>

	</c:param>
</c:import>