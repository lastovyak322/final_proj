package com.epam.lastoviak.online_store.db.dto;

public class Order {

  private long id;
  private long accountId;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp lastUpdate;
  private long orderStatusId;
  private long approvedBy;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
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


  public long getOrderStatusId() {
    return orderStatusId;
  }

  public void setOrderStatusId(long orderStatusId) {
    this.orderStatusId = orderStatusId;
  }


  public long getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(long approvedBy) {
    this.approvedBy = approvedBy;
  }

}
