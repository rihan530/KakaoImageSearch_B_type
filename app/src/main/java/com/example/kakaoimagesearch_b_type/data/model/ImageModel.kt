package com.example.kakaoimagesearch_b_type.data.model

import com.google.gson.annotations.SerializedName

//이미지 검색 응답을 위한 모델 클래스
//코드는 ImageModel이라는 주 클래스로 구성되어 있으며, 이는 이미지 검색 응답을 나타냄
//Metadata, Documents 두 개의 내부 클래스가 포함됨
//Documents는 검색 결과의 각 항목을 나타내며, Meta는 검색 응답의 메타 정보를 표출
//@SerializedName 애너테이션은 JSON 응답과 자바 객체 사이의 매핑을 지정
class ImageModel(
    @SerializedName("meta")
    val meta: Metadata,

    @SerializedName("documents")
    val documents: ArrayList<Documents>
) {
//    이미지 검색 응답에 대한 메타 정보를 나타내는 클래스
    data class Metadata(
        @SerializedName("is_end")
        val isEnd: Boolean,

        @SerializedName("pageable_count")
        val pageableCount: Int,

        @SerializedName("total_count")
        val totalCount: Int
    )
//    이미지 검색 응답에서 단일 문서 혹은 결과를 나타내는 클래스
    data class Documents(
        @SerializedName("collection")
        val collection: String,

        @SerializedName("thumbnail_url")
        val thumbnailUrl: String,

        @SerializedName("image_url")
        val imageUrl: String,

        @SerializedName("width")
        val width: Int,

        @SerializedName("height")
        val height: Int,

        @SerializedName("display_sitename")
        val displaySitename: String,

        @SerializedName("doc_url")
        val docurl: String,

        @SerializedName("datetime")
        val datetime: String
    )
}
