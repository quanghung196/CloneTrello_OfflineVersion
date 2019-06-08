package com.example.clonetrello_offlineversion;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clonetrello_offlineversion.adapter.ListWorks_Adapter;
import com.example.clonetrello_offlineversion.broadcast_service.AlarmReceiver;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.fragment.DatePicker_Fragment;
import com.example.clonetrello_offlineversion.fragment.TimePicker_Fragment;
import com.example.clonetrello_offlineversion.model.Card;
import com.example.clonetrello_offlineversion.model.ListWorks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Card_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView tvBoardName;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolBar;
    private EditText edtDescription;
    private CheckBox cbDateTime;
    private Button btnDateTime, btnAddWork;
    private ImageView imgSaveDescription;
    private RecyclerView recyclerColorTag;
    private RecyclerView recycleListWorks;

    private EditText edtNhapTenThe;
    private Button btnXacNhan, btnHuy;

    private int cardID;
    private DatabaseManagement manager;
    private Card card;

    private ListWorks_Adapter adapter;
    private ListWorks listWorks;
    private ArrayList<ListWorks> arrListWorks = new ArrayList<>();
    private ArrayList<String> arr = new ArrayList<>();

    private AlarmManager alarmManager;
    private PendingIntent pendingIntentToDay, pendingIntentTomorrow;

    private int taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_);

        Init();
        setUpToolBar();
        getTaskID();
        setUpEditTextDescription();
        dateTimePicker();
        setUpCheckBoxDateTime();
        addWork();
    }

    private void Init() {
        Bundle bundle = getIntent().getBundleExtra("TABLE_BOARD");
        cardID = bundle.getInt("cardId");

        btnAddWork = findViewById(R.id.btnAddWork);
        edtDescription = findViewById(R.id.edtDescription);
        imgSaveDescription = findViewById(R.id.imgSaveDescription);
        toolBar = findViewById(R.id.toolBar);
        btnDateTime = findViewById(R.id.btnDateTime);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolBarLayout);
        cbDateTime = findViewById(R.id.cbDateTime);
        tvBoardName = findViewById(R.id.tvBoardName);

        recyclerColorTag = findViewById(R.id.recycleColorTag);
        LinearLayoutManager layoutManagerColor = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerColorTag.setLayoutManager(layoutManagerColor);

        recycleListWorks = findViewById(R.id.recycleListWorks);
        LinearLayoutManager layoutManagerListWorks = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleListWorks.setLayoutManager(layoutManagerListWorks);
        manager = new DatabaseManagement(this);
        arrListWorks = manager.getAllListWorks(cardID + "");
        setAdapter();
    }

    private void getTaskID() {
        taskID = Integer.parseInt(manager.getTaskID(card.getBoardID() + ""));
    }

    private int getPositon() {
        int position = 0;
        arr.addAll(manager.getArrBoard(taskID + ""));
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(card.getBoardID() + "")) {
                position = i;
            }
        }
        return position;
    }

    //tạo class
    private ListWorks createListWorks() {
        listWorks = new ListWorks(card.getCardID(), "Danh sách công việc");
        return listWorks;
    }

    public void InsertListWorks() {
        listWorks = createListWorks();
        if (listWorks != null) {
            manager.insertListWorks(listWorks);
            arrListWorks.clear();
            arrListWorks.addAll(manager.getAllListWorks(card.getCardID() + ""));
            setAdapter();
            recycleListWorks.scrollToPosition(adapter.getItemCount() - 1);
            Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new ListWorks_Adapter(arrListWorks, this);
            recycleListWorks.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            recycleListWorks.scrollToPosition(0);
        }
    }

    private void addWork() {
        btnAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertListWorks();
                btnAddWork.requestFocus();
            }
        });
    }

    private void setUpToolBar() {
        card = manager.getCardById(cardID + "");

        collapsingToolbarLayout.setTitle(card.getCardName());

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolBar.setNavigationIcon(R.drawable.icon_x_24);
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getPositon();
                    Intent intent = new Intent(Card_Activity.this, Board_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idTask", taskID);
                    bundle.putInt("position", position);
                    intent.putExtra("TABLE_TASK", bundle);
                    startActivity(intent);
                    finish();
                }
            });
        }

        String boardID = card.getBoardID() + "";
        String boardName = manager.getBoardNameById(boardID);
        tvBoardName.setText(boardName);
    }

    private void setUpEditTextDescription() {
        imgSaveDescription.setVisibility(View.GONE);
        /*InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtDescription.getWindowToken(), 0);*/
        edtDescription.setFocusable(false);
        edtDescription.setCursorVisible(false);
        edtDescription.setText(card.getCardDescription());
        edtDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtDescription.setCursorVisible(true);
                edtDescription.setFocusableInTouchMode(true);
                return false;
            }
        });

        edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imgSaveDescription.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgSaveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setCardDescription(edtDescription.getText().toString().trim());
                manager.updateCard(card, card.getCardID());
                imgSaveDescription.setVisibility(View.GONE);
                edtDescription.setCursorVisible(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtDescription.getWindowToken(), 0);
            }
        });
    }

    private void dateTimePicker() {
        btnDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker_Fragment();
                timePicker.show(getSupportFragmentManager(), "timePicker");
                DialogFragment datePicker = new DatePicker_Fragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        if (card.getCardDateEnd() == null) {
            btnDateTime.setText("  Hết hạn vào...");
        } else {
            btnDateTime.setText("  Hết hạn vào " + card.getCardDateEnd() + " lúc " + card.getCardTimeEnd());
        }
    }

    private void splitDateAndTime() {
        if (card.getCardDateEnd() != null && card.getCardTimeEnd() != null) {
            String date = card.getCardDateEnd();
            String[] dateParts = date.split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);

            String time = card.getCardTimeEnd();
            String[] timeParts = time.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            GregorianCalendar expDate = new GregorianCalendar(year, month - 1, day);
            GregorianCalendar expDateTime = new GregorianCalendar(year, month - 1, day, hour, minute);
            GregorianCalendar now = new GregorianCalendar();
            boolean isExpired = now.after(expDate);
            boolean isExpired2 = now.after(expDateTime);

            if (isExpired) {
                Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_watch_yellow_24);
                btnDateTime.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                card.setCheckDateTime(0);
            } else {
                Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_watch_gray_24);
                btnDateTime.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                card.setCheckDateTime(0);
            }
            if (isExpired2) {
                Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_watch_red_24);
                btnDateTime.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                card.setCheckDateTime(0);
            }
        }
    }

    private void setUpCheckBoxDateTime() {
        int bool = card.getCheckDateTime();
        if (card.getCardDateEnd() == null) {
            cbDateTime.setVisibility(View.GONE);
        } else {
            cbDateTime.setVisibility(View.VISIBLE);
            if (bool == 1) {
                Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_watch_green_24);
                btnDateTime.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            } else {
                splitDateAndTime();
            }
        }
        if (bool == 0) {
            cbDateTime.setChecked(false);
        } else {
            cbDateTime.setChecked(true);
        }
        cbDateTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    card.setCheckDateTime(1);
                    Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_watch_green_24);
                    btnDateTime.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                } else {
                    splitDateAndTime();
                }
                Log.d("Check", +card.getCheckDateTime() + "");
                manager.updateCard(card, card.getCardID());
                Alarm();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemXoa:
                deleteCard();
                return true;
            case R.id.itemEdit:
                showEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showEditDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_sua_ten_the);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        edtNhapTenThe = dialog.findViewById(R.id.edtNhapTenThe);
        btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnXacNhan.setEnabled(false);

        edtNhapTenThe.setText(card.getCardName());
        edtNhapTenThe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtNhapTenThe.getText().toString().trim().equals("")) {
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
                card.setCardName(edtNhapTenThe.getText().toString().trim());
                manager.updateCard(card, card.getCardID());
                collapsingToolbarLayout.setTitle(card.getCardName());
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

    private void deleteCard() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Card_Activity.this);
        builder.setTitle("Xóa thẻ");
        builder.setMessage("Bạn có muốn xóa thẻ này??");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                manager.deleteCard(card.getCardID());
                //Code to run when the create order item is clicked
                int position = getPositon();
                Intent intent = new Intent(Card_Activity.this, Board_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idTask", taskID);
                bundle.putInt("position", position);
                intent.putExtra("TABLE_TASK", bundle);
                startActivity(intent);
                finish();
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
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String deadlineDate = dayOfMonth + "-" + (month + 1) + "-" + year;
        card.setCardDateEnd(deadlineDate);
        card.setCardTimeEnd("00:00");
        manager.updateCard(card, card.getCardID());
        btnDateTime.setText("  Hết hạn vào " + card.getCardDateEnd() + " lúc " + card.getCardTimeEnd());
        setUpCheckBoxDateTime();
        splitDateAndTime();
        Alarm();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String deadlineTime = "";
        if (minute < 10) {
            deadlineTime = hourOfDay + ":0" + minute;
        } else {
            deadlineTime = hourOfDay + ":" + minute;
        }
        card.setCardTimeEnd(deadlineTime);
        manager.updateCard(card, card.getCardID());
        btnDateTime.setText("  Hết hạn vào " + card.getCardDateEnd() + " lúc " + card.getCardTimeEnd());
        setUpCheckBoxDateTime();
        splitDateAndTime();
        Alarm();
    }

    private void Alarm() {
        String date = card.getCardDateEnd();
        String time = card.getCardTimeEnd();

        String[] dateParts = date.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.clear();
        calendar.set(year, month - 1, day, hour, minute);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.clear();
        calendar2.set(year, month - 1, day - 1, hour, minute);

        String taskName = manager.getTaskNameById(taskID + "");
        String boardName = manager.getBoardNameById(card.getBoardID() + "");

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Card_Activity.this, AlarmReceiver.class);
        intent.putExtra("taskName", taskName);
        intent.putExtra("boardName", boardName);
        intent.putExtra("cardName", card.getCardName());
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("cardID", card.getCardID());

        GregorianCalendar expDateTime = new GregorianCalendar(year, month - 1, day, hour, minute);
        GregorianCalendar now = new GregorianCalendar();

        int bool = card.getCheckDateTime();
        boolean isExpired = now.after(expDateTime);
        if (!isExpired && card.getCheckDateTime() == 0) {
            pendingIntentToDay = PendingIntent.getBroadcast(Card_Activity.this, card.getCardID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentToDay);
        }

        /*GregorianCalendar expDate = new GregorianCalendar(year, month - 1, day - 1);
        boolean isExpired2 = now.after(expDate);
        if (isExpired2 && card.getCheckDateTime() == 0) {
            pendingIntentTomorrow = PendingIntent.getBroadcast(Card_Activity.this, card.getCardID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntentTomorrow);
        }*/

        Log.d("Receiver", calendar.getTimeInMillis() + "");
    }
}
