package l10s18bok.naver.com.self5the1_0.MyShopping;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.MyMenu.MemberDTO;
import l10s18bok.naver.com.self5the1_0.R;

public class MyRviewAdapter2 extends RecyclerView.Adapter<MyRviewAdapter2.MyRviewAdapterViewHolder> {

    private RealmResults<MemberDTO> memberDTO;
    private OnItemClickListener mListener;
    private int lastPosition = -1;
    Context context;
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onGarbageClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class MyRviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView menuName;
        public TextView price;
        public FloatingActionButton btnGarbage;




        public MyRviewAdapterViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.cart_image);
            menuName = itemView.findViewById(R.id.txtMenuname);
            price = itemView.findViewById(R.id.txtPrice);
            btnGarbage = itemView.findViewById(R.id.btnDeleteMemu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                clickListener(listener,0);
                }
            });

            btnGarbage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,1);
                }
            });



        }
    public void clickListener (OnItemClickListener listener,int paramater) {
        if (listener != null) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {

                switch (paramater) {
                    case 0 :   listener.onItemClick(position);
                    break;

                    case 1 :   listener.onGarbageClick(position);
                        break;

                }
            }
        }
    }
    }



    public MyRviewAdapter2(RealmResults<MemberDTO> memberdto, Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view, parent, false);
        MyRviewAdapterViewHolder evh = new MyRviewAdapterViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyRviewAdapterViewHolder holder, int position) {
        MemberDTO currentItem = memberDTO.get(position);
        Uri myUri = Uri.parse(currentItem.getImage());
        //holder.backgroundColor.setBackgroundColor(currentItem.getBackgroundColor());

//        holder.mImageView.setImageDrawable(myUri);
        holder.menuName.setText(currentItem.getMenuname());
        holder.price.setText(String.format("%,d",currentItem.getPrice()));
        Glide.with(context).load(myUri).override(40,40).error(R.drawable.ic_sync_disabled)
                .placeholder(R.drawable.korea).animate(R.animator.picture_anim).into(holder.mImageView);
    //        Glide.with(context).load(myUri).override(40,40).animate(R.animator.picture_anim).into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
            return memberDTO.size();
    }



}

