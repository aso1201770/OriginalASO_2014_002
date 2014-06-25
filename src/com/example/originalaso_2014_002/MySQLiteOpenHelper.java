package com.example.originalaso_2014_002;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	/**
	 * @param context　呼び出しコンテクスト
	 * @param name 利用DB名
	 * @param factory カーソルファクトリー
	 * @param version DBバージョン
	 */
	public MySQLiteOpenHelper(Context context) {
		
		super (context, "20140021201782.sqlite3", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
		        "Hitokoto ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , phrase TEXT )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  db.execSQL("drop table Hitokoto");
		  onCreate(db);
		// TODO 自動生成されたメソッド・スタブ

	}
	/**
	 *　引数のフレーズをHitokotoテーブルにインサートするプライベートメソッド
	 *@param SQLITEDatabase インサート先DBのインスタンス変数
	 *@param inputMsg インサートするメッセージ
	 */
	public void insertHitokoto(SQLiteDatabase db, String inputMsg){
		
		String sqlstr=" insert into Hitokoto (phrase)values('"+inputMsg+"');";
		try{
			//トランザクション開始
			db.beginTransaction();
			db.execSQL(sqlstr);
			//トランザクション成功
			db.setTransactionSuccessful();
		}finally {
			 //トランザクション終了
			db.endTransaction();
		}
	return;
	}
	/**
	*引数のフレーズをHitokotoテーブルにインサートするプライベートメソッド
	*@param SQLiteDatabese インサート先DBのインスタンス変数
    *@param inputMsg インサートするメッセージ
    */
	public String selectRandomHitokoto(SQLiteDatabase db){
		String rtString = null;
		
		String sqlstr ="SELECT _id, phrase FROM Hitokoto ORDER BY RANDOM(); ";
	       try{
	    	   //トランザクション開始
	    	   SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sqlstr,null);
	    	   if(cursor.getCount()!=0){
	    		   //カーソル開始位置を先頭にする
	    		   cursor.moveToFirst();
	    		   rtString = cursor.getString(1);
	    	   }
	    	   cursor.close();
	       }finally{
	    	   //脛にカーソルもcloseしてあるので、何もしない
	       }
		 return rtString;
	}
}
