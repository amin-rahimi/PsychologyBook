package com.dnaroid.psychologybook.model;

public class PsyCategory {

	private int mId;
	private int mDrawableResource;
	private String mTitle;
	private int mItemCount;

	public int getItemCount() {
		return mItemCount;
	}

	public void setItemCount(int itemCount) {
		mItemCount = itemCount;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public int getDrawableResource() {
		return mDrawableResource;
	}

	public void setDrawableResource(int drawableResource) {
		mDrawableResource = drawableResource;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

}
