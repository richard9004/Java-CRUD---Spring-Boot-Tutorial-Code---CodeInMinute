package com.store.mystore.controllers;

import com.store.mystore.models.Product;
import com.store.mystore.models.ProductDto;
import com.store.mystore.services.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products",products);
        return "products/index";

    }

    @GetMapping("/create")
    public String showCreateProduct(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto",productDto);
        return "products/create-product";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result){

        if(result.hasErrors()){
            return "products/create-product";
        }

        // Set the unique filename in the product (assuming Product entity has an imageFileName field)
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        // Save the product in the repository
        repo.save(product);


        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable int id){
        try {

            Product product = repo.findById(id).get();
            model.addAttribute("product",product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto",productDto);



        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
            return "redirect:/products";
        }
        return "products/edit-product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(Model model, @PathVariable int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            Product product = repo.findById(id).get();
            model.addAttribute("product",product);
            return "products/edit-product";
        }

        // Retrieve existing product
        Product existingProduct = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));

        // Update product fields with data from the form
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setDescription(productDto.getDescription());

        // If you're handling image files, make sure to add logic here to save and update image paths

        // Save the updated product
        repo.save(existingProduct);
        // Redirect to the product list or details page
        return "redirect:/products";
    }


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        // Retrieve the product to confirm existence (optional)
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));

        // Delete the product
        repo.delete(product);

        // Redirect to the product list
        return "redirect:/products";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "products/view-product";
    }





}
