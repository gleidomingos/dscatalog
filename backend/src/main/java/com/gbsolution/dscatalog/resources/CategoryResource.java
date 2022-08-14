package com.gbsolution.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbsolution.dscatalog.dto.CategoryDto;
import com.gbsolution.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> findAll() {
		List<CategoryDto> list = service.findAll();
		return ResponseEntity.ok().body(list);

	}

}
