package l10s18bok.naver.com.self5the1_0.MyMenu;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.R;

public class MyRviewAdapter extends RecyclerView.Adapter<MyRviewAdapter.MyRviewAdapterViewHolder> {

 //   public ArrayList<MemberItem> memberDTO = new ArrayList<>();
    Realm realm;
    private RealmResults<MemberDTO> memberDTO;
    private OnItemClickListener mListener;
    private int lastPosition = -1;
    Context context;
    static MenuActivity mActivity;


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAddMenuClick(int position);

        void onDeleteClick(int position);

        void onReNameClick(int position);

        void onRePriceClick(int position);

        void onImageClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class MyRviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout backgroundColor;
        public CheckBox checkBox;
        public CircleImageView mImageView;
        public TextView menuName;
        public TextView price;
        public FloatingActionButton btnAddMenu;




        public MyRviewAdapterViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checked);
            mImageView = itemView.findViewById(R.id.profile_image);
            menuName = itemView.findViewById(R.id.txtMenuname);
            price = itemView.findViewById(R.id.txtPrice);
            btnAddMenu = itemView.findViewById(R.id.btnAddMemu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                clickListener(listener,0);
                }
            });

            btnAddMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,1);
                }
            });

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,2);
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,3);
                }
            });

            menuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener(listener,4);
                }
            });

            price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    clickListener(listener,5);
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

                    case 1 :   listener.onAddMenuClick(position);
                        break;

                    case 2 :   listener.onImageClick(position);
                        break;

                    case 3 :   listener.onDeleteClick(position);
                        break;

                    case 4 :   listener.onReNameClick(position);
                        break;

                    case 5 :   listener.onRePriceClick(position);
                        break;
                }
            }
        }
    }
    }



    public MyRviewAdapter(RealmResults<MemberDTO> memberdto,Context context) {
        memberDTO = memberdto;
        this.context = context;
        mActivity = (MenuActivity) context;



        //update(memberDTO);
    }

    public void update(RealmResults result) {
        memberDTO = result;
        notifyDataSetChanged();
    }
    @Override
    public MyRviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        MyRviewAdapterViewHolder evh = new MyRviewAdapterViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyRviewAdapterViewHolder holder, int position) {
        MemberDTO currentItem = memberDTO.get(position);
        Uri myUri = Uri.parse(currentItem.getImage());
       // holder.checkBox.setChecked(currentItem.isChked());
        holder.menuName.setText(currentItem.getMenuname());
        holder.price.setText(String.format("%,d",currentItem.getPrice()));
//        Glide.with(context).load(myUri).override(50,50).error(R.drawable.ic_sync_disabled)
//                .placeholder(R.drawable.korea).animate(R.animator.picture_anim).into(holder.mImageView);
        Glide.with(context).load(myUri).error(R.drawable.ic_sync_disabled)
                .placeholder(R.drawable.korea).animate(R.animator.picture_anim).into(holder.mImageView);

        if(mActivity.userInterface_setup) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
            return memberDTO.size();
    }



}

