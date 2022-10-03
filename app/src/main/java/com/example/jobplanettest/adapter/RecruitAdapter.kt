package com.example.jobplanettest.adapter

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanettest.R
import com.example.jobplanettest.databinding.ItemRecruitBinding
import com.example.jobplanettest.model.RecruitItem
import com.example.jobplanettest.util.extension.convertToAmountNotation
import com.example.jobplanettest.util.extension.dpToPixel

class RecruitAdapter() :
    ListAdapter<RecruitItem, RecruitAdapter.RecruitItemViewHolder>(RecruitDiffUtilCallBack) {

    var recruitList: List<RecruitItem> = mutableListOf<RecruitItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val dataBinding = ItemRecruitBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return RecruitItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: RecruitItemViewHolder, position: Int) {
        holder.bindProduct(position)
    }

    inner class RecruitItemViewHolder(private val binding: ItemRecruitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bindProduct(position: Int) {
            (binding.root.layoutParams as ViewGroup.MarginLayoutParams).apply {
                when {
                    position % 2 == 0 -> {
                        leftMargin = context.dpToPixel(20f).toInt()
                        rightMargin = context.dpToPixel(7f).toInt()
                    }
                    position % 2 == 1 -> {
                        leftMargin = context.dpToPixel(7f).toInt()
                        rightMargin = context.dpToPixel(20f).toInt()
                    }
                }
            }

            val data = recruitList[position]

            binding.tvTitle.text = data.title
            binding.tvRank.text = data.company.ratings.maxOf { it.rating }.toString()
            Glide.with(context).load(data.imageURL).centerCrop().into(binding.ivCompanyImage)
            binding.flWarfare.removeAllViews()
            data.appeal.split(", ").forEach { warfareText ->
                if (warfareText.isNotBlank()) createWarfareChip(warfareText)
            }
            binding.tvReward.text =
                context.getString(R.string.text_reward, data.reward.convertToAmountNotation())
        }

        private fun createWarfareChip(warfareText: String) {
            TextView(ContextThemeWrapper(context, R.style.Widget_walfare_textview)).apply {
                text = warfareText
            }.let { textView ->
                binding.flWarfare.addView(textView)
            }
        }
    }

    override fun getItemCount(): Int = recruitList.size

    fun submitRecruitList(list: List<RecruitItem>) {
        recruitList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_recruit
    }
}

private val RecruitDiffUtilCallBack = object : DiffUtil.ItemCallback<RecruitItem>() {
    override fun areItemsTheSame(oldItem: RecruitItem, newItem: RecruitItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecruitItem, newItem: RecruitItem): Boolean {
        return oldItem.title == newItem.title
    }
}
