package com.epam.lastoviak.online_store.db.dto;


public class Account {

  private String password;
  private String email;
  private String name;
  private String phone;
  private int id;
  private int roleId;
  private int statusId;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp lastUpdate;

  public Account() {
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
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

  @Override
  public String toString() {
    return "Account{" +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", id=" + id +
            ", roleId=" + roleId +
            ", statusId=" + statusId +
            '}';
  }
}
