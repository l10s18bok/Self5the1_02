package l10s18bok.naver.com.self5the1_0.MyShopping;

import android.content.Context;
import android.graphics.Region;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.security.cert.PKIXRevocationChecker;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.Analysis.SalesDetailsMember;
import l10s18bok.naver.com.self5the1_0.MyMenu.MenuActivity;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;
import l10s18bok.naver.com.self5the1_0.realm.SharedPreference;

/**
 * Created by pp on 2018-02-28.
 */

public class OptionDialog extends DialogFragment {
    private RealmResults<OptionMember> results;
    private RecyclerView mRecyclerView;
    private MyOptionAdapter oPtionAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ViewGroup rootView;
    private int reNameSelect = 0;
    private int takeOutPrice;
    private static final String TAKE_OUT_PRICE = "TakeOut_Price";
    private static final String OPTION_ON_OFF = "Option_On_Off";
    private  String oldNumber = "0";
    DateCommon dateCommon = new DateCommon();
    OptionCallback optionCallback;
    Realm realm;

    Switch option_on_off;
    CheckBox not_select;
    CheckBox chked_take_out;
    TextView add_option,takeOut_price;
    EditText input_option_name;
    Button  button_check, button_next, button_not_select;
    LinearLayout set_all_layout,layout_visible;
    ElegantNumberButton numberButton;

