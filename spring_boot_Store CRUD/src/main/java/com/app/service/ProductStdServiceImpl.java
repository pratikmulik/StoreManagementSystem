package com.app.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.ProductStdDao;
import com.app.dto.ProductDTO;
import com.app.dto.ProductStdDTO;
import com.app.entities.ProductStd;
@Service
public class ProductStdServiceImpl implements  ProductStdService {

	@Autowired
	private ProductStdDao productStdRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ProductStdDTO createProductStd(ProductStdDTO dto) {
		ProductStd product =mapper.map(dto, ProductStd.class);
		
		ProductStd savedProductStd=productStdRepo.save(product);
		System.out.println("Product saved in Master data");
		return mapper.map(savedProductStd, ProductStdDTO.class);
	}

	@Override
	public ProductStdDTO findProductStdById(Long productId) {
		//Optional<ProductStd> product=productStdRepo.findById(productId);
		//return mapper.map(product, ProductStdDTO.class);	
		
		return mapper.map(productStdRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Invalid Product ID or Product not yet saved"))
				, ProductStdDTO.class);
	}

	@Override
	public ProductStdDTO updateProductStd(Long productId, ProductStdDTO dto) {
		ProductStd product=productStdRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Invalid Product Id!!!"));
		mapper.map(dto, product);
		System.out.println("After mapping user="+product);
		productStdRepo.save(product);
		dto.setProductId(productId);
		return dto;
	}
	

	


}
