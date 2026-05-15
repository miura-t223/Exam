<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="scripts"><script></script></c:param>
<c:param name="avg1">${avg1}</c:param>
<c:param name="avg2">${avg2}</c:param>


    
    <%-- 成績参照表示ページ --%>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（科目）
            </h2>
<%------------------------------------ 検索用枠線 ------------------------------------%>
	        <%-- プルダウン --%>
            <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

                <%-- 科目情報 --%>
                <form method="get" action="TestListSubjectExecute.action">
                    <div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                        <p class="col-2 d-flex justify-content-center align-items-center">
                            <label class="form-label" for="student-f1-select">科目情報</label>
                        </p>

                        <div class="col-2">
                            <label class="form-label" for="student-f1-select">入学年度</label>
                            <select class="form-select" id="student-f1-select" name="f1">
                                <option value="0">----</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <%-- 現在のyearと選択されていた値が一致していればselectedを追記 --%>
                                    <option value="${year}" <c:if test="${year==f1}">selected</c:if>>
                                        ${year}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <label class="form-label" for="student-f2-select">クラス</label>
                            <select class="form-select" id="student-f2-select" name="f2">
                                <option value="0">----</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <%-- 現在のnumと選択されていた値が一致していればselectedを追記 --%>
                                    <option value="${num}" <c:if test="${num==f2}">selected</c:if>>
                                        ${num}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-4">
                            <label class="form-label" for="student-f3-select">科目</label>
                            <select class="form-select" id="student-f3-select" name="f3">
                                <option value="0">----</option>
                                <c:forEach var="subject" items="${class_subject_set}">
                                    <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
                                        ${subject.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <%-- 検索ボタン --%>
                        <div class="col-2 text-center">
                            <button class="btn btn-secondary" id="filter-button">検索</button>
                        </div>

                        <%-- 未入力エラー処理 --%>
                        <div class="mt-2 text-warning">
                            ${errors.get("f1")}
                        </div>
                    </div>
                </form>

<%------------------------------------ 検索用横線 ------------------------------------%>
                <div style="border-bottom: 1px solid #ccc; width: 95%; margin: 10px auto;"></div>

                <%-- 学生情報 --%>
                <form method="get" action="TestListStudentExecute.action">
                    <div class="row mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                        <%-- 科目情報(見出し) --%>
                        <p class="col-2 d-flex justify-content-center align-items-center">
                            <label class="form-label" for="student-f1-select">学生情報</label>
                        </p>

                        <%-- 学生番号で検索(テキストボックス) --%>
                        <div class="col-4">
                            <label class="form-label" for="student-no">学生番号</label>

                            <%-- 未入力 --%>
                            <c:choose>
                                <c:when test="${not empty errors.get('f4')}">
                                    <%-- エラー時（赤枠） --%>
                                    <input class="form-control"
                                           type="text"
                                           id="student-no"
                                           name="f4"
                                           maxlength="10"
                                           value="${f4}"
                                           placeholder="学生番号を入力してください"
                                           required
                                           data-bs-toggle="tooltip"
                                           data-bs-placement="right"
                                           title="${errors.get('f4')}"
                                           style="border-color:#dc3545;">
                                </c:when>
                                <c:otherwise>
                                    <%-- 通常時 --%>
                                    <input class="form-control"
                                           type="text"
                                           id="student-no"
                                           name="f4"
                                           maxlength="10"
                                           value="${f4}"
                                           placeholder="学生番号を入力してください"
                                           required
                                           data-bs-toggle="tooltip"
                                           data-bs-placement="right"
                                           title="${errors.get('f4')}">
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <%-- 検索ボタン --%>
                        <div class="col-2 text-center">
                            <button class="btn btn-secondary" id="filter-button">検索</button>
                        </div>

                        <%-- 未入力エラー処理 --%>
                        <div class="mt-2 text-warning">
                            ${errors.get("f4")}
                        </div>
                    </div>
                </form>
            </div>
            
            
            
            
<%-------------------------------------- 科目名 --------------------------------------%>
            <c:choose>
                <c:when test="${tests.size() > 0}">
                    <%--検索結果があった場合は科目名を表示--%>
                    <div>
                        <label>科目：${tests[0].subject.name}</label>
                    </div>
                    
                    
                    
<%-------------------------------------- 平均点 --------------------------------------%>
                    <c:set var="sum1" value="0" />
                    <c:set var="sum2" value="0" />
                    <c:set var="totalsum" value="0" />
                    <c:set var="count1" value="0" />
                    <c:set var="count2" value="0" />
                    <c:set var="totalcount" value="0" />
                    <c:set var="average1" value="0" />
                    <c:set var="average2" value="0" />
                    <c:set var="totalaverage" value="0" />
                    
                    <%-- 合計点数と受験人数を計算 --%>
                     <c:forEach var="t" items="${tests}">
                        <c:choose>
                            <c:when test="${t.no == 1}">
                                <c:set var="sum1" value="${sum1 + t.point}" />
                                <c:set var="count1" value="${count1 + 1}" />
                            </c:when>
                            <c:when test="${t.no == 2}">
                                <c:set var="sum2" value="${sum2 + t.point}" />
                                <c:set var="count2" value="${count2 + 1}" />
                            </c:when>
                        </c:choose>
                        <c:set var="totalsum" value="${totalsum + t.point}" />
                        <c:set var="totalcount" value="${totalcount + 1}" />
                    </c:forEach>
                    
                    <%-- 平均点を計算 --%>
                    <c:if test="${count1 > 0}">
                        <c:set var="average1" value="${sum1 div count1}" />
                    </c:if>
                    <c:if test="${count2 > 0}">
                        <c:set var="average2" value="${sum2 div count2}" />
                    </c:if>
                    <c:if test="${totalcount > 0}">
                        <c:set var="totalaverage" value="${totalsum div totalcount}" />
                    </c:if>
                    
                    <%-- 表示 --%>
                    <div class="row mt-4">
                        <%-- 試験1回目 --%>
                        <div class="col-5 text-center">
                            <%-- 試験1回目の受験人数がいない場合 --%>
                            <c:if test="${count1 == 0}">
                                <h5 style="margin-bottom:10px;">【1回目の試験】</h5>
                                <p style="font-size:20px; margin-bottom:10px;">受験人数：${count1}人</p>
                                <p style="font-size:20px; margin-bottom:10px;">平均点：ー</p>
                            </c:if>
                            <%-- 試験1回目の受験人数がいる場合 --%>
                            <c:if test="${count1 > 0}">
                                <h5 style="margin-bottom:10px;">【1回目の試験】</h5>
                                <p style="font-size:20px; margin-bottom:10px;">受験人数：${count1}人</p>
                                <p style="font-size:20px; margin-bottom:10px;">平均点：<span style="color:orange;"><fmt:formatNumber value="${average1}" maxFractionDigits="1" />点</span></p>
                            </c:if>
                        </div>
                        <%-- 縦線 --%>
                        <div class="col-1 d-flex justify-content-center">
                            <div style="border-left: 1px solid #ddd; height: 100%;"></div>
                        </div>
                        <%-- 試験2回目 --%>
                        <div class="col-5 text-center">
                            <%-- 試験2回目の受験人数がいない場合 --%>
                            <c:if test="${count2 == 0}">
                                <h5 style="margin-bottom:10px;">【2回目の試験】</h5>
                                <p style="font-size:20px; margin-bottom:10px;">受験人数：${count2}人</p>
                                <p style="font-size:20px; margin-bottom:10px;">平均点：ー</p>
                            </c:if>
                            <%-- 試験2回目の受験人数がいる場合 --%>
                            <c:if test="${count2 > 0}">
                                <h5 style="margin-bottom:10px;">【2回目の試験】</h5>
                                <p style="font-size:20px; margin-bottom:10px;">受験人数：${count2}人</p>
                                <p style="font-size:20px; margin-bottom:10px;">平均点：<span style="color:orange;"><fmt:formatNumber value="${average2}" maxFractionDigits="1" />点</span></p>
                            </c:if>
                            </div>
                        </div><p></p>
                        <%-- 2科目分の平均点を表示 --%>
                        <p style="font-weight:bold; font-size:30px; text-align:center; color:orange;">
                            科目平均：<fmt:formatNumber value="${totalaverage}" maxFractionDigits="1" />点<br>
                        </p>
                        
                        
                        
                        
                        
<%------------------------------------- テーブル -------------------------------------%>
                    <%-- 検索結果 --%>
                    <table class="table table-hover">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>1回</th>
                            <th>2回</th>
                            <th>操作</th>
                        </tr>

                        <c:forEach var="t" items="${tests}">
                            <tr>
                                <td>${t.student.entYear}</td>
                                <td>${t.student.classNum}</td>
                                <td>${t.student.no}</td>
                                <td>${t.student.name}</td>
                                <%-- 1回の得点 --%>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.no == 1}">
                                            ${t.point}
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <%-- 2回の得点 --%>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.no == 2}">
                                            ${t.point}
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <%-- 削除 --%>
                                <td>
                                    <a href="TestDelete.action?student_no=${t.student.no}&subject_cd=${t.subject.cd}&no=${t.no}">
                                    削除
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                
                <%-- 登録された学生情報がなかった場合 --%>
                <c:otherwise>
                    <div>学生情報が存在しませんでした</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>