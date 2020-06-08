package com.yst.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONBean implements Serializable {

	private static final long serialVersionUID = 6619202867262243183L;
	
	
	private Boolean flag;
	private String msg;
	private Object obj;
	private List<?> array;
	private Map<String, Object> map;
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public List<?> getArray() {
		return array;
	}
	public void setArray(List<?> array) {
		this.array = array;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public void put(String key, Object value){
		if(map == null) {
			map = new HashMap<String, Object>();
		}
		map.put(key, value);
	}
	
	
}
