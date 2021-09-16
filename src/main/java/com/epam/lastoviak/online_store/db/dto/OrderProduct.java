package com.epam.lastoviak.online_store.db.dto;


import java.math.BigDecimal;

public class OrderProduct {

  private int
 receiptId;
  private int
 productId;
  private BigDecimal price;
  private int
 count;


  public int
 getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(int
 receiptId) {
    this.receiptId = receiptId;
  }


  public int
 getProductId() {
    return productId;
  }

  public void setProductId(int
 productId) {
    this.productId = productId;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public int
 getCount() {
    return count;
  }

  public void setCount(int
 count) {
    this.count = count;
  }

}
