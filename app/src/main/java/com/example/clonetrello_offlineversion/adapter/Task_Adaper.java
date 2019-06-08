package com.example.clonetrello_offlineversion.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clonetrello_offlineversion.Board_Activity;
import com.example.clonetrello_offlineversion.R;
import com.example.clonetrello_offlineversion.model.Task;

import java.util.ArrayList;

public class Task_Adaper extends RecyclerView.Adapter<Task_Adaper.ViewHolder> {

    private ArrayList<Task> tasks;

    public Task_Adaper(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        Task_Adaper.ViewHolder viewHolder = new Task_Adaper.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Task task = tasks.get(position);
        viewHolder.imgMau.setBackgroundColor(task.getTaskColor());
        viewHolder.tvTenCV.setText(task.getTaskName());
        viewHolder.tvSTTCV.setText(task.getTaskId() + "");

        viewHolder.linearTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idTask = Integer.parseInt(task.getTaskId() + "");
                Intent intent = new Intent(v.getContext(), Board_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idTask", idTask);
                intent.putExtra("TABLE_TASK", bundle);
                v.getContext().startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMau;
        private TextView tvTenCV;
        private TextView tvSTTCV;
        private LinearLayout linearTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearTask = itemView.findViewById(R.id.linearTask);
            imgMau = itemView.findViewById(R.id.imgMau);
            tvTenCV = itemView.findViewById(R.id.tvTenCV);
            tvSTTCV = itemView.findViewById(R.id.tvSTTCV);
        }
    }

    public void filterList(ArrayList<Task> filteredList) {
        tasks = filteredList;
        notifyDataSetChanged();
    }
}
