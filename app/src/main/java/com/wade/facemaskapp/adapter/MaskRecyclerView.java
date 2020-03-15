package com.wade.facemaskapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wade.facemaskapp.R;
import com.wade.facemaskapp.bean.HospitalBean;
import com.wade.facemaskapp.bean.MaskBean;
import com.wade.facemaskapp.view.ActDetail;

import java.util.List;

/**
 * Created by Wade on 2020/3/14.
 */
public class MaskRecyclerView extends RecyclerView.Adapter<MaskRecyclerView.ViewHolder> {
    private List<MaskBean> maskBeanList;
    private List<HospitalBean> hospitalBeanList;
    private Context mContext;

    public MaskRecyclerView(List<MaskBean> maskBeanList,List<HospitalBean> hospitalBeanList){
        this.maskBeanList = maskBeanList;
        this.hospitalBeanList = hospitalBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mask,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTxtName.setText(maskBeanList.get(position).getName());
        holder.mTxtAddress.setText(maskBeanList.get(position).getAddress());
        holder.mTxtAdultMask.setText(maskBeanList.get(position).getAdultmask());
        holder.mTxtChildMask.setText(maskBeanList.get(position).getChildmask());
        holder.mTxtTel.setText(maskBeanList.get(position).getTel());
        holder.mTxtDateTime.setText(maskBeanList.get(position).getDatetime());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<hospitalBeanList.size(); i++){
                    if(hospitalBeanList.get(i).getId().equals(maskBeanList.get(position).getId())){
                        Intent intent = new Intent(mContext, ActDetail.class);
                        intent.putExtra(HospitalBean.HOSPITAL_BEAN,hospitalBeanList.get(i));
                        intent.putExtra(MaskBean.MASK_BEAN,maskBeanList.get(position));
                        mContext.startActivity(intent);
                        break;
                    }

                    if(i==hospitalBeanList.size()-1){
                        new AlertDialog.Builder(mContext)
                                .setTitle(mContext.getString(R.string.dialog_network_title_error))
                                .setMessage(mContext.getString(R.string.dialog_hospital_not_data))
                                .setIcon(R.drawable.ic_error_24dp)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return maskBeanList.size();
    }

    public void updateData(List<MaskBean> list) {
        this.maskBeanList.clear();
        this.maskBeanList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTxtName,mTxtAdultMask,mTxtChildMask,mTxtAddress,mTxtTel,mTxtDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.mCardView);
            mTxtName = itemView.findViewById(R.id.mTxtName);
            mTxtAdultMask = itemView.findViewById(R.id.mTxtAdultMask);
            mTxtChildMask = itemView.findViewById(R.id.mTxtChildMask);
            mTxtAddress = itemView.findViewById(R.id.mTxtAddress);
            mTxtTel = itemView.findViewById(R.id.mTxtTel);
            mTxtDateTime = itemView.findViewById(R.id.mTxtDateTime);
        }
    }
}
