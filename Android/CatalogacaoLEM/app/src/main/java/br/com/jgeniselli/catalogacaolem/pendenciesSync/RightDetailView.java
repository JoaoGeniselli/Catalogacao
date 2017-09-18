package br.com.jgeniselli.catalogacaolem.pendenciesSync;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 16/09/17.
 */

public class RightDetailView extends LinearLayout {

    private String title;
    private String detail;

    private TextView titleView;
    private TextView detailView;

    public RightDetailView(Context context) {
        super(context);
    }

    public RightDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RightDetailView,
                0, 0);

        try {
            setTitle(a.getString(R.styleable.RightDetailView_title));
            setDetail(a.getString(R.styleable.RightDetailView_detail));
        } finally {
            a.recycle();
        }
    }

    public RightDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RightDetailView,
                0, 0);

        try {
            setTitle(a.getString(R.styleable.RightDetailView_title));
            setDetail(a.getString(R.styleable.RightDetailView_detail));
        } finally {
            a.recycle();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RightDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_right_detail, this);
        this.titleView = (TextView) findViewById(R.id.titleView);
        this.detailView = (TextView) findViewById(R.id.detailView);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (this.titleView != null) {
            this.titleView.setText(title);
        }
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
        if (this.detailView != null) {
            this.detailView.setText(detail);
        }
    }
}
