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
            loadGateauPatateRecette();
            loadBonbonsMielRecette();
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
        ingredients.add(new Ingredient("1 bringelle ( aubergine )."));
        ingredients.add(new Ingredient("800 gr de boucané."));
        ingredients.add(new Ingredient("4 oignons."));
        ingredients.add(new Ingredient("4 tomates."));
        ingredients.add(new Ingredient("4 gousses d'ail."));
        ingredients.add(new Ingredient("2 piments."));
        ingredients.add(new Ingredient("1 cuillère à café de curcuma."));
        ingredients.add(new Ingredient("1 petite branche de thym."));
        ingredients.add(new Ingredient("3 cuillères à soupe d'huile."));
        ingredients.add(new Ingredient("Sel."));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Préparation de la recette : Coupez le boucané en morceaux de deux centimètres environ."));
        directions.add(new Direction("Dans une marmite faites bouillir le boucané pendant cinq minutes. Égouttez, renouveler cette opération encore une fois. Coupez l aubergine en gros morceaux et les tomates en petits morceaux."));
        directions.add(new Direction("Pelez l ail, et pilez avec une pincée de sel et les piments."));
        directions.add(new Direction("Émincez les oignons."));
        directions.add(new Direction("Mettez trois cuillères à soupe d huile dans une sauteuse et faites dorer le boucané. Ajoutez les oignons et laissez-les roussir."));
        directions.add(new Direction("Ajoutez le mélange pillé ( piments, ail, sel ), les morceaux de tomates, le curcuma, le thym, mélangez et laissez suer 2 à 3 minutes."));
        directions.add(new Direction("Ajoutez les morceaux de bringelle ( aubergine ) et laissez cuire 30 min environ, à feu doux et à couvert. Tournez régulièrement pendant la cuisson. (si nécessaire rajoutez de l huile ou un peu d'eau)."));
        directions.add(new Direction("Servez chaud.\n"));
        databaseAdapter.addNewRecipe(new Recette("Boucane bringelle", "Plats Créoles", "Niveau de difficulté : Facile.", ingredients, directions, file.getAbsolutePath()));
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
        ingredients.add(new Ingredient("Huile d'olive"));
        ingredients.add(new Ingredient("Curcuma"));
        ingredients.add(new Ingredient("Sel"));
        ingredients.add(new Ingredient("Poivre"));
        ingredients.add(new Ingredient("Thym"));
        ingredients.add(new Ingredient("4 Tomates"));
        ingredients.add(new Ingredient("6 Saucisses fumées"));
        ingredients.add(new Ingredient("6 gousse d'ail"));
        ingredients.add(new Ingredient("4 Oignons"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Piquez les saucisses et mettez les dans l'eau bouillante pendant 10 mn, dans la même marmite qui vous servira à la préparation du plat afin de garder les saveurs."));
        directions.add(new Direction("Videz la marmite. Réservez les saucisses. Faites-y chauffer de l'huile d'olive. Faites revenir les oignons émincés et l'ail écrasé sans qu'ils soient trop colorés."));
        directions.add(new Direction("Coupez les saucisses en troncons de 1,5 cm puis faites les revenir avec les oignons. Au bout de 5 mn, ajoutez les tomates coupées en petits morceaux et les aromates (le piment en morceaux). Mélangez le tout puis laisser mijoter sur feu doux en ôtant le couvercle de temps à autre pour éliminer l'excès d'eau. Servez avec du riz thai"));
        databaseAdapter.addNewRecipe(new Recette("Rougaille Saucisse", "Plats Créoles", "Niveau de difficulté : Facile.", ingredients, directions, file.getAbsolutePath()));
    }

    private void loadGateauPatateRecette() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.gateaupatatedouce);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "gateaupatatedouce.jpeg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("1 kg de patates douces "));
        ingredients.add(new Ingredient("180 g de sucre roux"));
        ingredients.add(new Ingredient("160 g de beurre"));
        ingredients.add(new Ingredient("100 g de farine"));
        ingredients.add(new Ingredient("3 œufs"));
        ingredients.add(new Ingredient("Les graines d’1 gousse de vanille"));
        ingredients.add(new Ingredient("2 c à s de rhum blanc"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Épluchez les patates douces, coupez-les en gros cubes et faites-les cuire à la vapeur 8 min à l’autocuiseur ou une trentaine de minutes dans une casserole d’eau, jusqu’à ce qu’elles soient tendres."));
        directions.add(new Direction("Préchauffez votre four à 180°C, chaleur tournante. Laisse tiédir un peu et écrasez les patates à la fourchette (surtout pas de mixeur qui donnera une pâte collante), incorporez le sucre roux et le beurre en morceaux. Mélangez bien."));
        directions.add(new Direction("Ajoutez en plusieurs fois, les œufs battus en omelette, mélangez-bien."));
        directions.add(new Direction("Versez la farine puis mélangez jusqu’à obtenir une pâte homogène."));
        directions.add(new Direction("Incorporez finalement les graines de vanille et le rhum. Mélangez pour les incorporer."));
        directions.add(new Direction("Versez la pâte dans un moule à manqué ou à charnière graissé."));
        directions.add(new Direction("Avec une fourchette, faites un dessin sur le dessus style quadrillage."));
        directions.add(new Direction("Enfournez pour 40 à 45 min.(le temps de cuisson peut varier selon les fours, l’épaisseur du gâteau…). La lame d’un couteau doit ressortir sèche."));
        directions.add(new Direction("Laissez refroidir le gâteau avant de le démouler."));
        databaseAdapter.addNewRecipe(new Recette("Gâteau patate (gâteau de patates douces)", "Dessert", "Niveau de difficulté : Moyen", ingredients, directions, file.getAbsolutePath()));
    }

    private void loadBonbonsMielRecette() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bonbonmiel);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "bonbonmiel.jpeg");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("250 g de farine de riz"));
        ingredients.add(new Ingredient("100 g de farine T45"));
        ingredients.add(new Ingredient("1 sachet de levure chimique"));
        ingredients.add(new Ingredient("1 pincée de sel"));
        ingredients.add(new Ingredient("Eau froide"));
        ingredients.add(new Ingredient("200 g de sucre"));
        ingredients.add(new Ingredient("200 g de miel"));
        ingredients.add(new Ingredient("½ gousse de vanille"));
        ingredients.add(new Ingredient("Les zestes d’1/2 orange non traitée"));
        ingredients.add(new Ingredient("Huile"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Dans un saladier, mélanger au fouet les farines, le sel et la levure. Ajouter de l'eau en fouettant jusqu'à obtenir un ruban."));
        directions.add(new Direction("La pâte doit être lisse, pas trop épaisse, pas trop liquide. Laisser reposer au minimum 1 heure à température ambiante."));
        directions.add(new Direction("Pendant ce temps, dans une casserole, mélanger le sucre, le miel, la vanille fendue dans le sens de la longueur et les zestes d'orange."));
        directions.add(new Direction("Versez la farine puis mélangez jusqu’à obtenir une pâte homogène."));
        directions.add(new Direction("Faire chauffer à feu doux et laisser réduire de moitié. Laisser refroidir. Dans une poêle, chauffer environ 3 cm d'huile."));
        directions.add(new Direction("Remplir une seringue à pâtisserie et former des spirales d'environ 10 cm de diamètre en revenant au centre pour les souder. Dorer des 2 côtés."));
        directions.add(new Direction("Mettre à égoutter sur une assiette recouverte de papier absorbant."));
        directions.add(new Direction("Tremper les beignets encore chauds dans le sirop, puis mettre à égoutter sur une grille à pâtisserie."));
        directions.add(new Direction("Conserver dans une boite hermétique."));
        databaseAdapter.addNewRecipe(new Recette("Bonbons Miel", "Dessert", "Niveau de difficulté : Moyen", ingredients, directions, file.getAbsolutePath()));
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
