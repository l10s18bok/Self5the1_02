package l10s18bok.naver.com.self5the1_0.MyShopping;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import io.realm.Realm;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.MyMenu.CountdownTask;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.SharedPreference;

public class ShoppingMain extends AppCompatActivity implements Fragment_shopping_bsk.CloseShopping,Fragment_CardSign.SignFinishCallBack,OptionDialog.OptionCallback {
    DateCommon dateCommon = new DateCommon();
    SharedPreference mSharedpref ;
    Fragment_shopping_bsk shopping_bsk;
    Fragment_CardSign cardSign;
    CountdownTask countdownTask = new CountdownTask(this);

    private static final String OPTION_ON_OFF = "Option_On_Off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_main);
        mSharedpref = new SharedPreference(this);

        countdownTask.execute(30);

        shopping_bsk = new Fragment_shopping_bsk();
        cardSign = new Fragment_CardSign();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_anim2, R.animator.exit_anim).replace(R.id.main_frag,shopping_bsk).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm realm = Realm.getDefaultInstance();
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
 //       mSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void shoppingClose() {
        goMenuActivity();
    }

    @Override
    public void nextFragment(int payTotal) {
        if (mSharedpref.getBooleanPreferences(OPTION_ON_OFF, true)) {
            if(!dateCommon.isOption_choice()) {
                showOptionMenuDialog();
            }else{
                initCountdown();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_anim2, R.animator.exit_anim).replace(R.id.main_frag, cardSign).commit();

                //Toast.makeText(this, "필수 '옵션항목'을 선택해주세요", Toast.LENGTH_SHORT).show();
            }
        } else {
            initCountdown();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_anim2, R.animator.exit_anim).replace(R.id.main_frag, cardSign).commit();

        }
    }

    @Override
    public void signFinish(int btnClickStatus) {
        if(btnClickStatus == 1) {
            goMenuActivity();
        }else if(btnClickStatus == 2){
            goMenuActivity();
        }else {
            initCountdown();
        }
    }

    public void initCountdown() {
        if(countdownTask.getStatus() == AsyncTask.Status.RUNNING)  {
            countdownTask.cancel(true);
            countdownTask = new CountdownTask(this);
            countdownTask.execute(30);
        }
    }
    public void goMenuActivity() {
        if(countdownTask.getStatus() == AsyncTask.Status.RUNNING) countdownTask.cancel(true);
        finish();
    }


    public void showOptionMenuDialog(){
        OptionDialog optionDialog = new OptionDialog();
        optionDialog.show(getSupportFragmentManager(),"Option Select");
    }

    @Override
    public void chkedOption() {
        initCountdown();
        shopping_bsk.showOptionList();
        //getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_anim2, R.animator.exit_anim).replace(R.id.main_frag,shopping_bsk).commit();
    }

    @Override
    public void clearOption() {
        initCountdown();
        shopping_bsk.clearOptionList();
    }

/*
    public void goMenuActivity () {
        Intent menuActivity = new Intent(ShoppingMain.this, MenuActivity.class);
        menuActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Bundle bundle = new Bundle();
        bundle.putInt("status",0);
        menuActivity.putExtra("Payment status",bundle);

        setResult(RESULT_OK,menuActivity);
        startActivity(menuActivity);
        finish();
    }
*/
}
