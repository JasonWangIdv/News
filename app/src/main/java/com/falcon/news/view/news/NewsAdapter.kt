package com.falcon.news.view.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.falcon.news.R
import com.falcon.news.databinding.ItemDividerBinding
import com.falcon.news.databinding.ItemNewsBinding
import com.falcon.news.entity.BaseItemEntity
import com.falcon.news.entity.DividerItemEntity
import com.falcon.news.entity.NewsItemEntity

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dividerViewType = 1
    private val newsViewType = 2

    private val list = mutableListOf<BaseItemEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (dividerViewType == viewType) {
            val viewBinding = ItemDividerBinding.inflate(inflater, parent, false)
            DividerViewHolder(viewBinding)
        } else {
            val viewBinding = ItemNewsBinding.inflate(inflater, parent, false)
            NewsViewHolder(viewBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if (holder is DividerViewHolder) {
            holder.bind(item as DividerItemEntity)
        } else if (holder is NewsViewHolder) {
            holder.bind(item as NewsItemEntity)
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return if (item is DividerItemEntity) {
            dividerViewType
        } else
            newsViewType
    }

    class DividerViewHolder(private val viewBinding: ItemDividerBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: DividerItemEntity) {
            viewBinding.tvTitle.text = item.title
        }
    }

    class NewsViewHolder(private val viewBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: NewsItemEntity) {
            Glide.with(viewBinding.root).load(item.thumb).error(R.mipmap.ic_launcher).into(viewBinding.ivThumb)
            viewBinding.tvSubscript.text = item.subscript
            viewBinding.tvCreated.text = item.created
            viewBinding.tvTitle.text = item.title
            viewBinding.tvSubTitle.text = item.subTitle
        }
    }

    fun replaceDataAll(data: List<BaseItemEntity>) {
        val oldSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, data.size)
    }

}