package com.limox.jesus.ejerciciocuadernoclase.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;
import com.limox.jesus.ejerciciocuadernoclase.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usuario on 13/02/17.
 */
public class StudentsRecyclerAdapter extends RecyclerView.Adapter<StudentsRecyclerAdapter.MyViewHolder> {
    private ArrayList<Student> studentsList;
    private LayoutInflater inflater;

    public StudentsRecyclerAdapter(Context context) {
        this.studentsList = new ArrayList<Student>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.singleitem, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Student feed = studentsList.get(position);
        //Pass the values of feeds object to Views
        holder.name.setText(feed.getName());
        holder.surname.setText(feed.getSurname());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_view)
        TextView name;
        @BindView(R.id.surname_view)
        TextView surname;

        //private TextView name, actitud;
        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //name = (TextView) itemView.findViewById(R.id.name_view);
            //actitud = (TextView) itemView.findViewById(R.id.link_view);
        }
    }

    public void set(ArrayList<Student> sitesList) {
        //set all the sites
        this.studentsList.clear();
        this.studentsList.addAll(sitesList);
        notifyDataSetChanged();
    }

    public Student getAt(int position) {
        //get the site in the position
        return this.studentsList.get(position);
    }

    public void add(Student site) {
        //add a site
        this.studentsList.add(site);
        notifyItemInserted(studentsList.size() - 1);

    }

    public void modifyAt(Student site, int position) {
        //modify a site in the position
        this.studentsList.set(position, site);
        notifyItemChanged(position);
    }

    public void removeAt(int position) {
        //delete a site in the position
        this.studentsList.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<Student> get(){
        return this.studentsList;
    }
}