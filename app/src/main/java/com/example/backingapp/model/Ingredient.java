package com.example.backingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {
    @SerializedName("quantity")
    private
    Float quantity;
    @SerializedName("measure")
    private
    String measure;
    @SerializedName("ingredient")
    private
    String ingredient;

    public Ingredient(Float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
