package com.example.jobplanettest.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.jobplanettest.util.extension.dpToPixel

class RecruitItemDecoration(

    private val orientation: Int = GridLayoutManager.VERTICAL
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (orientation == GridLayoutManager.VERTICAL) {
                // 포지션이 0 일때, space count 20
                when {
                    parent.getChildAdapterPosition(view) == 0 -> {}
                    parent.getChildAdapterPosition(view) % 2 == 1 -> {
                        left = view.context.dpToPixel(20f).toInt()
                        right = view.context.dpToPixel(7f).toInt()
                    }
                    parent.getChildAdapterPosition(view) % 2 == 0 -> {
                        left = view.context.dpToPixel(7f).toInt()
                        right = view.context.dpToPixel(20f).toInt()
                    }
                    else -> {}
                }
            }
        }
    }
}
