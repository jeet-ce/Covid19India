package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19india.R;
import com.example.covid19india.StateDetails;

import java.util.ArrayList;

import model.HistoryModel;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
    private static final  String TAG="==== HistoryListAdapter ";
    private ArrayList<HistoryModel> historyModels = new ArrayList<>();
    private Context context;
    private StateViewListner stateViewListner;

    public HistoryListAdapter(Context context) {
        this.context = context;
    }
    public ArrayList<HistoryModel> getHistoryModels() {
        return historyModels;
    }

    public void setHistoryModels(ArrayList<HistoryModel> items,StateViewListner stateViewListner) {
        this.stateViewListner=stateViewListner;
        if (historyModels != null) {
            if (historyModels.size() > 0) {
                historyModels.clear();
            }
            historyModels.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item_holder, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: =================");
        holder.lastUpdateDate.setText(historyModels.get(position).getLastUpdate());
        holder.tvConfirmed.setText(historyModels.get(position).getConfirmed());
        holder.tvRecovered.setText(historyModels.get(position).getRecovered());
        holder.tvDeath.setText(historyModels.get(position).getDeaths());
        holder.tvTConfirmed.setText("[+"+historyModels.get(position).getTconfirmed()+"]");
        holder.tvTRecovered.setText("[+"+historyModels.get(position).getTrecovered()+"]");
        holder.tvTDeath.setText("[+"+historyModels.get(position).getTdeaths()+"]");
        holder.tvListCountry.setText(historyModels.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lastUpdateDate;
        TextView tvConfirmed;
        TextView tvRecovered;
        TextView tvDeath;
        TextView tvTConfirmed;
        TextView tvTRecovered;
        TextView tvTDeath;
        TextView tvListCountry;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            lastUpdateDate = itemView.findViewById(R.id.tvListLastUpdate);
            tvConfirmed = itemView.findViewById(R.id.tvListConfirmed);
            tvRecovered = itemView.findViewById(R.id.tvListRecovered);
            tvDeath = itemView.findViewById(R.id.tvListDeath);
            tvTConfirmed = itemView.findViewById(R.id.tvListTConfirmed);
            tvTRecovered = itemView.findViewById(R.id.tvListTRecovered);
            tvTDeath = itemView.findViewById(R.id.tvListTDeath);
            tvListCountry = itemView.findViewById(R.id.tvListCountry);
            //====================ItemClick=========================
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stateViewListner.onStateItemClick(getAdapterPosition());
                }
            });
        }
    }

}
