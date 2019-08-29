<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<form method="post">
	제목: <input type="text" name="title"><br>
	내용 : <textarea name="content" rows="20" cols="10"></textarea><br>
	작성자 : <input type="text" name="writer"><br>
	<input type="hidden" name="ord" value="${param.ord}"><br>
	<input type="hidden" name="grp" value="${param.parent}"><br>
	<!-- reply의 그룹번호 안에 값 넘기기   -->
	<input type="hidden" name="level" value="${param.level}"><br>
	<input type="submit">
</form>