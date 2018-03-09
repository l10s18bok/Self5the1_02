package l10s18bok.naver.com.self5the1_0.MyShopping;

import io.realm.RealmObject;

/**
 * Created by pp on 2018-02-10.
 */

public class OptionMember extends RealmObject{

    private String mOptionName;
    private int mOptionPrice;

    public String getmOptionName() {
        return mOptionName;
    }

    public void setmOptionName(String mOptionName) {
        this.mOptionName = mOptionName;
    }

    public int getmOptionPrice() {
        return mOptionPrice;
    }

    public void setmOptionPrice(int mOptionPrice) {
        this.mOptionPrice = mOptionPrice;
    }

}
