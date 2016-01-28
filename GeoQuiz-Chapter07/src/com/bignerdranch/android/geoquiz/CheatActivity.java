package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	

	private int mIsCheated = 0 ;
	//----chapter 6 ----
	private TextView mApiLevelView;
	private String mApiLevel;
	//------------------
	public static final String EXTRA_ANSWER_IS_TRUE = 
			"com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = 
			"com.bignerdranch.android.geoquiz.answer_shown";

	private static final String KEY_ISCHEATED = "IsCheated";

	
	private void setAnswerShowResult(boolean isAnswerShown) {  
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown); //作弊过的情况  存储 
		setResult(RESULT_OK,data);			//作弊过
		//setResult(RESULT_CANCELED,data);  //没有作弊
		mIsCheated = 1;
	}
	
	@Override
	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		
        if(saveInstanceState != null){  //设备旋转时调出保存的数据
        	mIsCheated = saveInstanceState.getInt(KEY_ISCHEATED,0);  //获取返回值

        	if( mIsCheated ==1 ) {           //在横竖屏切换过
        		setAnswerShowResult(true);
        	}
       	}
		
		setContentView(R.layout.activity_cheat);   //显示新的布局
		
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		
		//---chapter 6--------------
		mApiLevelView = (TextView)findViewById(R.id.api_level);
		
		mApiLevel = String.valueOf(android.os.Build.VERSION.SDK_INT);
		
		mApiLevelView.setText("API Level :"+mApiLevel);
		//--------------------------
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mAnswerIsTrue){
					mAnswerTextView.setText(R.string.true_button);
										
				}
				else {
					mAnswerTextView.setText(R.string.false_button);

				}
				
				setAnswerShowResult(true); // 作弊过，返回结果到父 Activity
			
			}
		});
		
	}
	//-----------在设备旋转时，保存数据--------------------
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){   //存储 是否作弊
		super.onSaveInstanceState(savedInstanceState);
//		Log.i(TAG,"Cheat Activity onSaveInstanceState");
		savedInstanceState.putInt(KEY_ISCHEATED, mIsCheated);     //设备旋转，保存作弊信息
	}


}
