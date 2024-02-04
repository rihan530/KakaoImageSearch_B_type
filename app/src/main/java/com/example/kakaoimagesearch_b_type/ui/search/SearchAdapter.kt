package com.example.kakaoimagesearch_b_type.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kakaoimagesearch_b_type.Constants
import com.example.kakaoimagesearch_b_type.databinding.SearchItemBinding
import com.example.kakaoimagesearch_b_type.model.SearchItemModel
import com.example.kakaoimagesearch_b_type.utils.Utils.addPrefItem
import com.example.kakaoimagesearch_b_type.utils.Utils.deletePrefItem
import com.example.kakaoimagesearch_b_type.utils.Utils.getDateFromTimestampWithFormat

class SearchAdapter(private val mContext: Context): RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    // API 검색한 아이템 목록 정의
    var items = ArrayList<SearchItemModel>()

    // 아이템 목록 삭제 후 갱신
    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // ViewHolder 아이템 정의
    inner class ItemViewHolder(binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var iv_thum_image: ImageView = binding.ivThumImage
        var iv_like: ImageView = binding.ivLike
        var tv_title: TextView = binding.tvTitle
        var tv_datetime: TextView = binding.tvDatetime
        var cl_thumb_item: ConstraintLayout = binding.clThumbItem

        init {
            iv_like.visibility = View.GONE
            iv_thum_image.setOnClickListener(this)
            cl_thumb_item.setOnClickListener(this)
        }

        // 아이템 클릭 이벤트 처리
        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            if (!item.isLike) {
                addPrefItem(mContext, item)
                item.isLike = true
            } else {
                deletePrefItem(mContext, item.url)
                item.isLike = false
            }

            // 아이템 클릭 후 Like 값에 따른 이미지 visibility 갱신
            notifyItemChanged(position)
        }
    }

    // ViewHolder 바인딩 처리
    override fun onBindViewHolder(holder: SearchAdapter.ItemViewHolder, position: Int) {
        Glide.with(mContext)
            .load(items[position].url)
            .into(holder.iv_thum_image)

        val type = if (items[position].type == Constants.SEARCH_TYPE_VIDEO) "[Video] " else "[Image] "
        holder.iv_like.visibility = if (items[position].isLike) View.VISIBLE else View.INVISIBLE
        holder.tv_title.text = type + items[position].title
        holder.tv_datetime.text =
            getDateFromTimestampWithFormat(
                items[position].dateTime,
                "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
                "yyyy-MM-dd HH:mm:ss"
            )
    }

    // 리스트에 출력할 아이템 수 반환
    override fun getItemCount(): Int {
        return items.size
    }
}