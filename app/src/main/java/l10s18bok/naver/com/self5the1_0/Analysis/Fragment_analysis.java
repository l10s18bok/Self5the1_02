package l10s18bok.naver.com.self5the1_0.Analysis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.MyShopping.SaveMemberDTO;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;

/**
 * Created by pp on 2018-02-05.
 */

public class Fragment_analysis  extends Fragment {
    private ViewGroup rootView;
    private RealmResults<SaveMemberDTO> results;
    private int payTotal;

    private MemoryRviewAdapter m3Adapter;
    TextView sumTextView;
    Realm realm;
    RealmListener rListener;
 //   Fragment_shopping_bsk.CloseShopping closeShopping;
    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            m3Adapter.update(results);
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
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_num_statement, container, false);
        sumTextView = (TextView) rootView.findViewById(R.id.txtTotal);

        getBundle();
        buildRecyclerView();


        return rootView;
    }


    public void getBundle () {
        //Bundle extra = getArguments();
        //int tabPosition = extra.getInt("TabPosition");
        //rListener = new RealmListener();
        //realm = rListener.realmDefaultConfiguration(100);
        //results = rListener.getResultsDB();
        //Toast.makeText(getContext(), ""+results, Toast.LENGTH_SHORT).show();
        //payTotal = results.sum("price").intValue();
 //       sumTextView.setText(""+payTotal);

    }

    public void buildRecyclerView() {
        RecyclerView mRecyclerView = rootView.findViewById(R.id.details_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        m3Adapter = new MemoryRviewAdapter(results,getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(m3Adapter);
        //mRecyclerView.addItemDecoration(new RecycleViewDecoration(getContext()));
        //setUpItemTouchHelper();
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
//        try {
//            closeShopping  = (Fragment_shopping_bsk.CloseShopping) context;
//        }catch (Exception ex) {}

    }
//    public interface CloseShopping {
//        public void shoppingClose();
//        public void nextFragment(int tabPosition,int payTotal);
//    }
}

