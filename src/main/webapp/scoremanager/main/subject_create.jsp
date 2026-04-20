<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>

            <div class="mx-3" style="max-width: 40rem;">

                <%-- 共通エラー（DB失敗など） --%>
                <c:if test="${not empty errors.common}">
                    <div class="text-warning mb-2">${errors.common}</div>
                </c:if>

                <form action="SubjectCreateExecute.action" method="post">
                    <div class="mb-3">
                        <label class="form-label" for="cd">科目コード</label>
                        <input type="text"
                               class="form-control"
                               id="cd"
                               name="cd"
                               value="${cd}"
                               placeholder="科目コードを入力してください"
                               maxlength="3"
                               required>
                        <div class="text-warning">${errors.cd}</div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="name">科目名</label>
                        <input type="text"
                               class="form-control"
                               id="name"
                               name="name"
                               value="${name}"
                               placeholder="科目名を入力してください"
                               maxlength="20"
                               required>
                        <div class="text-warning">${errors.name}</div>
                    </div>

                    <%-- 設計書：登録ボタン（青）＋戻るリンク --%>
                    <div class="mt-2">
                        <button class="btn btn-primary" type="submit">登録</button>
                    </div>
                    <div class="mt-2">
                        <a href="SubjectList.action">戻る</a>
                    </div>
                </form>

            </div>
        </section>
    </c:param>
</c:import>