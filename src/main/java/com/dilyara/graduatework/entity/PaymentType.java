package com.dilyara.graduatework.entity;

public enum PaymentType {
  CASH(0, "Наличные"),
  CREDIT_CARD(1, "Кредитная карта"),
  CARD(2, "Карта");

  private final int id;
  private final String title;

  PaymentType (int id, String title) {
    this.id = id;
    this.title = title;
  }

  public int getId () {
    return id;
  }

  public String getTitle () {
    return title;
  }
}
