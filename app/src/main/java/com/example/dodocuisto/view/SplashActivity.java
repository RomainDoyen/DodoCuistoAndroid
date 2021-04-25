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

            databaseAdapter.addNewUser(new User("dodocuisto", "dodo cuisto", "dodocuisto@gmail.com", "password"));

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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadDefaultRecipes();
                else
                    Toast.makeText(this, "Permission refusée pour écrire les recettes par défaut.", Toast.LENGTH_LONG).show();

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
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
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
        ingredients.add(new Ingredient("-Huile d'olive"));
        ingredients.add(new Ingredient("-Curcuma"));
        ingredients.add(new Ingredient("-Sel"));
        ingredients.add(new Ingredient("-Poivre"));
        ingredients.add(new Ingredient("-Thym"));
        ingredients.add(new Ingredient("-4 Tomates"));
        ingredients.add(new Ingredient("-6 Saucisses fumées"));
        ingredients.add(new Ingredient("-6 gousse d'ail"));
        ingredients.add(new Ingredient("-4 Oignons"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Piquez les saucisses et mettez les dans l'eau bouillante pendant 10 mn, dans la même marmite qui vous servira à la préparation du plat afin de garder les saveurs."));
        directions.add(new Direction("Videz la marmite. Réservez les saucisses. Faites-y chauffer de l'huile d'olive. Faites revenir les oignons émincés et l'ail écrasé sans qu'ils soient trop colorés.\n" +
                "\n"));
        directions.add(new Direction("Coupez les saucisses en troncons de 1,5 cm puis faites les revenir avec les oignons. Au bout de 5 mn, ajoutez les tomates coupées en petits morceaux et les aromates (le piment en morceaux). Mélangez le tout puis laisser mijoter sur feu doux en ôtant le couvercle de temps à autre pour éliminer l'excès d'eau. Servez avec du riz thai"));
        databaseAdapter.addNewRecipe(new Recette("Rougaille Saucisse",
                "Reunion", "Niveau de difficulté : Facile.", ingredients, directions, file.getAbsolutePath()));
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
