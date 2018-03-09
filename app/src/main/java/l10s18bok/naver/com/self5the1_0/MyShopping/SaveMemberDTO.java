package l10s18bok.naver.com.self5the1_0.MyShopping;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by pp on 2017-11-21.
 */

public class SaveMemberDTO extends RealmObject {
//    @Ignore
//    private int menuNum;
    //private int backgroundColor;
    private int mNo;
    private String mData;
    private String mMenuname;
    private int mAmount;
    private int mPrice;
    private String mCash_card;
    private String mRemaks;


    public int getmNo() {
        return mNo;
    }

    public void setmNo(int mNo) {
        this.mNo = mNo;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public String getmMenuname() {
        return mMenuname;
    }

    public void setmMenuname(String mMenuname) {
        this.mMenuname = mMenuname;
    }

    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }

    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getmCash_card() {
        return mCash_card;
    }

    public void setmCash_card(String mCash_card) {
        this.mCash_card = mCash_card;
    }

    public String getmRemaks() {
        return mRemaks;
    }

    public void setmRemaks(String mRemaks) {
        this.mRemaks = mRemaks;
    }
}
