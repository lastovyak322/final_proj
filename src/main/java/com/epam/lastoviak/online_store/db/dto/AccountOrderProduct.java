package com.epam.lastoviak.online_store.db.dto;


import java.math.BigDecimal;

public class AccountOrderProduct {

    private int accountOrderId;
    private int productId;
    private BigDecimal price;
    private int count;


    public int getAccountOrderId() {
        return accountOrderId;
    }

    public void setAccountOrderId(int accountOrderId) {
        this.accountOrderId = accountOrderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
