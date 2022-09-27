package com.example.jobplanettest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanettest.databinding.ItemRecruitBinding
import com.example.jobplanettest.model.Recruit

class RecruitAdapter(val context: Context) :
    RecyclerView.Adapter<RecruitAdapter.RecruitItemViewHolder>() {

    lateinit var recruitList: Recruit
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

        fun bindProduct(position: Int) {
            val data = recruitList.recruitItems[position]

            binding.tvTitle.text = data.title
            binding.tvRank.text = data.company.ratings.maxOf { it.rating }.toString()
            Glide.with(context).load(data.imageURL).into(binding.ivCompanyImage)
        }
    }

    override fun getItemCount(): Int = recruitList.recruitItems.size

    fun submitRecruitList(list: Recruit) {
        recruitList = list
    }
}
