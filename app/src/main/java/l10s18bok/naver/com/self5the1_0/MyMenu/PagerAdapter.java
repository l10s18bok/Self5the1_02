package l10s18bok.naver.com.self5the1_0.MyMenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pp on 2018-01-10.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    Context context;

    private String tabTitles[] ;
    private Map<Integer,String> mFragmentTags;
    private FragmentManager mFregmentManager;
    //List<Configuration> configurations = new ArrayList<Configuration>;
    //private List<Fragment> fragments = new ArrayList<Fragment>();
    List<AllTab> registeredFragments = new ArrayList<AllTab>();

    public void setTabTitles(String tabTitles) {
        this.tabTitles = tabTitles.trim().split("[,]");
        //this.tabTitles = tabTitles;
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    public PagerAdapter(FragmentManager fm, String tabTitles, Context context) {
        super(fm);

        this.context = context;

        setTabTitles(tabTitles);


    }

    @Override
    public Fragment getItem(int position) {
        return AllTab.newInstance(position);
    }


    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }


    @Override
    public CharSequence getPageTitle(int position) {
                  return tabTitles[position];
    }

    public AllTab getRegisteredFragment(int position) {
        return (AllTab) registeredFragments.get(position);
    }


}
