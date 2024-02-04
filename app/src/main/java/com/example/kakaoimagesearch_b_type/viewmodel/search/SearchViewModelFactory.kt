package com.example.kakaoimagesearch_b_type.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kakaoimagesearch_b_type.data.api.NetworkInterface

//Retrofit_interface 타입의 apiService를 매개변수로 받고, apiService는 나중에 SearchViewModel을 생성할 때 주입
class SearchViewModelFactory(private val apiService: NetworkInterface) : ViewModelProvider.Factory {

    // create 함수는 ViewModelProvider.Factory 인터페이스에서 오버라이드된 함수로 ViewModel 객체를 반환
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(apiService) as T
    }
}