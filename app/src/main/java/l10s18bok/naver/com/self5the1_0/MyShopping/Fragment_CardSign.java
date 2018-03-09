package l10s18bok.naver.com.self5the1_0.MyShopping;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.Analysis.Common.DateCommon;
import l10s18bok.naver.com.self5the1_0.MyMenu.MemberDTO;
import l10s18bok.naver.com.self5the1_0.R;
import l10s18bok.naver.com.self5the1_0.realm.RealmListener;

public class Fragment_CardSign extends Fragment implements View.OnClickListener {
    private CanvasView canvasView;
    private ViewGroup rootView;
    ImageView gifImageView;
    TextView txtTotal;
    RealmResults<MemberDTO> results;
    RealmListener rListener;
    Realm realm;
    SignFinishCallBack signFinishCallBack;
    DateCommon dateCommon = new DateCommon();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_card_sign, container, false);
        canvasView = (CanvasView)rootView.findViewById(R.id.signCanvas);
        gifImageView = (ImageView)rootView.findViewById(R.id.gifImageView);
        txtTotal = rootView.findViewById(R.id.txtPaymentTotal);
        Glide.with((getActivity()))
                .load(R.drawable.ic_card_new)
                .asGif().placeholder(R.drawable.ic_card_new).crossFade().into(gifImageView);
        initRealm();
        canvasView.clearCanvas();

        Button signOk = rootView.findViewById(R.id.btnCardPayment);
        Button payCancell = rootView.findViewById(R.id.btnCancellation);
        Button reSigned = rootView.findViewById(R.id.btnReSign);

        signOk.setOnClickListener(this);
        payCancell.setOnClickListener(this);
        reSigned.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCardPayment) {
            rListener = new RealmListener();
            rListener.saveDBSales();
            dateCommon.setOption_choice(false);
            dateCommon.setOption_total(0);
           // rListener.clearConfiguration(10);
            signFinishCallBack.signFinish(1);
        }else if(v.getId() == R.id.btnCancellation) {
            rListener.clearConfiguration(10);
            signFinishCallBack.signFinish(2);
        }else if(v.getId() == R.id.btnReSign) {
            canvasView.clearCanvas();
            rListener.clearDB(100);
            signFinishCallBack.signFinish(3);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            signFinishCallBack  = (SignFinishCallBack) context;
        }catch (Exception ex) {}

    }
    public interface SignFinishCallBack {
        void signFinish(int btnClickStatus);
    }

    public void initRealm() {
        rListener = new RealmListener();
        realm = rListener.realmDefaultConfiguration(10);
        results = rListener.getResults(10);
        int payTotal = results.sum("price").intValue();
        txtTotal.setText("합계 : "+String.format("%,d",payTotal));
        //results = rListener.getResults(10);
    }
}