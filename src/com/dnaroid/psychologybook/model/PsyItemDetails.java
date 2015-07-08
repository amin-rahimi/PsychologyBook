package com.dnaroid.psychologybook.model;

public class PsyItemDetails {

	private String mTitle;
	private int mId;
	private String mText;
	private int mIsFavorite;
	private int mIsNew;

	public int getIsNew() {
		return mIsNew;
	}

	public void setIsNew(int isNew) {
		mIsNew = isNew;
	}

	private int mCategoryId;;

	public int getCategoryId() {
		return mCategoryId;
	}

	public void setCategoryId(int categoryId) {
		mCategoryId = categoryId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		mText = text;
	}

	public int isIsFavorite() {
		return mIsFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		mIsFavorite = isFavorite;
	}

}
