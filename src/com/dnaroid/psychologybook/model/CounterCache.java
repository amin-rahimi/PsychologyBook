package com.dnaroid.psychologybook.model;

public class CounterCache {
	private int mCategoryId;
	private int mAllCount;
	private int mFreeCount;

	public int getCategoryId() {
		return mCategoryId;
	}

	public void setCategoryId(int categoryId) {
		mCategoryId = categoryId;
	}

	public int getAllCount() {
		return mAllCount;
	}

	public void setAllCount(int allCount) {
		mAllCount = allCount;
	}

	public int getFreeCount() {
		return mFreeCount;
	}

	public void setFreeCount(int freeCount) {
		mFreeCount = freeCount;
	}

}
