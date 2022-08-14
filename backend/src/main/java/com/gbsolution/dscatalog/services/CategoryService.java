package com.gbsolution.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gbsolution.dscatalog.dto.CategoryDto;
import com.gbsolution.dscatalog.entities.Category;
import com.gbsolution.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDto> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());

	}
}

/*
 * List<CategoryDto> lisDto = new ArrayList<>(); for (Category category : list)
 * { lisDto.add(new CategoryDto(category)); }
 */