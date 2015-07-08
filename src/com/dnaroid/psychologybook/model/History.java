package com.dnaroid.psychologybook.model;

public class History {
	private int mCategoryId;
	private String mCurrentPage;
	private int mScrollPosition;

	public int getScrollPosition() {
		return mScrollPosition;
	}

	public void setScrollPosition(int scrollPosition) {
		mScrollPosition = scrollPosition;
	}

	public History(String currentPage, int categoryId) {
		this.mCurrentPage = currentPage;
		this.mCategoryId = categoryId;
	}

	public History(String currentPage, int categoryId, int scrollPosition) {
		this.mCurrentPage = currentPage;
		this.mCategoryId = categoryId;
		this.mScrollPosition = scrollPosition;
	}

	public int getCategoryId() {
		return mCategoryId;
	}

	public void setCategoryId(int categoryId) {
		mCategoryId = categoryId;
	}

	public String getCurrentPage() {
		return mCurrentPage;
	}

	public void setCurrentPage(String currentPage) {
		mCurrentPage = currentPage;
	}

}
