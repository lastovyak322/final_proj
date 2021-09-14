package com.epam.lastoviak.online_store.db.dto;


public class ProductDescription {

  private int productId;
  private int languageId;
  private String description;


  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }


  public int getLanguageId() {
    return languageId;
  }

  public void setLanguageId(int languageId) {
    this.languageId = languageId;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
