package com.prettyant.loan.view.widget.head;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prettyant.loan.R;
import com.prettyant.loan.view.widget.RefreshCircleView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalClassics;


/**
 * @author ChenYu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 2:12 PM  2020/6/11
 * PackageName : com.example.customizetextview.widget
 * describle : 在线客服下拉刷新
 */
public class Header extends InternalClassics<Header> implements RefreshHeader {

    private RefreshCircleView mSquareView;
    private TextView          mHintTextView;
    private int               mRefreshState = dipToPx(getContext(), 98);
    private int dipToPx(Context context, int dpValue) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
        return value;
    }
    //<editor-fold desc="RelativeLayout">
    public Header(Context context) {
        this(context, null);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        View.inflate(context, R.layout.pull_header_view_circle, this);
        final View thisView = this;
        mSquareView = thisView.findViewById(R.id.refresh_circle_view);
        mHintTextView = thisView.findViewById(R.id.hint_circle_text);
    }

    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (success) {
            mSquareView.refreshSuccess();
        } else {
            mSquareView.stopTransform();
        }
        return mFinishDuration / 3;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        String text = "";
        switch (newState) {
            case None:
                mSquareView.stopTransform();
                text = getContext().getString(R.string.header_hint_level_down);
                break;
            case PullDownToRefresh:
                text = getContext().getString(R.string.header_hint_level_down);
                break;
            case Refreshing:
            case RefreshReleased:
                text = getContext().getString(R.string.header_hint_refreshing);
                break;
            case ReleaseToRefresh:
                text = getContext().getString(R.string.header_hint_level_release);
                break;
            case Loading:
                text = getContext().getString(R.string.footer_hint_loading);
                break;
            case RefreshFinish:
                text = getContext().getString(R.string.header_hint_refreshing);
                break;
            default:
                break;
        }
        mHintTextView.setText(text);
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mSquareView.startTransform();
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        //do nothing
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        mSquareView.transform(offset * 1.0f / mRefreshState);
    }
}
