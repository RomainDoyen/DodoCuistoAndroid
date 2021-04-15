package com.example.dodocuisto.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.dodocuisto.modele.Recette;

import java.util.List;

import dao.RecipeDAO;
import dao.SQLiteDatabaseHelper;
import dao.UserDAO;
import com.example.dodocuisto.modele.Recette;
import com.example.dodocuisto.modele.User;
import utils.UserPreferences;

public class DatabaseController {
    private static DatabaseController instance;
    private static final String DATABASE_NAME = "recipesDatabase";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

    private UserDAO userDAO;
    private RecipeDAO recipeDAO;

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
        userDAO = new UserDAO(db);
        recipeDAO = new RecipeDAO(db);
        return this;
    }

    public boolean signIn(String email, String password) {
        User currentUser = userDAO.getUserByEmailAndPassword(email, password);
        UserPreferences.saveCurrentUser(mContext, currentUser);
        return currentUser != null;
    }

    public void addNewUser(User user) {
        userDAO.insert(user);
    }

    public long addNewRecipe(Recette recipe) {
        return recipeDAO.insert(recipe);
    }

    public void updateRecipe(Recette recipe) {
        recipeDAO.update(recipe);
    }

    public void deleteRecipe(long recipeId) {
        recipeDAO.deleteById(recipeId);
    }

    public List<Recette> getAllRecipesByCategory(String category) {
        return recipeDAO.selectAllByCategory(category);
    }
}
