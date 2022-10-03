package com.example.jobplanettest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanettest.R
import com.example.jobplanettest.databinding.ItemCompanyBinding
import com.example.jobplanettest.databinding.ItemHorizonThemeBinding
import com.example.jobplanettest.databinding.ItemReviewBinding
import com.example.jobplanettest.model.CellItem
import com.example.jobplanettest.model.enums.CellTypeEnum
import com.example.jobplanettest.util.extension.convertToAmountNotation
import com.example.jobplanettest.util.extension.substringYYMMDD

class EnterPriseAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var enterpriseList: List<CellItem> = mutableListOf<CellItem>()
    val recruitAdapter by lazy { RecruitAdapter() }
    private val horizonThemePool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CellTypeEnum.CELL_TYPE_COMPANY.ordinal -> {
                CompanyItemViewHolder(ItemCompanyBinding.inflate(layoutInflater, parent, false))
            }
            CellTypeEnum.CELL_TYPE_REVIEW.ordinal -> {
                ReviewItemViewHolder(ItemReviewBinding.inflate(layoutInflater, parent, false))
            }
            CellTypeEnum.CELL_TYPE_HORIZONTAL_THEME.ordinal -> {
                HorizonThemeViewHolder(
                    ItemHorizonThemeBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            else -> {
                CompanyItemViewHolder(ItemCompanyBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CellTypeEnum.CELL_TYPE_COMPANY.ordinal -> {
                (holder as CompanyItemViewHolder).bind(enterpriseList[position])
            }
            CellTypeEnum.CELL_TYPE_REVIEW.ordinal -> {
                (holder as ReviewItemViewHolder).bind(enterpriseList[position])
            }

            CellTypeEnum.CELL_TYPE_HORIZONTAL_THEME.ordinal -> {
                (holder as HorizonThemeViewHolder).bind(enterpriseList[position])
            }
        }
    }

    override fun getItemCount(): Int = enterpriseList.size

    // 리뷰 페이지 ViewHolder
    inner class ReviewItemViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context
        fun bind(data: CellItem) {
            with(binding) {
                Glide.with(context).load(data.logo_path).centerCrop().into(binding.ivCompanyImg)
                tvTitle.text = data.name
                tvRank.text = data.rate_total_avg.toString()
                tvClassification.text = data.industry_name
                binding.tvDate.text = data.update_date.substringYYMMDD()
                binding.tvReviewSummary.text = data.review_summary
                binding.tvPros.text = data.pros
                binding.tvCons.text = data.cons
            }
        }
    }

    // 회사 페이지 ViewHolder
    inner class CompanyItemViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context
        fun bind(data: CellItem) {
            with(binding) {
                Glide.with(context).load(data.logo_path).centerCrop().into(binding.ivCompanyImg)
                tvTitle.text = data.name
                tvRank.text = data.rate_total_avg.toString()
                tvClassification.text = data.industry_name
                binding.tvDate.text = data.update_date.substringYYMMDD()
                binding.tvReviewSummary.text = data.review_summary
                binding.tvRequest.text = data.interview_question
                binding.tvSalary.text =
                    context.getString(R.string.text_salary, data.salary_avg.convertToAmountNotation())
            }
        }
    }

    inner class HorizonThemeViewHolder(private val binding: ItemHorizonThemeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context
        fun bind(data: CellItem) {
            LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
                isAutoMeasureEnabled = true
            }.let { linearLayoutManager ->
                binding.rvRecruit.layoutManager = linearLayoutManager
                binding.rvRecruit.adapter = recruitAdapter
                binding.rvRecruit.setRecycledViewPool(horizonThemePool)
                linearLayoutManager.recycleChildrenOnDetach = true
            }

            recruitAdapter.submitRecruitList(list = data.recommend_recruit)
        }
    }

    fun submitRecruitList(list: List<CellItem>) {
        enterpriseList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return enterpriseList[position].cell_type.ordinal
    }
}
