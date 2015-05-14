package com.guesswhat.android.sqlite.helper;

import com.guesswhat.android.sqlite.model.Question;
import com.guesswhat.android.sqlite.model.Record;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "guesswhatDatabase";
 
    // Table Names
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_RECORDS = "records";
 
    // QUESTIONS TABLE - column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ANSWER_1 = "answer_1";
    private static final String COLUMN_ANSWER_2 = "answer_2";
    private static final String COLUMN_ANSWER_3 = "answer_3";
    private static final String COLUMN_ANSWER_4 = "answer_4";
    private static final String COLUMN_CORRECT_ANSWER = "correct_answer";
 
    // RECORDS TABLE- column names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_POINTS = "points";
 
    // Table Create Statements
    // QUESTIONS table create statement
    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE "
            + TABLE_QUESTIONS + "(" + COLUMN_ID + " TEXT PRIMARY KEY," + COLUMN_ANSWER_1
            + " TEXT," + COLUMN_ANSWER_2 + " TEXT," + COLUMN_ANSWER_3 + " TEXT,"
            + COLUMN_ANSWER_4 + " TEXT," + COLUMN_CORRECT_ANSWER + " TEXT" + ")";
 
    // RECORDS table create statement
    private static final String CREATE_TABLE_RECORDS = "CREATE TABLE " + TABLE_RECORDS
            + "(" + COLUMN_USER_ID + " TEXT PRIMARY KEY," + COLUMN_POINTS + " INTEGER" + ")";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_RECORDS);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
 
        // create new tables
        onCreate(db);
    }
    
    public void addQuestion(Question question) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, question.getId());
        values.put(COLUMN_ANSWER_1, question.getAnswer1());
        values.put(COLUMN_ANSWER_2, question.getAnswer2());
        values.put(COLUMN_ANSWER_3, question.getAnswer3());
        values.put(COLUMN_ANSWER_4, question.getAnswer4());
        values.put(COLUMN_CORRECT_ANSWER, question.getCorrectAnswer());
 
        SQLiteDatabase db = this.getWritableDatabase();
        
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }	
    
    public void addRecord(Record record) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, record.getUserId());
        values.put(COLUMN_POINTS, record.getPoints());
 
        SQLiteDatabase db = this.getWritableDatabase();
        
        db.insert(TABLE_RECORDS, null, values);
        db.close();
    }
}
