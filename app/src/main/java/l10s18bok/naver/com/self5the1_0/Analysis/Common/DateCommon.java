package l10s18bok.naver.com.self5the1_0.Analysis.Common;

import java.util.ArrayList;
import java.util.List;

import l10s18bok.naver.com.self5the1_0.MyShopping.OptionMember;

/**
 * Created by pp on 2018-02-23.
 */

public final class DateCommon {
    private static int tab_size;
    private static String date;
    private  static int position;
    private static boolean setup;
    private static boolean option_choice;
    private  static boolean chk_takeOut;
    private static List<OptionMember> option_list = new ArrayList<>();
    private static String takeOut_list;
    private static int option_total;

    public DateCommon(){}

    public DateCommon(String date) {
        this.date = date;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSetup() {
        return setup;
    }

    public boolean isOption_choice() {
        return option_choice;
    }

    public void setOption_choice(boolean option_choice) {
        this.option_choice = option_choice;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public boolean isChk_takeOut() {
        return chk_takeOut;
    }

    public static void setChk_takeOut(boolean chk_takeOut) {
        DateCommon.chk_takeOut = chk_takeOut;
    }

    public String getTakeOut_list() {
        return takeOut_list;
    }

    public static void setTakeOut_list(String takeOut_list) {
        DateCommon.takeOut_list = takeOut_list;
    }

    public static List<OptionMember> getOption_list() {
        return option_list;
    }

    public static void setOption_list(List<OptionMember> option_list) {
        DateCommon.option_list = option_list;
    }

    public static int getTab_size() {
        return tab_size;
    }

    public static void setTab_size(int tab_size) {
        DateCommon.tab_size = tab_size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static int getOption_total() {
        return option_total;
    }

    public static void setOption_total(int option_total) {
        DateCommon.option_total = option_total;
    }
}
