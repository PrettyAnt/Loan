package com.prettyant.loan.view.widget.foot;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author jiaBF
 * @describe 加载动画
 * @date 2016年2月1日
 */
public class UILoadingView extends FrameLayout {
    private ImageView imageView;


    public UILoadingView(Context context) {
        super(context);
        init();
    }

    public UILoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        imageView = new ImageView(getContext());
        addView(imageView);
    }

    public void setWithImageView() {
        if (imageView != null) {
//            BitmapDisplay.getInstance().displayAssert(imageView, "loading_with_tip.gif");
            String url = "file:///android_asset/loading_with_tip.gif";
            Glide.with(getContext())
//                    .asGif()
                    .load(url)
                    .into(imageView);
        }
    }

    public void setWithoutImageView() {
        if (imageView != null) {
//            BitmapDisplay.getInstance().displayAssert(imageView, "loading_without_tip.gif");
            String url = "file:///android_asset/loading.gif";
            Glide.with(getContext())
//                    .asGif()
                    .load(url)
                    .into(imageView);
        }
    }

    public void hide(){
        if (imageView != null) {
            imageView.setVisibility(GONE);
            Glide.with(imageView.getContext()).clear(imageView);
        }
    }

    public void show(){
        if (imageView != null) {
            setWithoutImageView();
            imageView.setVisibility(VISIBLE);
        }
    }
}
