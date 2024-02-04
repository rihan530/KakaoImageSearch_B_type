package com.example.kakaoimagesearch_b_type.ui.bookmark

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kakaoimagesearch_b_type.databinding.FragmentBookmarkBinding
import com.example.kakaoimagesearch_b_type.model.SearchItemModel
import com.example.kakaoimagesearch_b_type.viewmodel.bookmark.BookmarkViewModel
import com.example.kakaoimagesearch_b_type.viewmodel.search.SharedViewModel

class BookmarkFragment: Fragment() {
    // 바인딩 및 변수 초기화
    private lateinit var mContext: Context

    val sharedViewModel by activityViewModels<SharedViewModel>()
    // 북마크 viewModel
    private val viewModel: BookmarkViewModel by viewModels()

    // 바인딩과 어댑터
    private var binding: FragmentBookmarkBinding? = null

    private lateinit var adapter: BookmarkAdapter

    // 프래그먼트가 액티비티에 붙을 때 호출(onAttach)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    // 프래그먼트 뷰 생성 시 호출(onCreateView)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 어댑터 초기화
        adapter = BookmarkAdapter(mContext)

        // 바인딩 설정
        binding = FragmentBookmarkBinding.inflate(inflater, container, false).apply {
            rvBookmark.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvBookmark.adapter = adapter
            pbBookmark.visibility = View.GONE
        }

        // 북마크에 추가한 아이템 로드
        viewModel.getBookmarkedItems(mContext)

        // 북마크 리스트를 관찰하여 UI 갱신
        viewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarks ->
            adapter.items = bookmarks.toMutableList()
            adapter.notifyDataSetChanged()
        }

        // 아이템 클릭 이벤트 처리
        adapter.setOnItemClickListener(object : BookmarkAdapter.OnItemClickListener {
            override fun onItemClick(item: SearchItemModel, position: Int) {
                viewModel.deleteItem(mContext, item, position)
                Log.d(TAG, "onItemClick deleteItem position: $position")
                sharedViewModel.addDeletedItemUrls(item.url)
            }
        })
        return binding?.root
    }

    // 프래그먼트 종료 시 바인딩 제거(onDestroyView)
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}