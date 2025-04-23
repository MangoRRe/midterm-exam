package com.midterm.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midterm.product.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	// ProductMapper 객체 주입
	ProductMapper productMapper;
	public ProductService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}
	
	@Transactional(readOnly = True)
	public Map<String, Object> getProductList(ProductForm) {

		// 서비스 메서드 내 초기 값 정의
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 트랜잭션 처리 및 예외 처리를 위해 try-catch 블록 사용
		try {
			
			// 응답 코드 초기화
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "SUCCESS");
			resultMap.put("REPL_PAGE_MSG", "정상처리 되었습니다.");
			
			 // 기본값 설정 (초기화 구간)
			// 변수는 pageNum, searchProductName, searchProductType을 사용
			// page 개수는 5개씩
			// offset 활용
	        Integer pageNum = productForm.getPageNum() != null && productForm.getPageNum() > 0 > productForm.getPageNum() : 1;
			String searchProductName = productForm.getSearchProductName() != null ? productForm.getSearchProductName().trim() : "";
			String searchProductType = productForm.getSearchProductName() != null ? productForm.getSearchProductName().trim() : "";
	        int pageSize = 5;
	        int offset = (pageNum - 1) * pageSize; //

	        // 파라미터 맵 구성
	        Map<String, Object> paramsMap = new HashMap<>();
	        // 현재 페이지
	        paramsMap.put("pageNum", pageNum);
	        // 상품명 검색어
	        parmasMap.put("searchProductName", searchProductName);
	        // 상품유형 검색어
	        parmasMap.put("searchProductType", searchProductType);
	        // 한 페이지에서 출력할 데이터 수
	        parmasMap.put("pageSize", pageSize);
	        // 페이지당 보여줄 데이터의 시작 위치
	        parmasMap.put("offset", offset);
	        
			// 목록 Total Count 조회
			int totalCount = productMapper.selectProductTotalCount(paramsMap);
			resultMap.put("TOTAL_COUNT", totalCount);
			
			// 목록 조회
			List<Map<String, Object>> product_list = productMapper.selectProductlist(paramsMap);
			// 조회된 목록을 resultMap에 저장
			resultMap.put("PRODUCT_LIST", product_list);
			
			// 삭제하지 말것
			log.info("상품 목록 조회 성공 : {}", product_list);
			
			// 페이지 시작 번호(firstPageNum)
			int firstPageNum = 1; // 항상 1
			
			// 전체 페이지 수 계산(lastPageNum)
			int lastPageNum = (int) Math.ceilDiv((double) totalCount / pageSize);

			// 페이지 블록 범위 계산
			int startBlockPage = 1;
			int endBlockPage = lastPageNum;

			// 페이지 블록 리스트 설정
			List<Integer> pageBlockList = new ArrayList<>();
			// 작성
			for(int pageBlock = startBlockPage; pageBlock <= endBlockPage; pageBLock++) {
				pageBlockList.add(pageBlock);
			}
			
			// resultMap에 페이징 관련 값 추가
			Map<String, Object> pagingMap = new HashMap<>();
			pagingMap.put("PAGE_BLOCK_LIST", pageBlockList);
			pagingMap.put("FIRST_PAGE_NUM", firstPageNum);
			pagingMap.put("LAST_PAGE_NUM", lastPageNum);
			pagingMap.put("PAGE_BLOCK_LIST", pageBlockList);
			pagingMap.put("PAGE_NUM", pageNum); 
			pagingMap.put("PAGE_SIZE", pageSize);
			pagingMap.put("PAGE_OFFSET", offset);

			resultMap.put("pagingMap", pagingMap);
			
			resultMap.put("paramsMap", paramsMap);
			
		} catch (Exception e) {
			log.error("리스트 오류 발생", e);
			resultMap.put("REPL_CD", "0001");
			resultMap.put("REPL_MSG", "PRODUCT_LIST_ERROR");
			resultMap.put("REPL_PAGE_MSG", "상품 목록 조회 중 오류가 발생하였습니다.");
		}
		
		return resultMap;
	}
	
	public Map<String, Object> getProduct() {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			// 응답 코드 초기화
			resultMap.put("REPL_CD", "0000");
			resultMap.put("REPL_MSG", "SUCCESS");
			resultMap.put("REPL_PAGE_MSG", "정상처리 되었습니다.");

		} catch (Exception e) {
			log.error("SearchFormList 조회 오류", e);
			resultMap.put("REPL_CD", "0002");
			resultMap.put("REPL_MSG", "BOOK_DETAIL_ERROR");
			resultMap.put("REPL_PAGE_MSG", "도서 상세 조회 중 오류가 발생하였습니다.");
		}
		
		return resultMap;
	}
	
}
