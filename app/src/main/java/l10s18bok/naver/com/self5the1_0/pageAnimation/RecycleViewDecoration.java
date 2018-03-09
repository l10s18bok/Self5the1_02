package l10s18bok.naver.com.self5the1_0.pageAnimation;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import l10s18bok.naver.com.self5the1_0.R;

/**
 * Created by pp on 2017-12-10.
 */


public class RecycleViewDecoration extends RecyclerView.ItemDecoration {
    Context context;

    public RecycleViewDecoration(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super .getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildAdapterPosition(view) + 1;
        if (index % 3 == 0)
            outRect.set(20, 20, 20, 60);
        else
            outRect.set(20, 20, 20, 20);

        view.setBackgroundColor(0xFFECE9E9);
        ViewCompat.setElevation(view, 20.0f);
    }

    @Override          // 리싸이클뷰 센터 회사 로고처리
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super .onDrawOver(c, parent, state);

        int width = parent.getWidth();
        int height = parent.getHeight();

        Drawable dr = ResourcesCompat.getDrawable(context.getResources(), R.drawable.android, null);
        int drWidth = dr.getIntrinsicWidth();
        int drHeight = dr.getIntrinsicHeight();

        int left = width / 2 - drWidth / 2;
        int top = height / 2 - drHeight / 2;

//        c.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.android), left, top, null);

    }




}
