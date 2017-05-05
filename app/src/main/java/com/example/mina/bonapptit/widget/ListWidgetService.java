package com.example.mina.bonapptit.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mina.bonapptit.Data.RecipesContentProvider;
import com.example.mina.bonapptit.Data.RecipesContract;
import com.example.mina.bonapptit.R;


public class ListWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }


}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor mCursor;

    public ListRemoteViewsFactory(Context context) {
        this.mContext = context;
        Uri queryUri = RecipesContentProvider.Ingredients.INGREDIENTS;
        mCursor = mContext.getContentResolver().query(queryUri, null, null, null, null);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        Uri queryUri = RecipesContentProvider.Ingredients.INGREDIENTS;
        if (mCursor!=null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(queryUri, null, null, null, null);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) {
            return 0;
        }
        else {
            return mCursor.getCount();
        }

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);

        if (mCursor.moveToPosition(position)) {
            String ingredient = mCursor.getString(mCursor.getColumnIndex(RecipesContract.IngredientEntry.INGREDIENT_NAME));
            String measure = mCursor.getString(mCursor.getColumnIndex(RecipesContract.IngredientEntry.MEASURE));
            double quantity = mCursor.getDouble(mCursor.getColumnIndex(RecipesContract.IngredientEntry.QUANTITY));

            views.setTextViewText(R.id.ingreditent_text_view, ingredient);
            views.setTextViewText(R.id.measure_text_view, measure);
            views.setTextViewText(R.id.quantity_text_view, String.valueOf(quantity));
        }
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
