package com.example.kakaoimagesearch_b_type.data

import com.example.kakaoimagesearch_b_type.Constants
import com.example.kakaoimagesearch_b_type.data.api.NetworkInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//Retrofit 클라이언트 객체
//이 객체를 통해 API 호출을 위한 설정 및 인터페이스 생성이 가능
object NetworkClient {

    private const val SEARCH_BASE_URL = "https://dapi.kakao.com/v2/search/image"

    val apiService: NetworkInterface
        get() = instance.create(NetworkInterface::class.java)

    // Retrofit 인스턴스를 초기화하고 반환
    private val instance: Retrofit
        private get() {
            // Gson 객체 생성
            // setLenient()는 JSON 파싱이 좀 더 유연하게 처리되도록 동작
            val gson = GsonBuilder().setLenient().create()

            // Retrofit 빌더를 사용하여 Retrofit 인스턴스 생성
            return Retrofit.Builder()
                // 기본 URL 설정
                .baseUrl(Constants.BASE_URL)
                // JSON 파싱을 위한 컨버터 추가
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}