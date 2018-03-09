package l10s18bok.naver.com.self5the1_0.MyLogin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import l10s18bok.naver.com.self5the1_0.MyMenu.MenuActivity;
import l10s18bok.naver.com.self5the1_0.R;

public class LoginActivity extends AppCompatActivity {

    EditText eMailEdit;
    EditText passwoardEdit;
    Button btnJoinMembership, btnLogin, btnCancel;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //스마트폰 세로방향 고정
        eMailEdit = (EditText) findViewById(R.id.emailEdit);
        passwoardEdit = (EditText) findViewById(R.id.passwoardEdit);
        btnJoinMembership = (Button) findViewById(R.id.btnJoneMembership);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuActive = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(menuActive);
            }
        });


    }

    @Override
    public void onBackPressed() {

        if ( pressedTime == 0 ) {
            Toast.makeText(LoginActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(LoginActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
