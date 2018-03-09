package l10s18bok.naver.com.self5the1_0.Analysis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import l10s18bok.naver.com.self5the1_0.MyShopping.Fragment_CardSign;

/**
 * Created by pp on 2018-01-25.
 */

public class AnalysisPagerAdapter extends FragmentStatePagerAdapter {
    private String tabTitles[] ;

    public void setTabTitles(String tabTitles) {
        this.tabTitles = tabTitles.trim().split("[,]");
        //this.tabTitles = tabTitles;
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    public AnalysisPagerAdapter(FragmentManager fm,String tabTitles) {

        super(fm);
        setTabTitles(tabTitles);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :  return new FragmentSaleDetails();
            case 1 : return new Fragment_PieChart();
            case 2 : return new Fragment_LineChart();
            case 3 : return new Fragment_BarChart();
            default : return new FragmentSaleDetails();
        }

       // return arrFragments[position];
    }
    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }
    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }
}
