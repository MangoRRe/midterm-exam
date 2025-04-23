package com.midterm.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
	
	int selectProductTotalCount(Map<String, Object> paramsMap);
		List<Map<String, Object>> selectProductlist(Map<String, Object> paramsMap);
}
