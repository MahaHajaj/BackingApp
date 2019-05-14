package com.example.backingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.backingapp.BuildConfig;
import com.example.backingapp.MainActivity;
import com.example.backingapp.R;
import com.example.backingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static final String RECIPE_TITLE = "RECIPE_TITLE";
    public static final String RECIPE_TEXT = "RECIPE_TEXT";
    public static final String ID = "ID";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object


        Intent intent = new Intent(context, MainActivity.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        SharedPreferences sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        views.setTextViewText(R.id.appwidget_title, sharedPreferences.getString(RECIPE_TITLE,""));
        views.setTextViewText(R.id.appwidget_text, sharedPreferences.getString(RECIPE_TEXT,""));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.appwidget_title,pendingIntent);
       views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

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
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        if (intent.hasExtra("recipe")) {
//            Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
//            text = recipe.getName();
//            ingredients = recipe.getIngredients();
//        } else {
//            text = context.getString((R.string.no_recipe_selected));
//        }
//
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
//        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), BakingAppWidget.class);
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appWidget);
//        if (appWidgetIds != null && appWidgetIds.length > 0) {
//            onUpdate(context, appWidgetManager, appWidgetIds);
//        }
//
//        super.onReceive(context, intent);
//    }
}

