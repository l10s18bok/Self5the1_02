package l10s18bok.naver.com.self5the1_0.MyMenu;

import android.net.Uri;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by pp on 2017-11-21.
 */

public class MemberDTO extends RealmObject {
//    @Ignore
//    private int menuNum;
    //private int backgroundColor;
   // @Ignore
    private int index;
    private String image;
    private String menuname;
    private int price;

/*
    public MemberDTO(int index,int backgroundColor, boolean chked, String image, String menuname, String price) {
        this.index = index;
        this.backgroundColor = backgroundColor;
        this.chked = chked;
        this.image = image;
        this.menuname = menuname;
        this.price = price;
    }
*/

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public String getMenuname() {
        return menuname;
    }
    public int getPrice() {
        return price;
    }
}
