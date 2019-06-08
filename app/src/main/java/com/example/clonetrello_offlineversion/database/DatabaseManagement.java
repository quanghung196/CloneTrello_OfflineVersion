package com.example.clonetrello_offlineversion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.clonetrello_offlineversion.model.Board;
import com.example.clonetrello_offlineversion.model.Card;
import com.example.clonetrello_offlineversion.model.ListWorks;
import com.example.clonetrello_offlineversion.model.Task;
import com.example.clonetrello_offlineversion.model.Work;

import java.util.ArrayList;

public class DatabaseManagement extends SQLiteOpenHelper {

    public static final String DB_NAME = "Trello";
    public static final String TB_TASK = "task";
    public static final String TB_BOARD = "board";
    public static final String TB_CARD = "card";
    public static final String TB_LIST_WORKS = "listWorks";
    public static final String TB_WORK = "workToDo";
    public static final int DB_VERSION = 5;

    private SQLiteDatabase database;
    private Context context;


    private static final String ID = "_id";
    //Task
    private static final String TASK_NAME = "tenCV";
    private static final String TASK_COLOR = "mauCV";

    //Board
    private static final String TASK_ID = "maCV";
    private static final String BOARD_NAME = "tenBang";

    //Card
    private static final String BOARD_ID = "maBang";
    private static final String CARD_NAME = "tenThe";
    private static final String CARD_DESCRIPTION = "moTaThe";
    private static final String CARD_DAY_END = "ngayHetHan";
    private static final String CARD_TIME_END = "gioHenHan";
    private static final String BOOL_DATE_TIME = "boolDateTime";

    //ListWorks
    private static final String CARD_ID = "maThe";
    private static final String LIST_WORKS_NAME = "tenDanhSachCV";

    //Work
    private static final String LIST_WORKS_ID = "maDSCongViec";
    private static final String WORK_NAME = "tenCongViec";
    private static final String BOOL_DONE_YET = "boolDoneYet";

    //Trigger
    private static final String TRIGGER_DELETE_BOARD = "triggerDeleteBoard";
    private static final String TRIGGER_DELETE_CARD = "triggerDeleteCard";
    private static final String TRIGGER_DELETE_LISTWORKS = "triggerDeleteListWorks";
    private static final String TRIGGER_DELETE_WORK = "triggerDeleteWork";

