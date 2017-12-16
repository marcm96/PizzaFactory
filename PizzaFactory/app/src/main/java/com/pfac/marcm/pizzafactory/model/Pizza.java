package com.pfac.marcm.pizzafactory.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcm on 29/10/2017.
 */

public class Pizza {
    private String id;
    private String pizzaName;
    private String ingredients;
    private String weight;
    private int price;


    public Pizza() {
    }

    public Pizza(String id, String pizzaName, String ingredients, String weight, int price) {
        this.id = id;
        this.pizzaName = pizzaName;
        this.ingredients = ingredients;
        this.weight = weight;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "pizzaName='" + pizzaName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", weight='" + weight + '\'' +
                ", price=" + price +
                '}';
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pizzaName", pizzaName);
        result.put("ingredients", ingredients);
        result.put("weight", weight);
        result.put("price", price);

        return result;
    }
}
