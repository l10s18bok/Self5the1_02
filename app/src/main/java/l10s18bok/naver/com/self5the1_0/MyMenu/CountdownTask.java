package l10s18bok.naver.com.self5the1_0.MyMenu;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import l10s18bok.naver.com.self5the1_0.MyShopping.ShoppingMain;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;

/**
 * Created by pp on 2018-02-07.
 */

public class CountdownTask extends AsyncTask<Integer,String,String> {
        private Activity activity;
        public CountdownTask(Activity activity) {this.activity = activity;}

    @Override
    protected void onPreExecute() {
        //Toast.makeText(this.activity, "Starting Async Task", Toast.LENGTH_SHORT).show();
    }

    @Override
        protected String doInBackground(Integer... integers) {
                int number = integers[0];

                while (number > 0) {
                    if (isCancelled() == true) {
                        return null;
                    }
                    try {
                        number--;
                        this.publishProgress(number + "");
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        Log.d("PELLODEBUG", "Exception while counting down" + e.getMessage());
                    }
                }
                return null;
        }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(Integer.parseInt(values[0]) == 10) {
            Toast.makeText(this.activity,"10초뒤 자동종료 됩니다.!!!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        RealmListener realmListener = new RealmListener();
        realmListener.clearConfiguration(10);
        if(activity.getClass().equals(MenuActivity.class)){
            MenuActivity menuActivity = (MenuActivity) activity;
            menuActivity.stopAnimation(menuActivity.fab);
        }else if(activity.getClass().equals(ShoppingMain.class)){
            ShoppingMain shoppingMain = (ShoppingMain) activity;
            shoppingMain.goMenuActivity();
        }
        Toast.makeText(this.activity,"1분동안 입력이 없어 자동 종료 되었습니다.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {

    }
}
