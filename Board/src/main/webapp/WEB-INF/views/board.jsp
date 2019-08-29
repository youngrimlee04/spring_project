<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
    <!--  model.attribute에 넣은 이름 board-->
${board.ID}<br>
${board.TITLE}<br>
${board.CONTENT}<br>
${board.HIT}<br>
${board.WRITER}<br>
${board.WRITE_DATE}<br>
${board.GRP}<br>
${board.ORD}<br>
${board.LEVEL}<br>   
<button onclick="moveReply()">답변 쓰기</button> 
댓글 : <input type="text" id="comment">
<button onclick="addComment()">댓글 작성완료</button> 

<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>
	function addComment(){
		var comment = $("#comment").val();
		$.ajax({
			url: "../addComment",
			data: {
				"id":"${board.ID}",
				"content":comment
			},
			success: function(res){
				alert(res);
			}
		})
	}
	function moveReply(){
		location="../reply?parent=${board.GRP}&ord=${board.ORD}&level=${board.LEVEL}";
		// 내가 답변을 달고자 하는 부모 게시글에 번호를 ?에 담아주려고 ?parent 사용
		// http://localhost:8080/board/reply
		// board.jsp에서 버튼 누르면 Controller의 String reply 호출
	}

</script>