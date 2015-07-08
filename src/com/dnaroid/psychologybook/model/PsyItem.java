package com.dnaroid.psychologybook.model;

public class PsyItem {

	private String mTitle;
	private int mId;
	private int mCategoryId;
	private int mIsFavorite;
	private int mIsNew;
	private int mIsFree;
	
	
	public PsyItem(String title, int id, int categoryId, int isNew, int isFree){
		this.mId = id;
		this.mCategoryId = categoryId;
		this.mIsNew = isNew;
		this.mIsFree = isFree;
		this.mTitle = title;
	}
	
	public PsyItem(){
		
	}

	public int getIsFree() {
		return mIsFree;
	}

	public void setIsFree(int isFree) {
		mIsFree = isFree;
	}

	public int getIsFavorite() {
		return mIsFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		mIsFavorite = isFavorite;
	}

	public int getIsNew() {
		return mIsNew;
	}

	public void setIsNew(int isNew) {
		mIsNew = isNew;
	}

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

}
