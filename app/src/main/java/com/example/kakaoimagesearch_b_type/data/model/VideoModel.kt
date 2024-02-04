package com.example.kakaoimagesearch_b_type.data.model

import com.google.gson.annotations.SerializedName

//비디오 검색 응답을 위한 모델 클래스
class VideoModel(
    @SerializedName("meta")
    var meta: Metadata,

    @SerializedName("documents")
    var documents: ArrayList<Documents>
) {
//    비디오 검색 응답에 대한 메타 정보를 나타내는 클래스
    data class Metadata(
        @SerializedName("is_end")
        val isEnd: Boolean,

        @SerializedName("pageable_count")
        val pageableCount: Int,

        @SerializedName("total_count")
        val totalCount: Int
    )
//    비디오 검색 응답에서 단일 문서 혹은 결과를 나타내는 클래스
    data class Documents(
        @SerializedName("title")
        val title: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("datetime")
        val datetime: String,

        @SerializedName("play_time")
        val playTime: String,

        @SerializedName("thumbnail")
        val thumbnail: String,

        @SerializedName("author")
        val author: String
    )
}
