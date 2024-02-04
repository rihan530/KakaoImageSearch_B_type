package com.example.kakaoimagesearch_b_type.viewmodel.bookmark

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kakaoimagesearch_b_type.model.SearchItemModel
import com.example.kakaoimagesearch_b_type.utils.Utils

class BookmarkViewModel: ViewModel() {
    // 북마크된 아이템들에 대한 MutableLiveData 선언
    private val _bookmarkedItems = MutableLiveData<List<SearchItemModel>>()

    // 외부에서 관찰할 수 있도록 LiveData로 제공
    val bookmarkedItems: LiveData<List<SearchItemModel>> get() = _bookmarkedItems

    // 저장된 북마크 아이템들을 가져오는 메서드
    fun getBookmarkedItems(context: Context) {
        // Utils 클래스를 이용해 저장된 북마크를 가져와서 _bookmarkedItems에 저장
        _bookmarkedItems.value = Utils.getPrefBookmarkItems(context)
    }

    // 특정 아이템을 삭제하는 메서드
    fun deleteItem(context: Context, item: SearchItemModel, position: Int) {
        // Utils 클래스를 이용해 아이템 삭제
        Utils.deletePrefItem(context, item.url)
        Log.d(TAG, "deleteItem_position: $position, url = ${item.url}")

        // 삭제된 아이템 정보를 반영 및 LiveData 갱신
        _bookmarkedItems.value?.let { currentItems ->
            val updateItems = currentItems.toMutableList()
            updateItems.remove(item)
            _bookmarkedItems.value = updateItems
        }
    }
}