    public DatabaseManagement(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE
        String sqlQueryTask = "CREATE TABLE " + TB_TASK + " (" +
                ID + " integer primary key autoincrement , " +
                TASK_NAME + " TEXT, " +
                TASK_COLOR + " TEXT)";
        db.execSQL(sqlQueryTask);

        String sqlQueryBoard = "CREATE TABLE " + TB_BOARD + " (" +
                ID + " integer primary key autoincrement , " +
                TASK_ID + " TEXT, " +
                BOARD_NAME + " TEXT)";
        db.execSQL(sqlQueryBoard);

        String sqlQueryCard = "CREATE TABLE " + TB_CARD + " (" +
                ID + " integer primary key autoincrement , " +
                BOARD_ID + " TEXT, " +
                CARD_NAME + " TEXT, " +
                CARD_DESCRIPTION + " TEXT, " +
                CARD_DAY_END + " TEXT, " +
                CARD_TIME_END + " TEXT, " +
                BOOL_DATE_TIME + " BOOL)";
        db.execSQL(sqlQueryCard);

        String sqlQueryListWorks = "CREATE TABLE " + TB_LIST_WORKS + " (" +
                ID + " integer primary key autoincrement , " +
                CARD_ID + " TEXT, " +
                LIST_WORKS_NAME + " TEXT)";
        db.execSQL(sqlQueryListWorks);

        String sqlQueryWork = "CREATE TABLE " + TB_WORK + " (" +
                ID + " integer primary key autoincrement , " +
                CARD_ID + " TEXT, " +
                LIST_WORKS_ID + " TEXT, " +
                WORK_NAME + " TEXT, " +
                BOOL_DONE_YET + " BOOL)";
        db.execSQL(sqlQueryWork);

        //CREATE TRIGGER
        String sqlTriggerDeleteBoard =
                "CREATE TRIGGER " + TRIGGER_DELETE_BOARD +
                        " AFTER DELETE ON " + TB_TASK +
                        " FOR EACH ROW" +
                        " BEGIN" +
                        " DELETE FROM " + TB_BOARD + " WHERE " + TASK_ID + " = OLD." + ID + ";" +
                        " END";
        db.execSQL(sqlTriggerDeleteBoard);

        String sqlTriggerDeleteCard =
                "CREATE TRIGGER " + TRIGGER_DELETE_CARD +
                        " AFTER DELETE ON " + TB_BOARD +
                        " FOR EACH ROW" +
                        " BEGIN" +
                        " DELETE FROM " + TB_CARD + " WHERE " + BOARD_ID + " = OLD." + ID + ";" +
                        " END";
        db.execSQL(sqlTriggerDeleteCard);

        String sqlTriggerDeleteListWorks =
                "CREATE TRIGGER " + TRIGGER_DELETE_LISTWORKS +
                        " AFTER DELETE ON " + TB_CARD +
                        " FOR EACH ROW" +
                        " BEGIN" +
                        " DELETE FROM " + TB_LIST_WORKS + " WHERE " + CARD_ID + " = OLD." + ID + ";" +
                        " END";
        db.execSQL(sqlTriggerDeleteListWorks);

        String sqlTriggerDeleteWork =
                "CREATE TRIGGER " + TRIGGER_DELETE_WORK +
                        " AFTER DELETE ON " + TB_LIST_WORKS +
                        " FOR EACH ROW" +
                        " BEGIN" +
                        " DELETE FROM " + TB_WORK + " WHERE " + LIST_WORKS_ID + " = OLD." + ID + ";" +
                        " END";
        db.execSQL(sqlTriggerDeleteWork);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP TABLE
        String dropTableTask = "DROP TABLE IF EXISTS " + TB_TASK;
        String dropTableBoard = "DROP TABLE IF EXISTS " + TB_BOARD;
        String dropTableCard = "DROP TABLE IF EXISTS " + TB_CARD;
        String dropTableListWorks = "DROP TABLE IF EXISTS " + TB_LIST_WORKS;
        String dropTableWork = "DROP TABLE IF EXISTS " + TB_WORK;
        db.execSQL(dropTableTask);
        db.execSQL(dropTableBoard);
        db.execSQL(dropTableCard);
        db.execSQL(dropTableListWorks);
        db.execSQL(dropTableWork);
        //DROP TRIGGER
        String dropTriggerDeleteBoard = "DROP TRIGGER IF EXISTS " + TRIGGER_DELETE_BOARD;
        String dropTriggerDeleteCard = "DROP TRIGGER IF EXISTS " + TRIGGER_DELETE_CARD;
        String dropTriggerDeleteListWorks = "DROP TRIGGER IF EXISTS " + TRIGGER_DELETE_LISTWORKS;
        String dropTriggerDeleteWork = "DROP TRIGGER IF EXISTS " + TRIGGER_DELETE_WORK;
        db.execSQL(dropTriggerDeleteBoard);
        db.execSQL(dropTriggerDeleteCard);
        db.execSQL(dropTableCard);
        db.execSQL(dropTriggerDeleteListWorks);
        db.execSQL(dropTriggerDeleteWork);
        //
        onCreate(db);
    }

