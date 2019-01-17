package com.toune.customtablayout.customtablayout;

import android.content.Context;

public class Uiutil {
    public static int dp2px(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

}
