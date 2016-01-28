package com.bignerdranch.android.geoquiz;

public class TrueFalse {
	private int mQuestion;
	private boolean mTrueQuestion;
	
	private boolean mIsCheated;

	public TrueFalse(int question,boolean trueQuestion,boolean isOrNotCheated) {
		mQuestion = question;
		mTrueQuestion = trueQuestion;
		mIsCheated = isOrNotCheated ;
	}
	public int getQuestion() {
		return mQuestion;
	}
	public void setQuestion(int question) {
		mQuestion = question;
	}
	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}
	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}

	public boolean isCheated() {
		return mIsCheated;
	}
	public void SetCheated(boolean isOrNotCheated) {
		mIsCheated = isOrNotCheated;
	}
	
}
