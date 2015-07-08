package com.dnaroid.psychologybook.model;

public class MenuItemSection implements MenuItemInterface {
	private String mTitle;

	public MenuItemSection(String title) {
		this.mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return true;
	}

}
