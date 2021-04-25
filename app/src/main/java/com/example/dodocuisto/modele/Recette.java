package com.example.dodocuisto.modele;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


public class Recette implements Parcelable {
    private long id;
    private String name;
    private String category;
    private String description;
    private List<Ingredient> ingredients;
    private List<Direction> directions;
    private String imagePath;

    public Recette() {
        ingredients = new ArrayList<>();
        directions = new ArrayList<>();
    }

    public Recette(String category) {
        this();
        this.category = category;
    }

    public Recette(String name, String category, String description, String imagePath) {
        this(category);
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Recette(long id, String name, String category, String description, String imagePath) {
        this(name, category, description, imagePath);
        this.id = id;
    }

    public Recette(String name, String category, String description, List<Ingredient> ingredients, List<Direction> directions, String imagePath) {
        this(name, category, description, imagePath);
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Recette(long id, String name, String category, String description, List<Ingredient> ingredients, List<Direction> directions, String imagePath) {
        this(name, category, description, ingredients, directions, imagePath);
        this.id = id;
    }

    private Recette(Parcel in) {
        ingredients = new ArrayList<>();
        directions = new ArrayList<>();

        id = in.readLong();
        name = in.readString();
        category = in.readString();
        description = in.readString();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        in.readTypedList(directions, Direction.CREATOR);
        imagePath = in.readString();
    }

    public static final Creator<Recette> CREATOR = new Creator<Recette>() {
        @Override
        public Recette createFromParcel(Parcel in) {
            return new Recette(in);
        }

        @Override
        public Recette[] newArray(int size) {
            return new Recette[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(directions);
        parcel.writeString(imagePath);
    }
}
