<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
번호 : ${map.ID} <br>
제목 : ${map.TITLE} <br>
파일1 : ${map.FILE1} <br>
<img src="../resources/${map.FILE1}"><br>
<!-- resource(상대경로) 밑에 파일 저장 되게 내가 HomeController에서 설정 해놨음 -->
파일2 : ${map.FILE2} <br>
<img src="../resources/${map.FILE2}"><br>
<!--   view에서 수정 누르면 edit로 이동 -->
<button type="button" onclick="moveEdit()">수정</button>
<button type="button" onclick="moveDelete()">삭제</button>
<script>
// http://localhost/sample/show/162 에서 한 단계 나가서 이동
// http://localhost/sample/edit/162

	function moveDelete(){
		location="../delete/${map.ID}";
	}

	function moveEdit(){
		location="../edit/${map.ID}";
	}
</script>