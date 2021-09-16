package com.epam.lastoviak.online_store.db.dto;


public class OrderProduct {

  private long receiptId;
  private long productId;
  private double price;
  private long count;


  public long getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(long receiptId) {
    this.receiptId = receiptId;
  }


  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

}
