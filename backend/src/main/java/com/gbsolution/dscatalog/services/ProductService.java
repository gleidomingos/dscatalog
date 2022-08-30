package com.gbsolution.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gbsolution.dscatalog.dto.CategoryDto;
import com.gbsolution.dscatalog.dto.ProductDto;
import com.gbsolution.dscatalog.entities.Category;
import com.gbsolution.dscatalog.entities.Product;
import com.gbsolution.dscatalog.repositories.CategoryRepository;
import com.gbsolution.dscatalog.repositories.ProductRepository;
import com.gbsolution.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
		Page<Product> page = repository.findAll(pageRequest);
		return page.map(x -> new ProductDto(x));

	}

	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not found "));
		return new ProductDto(entity, entity.getCategories());

	}

	@Transactional()
	public ProductDto insert(ProductDto dto) {
		Product product = new Product();
		copyDtoToEntity(dto, product);
		product = repository.save(product);
		return new ProductDto(product);

	}

	@Transactional()
	public ProductDto update(Long id, ProductDto dto) {
		try {

			Product product = repository.getOne(id);
			copyDtoToEntity(dto, product);
			product = repository.save(product);
			return new ProductDto(product);

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("Id Not found" + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not found" + id);
		} catch (DataIntegrityViolationException e) {
			throw new ResourceNotFoundException("Integrity violation");
		}

	}
	

	private void copyDtoToEntity(ProductDto dto, Product product) {
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setDate(dto.getDate());
		product.setImgUrl(dto.getImgUrl());
		product.getCategories().clear();
		for (CategoryDto catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			product.getCategories().add(category);
		}
	}


}

/*
 * List<ProductDto> lisDto = new ArrayList<>(); for (Product product : list)
 * { lisDto.add(new ProductDto(product)); }
 */