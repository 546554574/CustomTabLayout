package com.toune.customtablayout.customtablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyTabLayout extends LinearLayout {
    private List<String> titles = new ArrayList<>();
    private Context context;
    private int linePadding;

    public MyTabLayout(Context context) {
        super(context);
        initView(context, null);
    }

    private List<TextView> textViewList = new ArrayList<>();
    private List<TextView> lineViewList = new ArrayList<>();

    private int selectColor;
    private int defaultColor;

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }


    public void setTitles(List<String> titles) {
        removeAllViews();
        this.titles = titles;
        for (int i = 0; i < titles.size(); i++) {
            View inflate = View.inflate(context, R.layout.adapter_my_release_title, null);
            final TextView viewById = inflate.findViewById(R.id.name_tv);
            viewById.setText(titles.get(i));
            final TextView lineTv = inflate.findViewById(R.id.tab_line_tv);
            LayoutParams lp = new LayoutParams(lineTv.getLayoutParams());
            lp.setMargins(0, Uiutil.dp2px(context,linePadding), 0, 0);
            lineTv.setLayoutParams(lp);
            ViewTreeObserver vto = viewById.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    viewById.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    viewById.getHeight();
                    lineTv.setWidth(viewById.getWidth());
                }
            });
            textViewList.add(viewById);
            lineViewList.add(lineTv);
            inflate.setTag(i);
            inflate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick((Integer) v.getTag());
                    }
                    if (isChange) {
                        notifyTitleView((Integer) v.getTag());
                    }
                }
            });
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            addView(inflate, layoutParams);
        }
        notifyTitleView(0);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCurrentItem(int position) {
        notifyTitleView(position);
    }

    boolean isChange = true;

    public void setIsChange(boolean b) {
        isChange = b;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.my_tab_layout);
            String titleStr = mTypedArray.getString(R.styleable.my_tab_layout_titles);
            titles.clear();
            if (titleStr != null && titleStr.split(",").length > 0) {
                for (int i = 0; i < titleStr.split(",").length; i++) {
                    titles.add(titleStr.split(",")[i]);
                }
            }
            selectColor = mTypedArray.getColor(R.styleable.my_tab_layout_select_text_color, Color.WHITE);
            defaultColor = mTypedArray.getColor(R.styleable.my_tab_layout_unselect_text_color,Color.GRAY);
            linePadding = mTypedArray.getInt(R.styleable.my_tab_layout_line_padding, 9);
            mTypedArray.recycle();
            if (titles.size() > 0) {
                setTitles(titles);
            }
        }
    }

    private void notifyTitleView(int key) {
        for (int i = 0; i < textViewList.size(); i++) {
            if (key == i) {
                lineViewList.get(i).setVisibility(VISIBLE);
                if (selectColor != 0) {
                    textViewList.get(i).setTextColor(selectColor);
                } else {
                    textViewList.get(i).setTextColor(Color.BLUE);
                }
            } else {
                lineViewList.get(i).setVisibility(GONE);
                if (defaultColor != 0) {
                    textViewList.get(i).setTextColor(defaultColor);
                } else {
                    textViewList.get(i).setTextColor(Color.GRAY);
                }
            }
        }
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }
}
