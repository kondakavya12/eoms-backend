package com.tataelxsi.training.ordermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCT_DATABASE")
public class ProductEntity {

    @Id
    @Column(name = "S_NO")
    private Integer serialNum;

    @Column(name = "ProductId")
    private String prodId;
    @Column(name = "ProductName")
    private String prodName;
    @Column(name = "ProductPrice")
    private Long prodPrice;

    public ProductEntity(){

    }

    public ProductEntity(Integer serialNum, String prodId, String prodName, Long prodPrice){
        super();
        this.serialNum = serialNum;
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Long getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Long prodPrice) {
        this.prodPrice = prodPrice;
    }
}