    SharedPreference mSharedpref;
    RealmListener rListener;
    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            //results = results.sort("index");
            oPtionAdapter.update(results);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.dialog_list, container, false);
        option_on_off = rootView.findViewById(R.id.sw_option_on_off);
        chked_take_out = rootView.findViewById(R.id.chk_take_out);
        add_option = rootView.findViewById(R.id.txtAddOption);
        takeOut_price = rootView.findViewById(R.id.txt_takeOut_price);
        input_option_name = rootView.findViewById(R.id.edit_reOption);
        button_check = rootView.findViewById(R.id.btn_check);
        button_next = rootView.findViewById(R.id.btn_next);
        button_not_select = rootView.findViewById(R.id.btn_not_select);
        set_all_layout = rootView.findViewById(R.id.set_all_layout);
        layout_visible = rootView.findViewById(R.id.layout_visible);
        numberButton = (ElegantNumberButton) rootView.findViewById(R.id.btn_number_btn);

        option_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mSharedpref.setBooleanPreferences(OPTION_ON_OFF, isChecked);
            }
        });

        add_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rListener.insertOption(9);
                //oPtionAdapter.update(results);
            }
        });
        chked_take_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateCommon.setChk_takeOut(isChecked);
            }
        });
        takeOut_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateCommon.isSetup()) {
                    reNameSelect = 2;
                    takeOutPrice = mSharedpref.getIntegerPreferences(TAKE_OUT_PRICE,0);
                    if(takeOutPrice == 0){
                        input_option_name.setHint("");
                    }else
                        input_option_name.setHint(String.valueOf(takeOutPrice));

                    layout_visible.setVisibility(View.VISIBLE);
                }
            }
        });

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reNameSelect < 2) {
                    rListener.changTextOption(9, dateCommon.getPosition(), input_option_name.getText().toString(), reNameSelect);

                }else {
                    int takeOutPrice = Integer.parseInt( input_option_name.getText().toString());
                    mSharedpref.setIntegerPreferences(TAKE_OUT_PRICE,takeOutPrice);
                    takeOut_price.setText(String.format("%,d",takeOutPrice));
                }
                layout_visible.setVisibility(View.GONE);
                input_option_name.setText("");
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCommon.setOption_choice(true);
                dismiss();
            }
        });
        button_not_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCommon.setOption_choice(false);
                optionCallback.clearOption();
                dismiss();
            }
        });
        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateCommon.isChk_takeOut()) {
                    takeOutPrice = mSharedpref.getIntegerPreferences(TAKE_OUT_PRICE,0);
                    int num = Integer.parseInt(numberButton.getNumber());
                    //String oList = dateCommon.getTakeOut_list();
                    int oTotal = dateCommon.getOption_total();
                    //dateCommon.setTakeOut_list( "포장(Take Out)" + " : " + "+"+ String.format("%,d",takeOutPrice*num ));
                    OptionMember op_member = new OptionMember();
                    op_member.setmOptionName("포장(Take Out)");
                    op_member.setmOptionPrice(takeOutPrice);
                    dateCommon.getOption_list().add(op_member);
                    dateCommon.setOption_total(oTotal + takeOutPrice*num);
                    optionCallback.chkedOption();
                    oldNumber = numberButton.getNumber();

                }else numberButton.setNumber(oldNumber);
            }
        });


        getDefaultSetup();
        getBundle();
        buildRecyclerView();

        oPtionAdapter.setOnItemCliceListener(new MyOptionAdapter.OnItemClickListener() {
            @Override
            public void onCheckClick(int position,boolean ischked) {
                OptionMember opt_list = results.get(position);
                int oTotal = dateCommon.getOption_total(),option_price = opt_list.getmOptionPrice();
                if(ischked) {

                    dateCommon.getOption_list().add(opt_list);
                   // String option_List = opt_list.getmOptionName();
                   // dateCommon.getOption_list().set(position,option_List + " : " + "+" + String.format("%,d", option_price) +"\n");
                    dateCommon.setOption_total(oTotal + option_price);
                    optionCallback.chkedOption();
                }else {
                    for(int i = 0; i < dateCommon.getOption_list().size(); i++){
                        OptionMember list_option = dateCommon.getOption_list().get(i);
                        if(opt_list.equals(list_option)){
                            dateCommon.getOption_list().remove(i);
                        }
                    }

                   // oTotal = dateCommon.getOption_total();
                   // dateCommon.setOption_total(oTotal - option_price );
                    optionCallback.chkedOption();
                }
                ArrayAdapter adapter;
                adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1);

            }

            @Override
            public void onOptionNameClick(int position) {
                if(dateCommon.isSetup()) {
                    reNameSelect = 0;
                    dateCommon.setPosition(position);
                    input_option_name.setHint(results.get(position).getmOptionName());
                    layout_visible.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onOptionPriceClick(int position) {
                if(dateCommon.isSetup()) {
                    reNameSelect = 1;
                    dateCommon.setPosition(position);
                    input_option_name.setHint(String.valueOf(results.get(position).getmOptionPrice()));
                    layout_visible.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }
    public void getBundle () {

        Bundle extra = getArguments();

        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(9);

        results = rListener.getResultsOption(9);

//        for(int i = 0; i < results.size(); i++) {
//            dateCommon.getOption_list().add("");
//        }

        // Toast.makeText(getContext(),"position : "+getArguments().getInt("TabPosition"), Toast.LENGTH_SHORT).show();
    }


    public void buildRecyclerView() {
        mRecyclerView = rootView.findViewById(R.id.my_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),2,GridLayoutManager.VERTICAL,false);
        oPtionAdapter = new MyOptionAdapter(results,getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(oPtionAdapter);
        if(dateCommon.isSetup()) {
            setUpItemTouchHelper();
        }
        //mRecyclerView.addItemDecoration(new RecycleViewDecoration(getContext()));

    }


    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if(dateCommon.isSetup()) {
                    int swipedPosition = viewHolder.getAdapterPosition();
                    OptionMember deleteOption = results.get(swipedPosition);
                    MyOptionAdapter adapter = (MyOptionAdapter) mRecyclerView.getAdapter();
                    rListener.removeOptionItem(deleteOption);

                }

            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void getDefaultSetup () {
        dateCommon.setOption_choice(false);
        mSharedpref = new SharedPreference(getContext());
        takeOutPrice = mSharedpref.getIntegerPreferences(TAKE_OUT_PRICE,0);
        if(takeOutPrice == 0){ takeOut_price.setText("");
        }else takeOut_price.setText(String.format("%,d",takeOutPrice));

        if(dateCommon.isSetup()) {
            add_option.setVisibility(View.VISIBLE);
            option_on_off.setChecked(mSharedpref.getBooleanPreferences(OPTION_ON_OFF,true));

        }else{
            set_all_layout.setVisibility(View.GONE);
            chked_take_out.setChecked(false);
        }
        dateCommon.setChk_takeOut(false);
        layout_visible.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        results.removeChangeListener(realmChangeListener);
        realm.close();

    }

    @Override
    public void onStart() {
        super.onStart();
        results.addChangeListener(realmChangeListener);

    }
    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            optionCallback  = (OptionCallback) context;
        }catch (Exception ex) {}

    }
    public interface OptionCallback {
        public void chkedOption();
        public void clearOption();
    }
}