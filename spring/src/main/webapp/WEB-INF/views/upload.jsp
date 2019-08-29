<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form method="post" enctype="multipart/form-data">
	<input type="file" name="file"><br>
	<input type="file" name="file2"><br>
	<input type="file" name="file3" multiple><br> 
	<!-- 파일 첨부시 ctrl이나 shift 눌러서 여러 개 파일 지정 가능한 게 multiple -->
	<input type="submit"> <!-- value 사용 시 버튼명으로 표시  -->

</form>