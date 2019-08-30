<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">


<nav class="navbar navbar-expand-sm justify-content-center">
 
  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="shop?">반경 500m 이내 매장 검색</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="register">매장 등록</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="shop?">반경 1km 이내 매장 검색</a>
    </li>
  </ul>
</nav>


   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
	$.ajax({
		url: 'data',
		success: function(result){
			var list = result.list;
			var html = ''; // 최종 HTML 모습
			for(var i=0; i<list.length;i++){
				var item = list[i];
				var name = item.NAME;
				var address = item.ADDRESS;
				var fileNm = item.FILE_NM;
				// 위의 JSON으로 뽑아낸 것을 아래 방법 통해 Body에 삽입 => AJAX 방식
				html +='<img src="' + fileNm + '">';
			}
			$('body').append(html);
		}
	});
</script>
