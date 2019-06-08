package com.example.clonetrello_offlineversion;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.clonetrello_offlineversion.adapter.Task_Adaper;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.model.Task;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;

public class Main_Activity extends AppCompatActivity {

    //main
    private Toolbar toolbar;
    private RecyclerView recyclerDSBang;
    private EditText edtSearch;
    private LinearLayout linearMain;

    int colorPicker = -16711681;

    //dialog
    private EditText edtNhapCV;
    private Button btnXacNhan, btnHuy;
    private LinearLayout linearPhongNen;
    private Button btnColorPicker;

    //database
    private DatabaseManagement management;
    private Task task;
    private ArrayList<Task> tasks;
    private Task_Adaper adapter;

    private String tenCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        setUpRecycler();
    }

    private void Init() {
        linearMain = findViewById(R.id.linearMain);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //database
        management = new DatabaseManagement(this);
        tasks = management.getAllTask();

        //edittext
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.setCursorVisible(false);
        edtSearch.setFocusable(false);

        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtSearch.setCursorVisible(true);
                edtSearch.setFocusableInTouchMode(true);
                return false;
            }
        });

        linearMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().toString().trim().equals("")) {
                    edtSearch.setCursorVisible(false);
                } else {
                    edtSearch.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void setUpRecycler() {
        //xử lí lvDSLop
        recyclerDSBang = findViewById(R.id.recyclerDSBang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerDSBang.setLayoutManager(layoutManager);
        setAdapter();
    }

    private void filter(String text) {
        ArrayList<Task> filterList = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getTaskName().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                filterList.add(task);
            }
        }
        adapter.filterList(filterList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                showDialogThemLop();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void deleteTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_Activity.this);
        builder.setTitle("Xóa task");
        builder.setMessage("Bạn có muốn xóa task này??");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                management.deleteTask(idTask);
                tasks.clear();
                tasks.addAll(management.getAllTask());
                setAdapter();
                Toast.makeText(Main_Activity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

    private void setAdapter() {
        if (adapter == null) {
            adapter = new Task_Adaper(tasks);
            recyclerDSBang.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            recyclerDSBang.scrollToPosition(adapter.getItemCount() - 1);
        }
    }


    //tạo class
    private Task createTask() {
        task = new Task(tenCV, colorPicker);

        return task;
    }

    //insert class
    private void insertTask() {
        task = createTask();
        if (task != null) {
            management.insertTask(task);
            tasks.clear();
            tasks.addAll(management.getAllTask());
            setAdapter();
            Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show();
            colorPicker = -16711681;
        }
    }

    //xủ lí imgAdd
    public void imgAdd(View view) {
        showDialogThemLop();
    }

    //show dialog thêm lớp
    @SuppressLint("ResourceAsColor")
    private void showDialogThemLop() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_them_cong_viec);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        edtNhapCV = dialog.findViewById(R.id.edtNhapCV);
        linearPhongNen = dialog.findViewById(R.id.linearPhongNen);
        btnColorPicker = dialog.findViewById(R.id.btnColorPicker);
        btnColorPicker.setBackgroundColor(R.color.colorBlue);
        btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnXacNhan.setEnabled(false);
        edtNhapCV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtNhapCV.getText().toString().trim().equals("")) {
                    btnXacNhan.setEnabled(true);
                } else {
                    btnXacNhan.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        linearPhongNen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog();
            }
        });

        btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenCV = edtNhapCV.getText().toString().trim();
                insertTask();
                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                colorPicker = -16711681;
            }
        });

    }

    private void colorPickerDialog() {
        final Context context = Main_Activity.this;

        ColorPickerDialogBuilder
                .with(context)
                .setTitle(R.string.color_dialog_title)
                .initialColor(0xffffffff)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorChangedListener(new OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int selectedColor) {
                        // Handle on color change
                        Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        //Toast.makeText(context, "0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //changeBackgroundColor(selectedColor);
                        if (allColors != null) {
                            StringBuilder sb = null;

                            for (Integer color : allColors) {
                                if (color == null)
                                    continue;
                                if (sb == null)
                                    sb = new StringBuilder("Color List:");
                                sb.append("\r\n#" + Integer.toHexString(color).toUpperCase());
                            }

                            if (sb != null) {

                            }
                            btnColorPicker.setBackgroundColor(selectedColor);
                            colorPicker = selectedColor;
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colorPicker = -16711681;
                    }
                })
                .showColorEdit(true)
                .setColorEditTextColor(ContextCompat.getColor(Main_Activity.this, android.R.color.holo_blue_bright))
                .build()
                .show();
    }
}
