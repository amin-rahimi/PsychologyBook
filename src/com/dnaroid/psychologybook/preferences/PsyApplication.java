package com.dnaroid.psychologybook.preferences;

import java.util.HashMap;

import com.dnaroid.psychologybook.model.History;

import android.app.Application;

public class PsyApplication extends Application {

	private History mHistory;
	public static HashMap<Integer, Integer> categoryCountCache;
	public static HashMap<Integer, Integer> menuCountCache;
	public PsyApplication(){
		categoryCountCache = new HashMap<Integer, Integer>();
		menuCountCache = new HashMap<Integer, Integer>();
	}

	public History getHistory() {
		return mHistory;
	}

	public void setHistory(History history) {
		mHistory = history;
	}

}
