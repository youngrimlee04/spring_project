<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">


<nav class="navbar navbar-expand-sm justify-content-center">
 
  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="register">매장 등록</a>
    </li>
  </ul>

</nav>

<div class="container-fluid">
    <!-- End Row -->
    <div class="row">
        <div class="col-12 m-b-30">
		<c:forEach items="${list}" var="item" varStatus="status">
			<c:if test="${status.count % 4 == 1}">
				<div class="row">
			</c:if>
          <div class="col-md-6 col-lg-3">
              <div class="card">
              	<a href="shop/${item.ID}"> <!-- Controller가 넘긴 모델 -->
                   <img class="img-fluid" src="${item.FILE_NM}" height="50px" onerror="this.src='/resources/error.png'">
                   <div class="card-body">
                       <h5 class="card-title">${item.NAME}</h5>
                       <p class="card-text">${item.ADDRESS}</p>
                       <p class="card-text"><small class="text-muted">${item.CRE_DATE}</small>
                       </p>
                   </div>
                  </a>
              </div>
          </div>
			<c:if test="${status.count % 4 == 0}">
				</div>
			</c:if>
		</c:forEach>        

        </div>
    </div>
    <!-- End Row -->
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
