package com.dnaroid.psychologybook.model;

public class MenuItemEntry implements MenuItemInterface {
	private String mTitle;
	private int mId;
	

	public int getId() {
		return mId;
	}

	public MenuItemEntry(String title, int id) {
		this.mTitle = title;
		this.mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}

}
