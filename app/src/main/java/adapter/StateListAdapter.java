package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19india.R;

import java.util.ArrayList;

import model.HistoryModel;
import model.StateDataModel;
/**
 * Created by Jeet Patel on 12/04/2020.
 */
public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.ViewHolder> {
    private static final  String TAG="==== StateListAdapter ";
    private ArrayList<StateDataModel> stateDataModels = new ArrayList<>();
    private Context context;
    private StateViewListner stateViewListner;

    public StateListAdapter(Context context) {
        this.context = context;
    }
    public ArrayList<StateDataModel> getStatedataModels() { return stateDataModels;    }

    public void setHistoryModels(ArrayList<StateDataModel> items) {
        if (stateDataModels != null) {
            if (stateDataModels.size() > 0) {
                stateDataModels.clear();
            }
            stateDataModels.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StateListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.district_item_holder, parent, false);
        return new StateListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateListAdapter.ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: =================");
        holder.tvConfirmed.setText(stateDataModels.get(position).getConfirmed());
        holder.tvListCountry.setText(stateDataModels.get(position).getDistrict());
    }

    @Override
    public int getItemCount() {
        return stateDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvConfirmed;
        TextView tvListCountry;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConfirmed = itemView.findViewById(R.id.tvListConfirmed);
            tvListCountry = itemView.findViewById(R.id.tvListCountry);
        }
    }

}
