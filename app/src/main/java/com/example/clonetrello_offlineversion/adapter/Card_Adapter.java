package com.example.clonetrello_offlineversion.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clonetrello_offlineversion.Card_Activity;
import com.example.clonetrello_offlineversion.R;
import com.example.clonetrello_offlineversion.database.DatabaseManagement;
import com.example.clonetrello_offlineversion.model.Card;
import com.example.clonetrello_offlineversion.interfacee.setOnItemClick;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.ViewHolder> {

    private ArrayList<Card> cards;
    private Context context;

    public Card_Adapter(ArrayList<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Card card = cards.get(position);
        viewHolder.tvCardName.setText(card.getCardName());
        viewHolder.tvCardID.setText(card.getCardID() + "");

        viewHolder.imgDescriptionIcon.setImageResource(R.drawable.icon_description_16);
        if (card.getCardDescription() == null || card.getCardDescription().trim().equals("")) {
            viewHolder.imgDescriptionIcon.setVisibility(View.GONE);
        }

        String date = card.getCardDateEnd();
        String time = card.getCardTimeEnd();
        int bool = card.getCheckDateTime();

        if (date == null) {
            viewHolder.tvTimer.setVisibility(View.GONE);
        } else {
            String[] dateParts = date.split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            viewHolder.tvTimer.setText(day + " thg " + month);

            String[] timeParts = time.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            if (bool == 1) {
                viewHolder.tvTimer.setBackgroundResource(R.drawable.timer_background_green);
                viewHolder.tvTimer.setTextColor(Color.WHITE);
                viewHolder.tvTimer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_watch_white_18, 0, 0, 0);
            } else {
                GregorianCalendar expDate = new GregorianCalendar(year, month - 1, day);
                GregorianCalendar expDateTime = new GregorianCalendar(year, month - 1, day, hour, minute);
                GregorianCalendar now = new GregorianCalendar();
                boolean isExpired = now.after(expDate);
                boolean isExpired2 = now.after(expDateTime);

                if (isExpired) {
                    viewHolder.tvTimer.setBackgroundResource(R.drawable.timer_background_yellow);
                    viewHolder.tvTimer.setTextColor(Color.WHITE);
                    viewHolder.tvTimer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_watch_white_18, 0, 0, 0);
                } else {
                    viewHolder.tvTimer.setTextColor(Color.GRAY);
                    viewHolder.tvTimer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_watch_gray_18, 0, 0, 0);
                }
                if (isExpired2) {
                    viewHolder.tvTimer.setBackgroundResource(R.drawable.timer_background_red);
                    viewHolder.tvTimer.setTextColor(Color.WHITE);
                    viewHolder.tvTimer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_watch_white_18, 0, 0, 0);
                }
            }
        }

        viewHolder.setItemClickListener(new setOnItemClick() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), Card_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("cardId", card.getCardID());
                intent.putExtra("TABLE_BOARD", bundle);
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.management = new DatabaseManagement(context);
        int countWork = viewHolder.management.countWork(card.getCardID());
        int countWorkDone = viewHolder.management.countDoneWork(card.getCardID());
        viewHolder.tvProgress.setText(countWorkDone + "/" + countWork);
        if (countWork == 0) {
            viewHolder.tvProgress.setVisibility(View.GONE);
        } else {
            if (countWorkDone < countWork) {
                viewHolder.tvProgress.setTextColor(Color.GRAY);
                viewHolder.tvProgress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_checked_checkbox_gray_18, 0, 0, 0);
            } else {
                viewHolder.tvProgress.setBackgroundResource(R.drawable.timer_background_green);
                viewHolder.tvProgress.setTextColor(Color.WHITE);
                viewHolder.tvProgress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_checked_checkbox_white_18, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvCardName;
        private TextView tvCardID;
        private TextView tvTimer;
        private ImageView imgDescriptionIcon;
        private TextView tvProgress;

        private DatabaseManagement management;

        private setOnItemClick itemClickListener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            tvCardID = itemView.findViewById(R.id.tvCardID);
            tvTimer = itemView.findViewById(R.id.tvTimer);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            imgDescriptionIcon = itemView.findViewById(R.id.imgDescriptionIcon);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(setOnItemClick itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true); // Gọi interface , true là vì đây là onLongClick
            return true;
        }
    }
}
