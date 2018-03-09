package l10s18bok.naver.com.self5the1_0.Analysis;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by pp on 2017-11-21.
 */

public class SalesDetailsMember extends RealmObject {
//    @Ignore
//    private int menuNum;
    //private int backgroundColor;
   // @Ignore

    private long intDate;
    private int mNumberTable,mAmount,mOptionTotal, mTotal;
    private String mDate;
    private String mTime;
    private String mImageResource;
    private String mCash_card;
    private String mRemaks;
    private String mOptionList;
    private RealmList<SalesViewMember> saleMember;

    public int getmOptionTotal() {
        return mOptionTotal;
    }

    public void setmOptionTotal(int mOptionTotal) {
        this.mOptionTotal = mOptionTotal;
    }

    public String getmOptionList() {
        return mOptionList;
    }

    public void setmOptionList(String mOptionList) {
        this.mOptionList = mOptionList;
    }

    public int getmNumberTable() {
        return mNumberTable;
    }

    public void setmNumberTable(int mNumberTable) {
        this.mNumberTable = mNumberTable;
    }

    public long getIntDate() {
        return intDate;
    }

    public void setIntDate(long intDate) {
        this.intDate = intDate;
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

    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mData) {
        this.mDate = mData;
    }

    public String getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    public int getmTotal() {
        return mTotal;
    }

    public void setmTotal(int mTotal) {
        this.mTotal = mTotal;
    }

    public RealmList<SalesViewMember> getSaleMember() {
        return saleMember;
    }

    public void setSaleMember(RealmList<SalesViewMember> saleMember) {
        this.saleMember = saleMember;
    }

}
