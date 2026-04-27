<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログアウト | 得点管理システム</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-4">

        <!-- タイトル -->
        <h1 class="fw-bold p-3" style="background-color:#dfe7f1;">
            得点管理システム
        </h1>

        <!-- 見出し -->
        <div class="p-3 mt-4" style="background-color:#e5e5e5;">
            <h2 class="h5 fw-bold mb-0">ログアウト</h2>
        </div>

        <!-- メッセージ -->
        <div class="alert mt-4 text-center" style="background-color:#cfe2d6;">
            ログアウトしました
        </div>

        <!-- リンク（ボタンやめる） -->
        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/scoremanager/main/Login.action">
                ログイン画面へ戻る
            </a>
        </div>

        <!-- フッター -->
        <div class="text-center text-muted mt-5">
            &copy; 2023 TIC 大原学園
        </div>

    </div>

</body>
</html>