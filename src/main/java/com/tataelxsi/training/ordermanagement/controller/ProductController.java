package com.tataelxsi.training.ordermanagement.controller;

import com.tataelxsi.training.ordermanagement.entity.ProductEntity;
import com.tataelxsi.training.ordermanagement.exceptions.*;
import com.tataelxsi.training.ordermanagement.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "https://eoms-app.vercel.app/")  // Allow requests from the frontend
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductEntity product) {
        if(product.getSerialNum()==null || product.getSerialNum()<=0)
            throw new InvalidSerialNumberException("Enter valid Serial Number");
        else if(product.getProdId()==null || product.getProdId().isEmpty())
            throw new InvalidProductIdException("Enter valid Product ID");
        else if(product.getProdName()==null || product.getProdName().isEmpty())
            throw new InvalidProductNameException("Enter valid Product Name");
        else if(product.getProdPrice()==null || product.getProdPrice()<=0)
            throw new InvalidProductPriceException("Enter valid Product Price");
        else {
            productService.addProduct(product);
            return "Product "+ product.getProdName() +" has been added successfully.";
        }
    }

    @GetMapping("/get/{serialNum}")
    public ProductEntity getProduct(@PathVariable Integer serialNum) {
        if(productService.isPresent(serialNum))
            return productService.getProduct(serialNum);
        else
            throw new ProductNotFoundException("No product found with serial num: "+ serialNum);
    }

    @GetMapping("/all")
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> ref = productService.getAllProducts();
        if(ref.isEmpty())
            throw new ListEmptyException("No Products Found!");
        return productService.getAllProducts();
    }

    @PutMapping("/update/{serialNum}")
    public String updateProduct(@PathVariable Integer serialNum, @RequestBody ProductEntity updatedProduct) {
        if(updatedProduct.getSerialNum()==null || updatedProduct.getSerialNum()<=0)
            throw new InvalidSerialNumberException("Enter valid Serial Number");
        else if(updatedProduct.getProdId()==null || updatedProduct.getProdId().isEmpty())
            throw new InvalidProductIdException("Enter valid Product ID");
        else if(updatedProduct.getProdName()==null || updatedProduct.getProdName().isEmpty())
            throw new InvalidProductNameException("Enter valid Product Name");
        else if(updatedProduct.getProdPrice()==null || updatedProduct.getProdPrice()<=0)
            throw new InvalidProductPriceException("Enter valid Product Price");
        else {
            if(!productService.isPresent(serialNum))
                throw new ProductNotFoundException("No product with serial number: "+ serialNum);
            productService.updateProduct(serialNum, updatedProduct);
            return "Product "+ updatedProduct.getProdName() + " has been updated successfully.";
        }
    }

    @DeleteMapping("/delete/{serialNum}")
    public String deleteProduct(@PathVariable Integer serialNum) {
        if(productService.isPresent(serialNum)) {
            productService.deleteProduct(serialNum);
            return "Product with serial number "+ serialNum +" has been deleted successfully.";
        }
        else
            throw new ProductNotFoundException("No product with serial number: "+ serialNum);
    }
}
