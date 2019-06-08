package com.example.clonetrello_offlineversion.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clonetrello_offlineversion.R;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.model.Work;

import java.util.ArrayList;

public class Work_Adapter extends RecyclerView.Adapter<Work_Adapter.ViewHolder> {
    private ArrayList<Work> works;
    private Context context;

    public Work_Adapter(ArrayList<Work> works, Context context) {
        this.works = works;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_work, viewGroup, false);
        Work_Adapter.ViewHolder viewHolder = new Work_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Work work = works.get(position);
        holder.tvWorkID.setText(work.getWorksID() + "");
        holder.edtWorkName.setText(work.getWorksName());

        holder.tvWorkID.setVisibility(View.GONE);

        holder.edtWorkName.setFocusable(false);
        holder.edtWorkName.setCursorVisible(false);
        holder.edtWorkName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.edtWorkName.setFocusableInTouchMode(true);
                holder.edtWorkName.setCursorVisible(true);
                return false;
            }
        });

        holder.management = new DatabaseManagement(context);

        if (work.getWorkDoneYet() == 0) {
            holder.cbDoneYet.setChecked(false);
            holder.edtWorkName.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            holder.edtWorkName.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else {
            holder.cbDoneYet.setChecked(true);
            holder.edtWorkName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.edtWorkName.setTextColor(context.getResources().getColor(R.color.colorGray));
        }

        holder.cbDoneYet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    work.setWorkDoneYet(1);
                    holder.edtWorkName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.edtWorkName.setTextColor(context.getResources().getColor(R.color.colorGray));
                } else {
                    work.setWorkDoneYet(0);
                    holder.edtWorkName.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                    holder.edtWorkName.setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
                holder.management.updateWork(work, work.getWorksID());
            }
        });

        holder.imgConfirmWorkName.setVisibility(View.GONE);
        holder.edtWorkName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.imgConfirmWorkName.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.imgConfirmWorkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.edtWorkName.getText().toString().trim())) {
                    holder.edtWorkName.setCursorVisible(false);
                    work.setWorksName(holder.edtWorkName.getText().toString().trim());
                    holder.management.updateWork(work, work.getWorksID());
                    holder.imgConfirmWorkName.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtWorkName.getWindowToken(), 0);
                }else{
                    holder.edtWorkName.setText(work.getWorksName());
                    Toast.makeText(context, "Cần nhập ít nhất 1 kí tự", Toast.LENGTH_SHORT).show();
                    holder.edtWorkName.setCursorVisible(false);
                    holder.imgConfirmWorkName.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtWorkName.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cbDoneYet;
        public EditText edtWorkName;
        public ImageView imgConfirmWorkName;
        public TextView tvWorkID;

        public DatabaseManagement management;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbDoneYet = itemView.findViewById(R.id.cbDoneYet);
            edtWorkName = itemView.findViewById(R.id.edtWorkName);
            imgConfirmWorkName = itemView.findViewById(R.id.imgConfirmWorkName);
            tvWorkID = itemView.findViewById(R.id.tvWorkID);
        }
    }
}
