package br.edu.ifce.engcomp.francis.diversidade.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.HourNucleus;

/**
 * Created by Joamila on 06/05/2016.
 */
public class HoursRecyclerViewAdapter extends RecyclerView.Adapter<HoursRecyclerViewAdapter.ViewHolder>{
    private ArrayList<HourNucleus> dataSource;
    private Context context;

    public HoursRecyclerViewAdapter(Context context, ArrayList<HourNucleus> dataSource){
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public HoursRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_hours_nucleus,parent,false);
        Log.i("HOUR-TESTE", "ONCREATE");
        return new ViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(HoursRecyclerViewAdapter.ViewHolder holder, int position) {
        HourNucleus hour = this.dataSource.get(position);

        holder.dayHourTextView.setText(hour.getWeekDays() + " - " + hour.getHour());
        Log.i("HOUR-TESTE", "BINDVIEW");
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView dayHourTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.dayHourTextView = (TextView) itemView.findViewById(R.id.day_nucleus_hour);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
