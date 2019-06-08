package com.example.clonetrello_offlineversion.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clonetrello_offlineversion.R;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.model.Board;
import com.example.clonetrello_offlineversion.model.Card;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Board_Adapter extends RecyclerView.Adapter<Board_Adapter.ViewHolder> {

    private ArrayList<Board> boards;
    private Context context;

    public Board_Adapter(Context context, ArrayList<Board> students) {
        this.boards = students;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.management = new DatabaseManagement(context);
        final Board board = boards.get(position);
        holder.tvSTTBang.setText(board.getBoardId() + "");
        holder.edtTenBang.setText(board.getBoardName());

        holder.boardId = Integer.parseInt(holder.tvSTTBang.getText().toString());
        holder.boardName = holder.edtTenBang.getText().toString();

        holder.imgConfirm.setVisibility(View.GONE);
        holder.edtTenBang.setFocusable(false);
        holder.edtTenBang.setCursorVisible(false);
        holder.edtTenBang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.edtTenBang.setFocusableInTouchMode(true);
                holder.edtTenBang.setCursorVisible(true);
                holder.imgConfirm.setVisibility(View.VISIBLE);
                return false;
            }
        });

        holder.imgConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgConfirm.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.edtTenBang.getWindowToken(), 0);
                holder.edtTenBang.setCursorVisible(false);
                if (TextUtils.isEmpty(holder.edtTenBang.getText().toString().trim())) {
                    holder.edtTenBang.setText(board.getBoardName());
                    Toast.makeText(context, "Cần nhập ít nhất 1 kí tự", Toast.LENGTH_SHORT).show();
                } else {
                    board.setBoardName(holder.edtTenBang.getText().toString().trim());
                    holder.management.updateBoard(board, board.getBoardId());
                }
            }
        });

        holder.card = new Card();
        if (holder.cards == null) {
            holder.cards = new ArrayList<>();
        } else {
            holder.cards.clear();
        }
        holder.cards.addAll(holder.management.getAllCard(holder.boardId + ""));
        holder.manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerCard.setLayoutManager(holder.manager);
        holder.setAdapter();
        holder.cardAdapter.notifyDataSetChanged();

        holder.tvThemThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edtCardName.setVisibility(View.VISIBLE);
                holder.edtCardName.setFocusableInTouchMode(true);
                holder.tvHuy.setVisibility(View.VISIBLE);
                holder.tvThem.setVisibility(View.VISIBLE);
                holder.tvThemThe.setVisibility(View.GONE);
            }
        });

        holder.tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edtCardName.setText("");
                holder.edtCardName.setVisibility(View.GONE);
                holder.tvHuy.setVisibility(View.GONE);
                holder.tvThem.setVisibility(View.GONE);
                holder.tvThemThe.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.edtCardName.getWindowToken(), 0);
            }
        });

        holder.tvThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(holder.edtCardName.getText().toString().trim())) {
                    Toast.makeText(context, "Mời nhập tên bảng", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d("Info", "Id task: " + holder.taskId + ", Id Bảng: " + board.getBoardId() + ", Tên bảng: " + board.getBoardName());
                    //holder.tvSTTBang.setText(board.getBoardId() + "");
                    holder.InsertCard();
                    holder.recyclerCard.scrollToPosition(holder.cardAdapter.getItemCount() - 1);
                }
            }
        });

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.showPopupWindow(holder.imgMenu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText edtTenBang;
        public TextView tvSTTBang;
        public RecyclerView recyclerCard;
        public EditText edtCardName;
        public ImageView imgMenu;
        public ImageView imgConfirm;

        public TextView tvHuy;
        public TextView tvThemThe;
        public TextView tvThem;

        public TextView tvTenThe;
        public Button btnXoaBang;

        public LinearLayoutManager manager;
        public Card card;
        public ArrayList<Card> cards;
        public Card_Adapter cardAdapter;

        public DatabaseManagement management;

        public int taskId, boardId;
        public String boardName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtTenBang = itemView.findViewById(R.id.edtTenBang);
            imgMenu = itemView.findViewById(R.id.imgMenu);
            imgConfirm = itemView.findViewById(R.id.imgConfirm);
            tvSTTBang = itemView.findViewById(R.id.tvSTTBang);
            recyclerCard = itemView.findViewById(R.id.recyclerCard);
            edtCardName = itemView.findViewById(R.id.edtCardName);
            tvHuy = itemView.findViewById(R.id.tvHuy);
            tvThemThe = itemView.findViewById(R.id.tvThemThe);
            tvThem = itemView.findViewById(R.id.tvThem);
        }

        private Point getPointOfView(View view) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            return new Point(location[0], location[1]);
        }

        private void showAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa bảng");
            builder.setMessage("Bạn có muốn xóa bảng này??");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    management.deleteBoard(boardId);
                    removeItem(getAdapterPosition());
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

        private void showPopupWindow(View view) {
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window_board, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            Log.d("point", "số đo x,y (" + popupWindow.getWidth() + ", " + popupWindow.getHeight() + ")");
            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            Point point = getPointOfView(imgMenu);
            Log.d("point", "view point x,y (" + point.x + ", " + point.y + ")");
            popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (int) (imgMenu.getX() - 820), (int) (imgMenu.getY()+510));

            tvTenThe = popupView.findViewById(R.id.tvTenThe);
            btnXoaBang = popupView.findViewById(R.id.btnXoaBang);
            tvTenThe.setText(boardName);
            btnXoaBang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                    popupWindow.dismiss();
                }
            });

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
        }

        private void removeItem(int position) {
            boards.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, boards.size());
        }

        //tạo class
        private Card createCard() {
            card = new Card(boardId, edtCardName.getText().toString(), 0);
            Log.d("Result", card.getBoardID() + ", " + card.getCardName() + ", " + card.getCheckDateTime());
            return card;
        }

        private void InsertCard() {
            card = createCard();
            if (card != null) {
                management.insertCard(card);
                cards.clear();
                cards.addAll(management.getAllCard(boardId + ""));
                setAdapter();
                Toast.makeText(context, "Insert Successfully", Toast.LENGTH_SHORT).show();
                edtCardName.setText("");
            }
        }

        private void setAdapter() {
            if (cardAdapter == null) {
                cardAdapter = new Card_Adapter(cards, context);
                recyclerCard.setAdapter(cardAdapter);
            } else {
                cardAdapter.notifyDataSetChanged();
            }
        }
    }
}
