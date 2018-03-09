package l10s18bok.naver.com.self5the1_0.MyMenu;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;
import l10s18bok.naver.com.self5the1_0.pageAnimation.RecycleViewDecoration;

/**
 * Created by pp on 2017-11-22.
 */

public class AllTab extends Fragment {
    private static final String CON_TAB_NUMBER = "configuration_number";
    private RealmResults<MemberDTO> results;
    private RecyclerView mRecyclerView;
    private MyRviewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ViewGroup rootView;
    private int reNameSelect = 0;
    private int tabPosition = 0;
    MenuActivity menuActivity;
    Dialog myDialog;
    EditText dialogEdit;
    Realm realm;
    MenuAcitvityCallBack menuAcitvityCallBack;


    RealmListener rListener;
    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            results = results.sort("index");
            mAdapter.update(results);
        }
    };
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {

        }else {

        }
    }

    public static AllTab newInstance(int position) {
        
        Bundle args = new Bundle();
        args.putInt("TabPosition",position);
        AllTab fragment = new AllTab();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.all_tab_layout, container, false);
        getBundle();
        buildRecyclerView();
        mAdapter.setOnItemClickListener(new MyRviewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                if(!menuActivity.userInterface_setup) {
                        //rListener.clearConfiguration(position);
                    }
            }
            @Override
            public void onAddMenuClick(int position) {
                if(!menuActivity.userInterface_setup) {
                    menuAcitvityCallBack.mCountCallBack();
                    MemberDTO currentItem = results.get(position);
                    rListener.save(currentItem);
                    Snackbar.make(mRecyclerView,"' "+currentItem.getMenuname()+" ' 가 장바구니에 추가되었습니다. " , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    rListener.insertItem(tabPosition);
                    results = results.sort("index");
                    mAdapter.update(results);
                }
            }

            @Override
            public void onDeleteClick(int position) {
                if(menuActivity.userInterface_setup) {
                    MemberDTO deleteItem = results.get(position);
                    rListener.removeItem(deleteItem);
                    results = results.sort("index");
                    mAdapter.update(results);
                    Toast.makeText(getContext(), position + " : 을 삭제했습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onReNameClick(int position) {
                if(menuActivity.userInterface_setup) {
                    reNameSelect = 1;

                    editDialog(results.get(position).getMenuname(), position);
                }
            }

            @Override
            public void onRePriceClick(int position) {
                if(menuActivity.userInterface_setup) {
                    reNameSelect = 2;
                    int intPrice = results.get(position).getPrice();
                    String price = Integer.toString(intPrice);
                    editDialog(price, position);
                }
            }

            @Override
            public void onImageClick(int position) {
                if(menuActivity.userInterface_setup) {
                    reNameSelect = 0;
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setType("image/*");
                    menuActivity.imagePosition = position;
                    //saveFregmentData.setData(position);
                    //intent.putExtra("position",position);
                    getActivity().startActivityForResult(intent, 10);
                }
            }

        });

        return rootView;
    }
    public void getBundle () {
        Bundle extra = getArguments();
        tabPosition = extra.getInt("TabPosition");
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(tabPosition);
        if(realm.isEmpty()) {
            rListener.insertItem(tabPosition);
        }
        results = rListener.getResults(tabPosition);
        results = results.sort("index");

       // Toast.makeText(getContext(),"position : "+getArguments().getInt("TabPosition"), Toast.LENGTH_SHORT).show();
    }



    public void menuImageChang (int tabPosition, int menuPosition, String imageUrl) {

        rListener = new RealmListener();
        rListener.changText(tabPosition,menuPosition,imageUrl,reNameSelect);
    }


    public void buildRecyclerView() {
        mRecyclerView = rootView.findViewById(R.id.my_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
     //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),2,GridLayoutManager.VERTICAL,false);
        mAdapter = new MyRviewAdapter(results,getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDecoration(getContext()));
        //setUpItemTouchHelper();


    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if(menuActivity.userInterface_setup) {
                    int swipedPosition = viewHolder.getAdapterPosition();
                    //MyRviewAdapter adapter = (MyRviewAdapter) mRecyclerView.getAdapter();
                    //rListener.removeItem(swipedPosition);

                }

            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void editDialog(String editHint,final int position) {

        myDialog = new Dialog(getContext());
        myDialog.setContentView(R.layout.dialog_edit);
        dialogEdit = (EditText) myDialog.findViewById(R.id.dialogEdit1);
        Button dialogOk = (Button) myDialog.findViewById(R.id.dialogBtnOK);
        Button dialogCancel = (Button) myDialog.findViewById(R.id.dialogBtnCancel);
        dialogOk.setEnabled(true);
        dialogCancel.setEnabled(true);
        dialogEdit.setHint(editHint);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changeValue = dialogEdit.getText().toString().trim();
                if(!changeValue.isEmpty()) {
                    rListener.changText(menuActivity.tabPosition, position, changeValue, reNameSelect);
//                    results = results.sort("index");
//                    mAdapter.update(results);
                    mAdapter.notifyDataSetChanged();
                }else Toast.makeText(menuActivity, "변경할 내용을 입력하세요", Toast.LENGTH_SHORT).show();
                myDialog.cancel();
            }
        });

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.cancel();
            }
        });
        myDialog.show();

    }
    public interface MenuAcitvityCallBack {
        void mCountCallBack();
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
        menuActivity = null;
    }




    @Override
    public void onAttach(Context context) {
         super.onAttach(context);
         menuActivity = (MenuActivity) getActivity();
        try {
            menuAcitvityCallBack  = (MenuAcitvityCallBack) context;
        }catch (Exception ex) {}
    }

}