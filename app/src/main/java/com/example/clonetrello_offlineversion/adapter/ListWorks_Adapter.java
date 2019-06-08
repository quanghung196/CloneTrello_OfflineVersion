package com.example.clonetrello_offlineversion.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clonetrello_offlineversion.R;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.model.ListWorks;
import com.example.clonetrello_offlineversion.model.Work;

import java.util.ArrayList;

public class ListWorks_Adapter extends RecyclerView.Adapter<ListWorks_Adapter.ViewHolder> {

    private ArrayList<ListWorks> arrListWorks;
    private Context context;

    public ListWorks_Adapter(ArrayList<ListWorks> arrListWorks, Context context) {
        this.arrListWorks = arrListWorks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_works, viewGroup, false);
        ListWorks_Adapter.ViewHolder viewHolder = new ListWorks_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ListWorks listWorks = arrListWorks.get(position);
        holder.tvListWorksID.setText(listWorks.getListWorksID() + "");
        holder.edtListWorksName.setText(listWorks.getListWorksName());

        listWorks.setClickCount(holder.clickCount);

        holder.management = new DatabaseManagement(context);

        holder.tvListWorksID.setVisibility(View.GONE);
        holder.imgConfirmListWorksName.setVisibility(View.GONE);

        holder.edtListWorksName.setFocusable(false);
        holder.edtListWorksName.setCursorVisible(false);

        holder.cardID = listWorks.getCardID();
        holder.listWorksID = listWorks.getListWorksID();

        holder.work = new Work();
        if (holder.works == null) {
            holder.works = new ArrayList<>();
        } else {
            holder.works.clear();
        }
        holder.works.addAll(holder.management.getAllWorks(holder.listWorksID + ""));
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recycleWorks.setLayoutManager(manager);
        holder.setAdapter();
        holder.workAdapter.notifyDataSetChanged();

        holder.edtListWorksName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.edtListWorksName.setFocusableInTouchMode(true);
                holder.edtListWorksName.setCursorVisible(true);
                return false;
            }
        });

        holder.edtListWorksName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.imgConfirmListWorksName.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.imgConfirmListWorksName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.edtListWorksName.getText().toString().trim())) {
                    listWorks.setListWorksName(holder.edtListWorksName.getText().toString().trim());
                    holder.imgConfirmListWorksName.setVisibility(View.GONE);
                    holder.edtListWorksName.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtListWorksName.getWindowToken(), 0);
                    holder.management.updateListWorks(listWorks, listWorks.getListWorksID());
                } else {
                    Toast.makeText(context, "Cần nhập ít nhất 1 kí tự", Toast.LENGTH_SHORT).show();
                    holder.edtListWorksName.setText(listWorks.getListWorksName());
                    holder.imgConfirmListWorksName.setVisibility(View.GONE);
                    holder.edtListWorksName.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtListWorksName.getWindowToken(), 0);
                }
            }
        });

        holder.imgConfirmWorkName.setVisibility(View.GONE);
        holder.edtAddWork.setFocusable(false);
        holder.edtAddWork.setCursorVisible(false);
        holder.edtAddWork.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.edtAddWork.setFocusableInTouchMode(true);
                holder.edtAddWork.setCursorVisible(true);
                return false;
            }
        });

        holder.edtAddWork.addTextChangedListener(new TextWatcher() {
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
                if (!TextUtils.isEmpty(holder.edtAddWork.getText().toString().trim())) {
                    holder.InsertWork();
                    holder.recycleWorks.scrollToPosition(holder.workAdapter.getItemCount() - 1);
                    holder.edtAddWork.setCursorVisible(false);
                    holder.imgConfirmWorkName.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtAddWork.getWindowToken(), 0);
                } else {
                    Toast.makeText(context, "Cần nhập ít nhất 1 kí tự", Toast.LENGTH_SHORT).show();
                    holder.edtAddWork.setCursorVisible(false);
                    holder.imgConfirmWorkName.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtAddWork.getWindowToken(), 0);
                }
            }
        });

        holder.imgExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listWorks.setClickCount(holder.clickCount += 1);
                if (listWorks.getClickCount() % 2 != 0) {
                    holder.imgExpand.setImageResource(R.drawable.icon_expand_arrow_24);
                    holder.recycleWorks.setVisibility(View.GONE);
                    holder.edtAddWork.setVisibility(View.GONE);
                } else {
                    holder.imgExpand.setImageResource(R.drawable.icon_collapse_arrow_24);
                    holder.recycleWorks.setVisibility(View.VISIBLE);
                    holder.edtAddWork.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrListWorks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText edtListWorksName, edtAddWork;
        public TextView tvListWorksID;
        public RecyclerView recycleWorks;
        public ImageView imgConfirmListWorksName, imgExpand, imgConfirmWorkName;

        public DatabaseManagement management;
        public Work work;
        public ArrayList<Work> works;
        public Work_Adapter workAdapter;

        public int cardID, listWorksID;

        public int clickCount = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtListWorksName = itemView.findViewById(R.id.edtListWorksName);
            edtAddWork = itemView.findViewById(R.id.edtAddWork);
            tvListWorksID = itemView.findViewById(R.id.tvListWorksID);
            imgConfirmListWorksName = itemView.findViewById(R.id.imgConfirmListWorksName);
            imgExpand = itemView.findViewById(R.id.imgExpand);
            imgConfirmWorkName = itemView.findViewById(R.id.imgConfirmWorkName);
            recycleWorks = itemView.findViewById(R.id.recycleWorks);
        }

        //tạo class
        private Work createWork() {
            work = new Work(cardID, listWorksID, 0, edtAddWork.getText().toString().trim());
            return work;
        }

        private void InsertWork() {
            work = createWork();
            if (work != null) {
                management.insertWork(work);
                works.clear();
                works.addAll(management.getAllWorks(listWorksID + ""));
                setAdapter();
                Log.d("Resutl", listWorksID + ", " + edtAddWork.getText().toString());
                edtAddWork.setText("");
            }
        }

        private void setAdapter() {
            if (workAdapter == null) {
                workAdapter = new Work_Adapter(works, context);
                recycleWorks.setAdapter(workAdapter);
            } else {
                workAdapter.notifyDataSetChanged();
            }
        }
    }
}
