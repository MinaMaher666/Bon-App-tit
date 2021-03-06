package com.example.mina.bonapptit.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import com.example.mina.bonapptit.Data.RecipesContentProvider;
import com.example.mina.bonapptit.Data.RecipesContract;
import com.example.mina.bonapptit.MainActivity;
import com.example.mina.bonapptit.R;


/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        Intent widgetServiceIntent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_ingredients_list_view, widgetServiceIntent);

        views.setEmptyView(R.id.widget_ingredients_list_view, R.id.empty_widget_view);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        Uri queryUri = RecipesContentProvider.Ingredients.INGREDIENTS;
        Cursor cursor = context.getContentResolver().query(queryUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            String recipeTitle = cursor.getString(cursor.getColumnIndex(RecipesContract.IngredientEntry.RECIPE_NAME));
            views.setTextViewText(R.id.widget_recipe_title, recipeTitle);
            views.setViewVisibility(R.id.widget_recipe_title, View.VISIBLE);
        }
        cursor.close();
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

