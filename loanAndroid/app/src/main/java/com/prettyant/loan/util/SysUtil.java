package com.prettyant.loan.util;

import android.content.Context;

/**
 * Created by chenyu on 2023-05-08
 */
public class SysUtil {

    /**
     * 将dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
