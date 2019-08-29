<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<p id="location">
<form method='post' enctype='multipart/form-data'>
	매장명 : <input type='text' name='name'><br> 위도 : <input
		type='text' name='lat' readonly><br> 경도 : <input
		type='text' name='lng' readonly><br> 주소 : <input
		type='text' name='address'><br> 사진 : <input type='file'
		name='img' multiple><br> <input type='submit' value='등록'>
</form>
<hr>
<div id="map" style="width: 500px; height: 400px;"></div>
<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>

<!-- TODO. 카카오 지도 라이브러리 등록 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9df5e6336b39ea13f43e62b7d708f08b&libraries=services"></script>
<script>
	// TODO 현재 위도 경도 가져오기

	// TODO 현재 위치를 기준으로 지도 표시

	// TODO 클릭된 위치의 주소와 위도/경도 확인
	// (1) 내 위치 알아낸 후
	navigator.geolocation.getCurrentPosition(function(pos) {
		var latitude = pos.coords.latitude;
		var longitude = pos.coords.longitude;
		// 맨 위의 location p태그 사용, 가장 상단에 text로 위도, 경도 찍어줌
		document.querySelector("#location").textContent = 
			"현재 위치는" + latitude + "," + longitude;
		// alert("현재 위치는 : " + latitude + ", " + longitude);
	


/*   (2) 지도  뿌림 *///
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 로 내가 위에 div에서 미리 map 만들었기에 사용 가능
    mapOption = { 
        center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심 좌표, 내가 잡은 latitude와 longitude 넣음
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 지도를 클릭한 위치에 표출할 마커입니다
var marker = new kakao.maps.Marker({ 
    // 지도 중심좌표에 마커를 생성합니다 
    position: map.getCenter() 
}); 
// 지도에 마커를 표시합니다
marker.setMap(map);

// 지도에 클릭 이벤트를 등록합니다
// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
    
    // 클릭한 위도, 경도 정보를 가져옵니다 
    var latlng = mouseEvent.latLng; 
    
    // 마커 위치를 클릭한 위치로 옮깁니다
    marker.setPosition(latlng);
    
    // 위에 form태그에서 내가 만든 name = lat, lng를 불러옴
    // 내가 지도에서 찍은 부분이 form의 위도, 경도에 주소로 들어감
    document.querySelector("[name=lat]").value= latlng.getLat();
    document.querySelector("[name=lng]").value = latlng.getLng();
    
   	// (3) 위경도 사용 주소 찍기 : 카카오 사용 http://apis.map.kakao.com/web/sample/coord2addr/
   	var geocoder = new kakao.maps.services.Geocoder();
   	
    geocoder.coord2Address(latlng.getLng(), latlng.getLat(), function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var detailAddr = !!result[0].road_address ? result[0].road_address.address_name : result[0].address.address_name;
            
            document.querySelector("[name=address]").value = detailAddr;
        }   
    });
            
 
});

	});
</script>