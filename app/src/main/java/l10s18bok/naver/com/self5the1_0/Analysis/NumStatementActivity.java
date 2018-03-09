package l10s18bok.naver.com.self5the1_0.Analysis;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.BaseFragment;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DatePickerFrag;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.SharedPreference;

public class NumStatementActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,FragmentSaleDetails.EventCallBackActivity {
    TabLayout tabs;
    SharedPreference mSharedpref;
    private AnalysisPagerAdapter aPagerAdapter;
    private ViewPager mViewPager;
    private static final String TAB_TITLE = "TAB_TITLE";
    private String tabTitles = "매출조회,잘나가는메뉴,시간대별현황,월판매그래프";
    public int tabPosition;
    private String currentDateString;
    private Toolbar toolbar;
    private  DateCommon dateCommon;
    private final static String SALEDETAILS_TAG = "SALEDETAILS_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_statement);

        toolBarsetup();
        //getDefaultSetup();


    }
    public void todayIs() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDateString = dataFormat.format(date);
    }

    public void toolBarsetup() {
        todayIs();
        dateCommon = new DateCommon(currentDateString);




        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(10.f);
        toolbar.setTitle(currentDateString);
        toolbar.setLogo(R.drawable.ic_calendar);

        tabs = (TabLayout) findViewById(R.id.tabs2);
        aPagerAdapter = new AnalysisPagerAdapter(getSupportFragmentManager(),tabTitles);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(aPagerAdapter);
        mViewPager.setCurrentItem(0);

        tabs.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                tabPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_num_statement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_search) {
            DatePickerFrag datePicker = new DatePickerFrag();
            datePicker.show(getSupportFragmentManager(),"date picker");

        }

        if (id == R.id.action_all) {
            if (tabPosition < 2) {
                dateCommon.setDate("All");
                toolbar.setTitle("전체내역");
                aPagerAdapter.notifyDataSetChanged();
            } else {
                todayIs();
                toolbar.setTitle(currentDateString);
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isClosed()){
            realm.close();
        }

    }

    @Override
    protected  void onResume() {
        super.onResume();
        aPagerAdapter.notifyDataSetChanged();
    }
    public void getDefaultSetup () {
        mSharedpref = new SharedPreference(this);
        tabTitles = mSharedpref.getStringPreferences(TAB_TITLE,tabTitles);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = dataFormat.format(c.getTime());
        dateCommon.setDate(currentDateString);
        toolbar.setTitle(currentDateString);
        aPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteButtonEvent() {
        aPagerAdapter.notifyDataSetChanged();
    }
}