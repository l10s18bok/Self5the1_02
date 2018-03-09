package l10s18bok.naver.com.self5the1_0.Analysis;

import io.realm.RealmObject;

/**
 * Created by pp on 2018-02-10.
 */

public class SalesViewMember extends RealmObject {
    //    @Ignore
//    private int menuNum;
    //private int backgroundColor;

    private String mMenuname;
    private int mPrice;


    public String getmMenuname() {
        return mMenuname;
    }

    public void setmMenuname(String mMenuname) {
        this.mMenuname = mMenuname;
    }


    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

}
