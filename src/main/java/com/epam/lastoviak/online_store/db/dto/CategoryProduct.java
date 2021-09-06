package com.epam.lastoviak.online_store.db.dto;


public class CategoryProduct {

  private long categoryId;
  private long productId;


  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;
  }


  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

}
