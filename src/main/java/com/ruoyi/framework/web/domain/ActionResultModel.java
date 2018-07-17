package com.ruoyi.framework.web.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回bean
 * @author liusheng
 *
 */
public class ActionResultModel<T> {

	private boolean suc=false;
	private List<T> records=new ArrayList<T>();
	
	public boolean isSuc() {
		return suc;
	}
	public void setSuc(boolean suc) {
		this.suc = suc;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}

	

}