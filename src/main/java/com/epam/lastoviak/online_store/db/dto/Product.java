package com.epam.lastoviak.online_store.db.dto;


import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private int amount;
    private int categoryId;
    private java.sql.Timestamp lastUpdate;
    private int maxSpeed;
    private int maxLoad;
    private int manufacturer;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public java.sql.Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(java.sql.Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public int getManufacturerId() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && amount == product.amount && categoryId == product.categoryId && maxSpeed == product.maxSpeed && maxLoad == product.maxLoad && manufacturer == product.manufacturer && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(lastUpdate, product.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, amount, categoryId, lastUpdate, maxSpeed, maxLoad, manufacturer);
    }
}


