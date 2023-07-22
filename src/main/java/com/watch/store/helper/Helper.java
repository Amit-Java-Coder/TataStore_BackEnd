package com.watch.store.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.watch.store.dto.PageableResponse;


public class Helper {

	   public static <U,V> PageableResponse<V> getPageAbleResponse(Page<U> page,Class<V> type){
			List<U> entity= page.getContent();
			List<V> list= entity.stream().map(obj -> new ModelMapper().map(obj, type)).collect(Collectors.toList());
			
			PageableResponse<V> pageableResponse=new PageableResponse<>();
			pageableResponse.setContent(list);
			pageableResponse.setPageNumber(page.getNumber());
			pageableResponse.setPageSize(page.getSize());
			pageableResponse.setTotalElement(page.getTotalElements());
			pageableResponse.setTotalPage(page.getTotalPages());
			pageableResponse.setLastpage(page.isLast());
			
			return pageableResponse;
	   }
}
