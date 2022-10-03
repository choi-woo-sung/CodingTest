package com.example.jobplanettest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanettest.R
import com.example.jobplanettest.databinding.ItemChipBinding
import com.example.jobplanettest.model.enums.ChipTypeEnum

class ChipAdapter(val callback: (ChipTypeEnum) -> Unit) :
    RecyclerView.Adapter<ChipAdapter.ChipItemViewHolder>() {

    var chipTypeEnum: ChipTypeEnum = ChipTypeEnum.RECRUIT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemChipBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return ChipItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ChipItemViewHolder, position: Int) {
        holder.bindProduct(position)
    }

    inner class ChipItemViewHolder(private val binding: ItemChipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindProduct(position: Int) {
            when (chipTypeEnum) {
                ChipTypeEnum.RECRUIT -> binding.chipGroup.check(R.id.c_recruit)
                ChipTypeEnum.ENTERPRISE -> binding.chipGroup.check(R.id.c_enterprise)
            }

            binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                when (group.checkedChipId) {
                    R.id.c_enterprise -> {
                        callback.invoke(ChipTypeEnum.ENTERPRISE)
                        chipTypeEnum = ChipTypeEnum.ENTERPRISE
                    }
                    R.id.c_recruit -> {
                        callback.invoke(ChipTypeEnum.RECRUIT)
                        chipTypeEnum = ChipTypeEnum.RECRUIT
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_chip
    }

    override fun getItemCount(): Int = 1

    fun setChipType(chipTypeEnum: ChipTypeEnum) {
        this.chipTypeEnum = chipTypeEnum
    }
}
