package com.limox.jesus.ejerciciocuadernoclase.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.ejerciciocuadernoclase.Pojo.Incidence;
import com.limox.jesus.ejerciciocuadernoclase.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usuario on 13/02/17.
 */
public class IncidencesRecyclerAdapter extends RecyclerView.Adapter<IncidencesRecyclerAdapter.MyViewHolder> {
    private ArrayList<Incidence> incidenceList;
    private LayoutInflater inflater;

    public IncidencesRecyclerAdapter(Context context) {
        this.incidenceList = new ArrayList<Incidence>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_incidencia, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Incidence feed = incidenceList.get(position);
        //Pass the values of feeds object to Views
        holder.name.setText(feed.getName());
        holder.actitud.setText(feed.getActitud());
        holder.falta.setText(feed.getFaltas());
        holder.trabajo.setText(feed.getTrabajo());
        holder.observaciones.setText(feed.getObservaciones());
    }

    @Override
    public int getItemCount() {
        return incidenceList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txvAlumno)
        TextView name;
        @BindView(R.id.txvActitud)
        TextView actitud;
        @BindView(R.id.txvFalta)
        TextView falta;
        @BindView(R.id.txvTrabajo)
        TextView trabajo;
        @BindView(R.id.txvObservacioes)
        TextView observaciones;

        //private TextView name, actitud;
        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //name = (TextView) itemView.findViewById(R.id.name_view);
            //actitud = (TextView) itemView.findViewById(R.id.link_view);
        }
    }

    public void set(ArrayList<Incidence> incidences) {
        //set all the sites
        this.incidenceList.clear();
        this.incidenceList.addAll(incidences);
        notifyDataSetChanged();
    }

    public Incidence getAt(int position) {
        //get the site in the position
        return this.incidenceList.get(position);
    }

    public void add(Incidence incidence) {
        //add a incidence
        this.incidenceList.add(incidence);
        notifyItemInserted(incidenceList.size() - 1);

    }

    public void modifyAt(Incidence incidence, int position) {
        //modify a incidence in the position
        this.incidenceList.set(position, incidence);
        notifyItemChanged(position);
    }

    public void removeAt(int position) {
        //delete a site in the position
        this.incidenceList.remove(position);
        notifyItemRemoved(position);
    }
}