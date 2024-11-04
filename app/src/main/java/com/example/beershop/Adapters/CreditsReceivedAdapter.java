package com.example.beershop.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beershop.Models.CreditReceived;
import com.example.beershop.Models.CreditsModel;
import com.example.beershop.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreditsReceivedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<CreditReceived> creditsModel;

    public CreditsReceivedAdapter(Context context, ArrayList<CreditReceived> creditsModel) {
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

        TextView tv_credit_name,tv_credit_date,t2;
        ImageView qr_code,img;

        private BookViewHolder(View itemView) {
            super(itemView);

            //init views
            qr_code = itemView.findViewById(R.id.qr_code);
            tv_credit_name    = itemView.findViewById(R.id.credit_name);
            tv_credit_date      = itemView.findViewById(R.id.credit_date);
        }

        private void bind(int pos) {

            CreditReceived eventsModel = creditsModel.get(pos);
            Glide.with(context).load(eventsModel.getQrCode()).into(qr_code);
            tv_credit_name.setText(eventsModel.getSurveyName());
            tv_credit_date  .setText("Quantity: "+eventsModel.getQuantity());
        }
    }
}
