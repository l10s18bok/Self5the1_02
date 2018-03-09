package l10s18bok.naver.com.self5the1_0.MyShopping;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.Analysis.SalesDetailsMember;
import l10s18bok.naver.com.self5the1_0.MyMenu.MemberDTO;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.pageAnimation.RecycleViewDecoration;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;

/**
 * Created by pp on 2018-01-25.
 */

public class Fragment_shopping_bsk extends Fragment {
    private ViewGroup rootView;
    private RealmResults<MemberDTO> results;
    private int payTotal;

    private MyRviewAdapter2 mmAdapter;
    LinearLayout layoutOption;
    TextView sumTextView, option_list, takeOut_list;
    Realm realm;
    RealmListener rListener;
    DateCommon dateCommon = new DateCommon();
    CloseShopping closeShopping;
    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            if(realm.isEmpty()) {
                closeShopping.shoppingClose();
            }
            mmAdapter.update(results);
        }
    };
/*
    public static Fragment_shopping_bsk newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("TabPosition",position);
        Fragment_shopping_bsk fragment = new Fragment_shopping_bsk();
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_shopping_bsk, container, false);
        sumTextView = (TextView) rootView.findViewById(R.id.totalTextView);
        layoutOption = (LinearLayout) rootView.findViewById(R.id.layout_option_list);
        option_list = (TextView) rootView.findViewById(R.id.txt_option_list);
        takeOut_list = (TextView) rootView.findViewById(R.id.txt_takeOut_list);
        Button btnPayment = (Button) rootView.findViewById(R.id.btn_payment2);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rListener.clearConfiguration(10);
                closeShopping.nextFragment(payTotal);

            }
        });
        getBundle();
        buildRecyclerView();
        mmAdapter.setOnItemClickListener(new MyRviewAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onGarbageClick(int position) {
                MemberDTO deleteItem = results.get(position);
                Toast.makeText(getContext(), deleteItem.getMenuname() + " : 을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                rListener.removeItem(deleteItem);
                int total = results.sum("price").intValue();
                sumTextView.setText(String.format("%,d",+total));
//                if(results.size() == 0 ) {
//                    closeShopping.shoppingClose();
//                }
            }

        });

        return rootView;
    }


    public void getBundle () {
        //Bundle extra = getArguments();
        //int tabPosition = extra.getInt("TabPosition");
        layoutOption.setVisibility(View.GONE);
        dateCommon.setTakeOut_list("");
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(10);
        results = rListener.getResults(10);
        payTotal = results.sum("price").intValue();
        sumTextView.setText(String.format("%,d",payTotal));

    }

    public void buildRecyclerView() {
        RecyclerView mRecyclerView = rootView.findViewById(R.id.shopping_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mmAdapter = new MyRviewAdapter2(results,getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mmAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDecoration(getContext()));
        //setUpItemTouchHelper();
    }

    public void showOptionList() {
        layoutOption.setVisibility(View.VISIBLE);
        String temp = "";
        for(int i = 0; i < dateCommon.getOption_list().size(); i++){
            temp = temp + dateCommon.getOption_list().get(i);
        }
        option_list.setText(temp);
        takeOut_list.setText(dateCommon.getTakeOut_list());
    }

    public void clearOptionList() {
        dateCommon.getOption_list().clear();
        dateCommon.setOption_total(0);
        dateCommon.setTakeOut_list("");
        option_list.setText("");
        takeOut_list.setText("");
        layoutOption.setVisibility(View.GONE);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            closeShopping  = (CloseShopping) context;
        }catch (Exception ex) {}

    }
    public interface CloseShopping {
        public void shoppingClose();
        public void nextFragment(int payTotal);
    }
}


