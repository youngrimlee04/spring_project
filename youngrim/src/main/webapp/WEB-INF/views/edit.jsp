<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<form method="post">
	번호 : ${map.ID} <br>
	제목 : <input type="text" name="title" value="${map.TITLE}"><br>
	내용 : <input type="text" name="content" value="${map.CONTENT}"><br> 
	조회수 : ${map.HIT} <br>
	작성자 : ${map.WRITER} <br>
	작성일 : ${map.WRITE_DATE} <br>
	파일1 : ${map.FILE1} <br>
	<img src="../resources/${map.FILE1}"><br>
	<!-- resource(상대경로) 밑에 파일 저장 되게 내가 HomeController에서 설정 해놨음 -->
	파일2 : ${map.FILE2} <br>
	<img src="../resources/${map.FILE2}"><br>
	<input type="submit" value="수정완료">
</form>