package l10s18bok.naver.com.self5the1_0.realm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.Analysis.SalesDetailsMember;
import l10s18bok.naver.com.self5the1_0.Analysis.SalesViewMember;
import l10s18bok.naver.com.self5the1_0.MyMenu.MemberDTO;
import l10s18bok.naver.com.self5the1_0.MyShopping.OptionMember;


/**
 * Created by pp on 2018-01-16.
 */

public class RealmListener {


    Realm realm;
    RealmResults<MemberDTO> tab1,tab2,tab3,tab4;

    public RealmListener() {
    }


    public void insertItem(int tabP) {
        MemberDTO s = new MemberDTO();
        realm = realmDefaultConfiguration(tabP);
        s.setIndex(getAutoIncrementIndex(s));
        s.setImage("");
        s.setMenuname("MenuName");
        s.setPrice(5000);
        realm.beginTransaction();
        realm.copyToRealm(s);
        realm.commitTransaction();
    }


    public void insertOption(int tabP) {
        OptionMember s = new OptionMember();
        realm = realmDefaultConfiguration(tabP);
        s.setmOptionName("옵션내용을 입력하세요");
        s.setmOptionPrice(1000);
        realm.beginTransaction();
        realm.copyToRealm(s);
        realm.commitTransaction();
    }
    public void changText(int tabPosition, int position, String text, int reNameSelect) {
        RealmResults<MemberDTO> results = getResults(tabPosition);
        results = results.sort("index");

        //realm = Realm.getDefaultInstance();
        MemberDTO currents = results.get(position);
        if (currents == null) {
            return;
        }
        realm.beginTransaction();

        switch (reNameSelect){
            case 0 : currents.setImage(text);
                break;
            case 1 : currents.setMenuname(text);
                break;
            case 2 : currents.setPrice(Integer.parseInt(text));
                break;
        }
        realm.copyToRealm(currents);
        realm.commitTransaction();
    }

    public void changTextOption(int tabPosition, int position, String text, int reNameSelect) {
        RealmResults<OptionMember> results = getResultsOption(tabPosition);

        OptionMember currents = results.get(position);
        if (currents == null) {
            return;
        }
        realm.beginTransaction();

        switch (reNameSelect){
            case 0 : currents.setmOptionName(text);
                break;
            case 1 : currents.setmOptionPrice(Integer.parseInt(text));
                break;
        }
        realm.copyToRealm(currents);
        realm.commitTransaction();
    }

