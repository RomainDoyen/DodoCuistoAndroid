package com.example.dodocuisto.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import com.example.dodocuisto.R;
import com.example.dodocuisto.controller.DatabaseController;
import com.example.dodocuisto.modele.Direction;
import com.example.dodocuisto.modele.Ingredient;
import com.example.dodocuisto.modele.Recette;
import com.example.dodocuisto.utils.Files;
import com.example.dodocuisto.utils.ResultCodes;
import com.example.dodocuisto.view.fragments.NavigationFragment;
import com.example.dodocuisto.view.fragments.RecetteDirectionFragment;
import com.example.dodocuisto.view.fragments.RecetteImageFragment;
import com.example.dodocuisto.view.fragments.RecetteIngredientFragment;


public class CreaRecetteActivity extends AppCompatActivity implements RecetteImageFragment.ImageListener, RecetteDirectionFragment.DirectionsListener, RecetteIngredientFragment.IngredientListener {
    private static final int REQUEST_OPEN_GALLERY = 10;
    private static final int REQUEST_TO_ACCESS_GALLERY = 11;

    private Recette currentRecipe;
    private boolean isUpdating;
    private String currentCategory;
    private DatabaseController databaseAdapter;

    private Button nextButton;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        databaseAdapter = DatabaseController.getInstance(this);

        Intent intent = getIntent();
        isUpdating = intent.getBooleanExtra("isUpdating", false);
        if (isUpdating)
            currentRecipe = intent.getParcelableExtra("recipe");
        else {
            currentCategory = intent.getStringExtra("category");
            currentRecipe = new Recette(currentCategory);
        }

        initializeUI();
        displayFragment(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment instanceof RecetteIngredientFragment)
            displayFragment(0);
        else if (fragment instanceof RecetteDirectionFragment)
            displayFragment(1);
        else
            super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_OPEN_GALLERY:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri imageData = data.getData();
                        String imageSrc = Files.getRealPathFromURI(this, imageData);
                        ((RecetteImageFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container)).onImageSelected(imageSrc);
                        currentRecipe.setImagePath(imageSrc);
                        break;
                }
        }
    }

    @NonNull
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TO_ACCESS_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) openGallery();
                else
                    Toast.makeText(this, "Permission refusée pour accéder à la galerie.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void displayFragment(int position) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        String nextButtonText = "";

        switch (position) {
            case 0:
                fragment = RecetteImageFragment.newInstance(currentRecipe);
                ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                nextButtonText = "Suivant";
                break;
            case 1:
                fragment = RecetteIngredientFragment.newInstance(currentRecipe);
                ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
                nextButtonText = "Suivant";
                break;
            case 2:
                fragment = RecetteDirectionFragment.newInstance(currentRecipe);
                ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
                nextButtonText = "Terminer";
                break;
        }

        nextButton.setText(nextButtonText);

        ft.replace(R.id.frame_container, fragment, "fragment" + position);
        ft.commit();
    }

    public void onNext(View view) {
        ((NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container)).onNext();
    }

    private void initializeUI() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextButton = findViewById(R.id.nextButton);
    }

    @Override
    public void navigateToIngredientsFragment(String name, String description) {
        currentRecipe.setName(name);
        currentRecipe.setDescription(description);
        displayFragment(1);
    }

    @Override
    public void navigateToDirectionsFragment(List<Ingredient> ingredients) {
        currentRecipe.setIngredients(ingredients);
        displayFragment(2);
    }

    @Override
    public void onSelectImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_TO_ACCESS_GALLERY);
        } else {
            openGallery();
        }
    }

    @Override
    public void onStepsFinished(List<Direction> directions) {
        currentRecipe.setDirections(directions);
        if (isUpdating)
            databaseAdapter.updateRecipe(currentRecipe);
        else
            databaseAdapter.addNewRecipe(currentRecipe);

        Log.i("CreaRecipeActivity", "Final recipe: " + currentRecipe);
        setResult(isUpdating ? ResultCodes.RECIPE_EDITED : ResultCodes.RECIPE_ADDED);
        finish();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, REQUEST_OPEN_GALLERY);
    }
}

