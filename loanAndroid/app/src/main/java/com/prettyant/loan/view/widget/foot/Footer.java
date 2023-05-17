package com.prettyant.loan.view.widget.foot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prettyant.loan.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalClassics;

/**
 * 经典上拉底部组件
 * Created by scwang on 2017/5/28.
 * https://github.com/scwang90/SmartRefreshLayout
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Footer extends InternalClassics<Footer> implements RefreshFooter {

    protected boolean           mNoMoreData = false;
    private   View              mContentView;
    private   TextView          mHintView;
    private   ImageView         ivLoadIcon;
    private   UILoadingView mProgressBar;
    private   FrameLayout       flyLoadingLayout;

    protected String mTextPulling;//"上拉加载更多";
    protected String mTextRelease;//"释放立即加载";
    protected String mTextLoading;//"正在加载...";
    protected String mTextRefreshing;//"正在刷新...";
    protected String mTextFinish;//"加载完成";
    protected String mTextFailed;//"加载失败";
    protected String mTextNothing;//"没有更多数据了";

    //<editor-fold desc="LinearLayout">
    public Footer(Context context) {
        this(context, null);
    }

    public Footer(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        View.inflate(context, R.layout.pull_footer_view, this);
        mContentView = findViewById(R.id.pull_listview_footer_content);
        mProgressBar = findViewById(R.id.pull_listview_footer_progressbar);
        flyLoadingLayout = findViewById(R.id.flyLoadingLayout);
        ivLoadIcon = findViewById(R.id.ivLoadIcon);
        mHintView = findViewById(R.id.pull_listview_footer_hint_textview);
        final View thisView = this;

//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Zxkf_ClassicsFooter);
        mTextPulling = context.getString(R.string.xlistview_footer_hint_normal);
        mTextRelease = context.getString(R.string.xlistview_footer_hint_ready);
        mTextLoading = context.getString(R.string.xlistview_footer_hint_loading);

        mProgressBar.setWithoutImageView();
        mProgressBar.hide();
    }

    //</editor-fold>

    //<editor-fold desc="RefreshFooter">
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        //do nothing
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        mProgressBar.hide();
        if (!mNoMoreData) {
            mHintView.setText(success ? mTextFinish : mTextFailed);
            return mFinishDuration / 3;//延迟500毫秒之后再弹回
        }
        return 0;
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            if (noMoreData) {
                mHintView.setText(mTextNothing);
            } else {
                mHintView.setText(mTextPulling);
            }
        }
        return true;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
//        final View arrowView = mArrowView;
        mProgressBar.setVisibility(INVISIBLE);
        if (!mNoMoreData) {
            switch (newState) {
                case PullUpToLoad:
                    mHintView.setText(mTextPulling);
                    ivLoadIcon.setVisibility(View.GONE);
                    break;
                case Loading:
                    mProgressBar.requestFocus();
                    mProgressBar.show();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mHintView.setText(mTextLoading);
                    break;
                case LoadReleased:
                    mHintView.setText(mTextLoading);
                    break;
                case ReleaseToLoad:
                    mHintView.setText(mTextRelease);
                    break;
                default:
                    break;
            }
        }
    }
    //</editor-fold>

}
