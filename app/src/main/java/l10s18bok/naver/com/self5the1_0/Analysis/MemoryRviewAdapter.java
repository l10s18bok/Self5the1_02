package l10s18bok.naver.com.self5the1_0.Analysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import l10s18bok.naver.com.self5the1_0.MyShopping.SaveMemberDTO;
import l10s18bok.naver.com.self5the1_0.R;

public class MemoryRviewAdapter extends RecyclerView.Adapter<MemoryRviewAdapter.MyRviewAdapterViewHolder> {

    private RealmResults<SaveMemberDTO> memberDTO;

    private int lastPosition = -1;
    Context context;



    public static class MyRviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNo, txtDate, txtMenuName, txtAmount, txtPrice, txtCardCash, txtRemaks;


        public MyRviewAdapterViewHolder(View itemView) {
            super(itemView);

            txtNo = itemView.findViewById(R.id.txtNo);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtMenuName = itemView.findViewById(R.id.txtMenuname);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtCardCash = itemView.findViewById(R.id.txtCardCash);
            txtRemaks = itemView.findViewById(R.id.txtRemaks);

        }

    }



    public MemoryRviewAdapter(RealmResults<SaveMemberDTO> memberdto, Context context) {
        memberDTO = memberdto;
        this.context = context;
        update(memberDTO);
    }

    public void update(RealmResults<SaveMemberDTO> result) {
        memberDTO = result;
        notifyDataSetChanged();
    }
    @Override
    public MyRviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_view, parent, false);
        MyRviewAdapterViewHolder evh = new MyRviewAdapterViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyRviewAdapterViewHolder holder, int position) {
        SaveMemberDTO currentItem = memberDTO.get(position);
        holder.txtNo.setText(String.valueOf(currentItem.getmNo()));
        holder.txtDate.setText(currentItem.getmData());
        holder.txtMenuName.setText(currentItem.getmMenuname());
        holder.txtAmount.setText(String.valueOf(currentItem.getmAmount()));
        holder.txtPrice.setText(String.valueOf(currentItem.getmPrice()));
        holder.txtCardCash.setText(currentItem.getmCash_card());
        holder.txtRemaks.setText(currentItem.getmRemaks());
    }


    @Override
    public int getItemCount() {
            return memberDTO.size();
    }



}

