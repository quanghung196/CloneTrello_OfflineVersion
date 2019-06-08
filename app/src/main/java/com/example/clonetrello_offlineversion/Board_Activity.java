package com.example.clonetrello_offlineversion;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clonetrello_offlineversion.adapter.Board_Adapter;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.model.Board;
import com.example.clonetrello_offlineversion.model.CirclePagerIndicatorDecoration;

import com.example.clonetrello_offlineversion.model.Task;

import java.util.ArrayList;

public class Board_Activity extends AppCompatActivity {

    private RecyclerView recycleBoard;
    private Board board;
    private Task task;
    private Board_Adapter adapter;
    private ArrayList<Board> boards = new ArrayList<>();
    private DatabaseManagement management;

    private Button btnXacNhan, btnHuy;
    private Spinner spnBang;
    private EditText edtNhapBang;
    private TextView tvTenBangMain;

    private String tenBang, taskName;
    private int taskId;
    private int backGroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_);

        Init();
    }

    private void Init() {
        Bundle bundle = getIntent().getBundleExtra("TABLE_TASK");
        taskId = bundle.getInt("idTask");
        int position = bundle.getInt("position", 0);
        management = new DatabaseManagement(this);
        task = management.getTaskById(taskId + "");
        taskName = task.getTaskName();
        backGroundColor = task.getTaskColor();

        tvTenBangMain = findViewById(R.id.tvTenBangMain);
        tvTenBangMain.setText(taskName);

        //database
        boards = management.getAllBoard(taskId + "");

        recycleBoard = findViewById(R.id.recycleBoard);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycleBoard);
        recycleBoard.addItemDecoration(new CirclePagerIndicatorDecoration());

        recycleBoard.setBackgroundColor(backGroundColor);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycleBoard.setLayoutManager(manager);
        setAdapter();
        recycleBoard.scrollToPosition(position);

    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new Board_Adapter(this, boards);
            recycleBoard.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            recycleBoard.scrollToPosition(0);
        }
    }

    //tạo class
    private Board createBoard() {
        board = new Board(taskId, tenBang);
        return board;
    }

    public void InsertBoard() {
        board = createBoard();
        if (board != null) {
            management.insertBoard(board);
            boards.clear();
            boards.addAll(management.getAllBoard(taskId + ""));
            setAdapter();
            recycleBoard.scrollToPosition(adapter.getItemCount() - 1);
            Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void imgBack(View view) {
        Intent intent = new Intent(Board_Activity.this, Main_Activity.class);
        startActivity(intent);
        finish();
    }

    private void showDialogThemBang() {
        final Dialog dialog = new Dialog(Board_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_them_bang);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        edtNhapBang = dialog.findViewById(R.id.edtNhapBang);
        btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnXacNhan.setEnabled(false);
        edtNhapBang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtNhapBang.getText().toString().trim().equals("")) {
                    btnXacNhan.setEnabled(true);
                } else {
                    btnXacNhan.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenBang = edtNhapBang.getText().toString().trim();
                InsertBoard();
                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void imgAdd(View view) {
        showDialogThemBang();
    }
}
