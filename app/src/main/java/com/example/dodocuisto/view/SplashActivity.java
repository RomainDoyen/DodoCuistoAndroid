package com.example.dodocuisto.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dodocuisto.controller.DatabaseController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dodocuisto.R;
import com.example.dodocuisto.modele.Direction;
import com.example.dodocuisto.modele.Ingredient;
import com.example.dodocuisto.modele.Recette;
import com.example.dodocuisto.modele.User;
import com.example.dodocuisto.utils.UserPreferences;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_TO_WRITE = 1;
    private DatabaseController databaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseAdapter = DatabaseController.getInstance(this);

        if (UserPreferences.isFirstRun(this)) {
            UserPreferences.setIsFirstRun(this, false);

            databaseAdapter.addNewUser(new User("testrun", "test runner", "testrun@recipeapp.com", "password"));

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_TO_WRITE);
            } else {
                loadDefaultRecipes();
                navigateToLogin();
            }
        } else {
            if (UserPreferences.isUserLoggedIn(this))
                navigateToMainPage();
            else
                navigateToLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TO_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadDefaultRecipes();
                else
                    Toast.makeText(this, "Permission denied to write default recipes.", Toast.LENGTH_LONG).show();

                navigateToLogin();
                break;
        }
    }

    private void loadDefaultRecipes() {
        try {
            loadRougailSaucissesRecette();
            loadBoucaneBringellRecette();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBoucaneBringellRecette() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.boucanebringelle);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "boucanebringelle.jpg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Ingrédients : Pour 4 personnes."));
        ingredients.add(new Ingredient("- 1 bringelle ( aubergine )."));
        ingredients.add(new Ingredient("- 800 gr de boucané."));
        ingredients.add(new Ingredient("- 4 oignons."));
        ingredients.add(new Ingredient("- 4 tomates."));
        ingredients.add(new Ingredient("- 4 gousses d'ail."));
        ingredients.add(new Ingredient("- 2 piments."));
        ingredients.add(new Ingredient("- 1 cuillère à café de curcuma."));
        ingredients.add(new Ingredient("- 1 petite branche de thym."));
        ingredients.add(new Ingredient("- 3 cuillères à soupe d'huile."));
        ingredients.add(new Ingredient("- Sel."));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Préparation de la recette :\n" + "Coupez le boucané en morceaux de deux centimètres environ.\n"));
        directions.add(new Direction("Dans une marmite faites bouillir le boucané pendant cinq minutes. Égouttez, renouveler cette opération encore une fois.\n" +
                "\n" +
                "Coupez l'aubergine en gros morceaux et les tomates en petits morceaux.\n" +
                "\n" +
                "Pelez l'ail, et pilez avec une pincée de sel et les piments.\n" +
                "\n" +
                "Émincez les oignons.\n" +
                "\n" +
                "Mettez trois cuillères à soupe d'huile dans une sauteuse et faites dorer le boucané. Ajoutez les oignons et laissez-les roussir.\n" +
                "\n" +
                "Ajoutez le mélange pillé ( piments, ail, sel ), les morceaux de tomates, le curcuma, le thym, mélangez et laissez suer 2 à 3 minutes.\n" +
                "\n" +
                "Ajoutez les morceaux de bringelle ( aubergine ) et laissez cuire 30 min environ, à feu doux et à couvert. Tournez régulièrement pendant la cuisson. (si nécessaire rajoutez de l'huile ou un peu d'eau)."));
        directions.add(new Direction("Servez chaud.\n"));
        databaseAdapter.addNewRecipe(new Recette("Boucane bringelle",
                "Reunion", "Niveau de difficulté : Facile.", ingredients, directions, file.getAbsolutePath()));
    }

    private void loadRougailSaucissesRecette() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.rougailsaucisses);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "rougailsaucisses.jpg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("2 Tbs. extra-virgin olive oil"));
        ingredients.add(new Ingredient("2 medium cloves garlic, minced"));
        ingredients.add(new Ingredient("2 Tbs. chopped fresh aromatic herbs (thyme, sage, rosemary, marjoram, or a mix)"));
        ingredients.add(new Ingredient("⅓ cup soy sauce"));
        ingredients.add(new Ingredient("1 Tbs. kosher salt"));
        ingredients.add(new Ingredient("1 Tbs. ground black pepper"));
        ingredients.add(new Ingredient("1-1/2- to 2-lb. flank steak, trimmed of any excess fat and membrane"));
        ingredients.add(new Ingredient("1 recipe Chunky Tomato-Basil Vinaigrette"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Mix the oil, garlic, herbs, salt, and pepper in a small bowl. Rub all over the steak and let sit for about 20 min. at room temperature. Meanwhile, heat a gas grill to medium-high (you should be able to hold your hand 2 inches above the grate for 3 to 4 seconds) or prepare a medium-hot charcoal fire"));
        directions.add(new Direction("If your grill has a hot spot, position the thicker end of the flank steak nearer the hottest part of the fire. Grill until medium rare, 12 to 15 min., turning the steak every 3 to 4 min. to ensure even cooking. The thickest part of the steak will register 135°F to 140°F on an instant-read thermometer"));
        directions.add(new Direction("Transfer the steak to a cutting board and let it rest for 3 to 5 min. Slice across the grain, portion onto dinner plates, spoon on the vinaigrette, and serve."));
        databaseAdapter.addNewRecipe(new Recette("Mediterranean steak",
                "Mediterranean", "This steak gets a wet rub before grilling; the oil helps the other flavors spread.", ingredients, directions, file.getAbsolutePath()));
    }

    private void navigateToLogin() {
        Intent startIntent = new Intent(this, SigninActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void navigateToMainPage() {
        Intent startIntent = new Intent(this, MainActivity.class);
        startActivity(startIntent);
        finish();
    }
}
