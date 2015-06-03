package com.guesswhat.android.sqlite.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.guesswhat.android.service.rs.dto.QuestionDTO;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "guesswhatDatabase";
 
    // Table Names
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_SYSTEM_PROPERTIES = "system_properties";
 
    // QUESTIONS TABLE - column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ANSWER_1 = "answer_1";
    private static final String COLUMN_ANSWER_2 = "answer_2";
    private static final String COLUMN_ANSWER_3 = "answer_3";
    private static final String COLUMN_ANSWER_4 = "answer_4";
    private static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    
    // SYSTEM PROPERTIES TABLE- column names
    private static final String COLUMN_PROPERTY = "property";
    private static final String COLUMN_VALUE = "value";
 
    // Table Create Statements
    // QUESTIONS table create statement
    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE "
            + TABLE_QUESTIONS + "(" + COLUMN_ID + " TEXT PRIMARY KEY," + COLUMN_ANSWER_1
            + " TEXT," + COLUMN_ANSWER_2 + " TEXT," + COLUMN_ANSWER_3 + " TEXT,"
            + COLUMN_ANSWER_4 + " TEXT," + COLUMN_CORRECT_ANSWER + " TEXT" + ")";
    
    // SYSTEM_PROPERTIES table create statement
    private static final String CREATE_TABLE_SYSTEM_PROPERTIES = "CREATE TABLE " + TABLE_SYSTEM_PROPERTIES
            + "(" + COLUMN_PROPERTY + " TEXT PRIMARY KEY," + COLUMN_VALUE + " TEXT" + ")";
 
    private static DatabaseHelper helper;
    
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public static void init(Context context) {
    	helper = new DatabaseHelper(context);
    }
    
    public static DatabaseHelper getHelper() {
    	return helper;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_SYSTEM_PROPERTIES);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// on upgrade drop older tables
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYSTEM_PROPERTIES);
 
        // create new tables
        onCreate(db);
    }
    
    public void deleteAllQuestions() {
    	try (SQLiteDatabase db = this.getWritableDatabase()) {
        	db.delete(TABLE_QUESTIONS, null, null);
        }
    }
    
    public void addQuestions(List<QuestionDTO> questions) {
    	for (QuestionDTO questionDTO : questions) {
    		addQuestion(questionDTO);
    	}
    }
    
    private void addQuestion(QuestionDTO questionDTO) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, questionDTO.getId());
        values.put(COLUMN_ANSWER_1, questionDTO.getAnswer1());
        values.put(COLUMN_ANSWER_2, questionDTO.getAnswer2());
        values.put(COLUMN_ANSWER_3, questionDTO.getAnswer3());
        values.put(COLUMN_ANSWER_4, questionDTO.getAnswer4());
        values.put(COLUMN_CORRECT_ANSWER, questionDTO.getCorrectAnswer());
 
        try (SQLiteDatabase db = this.getWritableDatabase()) {
        	db.insert(TABLE_QUESTIONS, null, values);
        }
    }
    
    public List<QuestionDTO> getAllQuestions() {
    	List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
    	try (SQLiteDatabase db = this.getWritableDatabase()) {
    		Cursor cursor = db.rawQuery("select * from " + TABLE_QUESTIONS, null);

    		if (cursor.moveToFirst()) {
    			while (cursor.isAfterLast() == false) {
	                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
	                String answer1 = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER_1));
	                String answer2 = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER_2));
	                String answer3 = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER_3));
	                String answer4 = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER_4));
	                String correctAnswer = cursor.getString(cursor.getColumnIndex(COLUMN_CORRECT_ANSWER));

	                questions.add(new QuestionDTO(id, answer1, answer2, answer3, answer4, correctAnswer));
	                cursor.moveToNext();
	            }
    	    }
    	}
    	return questions;
    }
    
    public void putProperty(String property, String value) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROPERTY, property);
        values.put(COLUMN_VALUE, value);
 
        try (SQLiteDatabase db = this.getWritableDatabase()) {
        	db.insert(TABLE_SYSTEM_PROPERTIES, null, values);
        }
    }
    
    public void updateProperty(String property, String value) {
    	try (SQLiteDatabase db = this.getWritableDatabase()) {
	    	db.rawQuery("UPDATE " + TABLE_SYSTEM_PROPERTIES
	    			+ " SET " + COLUMN_PROPERTY + " = " + value
	    			+ " WHERE " + COLUMN_PROPERTY + " = ?",
	                new String[] { property });
    	}
    }
    
    public String getProperty(String property) {
    	String value = null;
    	try (SQLiteDatabase db = this.getWritableDatabase()) {
    		Cursor cursor = db.rawQuery("SELECT * from " + TABLE_SYSTEM_PROPERTIES
    				+ " WHERE " + COLUMN_PROPERTY + " = ?", 
    				new String[] { property });

    		if (cursor.moveToFirst()) {
    			value = cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
    	    }
    	}
    	return value;
    }
    
}
