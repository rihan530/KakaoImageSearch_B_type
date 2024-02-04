package com.example.kakaoimagesearch_b_type

object Constants {

    // 기본 API 엔드포인트
    // 여기서 모든 API 요청의 기본 URL로 사용됨
    var BASE_URL = "https://dapi.kakao.com"

    // Kakao API를 사용하기 위한 인증 헤더
    var AUTH_HEADER = "KakaoAK 72f1bc517f9c7728c12eadfefd955397"

    // 이미지 검색을 나타내는 타입 코드
    var SEARCH_TYPE_IMAGE = 0

    // 비디오 검색을 나타내는 타입 코드
    var SEARCH_TYPE_VIDEO = 1

    // 이미지 "좋아요" 상태를 저장하기 위한 Shared Preferences 키
    var PREF_KEY = "IMAGE_LIKE_PREF"
}
