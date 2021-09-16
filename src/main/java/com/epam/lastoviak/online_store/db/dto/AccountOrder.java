package com.epam.lastoviak.online_store.db.dto;

public class AccountOrder {

  private int id;
  private int accountId;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp lastUpdate;
  private int orderStatusId;
  private int approvedBy;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public java.sql.Timestamp getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }


  public int getOrderStatusId() {
    return orderStatusId;
  }

  public void setOrderStatusId(int orderStatusId) {
    this.orderStatusId = orderStatusId;
  }


  public int getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(int approvedBy) {
    this.approvedBy = approvedBy;
  }

}
