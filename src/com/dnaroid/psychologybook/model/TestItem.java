package com.dnaroid.psychologybook.model;

import java.util.ArrayList;

public class TestItem {
	private String mQuestion;
	private ArrayList<String> mChoices;

	public TestItem() {

	}

	public TestItem(String question, ArrayList<String> choices) {
		this.mQuestion = question;
		this.mChoices = choices;
	}

	public String getQuestion() {
		return mQuestion;
	}

	public void setQuestion(String question) {
		mQuestion = question;
	}

	public ArrayList<String> getChoices() {
		return mChoices;
	}

	public void setChoices(ArrayList<String> choices) {
		mChoices = choices;
	}

}
