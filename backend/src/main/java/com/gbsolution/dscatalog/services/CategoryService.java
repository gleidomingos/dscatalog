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
import com.gbsolution.dscatalog.entities.Category;
import com.gbsolution.dscatalog.repositories.CategoryRepository;
import com.gbsolution.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDto> findAllPaged(PageRequest pageRequest) {
		Page<Category> page = repository.findAll(pageRequest);
		return page.map(x -> new CategoryDto(x));

	}

	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not found "));
		return new CategoryDto(entity);

	}

	@Transactional()
	public CategoryDto insert(CategoryDto dto) {
		Category category = new Category();
		category.setName(dto.getName());
		category = repository.save(category);
		return new CategoryDto(category);

	}

	@Transactional()
	public CategoryDto update(Long id, CategoryDto dto) {
		try {

			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDto(entity);

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

}

/*
 * List<CategoryDto> lisDto = new ArrayList<>(); for (Category category : list)
 * { lisDto.add(new CategoryDto(category)); }
 */