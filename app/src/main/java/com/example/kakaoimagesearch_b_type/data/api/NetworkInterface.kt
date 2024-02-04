package com.example.kakaoimagesearch_b_type.data.api

import com.example.kakaoimagesearch_b_type.data.model.ImageModel
import com.example.kakaoimagesearch_b_type.data.model.VideoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

//Retrofit2 라이브러리를 사용하여 API 엔드포인트와 상호작용하는 인터페이스
//이 인터페이스는 Kakao 검색 API에 대한 endpoint를 정의하며, 여기에서는 이미지 및 비디오 검색 endpoint를 사용
interface NetworkInterface {

    @GET("v2/search/image")
    fun imageSearch(
        @Header("Authorization") apiKey: String?,
        @Query("query") query: String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ImageModel?>?

    @GET("v2/search/vclip")
    fun videoSearch(
        @Header("Authorization") apiKey: String?,
        @Query("query") query: String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<VideoModel?>?
}