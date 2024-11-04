package com.example.beershop.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.beershop.Models.Compaign.Voucher;
import com.example.beershop.Models.CreditReceived;
import com.example.beershop.Models.CreditsData;
import com.example.beershop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreditsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<CreditsData> creditsModel;
    ArrayList<Integer> codes;

    public CreditsAdapter(Context context, ArrayList<CreditsData> creditsModel, ArrayList<Integer> codes) {
        this.context = context;
        this.creditsModel = creditsModel;
        this.codes = codes;
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

        TextView tv_credit_name, tv_credit_date, t2;
        ImageView qr_code, img;

        private BookViewHolder(View itemView) {
            super(itemView);

            //init views
            qr_code = itemView.findViewById(R.id.qr_code);
            tv_credit_name = itemView.findViewById(R.id.credit_name);
            tv_credit_date = itemView.findViewById(R.id.credit_date);
        }

        private void bind(int pos) {


            CreditsData eventsModel = creditsModel.get(pos);

            Glide.with(context).load(eventsModel.getQrCode()).into(qr_code);
            tv_credit_name.setText(eventsModel.getSurveyName());
            try {
                tv_credit_date.setText("Quantity: " + eventsModel.getRemaining());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