    //insert
    public void insertTask(Task task) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getTaskName());
        values.put(TASK_COLOR, task.getTaskColor());
        database.insert(TB_TASK, null, values);
        database.close();
    }

    public void insertBoard(Board board) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOARD_NAME, board.getBoardName());
        values.put(TASK_ID, board.getTaskID());
        database.insert(TB_BOARD, null, values);
        database.close();
    }

    public void insertCard(Card card) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_NAME, card.getCardName());
        values.put(BOARD_ID, card.getBoardID());
        values.put(BOOL_DATE_TIME, card.getCheckDateTime());
        database.insert(TB_CARD, null, values);
        database.close();
    }

    public void insertListWorks(ListWorks listWorks) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_ID, listWorks.getCardID());
        values.put(LIST_WORKS_NAME, listWorks.getListWorksName());
        database.insert(TB_LIST_WORKS, null, values);
        database.close();
    }

    public void insertWork(Work work) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_ID, work.getCarID());
        values.put(LIST_WORKS_ID, work.getListWorksID());
        values.put(WORK_NAME, work.getWorksName());
        values.put(BOOL_DONE_YET, work.getWorkDoneYet());
        database.insert(TB_WORK, null, values);
        database.close();
    }

    //update
    public void updateTask(Task task, int taskId) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, task.getTaskId());
        values.put(TASK_NAME, task.getTaskName());
        values.put(TASK_COLOR, task.getTaskColor());
        database.update(TB_TASK, values, ID + " = " + taskId, null);
        database.close();
    }

    public void updateBoard(Board board, int boardId) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, board.getBoardId());
        values.put(BOARD_NAME, board.getBoardName());
        values.put(TASK_ID, board.getTaskID());
        database.update(TB_BOARD, values, ID + " = " + boardId, null);
        database.close();
    }

    public void updateCard(Card card, int cardId) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, card.getCardID());
        values.put(BOARD_ID, card.getBoardID());
        values.put(CARD_NAME, card.getCardName());
        values.put(CARD_DESCRIPTION, card.getCardDescription());
        values.put(CARD_DAY_END, card.getCardDateEnd());
        values.put(CARD_TIME_END, card.getCardTimeEnd());
        values.put(BOOL_DATE_TIME, card.getCheckDateTime());
        database.update(TB_CARD, values, ID + " = " + cardId, null);
        database.close();
    }

    public void updateListWorks(ListWorks listWorks, int listworksID) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, listWorks.getListWorksID());
        values.put(CARD_ID, listWorks.getCardID());
        values.put(LIST_WORKS_NAME, listWorks.getListWorksName());
        database.update(TB_LIST_WORKS, values, ID + " = " + listworksID, null);
        database.close();
    }

    public void updateWork(Work work, int workID) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, work.getWorksID());
        values.put(CARD_ID, work.getCarID());
        values.put(LIST_WORKS_ID, work.getListWorksID());
        values.put(WORK_NAME, work.getWorksName());
        values.put(BOOL_DONE_YET, work.getWorkDoneYet());
        database.update(TB_WORK, values, ID + " = " + workID, null);
        database.close();
    }

    //delete
    public void deleteTask(int taskId) {
        database = this.getWritableDatabase();
        database.delete(TB_TASK, ID + " = " + taskId, null);
        database.close();
    }

    public void deleteBoard(int boardId) {
        database = this.getWritableDatabase();
        database.delete(TB_BOARD, ID + " = " + boardId, null);
        database.close();
    }

    public void deleteCard(int cardId) {
        database = this.getWritableDatabase();
        database.delete(TB_CARD, ID + " = " + cardId, null);
    }

    public void deleteListWorks(int listworksID) {
        database = this.getWritableDatabase();
        database.delete(TB_LIST_WORKS, ID + " = " + listworksID, null);
    }

    public void deleteWork(int workID) {
        database = this.getWritableDatabase();
        database.delete(TB_WORK, ID + " = " + workID, null);
    }

    //Get All Task
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TB_TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setTaskId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setTaskColor(cursor.getInt(2));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    //Get All Board
    public ArrayList<Board> getAllBoard(String taskId) {
        ArrayList<Board> boards = new ArrayList<Board>();
        // Select All Query
        String selectQuery =
                "SELECT * FROM " + TB_BOARD +
                        " WHERE " + TASK_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{taskId});

        if (cursor.moveToFirst()) {
            do {
                Board board = new Board();
                board.setBoardId(cursor.getInt(0));
                board.setTaskID(cursor.getInt(1));
                board.setBoardName(cursor.getString(2));
                boards.add(board);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return boards;
    }

    //Get All Card
    public ArrayList<Card> getAllCard(String boardId) {
        ArrayList<Card> cards = new ArrayList<Card>();
        // Select All Query
        String selectQuery =
                "SELECT  * FROM " + TB_CARD +
                        " WHERE " + BOARD_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{boardId});

        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setCardID(cursor.getInt(0));
                card.setBoardID(cursor.getInt(1));
                card.setCardName(cursor.getString(2));
                card.setCardDescription(cursor.getString(3));
                card.setCardDateEnd(cursor.getString(4));
                card.setCardTimeEnd(cursor.getString(5));
                card.setCheckDateTime(cursor.getInt(6));
                cards.add(card);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cards;
    }

    //Get All List Works
    public ArrayList<ListWorks> getAllListWorks(String cardId) {
        ArrayList<ListWorks> arrlistWorks = new ArrayList<ListWorks>();
        // Select All Query
        String selectQuery =
                "SELECT  * FROM " + TB_LIST_WORKS +
                        " WHERE " + CARD_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{cardId});

        if (cursor.moveToFirst()) {
            do {
                ListWorks listWorks = new ListWorks();
                listWorks.setListWorksID(cursor.getInt(0));
                listWorks.setCardID(cursor.getInt(1));
                listWorks.setListWorksName(cursor.getString(2));
                arrlistWorks.add(listWorks);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrlistWorks;
    }

    //Get All Works
    public ArrayList<Work> getAllWorks(String listWorksId) {
        ArrayList<Work> works = new ArrayList<Work>();
        // Select All Query
        String selectQuery =
                "SELECT  * FROM " + TB_WORK +
                        " WHERE " + LIST_WORKS_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{listWorksId});

        if (cursor.moveToFirst()) {
            do {
                Work work = new Work();
                work.setWorksID(cursor.getInt(0));
                work.setCarID(cursor.getInt(1));
                work.setListWorksID(cursor.getInt(2));
                work.setWorksName(cursor.getString(3));
                work.setWorkDoneYet(cursor.getInt(4));
                works.add(work);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return works;
    }

    //LoadCardById
    public Card getCardById(String cardId) {
        String selectQuery =
                "SELECT * FROM " + TB_CARD +
                        " WHERE " + ID + " = " + cardId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Card card = new Card();
        if (cursor.moveToFirst()) {
            do {
                card.setCardID(cursor.getInt(0));
                card.setBoardID(cursor.getInt(1));
                card.setCardName(cursor.getString(2));
                card.setCardDescription(cursor.getString(3));
                card.setCardDateEnd(cursor.getString(4));
                card.setCardTimeEnd(cursor.getString(5));
                card.setCheckDateTime(cursor.getInt(6));
                Log.d("Card", card.getCardID() + ", " + card.getCardName());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return card;
    }

    //Load Card By Id
    public Task getTaskById(String taskId) {
        String selectQuery =
                "SELECT * FROM " + TB_TASK +
                        " WHERE " + ID + " = " + taskId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Task task = new Task();
        if (cursor.moveToFirst()) {
            do {
                task.setTaskId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setTaskColor(cursor.getInt(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return task;
    }

    //Get Board By ID
    public String getBoardNameById(String boardId) {
        String selectQuery =
                "SELECT " + BOARD_NAME + " FROM " + TB_BOARD +
                        " WHERE " + ID + " = " + boardId;
        String boardName = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                boardName = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return boardName;
    }

    //get task id by boardid
    public String getTaskID(String boardId) {
        String selectQuery =
                "SELECT " + TASK_ID + " FROM " + TB_BOARD +
                        " WHERE " + ID + " = " + boardId;
        String taskID = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                taskID = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskID;
    }

    //Get Board By ID
    public String getTaskNameById(String taskId) {
        String selectQuery =
                "SELECT " + TASK_NAME + " FROM " + TB_TASK +
                        " WHERE " + ID + " = " + taskId;
        String taskName = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                taskName = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskName;
    }

    //Get ListWorks By ID
    public ListWorks getListWorksId(String listWorksID) {
        String selectQuery =
                "SELECT * FROM " + TB_LIST_WORKS +
                        " WHERE " + ID + " = " + listWorksID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ListWorks listWorks = new ListWorks();
        if (cursor.moveToFirst()) {
            do {
                listWorks.setListWorksID(cursor.getInt(0));
                listWorks.setCardID(cursor.getInt(1));
                listWorks.setListWorksName(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listWorks;
    }

    //Count Done Work by CardID
    public int countDoneWork(int cardID) {
        String selectQuery =
                "SELECT * FROM " + TB_WORK +
                        " WHERE " + CARD_ID + " = " + cardID + " AND " + BOOL_DONE_YET + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getCount();
        return cursor.getCount();
    }

    public int countWork(int cardID) {
        String selectQuery =
                "SELECT * FROM " + TB_WORK +
                        " WHERE " + CARD_ID + " = " + cardID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public ArrayList<String> getArrBoard(String taskID) {
        ArrayList<String> arr = new ArrayList<>();
        String selectQuery =
                "SELECT  " + ID + " FROM " + TB_BOARD +
                        " WHERE " + TASK_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{taskID});

        if (cursor.moveToFirst()) {
            do {
                arr.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arr;
    }
}

