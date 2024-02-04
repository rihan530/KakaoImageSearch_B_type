package com.example.kakaoimagesearch_b_type.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//SharedViewModel은 여러 프래그먼트나 액티비티 간에 공유된 데이터를 저장하고 관리하는 데 사용
//예: 사용자가 이미지를 삭제할 경우 해당 이미지의 URL이 이 ViewModel에 저장되며, 이를 구독하고 있는 뷰들은 이 변화를 반영가능
class SharedViewModel : ViewModel() {

    private val _deletedItemUrls = MutableLiveData<Event<List<String>>>()
    val deletedItemUrls: LiveData<Event<List<String>>> get() = _deletedItemUrls

//    사용자가 이미지를 삭제할 때 호출되는 메서드
//    @param url | 삭제된 이미지의 URL
    fun addDeletedItemUrls(url: String) {
        val currentList = _deletedItemUrls.value?.peekContent() ?: emptyList()
        _deletedItemUrls.value = Event(currentList + url)
    }

//    삭제된 아이템 URL 리스트를 초기화
    fun clearDeletedItemUrls() {
        _deletedItemUrls.value = Event(emptyList())
    }
}

//데이터를 한 번만 전달하는데 사용하는 Event 클래스
//이를 통해 데이터가 이미 처리되었는지 확인하고, 이미 처리된 경우 다시 그 데이터를 전달하지 않음
open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

//    내용을 반환 및 재사용 방지
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
//    내용을 반환
//    이미 처리되어도 반환 처리
    fun peekContent(): T = content
}
