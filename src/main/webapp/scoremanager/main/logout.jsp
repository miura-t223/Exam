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

    <div class="container" style="max-width: 900px;">

        <!-- タイトル -->
        <h1 class="fw-bold p-3" style="background-color:#dfe7f1;">
            得点管理システム
        </h1>

        <!-- 見出し -->
		<div
		 class="p-2 fw-bold fs-4" style="background-color:#e5e5e5; width: 70%; margin: 0 auto;">
	         ログアウト
	    </div>

        <!-- メッセージ -->
        <div class="mt-3 text-center p-2"
		     style="background-color:#8CC3A9; width:70%; margin: 0 auto;">
		    ログアウトしました
		</div>
		
		
		<br>
		<br>
        <!-- リンク（ボタンやめる） -->
		<div class="mt-3" style="margin-left: 150px;">
		    <a href="Login.action">ログイン</a>
		</div>
		
        <!-- フッター -->
        <div class="text-center text-muted mt-5">
            &copy; 2023 TIC 大原学園
        </div>

    </div>

</body>
</html>