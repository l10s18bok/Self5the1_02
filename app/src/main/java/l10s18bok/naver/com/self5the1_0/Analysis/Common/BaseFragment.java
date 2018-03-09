package l10s18bok.naver.com.self5the1_0.Analysis.Common;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by pp on 2018-02-23.
 */

public abstract class BaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public abstract void change();
}
