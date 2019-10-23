package com.hackerone.demo.reporting.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils<T> {
	
	public static void copyNonNullProperties(Object src, Object target)
	{
		BeanUtils.copyProperties(src, target,getNullPropertyNames(src));
	}
	
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src= new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds=src.getPropertyDescriptors();
		
		Set<String> emptyNames = new HashSet<String>();
		for(java.beans.PropertyDescriptor pd : pds) {
			Object srcValue= src.getPropertyValue(pd.getName());
			//if(pd.getName().equalsIgnoreCase("reportId") || srcValue == null )
			if(srcValue == null )
				emptyNames.add(pd.getName());
		}
		
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
		
	}
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper= new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return objectMapper;
		
	}
}
