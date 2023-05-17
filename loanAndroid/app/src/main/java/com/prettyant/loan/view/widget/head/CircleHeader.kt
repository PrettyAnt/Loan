package com.prettyant.loan.view.widget.head


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.prettyant.loan.R
import com.prettyant.loan.databinding.PullHeaderViewCircleBinding
import com.prettyant.loan.view.widget.RefreshCircleView
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.internal.InternalClassics

//import kotlinx.android.synthetic.main.pull_header_view_circle.view.*

/**
 * 文字在线客服，聊天消息下拉
 */
class CircleHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    InternalClassics<CircleHeader>(context, attrs, 0), RefreshHeader {
    private var flyLoadingLayout: RelativeLayout
    private var hintCircleText: TextView
    private var refreshCircleView: RefreshCircleView

    init {
//        View.inflate(context, R.layout.pull_header_view_circle, this)

        var binding = PullHeaderViewCircleBinding.inflate(LayoutInflater.from(context))
        this.refreshCircleView = binding.refreshCircleView
        this.hintCircleText = binding.hintCircleText
        this.flyLoadingLayout = binding.flyLoadingLayout
    }

    fun setColor(color: Int) {
        refreshCircleView.setColor(color)
        hintCircleText?.setTextColor(color)
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        refreshCircleView.startTransform()
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        if (success) {
            refreshCircleView.refreshSuccess()
        } else {
            refreshCircleView.stopTransform()
        }
        return mFinishDuration / 3//延迟500毫秒之后再弹回
    }

    override fun hashCode(): Int {
        return super.hashCode()
        //do nothing
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        var text = ""
        when (newState) {
            RefreshState.None -> {
                refreshCircleView.stopTransform()
                text = context.getString(R.string.header_hint_level_down)
            }
            RefreshState.PullDownToRefresh -> text =
                context.getString(R.string.header_hint_level_down)
            RefreshState.Refreshing, RefreshState.RefreshReleased -> text =
                context.getString(R.string.header_hint_refreshing)
            RefreshState.ReleaseToRefresh -> text =
                context.getString(R.string.header_hint_level_release)
            RefreshState.Loading -> text = context.getString(R.string.footer_hint_loading)
            RefreshState.RefreshFinish -> text = context.getString(R.string.header_hint_refreshing)
            else -> {}
        }
        hintCircleText.text = text
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)
        //下拉一定距离，开始动画，效果更好
        if (percent > 0.65f) {
            val newPercent = (percent - 0.65f) / 0.35f
            refreshCircleView.transform(newPercent)
        }
//        refresh_view.transform(percent)
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        //do nothing
    }
}
