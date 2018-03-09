package l10s18bok.naver.com.self5the1_0.Analysis;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.MyMenu.MenuActivity;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.pageAnimation.RecycleViewDecoration;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;

/**
 * Created by pp on 2018-02-05.
 */

public class FragmentSaleDetails extends Fragment {
    private ViewGroup rootView;
    private RealmResults<SalesDetailsMember> realm_results;
    private SalesDetailviewAdapter salesDetailviewAdapter;
    public String currentDateString;
    private DateCommon dateCommon = new DateCommon();
    private int mNo = 0;
    private EventCallBackActivity eventCallBackActivity;
    TextView sumTextView, amountTextView;
    Realm realm;
    RealmListener rListener;
    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            salesDetailviewAdapter.update(realm_results.sort("intDate", Sort.DESCENDING));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_num_statement, container, false);
        sumTextView = (TextView) rootView.findViewById(R.id.txtTotal);
        amountTextView = (TextView) rootView.findViewById(R.id.txtAmont);

        getBundle();
        buildRecyclerView();

        salesDetailviewAdapter.setOnItemClickListener(new SalesDetailviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, final View view) {
                // Toast.makeText(getContext(),"클릭한 아이템 포지션 : "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButtonCancelClick(final int position, final View view) {
                final Animation anZoom = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_anim);
                anZoom.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation anMove = AnimationUtils.loadAnimation(getContext(), R.anim.move);
                        animation.setFillAfter(true);
                        view.startAnimation(anMove);

                        SalesDetailsMember deleteItem = realm_results.get(position);
                        rListener.paymentDeleteItem(deleteItem);
                        totalCall();
                        eventCallBackActivity.deleteButtonEvent();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(anZoom);


                //Toast.makeText(getContext(), "판매취소 준비중입니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButtonOutPutClick(int position) {
                SalesDetailsMember qrItem = realm_results.get(position);
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(qrItem.getmTotal()),BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrDialog(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    public void totalCall() {
        if (realm_results != null) {
            int total = realm_results.sum("mTotal").intValue();
            int amount = realm_results.sum("mAmount").intValue();
            sumTextView.setText(String.format("%,d", total));
            amountTextView.setText(String.format("%,d", amount));
        }

    }


    public void getBundle() {
        currentDateString = dateCommon.getDate();
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(100);
        realm_results = rListener.getDatePayment(currentDateString);
        totalCall();


    }

    public void dailySearch() {
//        rListener = new RealmListener();
//        realm = rListener.realmDefaultConfiguration(100);
        realm_results = rListener.getDatePayment(currentDateString);
        salesDetailviewAdapter.update(realm_results.sort("intDate", Sort.DESCENDING));
        totalCall();
    }

    public void buildRecyclerView() {
        if (realm_results != null) {
            RecyclerView mRecyclerView = rootView.findViewById(R.id.details_recyclerView);
            mRecyclerView.setHasFixedSize(true);
            //       GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),2,GridLayoutManager.VERTICAL,false);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
            salesDetailviewAdapter = new SalesDetailviewAdapter(realm_results, getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(salesDetailviewAdapter);
            mRecyclerView.addItemDecoration(new RecycleViewDecoration(getContext()));
            //setUpItemTouchHelper();
        }

    }

    public void qrDialog(Bitmap bitmap) {

        final Dialog qrDialog = new Dialog(getContext());
        qrDialog.setContentView(R.layout.qr_code);
        ImageView qrBitmap = (ImageView) qrDialog.findViewById(R.id.qr_code_imageView);
        Button dialogOk = (Button) qrDialog.findViewById(R.id.okButton);

        dialogOk.setEnabled(true);
        qrBitmap.setImageBitmap(bitmap);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qrDialog.cancel();
            }
        });

        qrDialog.show();

    }

    public interface EventCallBackActivity {
        void deleteButtonEvent();
    }


    @Override
    public void onStop() {
        super.onStop();
        realm_results.removeChangeListener(realmChangeListener);
        realm.close();
    }

    @Override
    public void onStart() {
        super.onStart();
        realm_results.addChangeListener(realmChangeListener);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            eventCallBackActivity  = (EventCallBackActivity) context;
        }catch (Exception ex) {}
    }
}
