package com.example.kakaoimagesearch_b_type.ui.search

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kakaoimagesearch_b_type.data.NetworkClient
import com.example.kakaoimagesearch_b_type.databinding.FragmentSearchBinding
import com.example.kakaoimagesearch_b_type.viewmodel.search.SearchViewModel
import com.example.kakaoimagesearch_b_type.viewmodel.search.SearchViewModelFactory
import com.example.kakaoimagesearch_b_type.viewmodel.search.SharedViewModel

class SearchFragment: Fragment() {
    // 바인딩 및 변수 초기화
    private lateinit var binding: FragmentSearchBinding

    private lateinit var mContext: Context

    private lateinit var adapter: SearchAdapter

    private lateinit var gridmanager: StaggeredGridLayoutManager

    val apiServiceInstance = NetworkClient.apiService
    // 검색 viewModel
    private val viewModel: SearchViewModel by viewModels { SearchViewModelFactory(apiServiceInstance) }
//    private val sharedViewModel: SearchViewModel by activityViewModels()
    val sharedViewModel by activityViewModels<SharedViewModel>()

    // 검색 상태 변수 선언
    private var lastQuery = ""
    private var loading = true
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    // 프래그먼트가 액티비티에 붙을 때 호출(onAttach)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    // 프래그먼트 뷰 생성 시 호출(onCreateView)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // 뷰 설정 메서드 호출
        setupViews(inflater, container)
        // 리스너 설정 메서드 호출
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ViewModel 관찰 설정
        observeViewModel()
    }

    // ViewModel에서 데이터 변화를 관찰하는 메서드
    private fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) { items ->
            adapter.items.addAll(items)
            adapter.notifyDataSetChanged()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
            loading = !isLoading
        }

        sharedViewModel.deletedItemUrls.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { urls ->
                urls.forEach { url ->
                    Log.d(TAG, "sharedViewModel.deletedItemUrl url = $url")
                    val targetItem = adapter.items.find { it.url == url }

                    targetItem?.let {
                        it.isLike = false
                        val itemIndex = adapter.items.indexOf(it)
                        adapter.notifyItemChanged(itemIndex)
                    }
                    // 삭제 후 목록에서도 삭제 처리
                    sharedViewModel.clearDeletedItemUrls()
                }
            }
        }
    }

    // 뷰 초기 설정 메서드
    private fun setupViews(inflater: LayoutInflater, container: ViewGroup?) {

        gridmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSearchResult.layoutManager = gridmanager
        binding.fbTop.visibility = View.GONE
        adapter = SearchAdapter(mContext)

        binding.rvSearchResult.adapter = adapter
        binding.rvSearchResult.addOnScrollListener(onScrollListener)
        binding.rvSearchResult.itemAnimator = null
        binding.etSearch.setText("")
        binding.pbSearch.visibility = View.GONE
    }

    // 클릭 및 스크롤 리스너 설정 메서드
    private fun setupListeners() {
        binding.fbTop.setOnClickListener { v: View? ->
            binding.rvSearchResult.smoothScrollToPosition(0)
        }

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.tvSearch.setOnClickListener {
            if (binding.etSearch.text.toString() == "") {
                Toast.makeText(mContext, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                adapter.clearItem()
                lastQuery = binding.etSearch.text.toString()
                loading = false
                viewModel.doSearch(lastQuery, viewModel.curPageCnt)
            }
            imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }
    }

    // 스크롤할 때 다음 리스트의 데이터를 로드
    private var onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                visibleItemCount = gridmanager.childCount
                totalItemCount = gridmanager.itemCount

                val firstVisibleItems = gridmanager.findFirstVisibleItemPositions(null)

                if (firstVisibleItems.isNotEmpty()) {
                    pastVisibleItems = firstVisibleItems[0]
                }

                if (loading && visibleItemCount + pastVisibleItems >= totalItemCount) {
                    loading = false
                    viewModel.curPageCnt += 1
                    viewModel.doSearch(lastQuery, viewModel.curPageCnt)
                }

                if (dy > 0 && binding.fbTop.visibility == View.VISIBLE) {
                    binding.fbTop.hide()
                } else if (dy < 0 && binding.fbTop.visibility != View.VISIBLE) {
                    binding.fbTop.show()
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    binding.fbTop.hide()
                }
            }
        }
}