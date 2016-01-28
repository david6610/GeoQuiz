package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
//import android.util.Log;

public class QuizActivity extends Activity {

	//private static final String TAG = "TestEvent2";
	//private TextView mText;
	
	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mPrevButton;
	private ImageButton mNextButton;
	
	private Button mCheatButton;
	
	private TextView mQuestionTextView;
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";

	//private static final String KEY_ISCHEATER = "IsCheater";  当只有一个变量时
	
	private static final String KEY_ISCHEATED[] = new String[] {
		"1.IsCheated","2.IsCheated","3.IsCheated","4.IsCheated","5.IsCheated"
	};
	
	//private boolean mIsCheater;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[]{
			new TrueFalse(R.string.question_oceans,true,false),
			new TrueFalse(R.string.question_mideast,false,false),
			new TrueFalse(R.string.question_africa,false,false),
			new TrueFalse(R.string.question_americas,true,false),
			new TrueFalse(R.string.question_asia,true,false),
	};
	
	private int mCurrentIndex = 0 ;
	
	private void updateQuestion() {
		//Log.d(TAG,"Updateing question text for question #" + mCurrentIndex,new Exception());
				
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
		
	}

	private void showCheatInfo(){
		int messageResId = 0;
		//---chapter 5--------------
		//if(mIsCheater) {  //作弊时显示  作弊不好！所有题整体判断一个标志
		if(mQuestionBank[mCurrentIndex].isCheated()) {  //每一个题一个作弊标志！
			messageResId = R.string.cheated_toast;
		}   //---------------------------
		else {				//不是作弊时，显示是否正确
			messageResId = R.string.nocheated_toast;
		}

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	private void checkAnswer(boolean userPressedTrue){
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		//---chapter 5--------------
		//if(mIsCheater) {  //作弊时显示  作弊不好！所有题整体判断一个标志
		if(mQuestionBank[mCurrentIndex].isCheated()) {  //每一个题一个作弊标志！
			messageResId = R.string.judgment_toast;
		}   //---------------------------
		else {				//不是作弊时，显示是否正确
			
			if(userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(savedInstanceState != null){  //设备旋转时调出保存的数据
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);  //获取问题序号

        	
        	//mIsCheater = savedInstanceState.getBoolean(KEY_ISCHEATER);  //单个作弊处理时 
        	for (int i=0; i< mQuestionBank.length; i++) {           //排除作弊后循环在答卷的BUG
    			
        		mQuestionBank[i].SetCheated(savedInstanceState.getBoolean(KEY_ISCHEATED[i])); 
    			
    		}
        	
        }
        
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);   //显示新的布局
        
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        
        
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View V) {
        		//Toast.makeText(getApplicationContext(), R.string.incorrect_toast , 
        		//		Toast.LENGTH_SHORT ).show();
        		checkAnswer(true);
        		
        	}
        });

        
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View V) {
        			//Does nothing yet, but soon!
        		//Toast.makeText(QuizActivity.this, R.string.correct_toast, 
        		//		Toast.LENGTH_SHORT).show();
        		checkAnswer(false);
        		
        	}
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View V) {
        			//Does nothing yet, but soon!
        		mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;

        		//mIsCheater = false ; //---chapter 5
        		showCheatInfo();
        		
        		updateQuestion();       		
        	}
        });
        //=======================================================
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View V) {
        			//Does nothing yet, but soon!
        		if(mCurrentIndex==0)
        			mCurrentIndex = mQuestionBank.length-1;
        		else
        			mCurrentIndex = (mCurrentIndex -1) % mQuestionBank.length;
        		

        		//mIsCheater = false ; //---chapter 5
        		showCheatInfo();
        		updateQuestion();       		
        	}
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View V) {
        			//Does nothing yet, but soon!
        		mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;

        		//mIsCheater = false ; //---chapter 5

        		showCheatInfo();

        		updateQuestion();       		
        	}
        });

        //=========chapter 5==================================
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View V) {
        		Intent i = new Intent(QuizActivity.this,CheatActivity.class);
        		
        		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        		i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        
        		//startActivity(i);             // 不需要返回结果时
        		startActivityForResult(i,0);	// 为了返回结果
        		
        	}
        });
        
        //======================================================
		showCheatInfo();
		updateQuestion(); 
    }
	
	@Override
    public void onStart() {
        super.onStart();
		Log.d(TAG,"onStart() called");
    }
	@Override
	public void onPause() {
        super.onStart();
		Log.d(TAG,"onPause() called");
    }
	@Override
	public void onResume() {
        super.onStart();
		Log.d(TAG,"onResume() called");
    }
	@Override
	public void onStop() {
        super.onStart();
		Log.d(TAG,"onStop() called");
    }
	@Override
	public void onDestroy() {
        super.onStart();
		Log.d(TAG,"onDestroy() called");
    }

	//-----------在设备旋转时，保存数据--------------------
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){   //存储 问题序号
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG,"onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);     //设备旋转，保存数据

		//savedInstanceState.putBoolean(KEY_ISCHEATER, mIsCheater); //设备旋转，保存数据
		for (int i=0; i< mQuestionBank.length; i++) {
			savedInstanceState.putBoolean(KEY_ISCHEATED[i], 
					mQuestionBank[i].isCheated());
			
		}
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //----------  chapter 5 -------------------------
    @Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(data == null ) {
    		return ;
    		
    	}
    	boolean mThisQustionIsCheated;
    	//mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    	mThisQustionIsCheated = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    	
    	mQuestionBank[mCurrentIndex].SetCheated(mThisQustionIsCheated);
    	
    	checkAnswer(true);
    }
    
    
}
