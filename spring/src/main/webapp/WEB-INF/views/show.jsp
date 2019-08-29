<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Popper JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

${totalCount} / ${totalPage}

<form action="show"> <!--검색 누르면 show.jsp로  -->
  <div class="form-group">
    <label for="search">검색어 :</label>
    <input type="text" class="form-control" id="search" name="search">
  </div>
  <!-- 검색어 hello 넣으면 show?search=hello됨, name에 search 넣었기 때문 -->
  <button type="submit" class="btn btn-primary">검색</button>
</form>

	
<div class="list-group">
	<c:forEach items="${list}" var="item">
	 <a href="show/${item.ID}" class="list-group-item list-group-item-action">
		${item.ID} / ${item.TITLE}
		</a>
	</c:forEach>
</div>

<!--var라는 변수에 값  담기-->
<ul class="pagination">
	<c:if test="${startPage>1}">
		<!--1페이지 앞에는 Previous 안보이게 함  -->
		<li class="page-item"><a class="page-link"
			href="show?page=${startPage-10}">Previous</a></li>
	</c:if>

	<c:forEach begin="${startPage}" end="${endPage}" var="item">
		<!--   ? 뒤 page 값이 param.page로 들어옴, 현재 19페이지면 19는 링크 안되게 if문  -->
		<c:if test="${item == param.page}">
			<li class="page-item"><a class="page-link" href="#">${item}</a>
			</li>
		</c:if>

		<c:if test="${item != param.page}">
			<li class="page-item"><a class="page-link"
				href="show?page=${item}"> ${item}</a></li>
		</c:if>

	</c:forEach>
	<li class="page-item"><a class="page-link"
		href="show?page=${startPage+10}">Next</a></li>
</ul>
<!-- 현 페이지에서 하나씩 next 하려면 startPage+10 자리에 param.page+1로  처리  -->