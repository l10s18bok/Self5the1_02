package l10s18bok.naver.com.self5the1_0.MyMenu;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;

import io.realm.Realm;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.Analysis.NumStatementActivity;
import l10s18bok.naver.com.self5the1_0.MyShopping.OptionDialog;
import l10s18bok.naver.com.self5the1_0.MyShopping.ShoppingMain;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;
import l10s18bok.naver.com.self5the1_0.realm.SharedPreference;
import l10s18bok.naver.com.self5the1_0.pageAnimation.CubeOutTransformer;


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MyInputDialog.DialogCommunicator,AllTab.MenuAcitvityCallBack {
    AllTab allTab ;
    Toolbar toolbar;
    TabLayout tabs;
    ViewPager viewPager;
    PagerAdapter adapter;
    SharedPreference mSharedpref;
    Realm realm;
    CountdownTask countdownTask = new CountdownTask(this);
    RealmListener realmListener = new RealmListener();
    RealmResults realm_results;
    CounterFab fab;
    DateCommon dateCommon = new DateCommon();
    private long pressedTime;
    private static final String TAB_TITLE = "TAB_TITLE";
    private static final String TAB_LENGTH = "TAB_LENGTH";
    private String tabTitles = "음식,음료,케익,커피";
    public boolean userInterface_setup = false;
    public int tabPosition;
    public int imagePosition;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Intent intent = new Intent(this.getIntent());
        if(requestCode == 10 && resultCode == RESULT_OK) {
            String pathUrl = data.getData().toString();
            allTab.menuImageChang(tabPosition, imagePosition, pathUrl);
        }else if(requestCode == 1 && resultCode == RESULT_OK) {
            Uri pathUrl = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pathUrl,proj,null,null,null);
            int column_index =0;
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            toolbar.setBackgroundResource(column_index);
        }
    }
    public String getPath(Uri uri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestDragAndDropPermissions(new String []{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
*/
        getDefaultSetup();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setTitleTextColor(Color.YELLOW);
        toolbar.setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        allTab = new AllTab();
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabTitles, this);  //1번

        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
        viewPager.setCurrentItem(0);
        //viewPager.setOffscreenPageLimit(0);

        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInterface_setup) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);
                }
            }
        });
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

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


        fab = (CounterFab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userInterface_setup) {
                    if(!realm.isEmpty()) {
                        countdownTask.cancel(true);
                        Intent shopping = new Intent(MenuActivity.this, ShoppingMain.class);
                        startActivity(shopping);
                    }else {
                        Snackbar.make(view, "먼저 메뉴를 주문하시고 눌러주세요.", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }

            }

        });

    }
    public void getBundle(){
        Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("Payment status");
                if(bundle != null) {
                    int paymentInt = bundle.getInt("status");
                    if (paymentInt == 0) {
                        stopAnimation(fab);
                    }
                }
    }

    public void getPayStatus() {

        realm = realmListener.realmDefaultConfiguration(10);
        realm_results = realmListener.getResults(10);
        fab.setCount(realm_results.size());
            if (realm.isEmpty()) {
                stopAnimation(fab);
            }
           else {
                countdownTask.cancel(true);
                countdownTask = new CountdownTask(this);
                countdownTask.execute(30);

            }
    }

    public void stopAnimation(View v) {
        if(countdownTask.getStatus() == AsyncTask.Status.RUNNING) {
            countdownTask.cancel(true);
        }
        realmListener.clearConfiguration(10);
        v.clearAnimation();
        v.animate().cancel();
        fab.setCount(0);

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        if ( pressedTime == 0 ) {
            Toast.makeText(MenuActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(MenuActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            userInterface_setup = !userInterface_setup;
            dateCommon.setSetup(userInterface_setup);
            adapter.notifyDataSetChanged();
            fab.setVisibility(View.VISIBLE);
            if(userInterface_setup) {
                stopAnimation(fab);
                fab.setVisibility(View.GONE);
            }

            return true;
        }else if(id == R.id.menu_delete) {
            showAddTabDialog();
            return true;
        }else if(id == R.id.menu_option){
            showOptionMenuDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent numStatement = new Intent(MenuActivity.this, NumStatementActivity.class);
            startActivity(numStatement);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*
    public void replaceFragment() {
        allTab = new AllTab();
        android.support.v4.app.FragmentManager fm1 = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.setCustomAnimations(R.animator.enter_anim2,R.animator.exit_anim).replace(R.id.container,allTab).commit();

    }

*/

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // realm = realmListener.realmDefaultConfiguration(10);
        if(!realm.isClosed()){
            realm.close();
        }
        try {
            if(countdownTask.getStatus() == AsyncTask.Status.RUNNING) {
                countdownTask.cancel(true);
            }
        }catch (Exception e){}
    }
    @Override
    protected  void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        getPayStatus();
    }

    public void showOptionMenuDialog(){
        OptionDialog optionDialog = new OptionDialog();
        optionDialog.show(getSupportFragmentManager(),"Option Select");
    }
    public void showAddTabDialog() {
        MyInputDialog myInputDialog = new MyInputDialog();
        myInputDialog.setTitle("Create Tap");
        myInputDialog.setMessage("생성할 탭 이름을 순서적으로 입력해주세요.");
        myInputDialog.setHint("예제 : 음식,음료수,케익,기타");
        myInputDialog.show(getFragmentManager(),"Create Tap");
    }

    @Override
    public void onDialogMessage(String message) {

        String tabTitle[] = message.split("[,]");
        if(tabTitle.length <= 4 ) {
            adapter.setTabTitles(message);
            adapter.notifyDataSetChanged();
            mSharedpref.setStringPreferences(TAB_TITLE,message);
            mSharedpref.setIntegerPreferences(TAB_LENGTH,tabTitle.length);
            for (int i = 0; i < tabTitle.length; i++) {
                tabs.getTabAt(i).setText(tabTitle[i]);
            }

            dateCommon.setTab_size(tabTitle.length);
        }else {
            Toast.makeText(this, "4가지 탭항목 보다 클수없습니다..", Toast.LENGTH_SHORT).show();
        }
    }
    public void getDefaultSetup () {
        mSharedpref = new SharedPreference(this);
        tabTitles = mSharedpref.getStringPreferences(TAB_TITLE,tabTitles);

        dateCommon.setTab_size(mSharedpref.getIntegerPreferences(TAB_LENGTH,4));
    }


    private void updateCountDownTimer() {

    }

    @Override
    public void mCountCallBack() {

        fab.increase();
        if(countdownTask.getStatus() != AsyncTask.Status.RUNNING){
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_anim);
            fab.startAnimation(animation);
            countdownTask = new CountdownTask(this);
            countdownTask.execute(30);
        }else {
            countdownTask.cancel(true);
            countdownTask = new CountdownTask(this);
            countdownTask.execute(30);
        }

    }


}
