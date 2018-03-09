package l10s18bok.naver.com.self5the1_0.Analysis;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Member;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.MyShopping.SaveMemberDTO;
import l10s18bok.naver.com.self5the1_0.R;

public class SalesDetailviewAdapter extends  RecyclerView.Adapter<SalesDetailviewAdapter.MyRviewAdapterViewHolder> {

    private RealmResults<SalesDetailsMember> memberDTO;
    Context context;
    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position,View view);

        void onButtonCancelClick(int position,View view);

        void onButtonOutPutClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class MyRviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNo, txtDate, txtTime, txtViewTotal, txtMenuName, txtPrice, txtAmount;
        public ImageView imageCard;
        public Button btnPayCancel, btnOutPut;
        public CardView zoomCard;
        //public RecyclerView rView;


        public MyRviewAdapterViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            zoomCard = itemView.findViewById(R.id.zoomCardView);
            txtNo = itemView.findViewById(R.id.txtViewNo);
            txtDate = itemView.findViewById(R.id.txtViewDate);
            txtTime = itemView.findViewById(R.id.txtViewTime);
            txtMenuName = itemView.findViewById(R.id.txtViewMenuname);
            txtPrice = itemView.findViewById(R.id.txtViewPrice);
            imageCard = itemView.findViewById(R.id.imageview_card);
            btnPayCancel = itemView.findViewById(R.id.btn_pay_cancel);
            btnOutPut = itemView.findViewById(R.id.btn_outPut);
            txtViewTotal = itemView.findViewById(R.id.txt_view_total);
            txtAmount = itemView.findViewById(R.id.txt_view_amount);
            //rView = itemView.findViewById(R.id.sales_recyclerView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickListener(listener,0);
                }
            });

            btnPayCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,1);
                }
            });

            btnOutPut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,2);
                }
            });


        }
        public void clickListener (OnItemClickListener listener,int paramater) {
            if (listener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {

                    switch (paramater) {
                        case 0 :   listener.onItemClick(position,zoomCard);
                            break;

                        case 1 :   listener.onButtonCancelClick(position,zoomCard);
                            break;

                        case 2 :   listener.onButtonOutPutClick(position);
                            break;

                    }
                }
            }
        }
    }


    public SalesDetailviewAdapter(RealmResults<SalesDetailsMember> memberdto, Context context) {
        memberDTO = memberdto;
        this.context = context;
        update(memberDTO);
    }

    public void update(RealmResults<SalesDetailsMember> result) {
        memberDTO = result;
        notifyDataSetChanged();
    }
    @Override
    public MyRviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_details, parent, false);
        MyRviewAdapterViewHolder evh = new MyRviewAdapterViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyRviewAdapterViewHolder holder, int position) {

        SalesDetailsMember currentItem = memberDTO.get(position);
        //Uri myUri = Uri.parse(currentItem.getmImageResource());
        holder.txtNo.setText(String.valueOf(position+1));
        holder.txtDate.setText(currentItem.getmDate());
        holder.txtTime.setText(currentItem.getmTime());
        holder.txtMenuName.setText("");
        holder.txtPrice.setText("");
        holder.txtAmount.setText("판매갯수 : "+String.valueOf(currentItem.getmAmount()));
        holder.txtViewTotal.setText("합계 : "+String.format("%,d",currentItem.getmTotal()));
        //holder.imageCard.setImageURI(myUri);
       // holder.txtViewTotal.setText(String.valueOf(currentItem.getmTotal()));
        for(int i = 0; i < memberDTO.get(position).getSaleMember().size(); i++) {
            String menuname = memberDTO.get(position).getSaleMember().get(i).getmMenuname();
            String price = String.format("%,d",memberDTO.get(position).getSaleMember().get(i).getmPrice());
            if(i+1 == memberDTO.get(position).getSaleMember().size()) {
                holder.txtMenuName.append(menuname);
                holder.txtPrice.append(price);
            } else {
                holder.txtMenuName.append(menuname + "\n");
                holder.txtPrice.append(price + "\n");
            }
        }
//        String date = currentItem.getmData();
//        String[] arrDate = date.trim().split("-",3);
//        arrDate = arrDate[2].split(":",2);


    }


    @Override
    public int getItemCount() {

        return memberDTO.size();
    }

}

