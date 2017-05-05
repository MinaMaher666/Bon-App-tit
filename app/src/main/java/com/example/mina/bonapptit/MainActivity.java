package com.example.mina.bonapptit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mina.bonapptit.Data.Recipe;
import com.example.mina.bonapptit.Data.RecipesContentProvider;
import com.example.mina.bonapptit.Data.RecipesContract;
import com.example.mina.bonapptit.Utils.JsonUtils;
import com.example.mina.bonapptit.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final int RECIPES_LOADER_ID = 666;
    private List<Recipe> mRecipes;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    MainFragment mainRecipesFragment;
    private Toast mToast;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        context = MainActivity.this;

        mRecipes = new ArrayList<>();
        mainRecipesFragment = new MainFragment();

        startMainFragment();

        if (NetworkUtils.isConnected(context)) {
            getSupportLoaderManager().initLoader(RECIPES_LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            cancelToast();
            mToast = Toast.makeText(context, R.string.no_networks_error, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!getSupportLoaderManager().hasRunningLoaders() && mRecipes.isEmpty()){
            mainRecipesFragment.setRecipes(mRecipes);
        }
    }

    public void cancelToast() {
        if (mToast != null)
            mToast.cancel();
    }

    public void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.GONE);
    }

    public void startMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mainRecipesFragment)
                .commit();
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(context) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                loadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<Recipe> loadInBackground() {
                String recipesUrlStr = getString(R.string.recipes_url);
                URL recipesUrl = NetworkUtils.getUrl(recipesUrlStr);
                String recipesJsonResponse = NetworkUtils.getJsonResponse(recipesUrl);
                return JsonUtils.extractRecipesFromJson(recipesJsonResponse, context);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mRecipes.clear();
        mRecipes.addAll(data);

        Log.i(LOG_TAG, "onLoadFinished: " + String.valueOf(mRecipes.size()));

        hideLoadingIndicator();
        mainRecipesFragment.setRecipes(mRecipes);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
