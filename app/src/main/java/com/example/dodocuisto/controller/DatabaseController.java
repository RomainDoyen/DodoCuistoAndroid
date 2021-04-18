package com.example.dodocuisto.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.dodocuisto.database.RecetteDatabase;
import com.example.dodocuisto.database.SQLiteDatabaseHelper;
import com.example.dodocuisto.modele.Recette;

import java.util.List;

import com.example.dodocuisto.controller.RecetteController;
import com.example.dodocuisto.database.UserDatabase;
import com.example.dodocuisto.modele.Recette;
import com.example.dodocuisto.modele.User;
import com.example.dodocuisto.controller.UserPreferences;

public class DatabaseController {
    private static DatabaseController instance;
    private static final String DATABASE_NAME = "recipesDatabase";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

    private UserDatabase userDatabase;
    private RecetteDatabase recetteDatabase;

    public static DatabaseController getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseController.class) {
                if (instance == null)
                    instance = new DatabaseController(context).open();
            }
        }

        return instance;
    }

    private DatabaseController(Context context) {
        mContext = context;
        dbHelper = new SQLiteDatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION);
    }

    private DatabaseController open() {
        db = dbHelper.getWritableDatabase();
        userDatabase = new UserDatabase(db);
        recetteDatabase = new RecetteDatabase(db);
        return this;
    }

    public boolean signIn(String email, String password) {
        User currentUser = userDatabase.getUserByEmailAndPassword(email, password);
        UserPreferences.saveCurrentUser(mContext, currentUser);
        return currentUser != null;
    }

    public void addNewUser(User user) {
        userDatabase.insert(user);
    }

    public long addNewRecipe(Recette recipe) {
        return recetteDatabase.insert(recipe);
    }

    public void updateRecipe(Recette recipe) {
        recetteDatabase.update(recipe);
    }

    public void deleteRecipe(long recipeId) {
        recetteDatabase.deleteById(recipeId);
    }

    public List<Recette> getAllRecipesByCategory(String category) {
        return recetteDatabase.selectAllByCategory(category);
    }
}
