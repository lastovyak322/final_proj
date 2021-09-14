package com.epam.lastoviak.online_store.db.dto;



public class ProductSpecification {

  private int productId;
  private double maxSpeed;
  private int maxLoad;
  private int powerReserve;
  private String manufacturer;


  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }


  public double getMaxSpeed() {
    return maxSpeed;
  }

  public void setMaxSpeed(double maxSpeed) {
    this.maxSpeed = maxSpeed;
  }


  public int getMaxLoad() {
    return maxLoad;
  }

  public void setMaxLoad(int maxLoad) {
    this.maxLoad = maxLoad;
  }


  public int getPowerReserve() {
    return powerReserve;
  }

  public void setPowerReserve(int powerReserve) {
    this.powerReserve = powerReserve;
  }


  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

}
