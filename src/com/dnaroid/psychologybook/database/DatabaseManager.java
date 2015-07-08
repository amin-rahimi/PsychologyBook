package com.dnaroid.psychologybook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseManager extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "psychology.db";
	private static final int DATABASE_VERSION = 2;

	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		setForcedUpgrade(DATABASE_VERSION);

	}

	
	
	@SuppressWarnings("unused")
	public void copyDatabase(){
		SQLiteDatabase db = getReadableDatabase();
	}

	public int getFreeTopicsCount() {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id" };
		String sqlTables = "topic";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "free=1", null, null, null, null);
		c.moveToFirst();
		return c.getCount();
	}

	public Cursor getCategories() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "title" };
		String sqlTables = "category";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
		c.moveToFirst();
		return c;
	}

	public Cursor getFreeTopics(int categoryId) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id" };
		String sqlTables = "topic";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "category_id=" + categoryId
				+ " and " + "free=1", null, null, null, null);
		c.moveToFirst();
		return c;
	}

	public String getCategoryName(int categoryId) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "title" };
		String sqlTables = "category";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "_id=" + categoryId, null, null,
				null, null);
		c.moveToFirst();
		return c.getString(c.getColumnIndexOrThrow("title"));
	}

	public Cursor getCategoryTopics(int categoryId) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "title", "category_id", "favorate",
				"free", "new", "best" };
		String sqlTables = "topic";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "category_id=" + categoryId, null,
				null, null, "free DESC");
		c.moveToFirst();
		return c;
	}

	public Cursor getTopic(int topicId) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "title", "category_id", "text",
				"favorate", "new" };
		String sqlTables = "topic";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "_id=" + topicId, null, null, null,
				null);
		c.moveToFirst();
		return c;
	}

	public Cursor searchTopics(String query) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "title", "category_id", "favorate", "new", "free" };
		String sqlTables = "topic";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "title LIKE ?", new String[] { "%"
				+ query + "%" }, null, null, "free DESC");
		c.moveToFirst();
		return c;
	}

	public void updateTopic(int topicId, int favorate) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("favorate", favorate);
		db.update("topic", cv, "_id=" + topicId, null);
	}

	public Cursor getFavorates() {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String[] sqlSelect = { "_id", "title", "category_id", "text", "free",
				"favorate", "new" };
		String sqlTables = "topic";
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "favorate = 1", null, null, null,
				null);
		c.moveToFirst();
		return c;
	}

}