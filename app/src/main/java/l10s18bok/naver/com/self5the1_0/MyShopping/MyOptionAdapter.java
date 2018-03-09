package l10s18bok.naver.com.self5the1_0.MyShopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.R;

public class MyOptionAdapter extends RecyclerView.Adapter<MyOptionAdapter.MyRviewAdapterViewHolder> {

    private RealmResults<OptionMember> memberDTO;
    private OnItemClickListener mListener;
    Context context;
    public interface OnItemClickListener {
        void onCheckClick(int position,boolean ischk);

        void onOptionNameClick(int position);

        void onOptionPriceClick(int position);
    }

    public void setOnItemCliceListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class MyRviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mOpChked;
        public TextView mOptionName;
        public TextView mOptionPrice;




        public MyRviewAdapterViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mOpChked = itemView.findViewById(R.id.select_option);
            mOptionName = itemView.findViewById(R.id.txt_option_name);
            mOptionPrice = itemView.findViewById(R.id.txt_option_price);

            mOpChked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                clickListener(listener,0);
                }
            });

            mOptionName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,1);
                }
            });

            mOptionPrice.setOnClickListener(new View.OnClickListener() {
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
                    case 0 :

                        listener.onCheckClick(position,mOpChked.isChecked());
                        break;

                    case 1 :   listener.onOptionNameClick(position);
                        break;

                    case 2 :   listener.onOptionPriceClick(position);
                        break;

                }
            }
        }
    }
    }



    public MyOptionAdapter(RealmResults<OptionMember> memberdto, Context context) {
        memberDTO = memberdto;
        this.context = context;
        update(memberDTO);
    }

    public void update(RealmResults result) {
        memberDTO = result;
        notifyDataSetChanged();
    }
    @Override
    public MyRviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_view, parent, false);
        MyRviewAdapterViewHolder evh = new MyRviewAdapterViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyRviewAdapterViewHolder holder, int position) {
        OptionMember currentItem = memberDTO.get(position);

        //holder.backgroundColor.setBackgroundColor(currentItem.getBackgroundColor());

//        holder.mImageView.setImageDrawable(myUri);

        holder.mOptionName.setText(currentItem.getmOptionName());
        holder.mOptionPrice.setText("+ "+String.format("%,d",currentItem.getmOptionPrice()));
     }


    @Override
    public int getItemCount() {
            return memberDTO.size();
    }



}

