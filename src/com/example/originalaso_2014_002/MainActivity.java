package com.example.originalaso_2014_002;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
	
	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent intent = null;
		switch(v.getId()){//どのボタンが押されたか判定
		case R.id.btnENTRY:	//btnMsgが押された
			//エディットテキストから入力内容を取り出す
			EditText etv = (EditText)findViewById(R.id.edtFirst);
			String inputMsg = etv.getText().toString();
			
			//inputMsgがMsgがnullでない、かつ、空でない場合のみ、if文内を実行
			if(inputMsg!=null && !inputMsg.isEmpty()){
				//MySQLiteOpenHelperのインサートメbtnCHECKッドを呼び出し
				helper.insertHitokoto(sdb, inputMsg);
			}
			
			//入力欄をクリア
			etv.setText("");
			break;
		case R.id.btnDELETE://メンテボタンが押された
			
			//インテントのインスタンス生成
			intent = new Intent(MainActivity.this,MaintenanceActivity.class);
			//次画面のアクティビティ起動
			startActivity(intent);
			break;
			
		case R.id.btnCHECK://一言チェックボタンが押された
			
			//MySQLiteOpenHelperのセレクト一言メソッドを呼び出して一言をランダムに取得
			String strHitokoto = helper.selectRandomHitokoto(sdb);
			
			//インテントのインスタンス生成
			intent = new Intent(MainActivity.this, HitokotoActivity.class);
			//インテントに一言を混入
			intent.putExtra("hitokoto",strHitokoto);
			
			//次画面のアクティビティ起動
			startActivity(intent);
			break;
			
			

		    
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    protected void onResume() {
    	//TODO 自走生成されたメソッド・スタブ
    	super.onResume();
        //画面(Activity)に渡されたインテントを取得
    	Intent intent = this.getIntent();
    	// intentから、Extraに混入させた値をキーワードで取得
    	String strHitokoto = intent.getStringExtra("hitokoto");
    	
    	//取得したStringを、TxtViewにセット
    	TextView txtHITOKOTO = (TextView)findViewById(R.id.txvHITOKOTO);
    	txtHITOKOTO.setText(strHitokoto);
    	
    	//登録ボタン変数にリスナーを登録する
    	Button btnENTRY = (Button)findViewById(R.id.btnENTRY);
    	btnENTRY.setOnClickListener(this);
    	
    	//メンテボタン変数にリスナーを登録する
    	Button btnMAINTE = (Button)findViewById(R.id.btnDELETE);
    	btnMAINTE.setOnClickListener(this);
    	
    	//　一言チェックボタン変数にリスナーを登録する
    	Button btnCHECK = (Button)findViewById(R.id.btnCHECK);
    	btnCHECK.setOnClickListener(this);
    	
    	//クラスのフィールド変数がNULLなら、データベース空間オープン
    	if(sdb == null) {
    		helper = new MySQLiteOpenHelper(getApplicationContext());
    	}
    	try{
    		sdb = helper.getWritableDatabase();
    	}catch(SQLiteException e){
    		//異常終了
    		return;
    	}
    }

}
