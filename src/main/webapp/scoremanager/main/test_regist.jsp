<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 
<c:import url="/common/base.jsp">
    <c:param name="title">成績管理</c:param>
    <c:param name="content">
        <section class="container mt-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            
            
            
            <!-- ===== 絞り込みフォーム ===== -->
            <form method="get" action="TestRegist.action">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                
                    <!-- 入学年度プルダウン (name=f1) -->
                    <div class="col-2">
                        <label class="form-label">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- クラスプルダウン (name=f2) -->
                    <div class="col-2">
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- 科目プルダウン (name=f3) -->
                    <div class="col-3">
                        <label class="form-label">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="sub" items="${subject_set}">
                                <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- 回数プルダウン (name=f4) -->
                    <div class="col-2">
                        <label class="form-label">回数</label>
                        <select class="form-select" name="f4">
                            <option value="0">--------</option>
                            <c:forEach var="n" begin="1" end="2">
                                <option value="${n}" <c:if test="${n == f4}">selected</c:if>>${n}</option>
                            </c:forEach>
                        </select>
                    </div>
 
                    <!-- 検索ボタン -->
                    <div class="col-1 text-center align-self-end">
                        <button class="btn btn-secondary" type="submit">検索</button>
                    </div>
                    
                    
                    <!-- エラーメッセージの設定(プルダウン未選択時) -->
                    <c:if test="${not empty f1 and not empty errors.filter}">
                    <div style="color: orange; margin-top: 4px;" class="small">${errors.filter}</div>
                    </c:if>
                </div>
            </form>
 
            <!-- ===== 検索結果 ===== -->
            <!-- 検索結果がなかった場合(該当学生なし)のエラー文を表示 -->
			<c:if test="${empty students and not empty message}">
			    <div style="margin: 10px 0 15px 4px;">
			        ${message}
			    </div>
			</c:if>
            
            
            
            <c:if test="${not empty students}">
                <!-- 科目名と回数の表示 -->
                <p>科目：${subject.name}（${f4}回）</p>
 
                <form method="post" action="TestRegistExecute.action">
 
                    <!-- 絞込条件を hidden で引き継ぐ -->
                    <input type="hidden" name="f1" value="${f1}">
                    <input type="hidden" name="f2" value="${f2}">
                    <input type="hidden" name="f3" value="${f3}">
                    <input type="hidden" name="f4" value="${f4}">
 
                    <!-- テーブル -->
                    <table class="table">
                        <thead>
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${students}">
                                <tr>
                                    <!-- 入学年度 -->
                                    <td>${s.entYear}</td>
                                    
                                    <!-- クラス -->
                                    <td>${s.classNum}</td>
 
                                    <!-- 学生番号 -->
                                    <td>
                                        ${s.no}
                                        <input type="hidden" name="student_no" value="${s.no}">
                                    </td>
                                    
                                    <!-- 氏名 -->
                                    <td>${s.name}</td>
                                    
									<!-- 点数 -->
									<td style="width: 280px;">
									    <!-- 登録済み・未登録どちらも入力欄を出す(登録と変更) -->
									    <input type="number" class="form-control"
									           name="point"
									           value="${point_map[s.no]}"
									           style="width: 100px;">
									           
									    <input type="hidden"
									           name="old_point_${s.no}" 
									           value="${point_map[s.no]}">
									           
									           
									           
									    <!-- エラー表示(点数範囲外) -->
									    <span class="small"
									          style="color:#FFA500; display:block; min-height:16px; margin-top:2px;">
									        <c:if test="${not empty errors[s.no]}">
									            ${errors[s.no]}
									        </c:if>
									    </span>
									</td>
									
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                     
                     

                     
                     
                    <!-- 登録ボタン -->
                    <div class="px-4 mt-3">
                         <!-- エラー：登録・変更箇所なしでボタンを押した場合 -->
						<c:if test="${not empty errors.global}">
						    <div class="small" 
						         style="color:#FFA500; margin-top:10px; margin-bottom:8px;">
						        ${errors.global}
						    </div>
						</c:if>
                        <button type="submit" class="btn btn-secondary">登録して終了</button>
                    </div>
                </form>
            </c:if>
            
            
            
            
        </section>
    </c:param>
</c:import>