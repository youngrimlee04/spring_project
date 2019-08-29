<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

        <!--**********************************
            Content body start
        ***********************************-->
        <div class="content-body">

            <div class="row page-titles mx-0">
                <div class="col p-md-0">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">${info.shopInfo.NAME}</li>
                        <li class="breadcrumb-item active">${info.shopInfo.ADDRESS}</li>
                    </ol>
                </div>
            </div>
            <!-- row -->

            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
	<div id="demo" class="carousel slide col" data-ride="carousel">
	
	  <!-- Indicators -->
	  <ul class="carousel-indicators">
	  	<c:forEach items="${info.shopImgs}" varStatus="status">
		    <li data-target="#demo" data-slide-to="${status.count - 1}" class="active"></li>
	  	</c:forEach>
	  </ul>
	
	  <!-- The slideshow -->
	  <div class="carousel-inner" style="text-align: center;">
		<c:forEach items="${info.shopImgs}" var="item" varStatus="status">
			<c:choose>
				<c:when test="${status.count == 1}">
				    <div class="carousel-item active">
				      <img src="${item.FILE_NM}">
				    </div>
				</c:when>
				<c:otherwise>
				    <div class="carousel-item">
				      <img src="${item.FILE_NM}">
				    </div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	  </div>
	
	  <!-- Left and right controls -->
	  <a class="carousel-control-prev" href="#demo" data-slide="prev">
	    <span class="carousel-control-prev-icon"></span>
	  </a>
	  <a class="carousel-control-next" href="#demo" data-slide="next">
	    <span class="carousel-control-next-icon"></span>
	  </a>
	
	</div>
                                
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <!-- #/ container -->
        </div>
        <!--**********************************
            Content body end
        ***********************************-->

<div class="container-fluid">
	<div class="row">
		<div class="col">
			<div class="card">
				<div class="card-body">
					<div id="map" style="margin: 0 auto; width: 500px; height: 400px;"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=38d34c29c097c5f5c8c02416cb4aefff&libraries=services"></script>
<script>
	var lat = "${info.shopInfo.LAT}";
	var lng = "${info.shopInfo.LNG}";

	makeMap(lat, lng);

	function makeMap(lat, lng) {
		var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(lat, lng), //지도의 중심좌표.
			level: 3 //지도의 레벨(확대, 축소 정도)
		};

		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

		// 마커가 표시될 위치입니다 
		var markerPosition  = new kakao.maps.LatLng(lat, lng); 

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
		    position: markerPosition
		});

		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);
	}
</script>
