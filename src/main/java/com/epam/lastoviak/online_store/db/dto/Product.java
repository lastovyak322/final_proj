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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && amount == product.amount && categoryId == product.categoryId && Objects.equals(name, product.name)
                && Objects.equals(price, product.price) && Objects.equals(lastUpdate, product.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, amount, categoryId, lastUpdate);
    }
}