    public void save(final MemberDTO currentItem) {
        Realm realm = realmDefaultConfiguration(10);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(currentItem);
                }
            });
    }

    public void paymentDeleteItem(SalesDetailsMember deleteItem){
        realm.beginTransaction();
        deleteItem.deleteFromRealm();
        realm.commitTransaction();
    }

    public void removeItem(MemberDTO deleteItem) {
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//                deleteItem.deleteFromRealm();
//            }
//        });

        realm.beginTransaction();
        deleteItem.deleteFromRealm();
        realm.commitTransaction();

    }

    public void removeOptionItem(OptionMember deleteOption) {
        realm.beginTransaction();
        deleteOption.deleteFromRealm();
        realm.commitTransaction();
    }


    public Realm realmDefaultConfiguration (int tabPosition) {

        switch (tabPosition) {
            case 0:

                RealmConfiguration tab0 = new RealmConfiguration.Builder()
                        .name("menu_list0")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration0);
                return Realm.getInstance(tab0);
               // break;
            case 1:
                RealmConfiguration tab1 = new RealmConfiguration.Builder()
                        .name("menu_list1")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration1);
                return Realm.getInstance(tab1);
                //break;
            case 2:
                RealmConfiguration tab2 = new RealmConfiguration.Builder()
                        .name("menu_list2")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration2);
                return Realm.getInstance(tab2);
                //break;
            case 3:
                RealmConfiguration tab3 = new RealmConfiguration.Builder()
                        .name("menu_list3")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration3);
                return Realm.getInstance(tab3);
            case 4:
                RealmConfiguration tab4 = new RealmConfiguration.Builder()
                        .name("menu_list4")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration3);
                return Realm.getInstance(tab4);
            case 5:
                RealmConfiguration tab5 = new RealmConfiguration.Builder()
                        .name("menu_list5")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration3);
                return Realm.getInstance(tab5);
                //break;
            case 9:
                RealmConfiguration option = new RealmConfiguration.Builder()
                        .name("option_list")
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(configuration3);
                return Realm.getInstance(option);
            case 10:

                RealmConfiguration shopping = new RealmConfiguration.Builder()
                        .name("shopping_list")
                        .inMemory()
                        .schemaVersion(0)
                        .build();
                //Realm.setDefaultConfiguration(shopping);
                return Realm.getInstance(shopping);
                //break;

            case 100:
                RealmConfiguration sales_list = new RealmConfiguration.Builder()
                        .name("sales_list")
                        .schemaVersion(0)
                        .build();
                return Realm.getInstance(sales_list);
            default: return null;
        }

    }


    public RealmResults<OptionMember> getResultsOption(int current_tab_position) {
        realm = realmDefaultConfiguration(current_tab_position);
        return realm.where(OptionMember.class).findAll();
    }
    public RealmResults<MemberDTO> getResults(int current_tab_position) {
        realm = realmDefaultConfiguration(current_tab_position);
        return realm.where(MemberDTO.class).findAll();
    }

    public void clearConfiguration(int tabPosition) {
        realm = realmDefaultConfiguration(tabPosition);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MemberDTO.class);
            }
        });
    }

    public void clearDB(int tabPosition) {
        realm = realmDefaultConfiguration(tabPosition);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(SalesDetailsMember.class);
            }
        });
    }


    public RealmResults<SalesDetailsMember> getResultsDB() {
        realm = realmDefaultConfiguration(100);
        return realm.where(SalesDetailsMember.class)
                .sort("intDate",Sort.DESCENDING).findAll();
    }


    public void saveDBSales () {
        realm = realmDefaultConfiguration(10);
        RealmResults<MemberDTO> results = realm.where(MemberDTO.class).findAll();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm", Locale.getDefault());
        Date date = new Date();
        String strDate = dataFormat.format(date);
        String reMark = "";
        String card_cash = "C";
        realm = realmDefaultConfiguration(100);
        //SalesDetailsMember sItem = new SalesDetailsMember();
        realm.beginTransaction();       //이 위치 절대 바꾸지 말것! error남
        SalesDetailsMember sItem = realm.createObject(SalesDetailsMember.class);
        for(int i = 0; i < results.size(); i++) {
            MemberDTO currentItem = results.get(i);
            //SalesViewMember sItem2 = new SalesViewMember();
            SalesViewMember sItem2 = realm.createObject(SalesViewMember.class);
            sItem2.setmMenuname(currentItem.getMenuname());
            sItem2.setmPrice(currentItem.getPrice());
            sItem.getSaleMember().add(sItem2);
        }
        String[] arrDate = strDate.trim().split(",",2);
        sItem.setmDate(arrDate[0]);
        sItem.setmTime(arrDate[1]);
        String[] s1 = arrDate[0].split("-",3);
        String[] s2 = arrDate[1].split(":",2);
        String kk = s1[0]+s1[1]+s1[2]+s2[0]+s2[1];
        sItem.setIntDate(Long.parseLong(kk));
        sItem.setmAmount(results.size());
        sItem.setmCash_card(card_cash);
        sItem.setmRemaks(reMark);
        sItem.setmTotal(results.sum("price").intValue());

        realm.copyToRealm(sItem);
        realm.commitTransaction();
        clearConfiguration(10);
    }
    public ArrayList<TimeSalseCounter> getSalesMonth(String strDate) {
        ArrayList<TimeSalseCounter> monthcount = new ArrayList<>();
        realm = realmDefaultConfiguration(100);
        RealmResults<SalesDetailsMember> results = realm.where(SalesDetailsMember.class).sort("intDate",Sort.DESCENDING).findAll();
        String[] c = strDate.split("-",3);
        String cMonth = c[1];
        String cYear = c[0];
        long cT, oldT;
        for(int i = 0; i < results.size(); i++  ){
            SalesDetailsMember sItem = results.get(i);
            c = sItem.getmDate().split("-",3);
            if(cMonth.equals(c[1]) && cYear.equals(c[0])){
                boolean chk = false;
                if(monthcount.size() == 0){
                    monthcount.add(new TimeSalseCounter(sItem.getmDate(),sItem.getmTotal()));
                }else {
                    for(int j = 0; j < monthcount.size(); j++){
                        String[] old = monthcount.get(j).getTime().split("-",3);
                        if(c[2].equals(old[2])){
                            oldT = monthcount.get(j).getAmount();
                            cT = sItem.getmTotal();
                            monthcount.get(j).setAmount(oldT + cT);
                            chk = true;
                        }else chk = false;
                    }
                    if(!chk) monthcount.add(new TimeSalseCounter(sItem.getmDate(),sItem.getmTotal()));
                }
            }
        }

        return monthcount;
    }
    public ArrayList<TimeSalseCounter> getSalesTime(String strDate) {
        realm = realmDefaultConfiguration(100);
        RealmResults<SalesDetailsMember> results = realm.where(SalesDetailsMember.class)
                .sort("intDate",Sort.ASCENDING).equalTo("mDate",strDate).findAll();
        long oldVlue, newVlue;

        ArrayList<TimeSalseCounter> timeCounters = new ArrayList<>();
        for(int i = 0; i < results.size(); i++) {
            SalesDetailsMember currentTime = results.get(i);
            String[] time = currentTime.getmTime().split(":");
            boolean chk = false;
            if(timeCounters.size() == 0) {
                timeCounters.add(new TimeSalseCounter(time[0],currentTime.getSaleMember().size()));
            }else {
                for (int j = 0; j < timeCounters.size(); j++) {
                    String sTime = timeCounters.get(j).getTime();
                    if (sTime.equals(time[0])) {
                        oldVlue = timeCounters.get(j).getAmount();
                        newVlue = currentTime.getSaleMember().size();
                        timeCounters.get(j).setAmount(oldVlue + newVlue);
                        chk = true;
                    } else {
                        chk = false;
                    }
                }
                if(!chk) timeCounters.add(new TimeSalseCounter(time[0], currentTime.getSaleMember().size()));

            }
        }

        return timeCounters;
    }



    public List<String> getmenunameAmountDB () {
        DateCommon dateCommon = new DateCommon();
        int tab_size = dateCommon.getTab_size();

        if(tab_size == 0) {
            return null;
        }else if(tab_size == 1){
            tab1 = getResults(0);
        }else if(tab_size == 2){
            tab1 = getResults(0);
            tab2 = getResults(1);
        }else if(tab_size == 3){
            tab1 = getResults(0);
            tab2 = getResults(1);
            tab3 = getResults(2);
        }else if(tab_size == 4){
            tab1 = getResults(0);
            tab2 = getResults(1);
            tab3 = getResults(2);
            tab4 = getResults(3);
        }
         List<String> menuname = new ArrayList<String>();

        for (int i = 0; i < tab_size; i++) {
            if (i == 0) {
                for (int j = 0; j < tab1.size(); j++) {
                    if(!tab1.get(j).getMenuname().equals("MenuName"))
                    menuname.add(tab1.get(j).getMenuname());
                }
            } else if (i == 1) {
                for (int j = 0; j < tab2.size(); j++) {
                    if(!tab2.get(j).getMenuname().equals("MenuName"))
                    menuname.add(tab2.get(j).getMenuname());
                }
            } else if (i == 2) {
                for (int j = 0; j < tab3.size(); j++) {
                    if(!tab3.get(j).getMenuname().equals("MenuName"))
                    menuname.add(tab3.get(j).getMenuname());
                }
            } else if (i == 3) {
                for (int j = 0; j < tab4.size(); j++) {
                    if(!tab4.get(j).getMenuname().equals("MenuName"))
                    menuname.add(tab4.get(j).getMenuname());
                }
            }
        }
        return menuname;
    }


    public RealmResults getDatePayment(String date) {
        realm = realmDefaultConfiguration(100);
        RealmResults<SalesDetailsMember> results;
        if(date != null) {
            if(date.equals("All")){
                results = realm.where(SalesDetailsMember.class).sort("intDate",Sort.DESCENDING).findAll();
            }else {
                results = realm.where(SalesDetailsMember.class)
                        .sort("intDate",Sort.DESCENDING).equalTo("mDate",date).findAll();
            }
        }else return null;


        return results;
    }
    public List<Integer> getMenuAmountDB (String day) {

//        List<String> menuname = getmenunameAmountDB();
//        List<Integer> mCount = new ArrayList<Integer>();
//        realm = realmDefaultConfiguration(100);
//
//        for(int i = 0; i < menuname.size(); i++) {
//            int sum = 0;
//            RealmResults<SalesDetailsMember> results = realm.where(SalesDetailsMember.class)
//                    .equalTo("saleMember.mMenuname",menuname.get(i)).findAll();
//            sum = results.size();
////            sum = results.sum(menuname.get(i)).intValue();
//            //query.equalTo("saleMember.mMenuname",menuname.get(i));
//            //RealmResults<SalesDetailsMember> results1 = query.findAll();
//        }



        List<String> menuname = getmenunameAmountDB();
        List<Integer> mCount = new ArrayList<Integer>();
        RealmResults<SalesDetailsMember> results = getDatePayment(day);
        SalesDetailsMember item = new SalesDetailsMember();
        SalesViewMember item2 = new SalesViewMember();
        for(int j= 0 ; j < menuname.size(); j++) {
            int count=0;
            for(int i = 0; i < results.size(); i ++) {
                item = results.get(i);

                for(int k = 0; k < item.getSaleMember().size(); k ++) {
                    item2 = item.getSaleMember().get(k);
                    if(menuname.get(j).equals(item2.getmMenuname())) {
                        count += 1;
                    }
                }
            }
            mCount.add(count);
        }

        return mCount;
    }

    // Realm에 Autoincrement가 없기 때문에 수동으로 처리해주는 부분
    public int getAutoIncrementIndex(Object object) {
        int nextIndex;
        Number currentIndex = null;

        // 질의 부분에서 instance를 확인하여 들어가는 부분
        // index에서 가장 큰 값을 구해서, 1을 더해준다.
        if (object instanceof MemberDTO) {
            currentIndex = realm.where(MemberDTO.class).max("index");
        }

        if (currentIndex == null) {
            nextIndex = 0;
        }
        else {
            nextIndex = currentIndex.intValue() + 1;
        }

        return nextIndex;
    }

}
