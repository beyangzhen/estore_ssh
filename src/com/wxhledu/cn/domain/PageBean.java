package com.wxhledu.cn.domain;

import java.util.List;

import lombok.Data;

@Data
public class PageBean<T> {

	/**
	 * 所有记录行
	 */
	private List<T> list;

	/**
	 * 总的记录条数
	 */
	private int totalRecord;

	/**
	 * 页大小
	 */
	private int pageSize;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 当前页数
	 */
	private int currentPage;

	/**
	 * 上一页
	 */
	private int prevPage;

	/**
	 * 下一页
	 */
	private int nextPage;

	/**
	 * 分布数目栏
	 */
	private int[] pageBar = new int[10];

	public int getTotalPage() {
		// 100 5 20
		// 101 5 21
		// 
		if (this.totalRecord % this.pageSize == 0) {
			this.totalPage = totalRecord / pageSize;
		} else {
			this.totalPage = totalRecord / pageSize + 1;

		}
		return this.totalPage;
	}

	public int getPrevPage() {
		if (this.currentPage - 1 < 1) {
			this.prevPage = 1;
		} else {
			this.prevPage = this.currentPage - 1;
		}
		return prevPage;
	}

	public int getNextPage() {
		if (this.currentPage >= this.totalPage || this.nextPage + 1 > this.totalPage) {
			this.nextPage = this.totalPage;
		} else {
			this.nextPage = this.currentPage + 1;
		}
		return nextPage;
	}

	public int[] getPageBar() {
		int[] pageBar = new int[this.totalPage];
		for (int i = 0; i < pageBar.length; i++) {
			pageBar[i] = i + 1;
		}
		this.pageBar = pageBar;
		return pageBar;
	}

}
