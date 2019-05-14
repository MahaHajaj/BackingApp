package com.example.backingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    @SerializedName("id")
    private
    String id;
    @SerializedName("name")
    private
    String name;
    @SerializedName("ingredients")
    private
    ArrayList<Ingredient> ingredients;
    @SerializedName("steps")
    private
    ArrayList<Step> steps;
    @SerializedName("servings")
    private
    int serving;

    public Recipe(String id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int serving) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.serving = serving;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getServing() {
        return serving;
    }
}
