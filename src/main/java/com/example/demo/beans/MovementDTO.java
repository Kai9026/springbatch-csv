package com.example.demo.beans;

public class MovementDTO {
  private String valueDate;
  private String category;
  private String subcategory;
  private String description;
  private String comment;
  private String image;
  private Float amount;
  private Float balance;

  public MovementDTO() {
    super();
  }

  public MovementDTO(
      String valueDate,
      String category,
      String subcategory,
      String description,
      String comment,
      String image,
      Float amount,
      Float balance) {
    super();
    this.valueDate = valueDate;
    this.category = category;
    this.subcategory = subcategory;
    this.description = description;
    this.comment = comment;
    this.image = image;
    this.amount = amount;
    this.balance = balance;
  }

  public String getValueDate() {
    return valueDate;
  }

  public void setValueDate(String valueDate) {
    this.valueDate = valueDate;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(String subcategory) {
    this.subcategory = subcategory;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public Float getBalance() {
    return balance;
  }

  public void setBalance(Float balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "Movement [valueDate="
        + valueDate
        + ", category="
        + category
        + ", subcategory="
        + subcategory
        + ", description="
        + description
        + ", comment="
        + comment
        + ", image="
        + image
        + ", amount="
        + amount
        + ", balance="
        + balance
        + "]";
  }
}
