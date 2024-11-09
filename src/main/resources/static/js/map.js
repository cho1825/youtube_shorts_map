// var container = document.getElementById('map');
// var options = {
//     center: new kakao.maps.LatLng(36.3504119, 127.3845475),
//     level: 5
// };
//
// var map = new kakao.maps.Map(container, options);
//
// //마커 찍기
// /*    // 마커가 표시될 위치입니다
//     var markerPosition  = new kakao.maps.LatLng(36.35, 127.384);
//
//     // 마커를 생성합니다
//     var marker = new kakao.maps.Marker({
//         position: markerPosition
//     });
//
//     // 마커가 지도 위에 표시되도록 설정합니다
//     marker.setMap(map);*/
//
// //마커 변경해서 찍기
// // var imageSrc = '/img/logo.webp', // 마커이미지의 주소입니다
//
// var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png',
//     imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
//     imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
//
// // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
// var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
//     markerPosition = new kakao.maps.LatLng(36.35, 127.384); // 마커가 표시될 위치입니다
//
// // 마커를 생성합니다
// var marker = new kakao.maps.Marker({
//     position: markerPosition,
//     image: markerImage // 마커이미지 설정
// });
//
// // 마커가 지도 위에 표시되도록 설정합니다
// marker.setMap(map);
//
//


var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(36.3504119, 127.3845475),
    level: 5
};

var map = new kakao.maps.Map(container, options);

// 마커 이미지 설정
var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png',
    imageSize = new kakao.maps.Size(34, 38),
    imageOption = {offset: new kakao.maps.Point(27, 69)};
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);


let lastClickedMarker = null; // 함수 밖에 전역 변수로 선언
// API 요청 함수
async function loadMarkers(regionCode, youtuberNm) {
    try {
        const response = await fetch(`/api/makers?regionCode=${regionCode}&youtuberNm=${youtuberNm}`);
        const placeDtoList = await response.json();

        placeDtoList.forEach(place => {
            const markerPosition = new kakao.maps.LatLng(place.latitude, place.longitude);

            const marker = new kakao.maps.Marker({
                position: markerPosition,
                image: markerImage
            });

            // // 마커 클릭 시 정보 표시
            // const infoWindow = new kakao.maps.InfoWindow({
            //     content: `<div style="padding:5px;">${place.name}</div>`
            // });
            //


            marker.setMap(map);

            const thumbnailUrl = `https://img.youtube.com/vi/${place.videoId}/hqdefault.jpg`;


            // 마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우를 생성합니다
            var iwContent = `<div style="width: 200px; padding: 5px;">
                                        <div style="padding:5px; font-size: 14px;">${place.name}</div>
                                        <div style="padding:5px; font-size: 12px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${place.videoUrl}</div>
                                        <div style="padding:5px; font-size: 12px;">${place.description}</div>
                                        <div>
                                            <a href="${place.videoUrl}" target="_blank" style="display: block;">
                                                <img src="${thumbnailUrl}" alt="YouTube Thumbnail" style="width: 100%; border-radius: 8px;">
                                            </a>
                                         </div>
                                    </div>`;

            // 인포윈도우를 생성합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: iwContent
            });

            // 마커에 마우스오버 이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'mouseover', function () {
                // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
                infowindow.open(map, marker);
            });

            // 마커에 마우스아웃 이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'mouseout', function () {
                // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
                infowindow.close();
            });

            kakao.maps.event.addListener(marker, 'touchstart', function () {
                handleMarkerClick(marker, place);
            });

            kakao.maps.event.addListener(marker, 'click', function () {
                handleMarkerClick(marker, place);
            });

            function handleMarkerClick(marker, place) {
                const placeInfoDiv = document.getElementById('place-info');
                const videoPlayerDiv = document.getElementById('video-player');
                const youtubeIframe = document.getElementById('youtube-iframe');

                // 현재 클릭된 마커가 이전에 클릭된 마커인지 확인
                if (lastClickedMarker === marker) {
                    // 같은 마커를 클릭한 경우 정보 숨김
                    placeInfoDiv.innerHTML = '';
                    placeInfoDiv.style.display = 'none';
                    videoPlayerDiv.style.display = 'none';
                    youtubeIframe.src = ''; // 동영상 중지
                    lastClickedMarker = null; // 초기화하여 다음 클릭 시 새로 열리게 함
                } else {
                    // 다른 마커를 클릭했을 때 정보 업데이트
                    placeInfoDiv.innerHTML = `
                        <div>
                            <h3>${place.name}</h3>
                            <p>URL: <a href="${place.videoUrl}" target="_blank">${place.videoUrl}</a></p>
                            <p>${place.description}</p>
                        </div>`;
                    placeInfoDiv.style.display = 'block';

                    // YouTube URL에서 동영상 ID만 추출하여 iframe에 설정
                    const videoId = place.videoUrl.split('v=')[1];
                    if (videoId) {
                        youtubeIframe.src = `https://www.youtube.com/embed/${videoId}`;
                        videoPlayerDiv.style.display = 'block'; // 동영상 플레이어 표시
                    } else {
                        videoPlayerDiv.style.display = 'none'; // 동영상 URL이 없으면 숨김
                        youtubeIframe.src = '';
                    }

                    lastClickedMarker = marker; // 마지막으로 클릭한 마커 업데이트

                }
            }

        });
    } catch (error) {
        console.error('Failed to load markers:', error);
    }
}

// 페이지 로드 후 자동 실행
window.onload = function () {
    loadMarkers(25, '맠카');
};