/**
 * 
 */
package com.asl.controller;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.asl.model.Product;

/**
 * @author zubayer
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public String loadProductPage(Model model) {
		ResponseEntity<Product[]> products = restTemplate.getForEntity("http://asl-product/pm1/product/products",
				Product[].class);
		model.addAttribute("products", Arrays.asList(products.getBody()));
		return "product";
	}

	@GetMapping("/delete/{pid}")
	public String deleteProduct(@PathVariable Long pid) {
		restTemplate.delete("http://asl-product/pm1/product/delete/" + pid);
		return "redirect:/product";
	}

	@GetMapping("/new")
	public String loadFormPage(Model model) {
		model.addAttribute("product", new Product());
		return "addproduct";
	}

	@GetMapping("/edit/{pid}")
	public String loadEditForm(@PathVariable Long pid, Model model) {
		ResponseEntity<Product> product = restTemplate.getForEntity("http://asl-product/pm1/product/" + pid,
				Product.class);
		model.addAttribute("product", product.getBody());
		return "addproduct";
	}

	@PostMapping("/save")
	public String saveProduct(Product product, Model model) {
		if (product == null || StringUtils.isBlank(product.getName())) {
			model.addAttribute("product", product);
			return "addproduct";
		}

		ResponseEntity<Product> savedproduct = restTemplate.postForEntity("http://asl-product/pm1/product/save",
				product, Product.class);
		model.addAttribute("product", savedproduct.getBody());
		return "addproduct";
	}

}
