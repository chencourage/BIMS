package com.yst.util;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class JsonFilter{
	
	public static SimplePropertyPreFilter getExclude(String[] excludeProperty){
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		for (int i = 0; i < excludeProperty.length; i++) {
			filter.getExcludes().add(excludeProperty[i]);
		}
		return filter;
	}
	
	
	public static SimplePropertyPreFilter getInclude(String[] includeProperty){
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		for (int i = 0; i < includeProperty.length; i++) {
			filter.getIncludes().add(includeProperty[i]);
		}
		return filter;
	}
	
}
