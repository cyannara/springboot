package com.example.demo.common;

import lombok.Data;

@Data
public class Paging {
	
	int pageUnit=10 ; 		//한페이지 출력할 레코드 건수
	int pageSize=10 ; 		//페이지번호 수 (5) 1~ 5까지
	long lastPage;     		//마지막 페이지번호
	long totalRecord;		//전체 레코드건수
	Integer page = 1;		//현재 페이지
	long startPage;			//페이지그룹내에서 시작페이지번호
	long endPage;			//페이지그룹내에서 마지막페이지번호
	int first;
	int last;
	
	public int getFirst() {
		first = (getPage() - 1) * getPageUnit() + 1;
		return first;
	}

	public int getLast() {
		last = getPage() * getPageUnit();
		return last;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getLastPage() {
		lastPage = totalRecord / pageUnit + 
				   ( totalRecord % pageUnit>0 ? 1 : 0 );
		return lastPage;
	}

	public void setLastPage(long lastPage) {
		this.lastPage = lastPage;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public long getStartPage() {
		startPage = (page-1)/pageSize * pageSize + 1;
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public long getEndPage() {
		endPage = (page-1)/pageSize  * pageSize  + pageSize ;
		if ( endPage > getLastPage() )
			endPage = getLastPage() ;
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
}