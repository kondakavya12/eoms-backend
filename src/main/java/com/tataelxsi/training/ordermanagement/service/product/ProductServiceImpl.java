package com.tataelxsi.training.ordermanagement.service.product;

import com.tataelxsi.training.ordermanagement.entity.ProductEntity;
import com.tataelxsi.training.ordermanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public ProductEntity getProduct(Integer serialNum) {
        return productRepository.findById(serialNum).get();
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void updateProduct(Integer serialNum, ProductEntity updatedProduct) {
        ProductEntity ref = getProduct(serialNum);
        ref.setSerialNum(updatedProduct.getSerialNum());
        ref.setProdId(updatedProduct.getProdId());
        ref.setProdName(updatedProduct.getProdName());
        ref.setProdPrice(updatedProduct.getProdPrice());
        productRepository.save(ref);
//        productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer serialNum) {
        productRepository.deleteById(serialNum);
    }

    public boolean isPresent(Integer serialNum) {
        if(productRepository.existsById(serialNum))
            return true;
        else
            return false;
    }
}
