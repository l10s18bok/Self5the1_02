package l10s18bok.naver.com.self5the1_0.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by pp on 2017-12-26.
 */

public class RealmApplication extends Application {

    @Override

    public void onCreate() {
        super.onCreate();
        Realm.init(this);

/*
        RealmConfiguration configuration0 = new RealmConfiguration.Builder()
                .name("menu_list0")
                .schemaVersion(0)
                .build();


        RealmConfiguration configuration1 = new RealmConfiguration.Builder()
                .name("menu_list1")
                .schemaVersion(0)
                .build();

       RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                .name("menu_list2")
                .schemaVersion(0)
                .build();

        RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                .name("menu_list3")
                .schemaVersion(0)
                .build();

        RealmConfiguration shopping = new RealmConfiguration.Builder()
                .name("shopping_list")
                .inMemory()
                .schemaVersion(0)
                .build();
*/
    }

}