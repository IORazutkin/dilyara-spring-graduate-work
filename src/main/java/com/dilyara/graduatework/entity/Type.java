package com.dilyara.graduatework.entity;

public enum Type {
  PROFIT(0, "Доход"),
  COST(1, "Расход");

  private final int id;
  private final String title;

  Type (int id, String title) {
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
