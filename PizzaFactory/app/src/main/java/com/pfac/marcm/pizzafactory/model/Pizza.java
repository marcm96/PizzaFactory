package com.pfac.marcm.pizzafactory.model;

/**
 * Created by marcm on 29/10/2017.
 */

public class Pizza {
    private String pizzaName;
    private String ingredients;
    private String wight;
    private int price;

    public Pizza(String pizzaName, String ingredients, String wight, int price) {
        this.pizzaName = pizzaName;
        this.ingredients = ingredients;
        this.wight = wight;
        this.price = price;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getWight() {
        return wight;
    }

    public void setWight(String wight) {
        this.wight = wight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
