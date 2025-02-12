package com.tataelxsi.training.ordermanagement.service.product;

import com.tataelxsi.training.ordermanagement.entity.ProductEntity;
import java.util.List;

public interface ProductService {

    void addProduct(ProductEntity product);

    ProductEntity getProduct(Integer serialNum);

    List<ProductEntity> getAllProducts();

    void updateProduct(Integer serialNum, ProductEntity updatedProduct);

    void deleteProduct(Integer serialNum);

    boolean isPresent(Integer serialNum);
}
