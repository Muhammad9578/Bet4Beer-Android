package com.example.beershop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beershop.Models.CreditsModel;
import com.example.beershop.R;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<CreditsModel> creditsModel;

    public OrderHistoryAdapter(Context context, ArrayList<CreditsModel> creditsModel) {
        this.context = context;
        this.creditsModel = creditsModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_credits, parent, false);
        return new BookViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookViewHolder holder1 = (BookViewHolder) holder;

        holder1.bind(position);

    }

    @Override
    public int getItemCount() {
        return creditsModel.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tv_credit_name, tv_credit_date, tv_credit_time;

        private BookViewHolder(View itemView) {
            super(itemView);

            //init views
            tv_credit_name = itemView.findViewById(R.id.credit_name);
            tv_credit_date = itemView.findViewById(R.id.credit_date);
        }

        private void bind(int pos) {

            CreditsModel eventsModel = creditsModel.get(pos);
            tv_credit_name.setText(eventsModel.getCredit_name());
            tv_credit_date.setText(eventsModel.getCredit_date());
            tv_credit_time.setText(eventsModel.getCredit_time());
        }
    }
}