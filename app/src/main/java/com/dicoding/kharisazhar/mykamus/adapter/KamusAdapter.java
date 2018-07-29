package com.dicoding.kharisazhar.mykamus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.kharisazhar.mykamus.DetailActivity;
import com.dicoding.kharisazhar.mykamus.PreloadActivity;
import com.dicoding.kharisazhar.mykamus.R;
import com.dicoding.kharisazhar.mykamus.model.KamusModel;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.MyViewHolder> {

    private ArrayList<KamusModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public KamusAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvWord.setText(mData.get(position).getWord());
        holder.tvTranslate.setText(mData.get(position).getTranslate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("EXTRA_WORD",mData.get(position).getWord());
                intent.putExtra("EXTRA_TRANSLATE",mData.get(position).getTranslate());
                context.startActivity(intent);
            }
        });
    }

    public void addItem(ArrayList<KamusModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWord, tvTranslate;
        public MyViewHolder(View itemView) {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tv_word);
            tvTranslate = itemView.findViewById(R.id.tv_translate);
        }
    }
}
