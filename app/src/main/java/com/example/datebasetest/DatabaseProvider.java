package com.example.datebasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {
    public static final int BOOK_DIAL = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIAL = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.example.datebasetest.provider";
    private static UriMatcher uriMatcher;
    private MyDatabaseHelper dbhelper;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIAL);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIAL);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
    }
    public DatabaseProvider() {
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int newsId = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIAL:
                newsId = db.delete("book",selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String newId = uri.getPathSegments().get(1);
                newsId = db.delete("book","id = ?",new String[]{newId
                });
                break;
            case CATEGORY_DIAL:
                newsId = db.delete("category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String newId1 = uri.getPathSegments().get(1);
                newsId = db.delete("category","id = ?",new String[]{newId1});
                break;
            default:
                break;
        }
        return newsId;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case BOOK_DIAL:
                return "vnd.anroid.cursor.dir/vnd.com.example.datebasetest.provider.book";
            case BOOK_ITEM:
                return "vnd.anroid.cursor.item/vnd.com.example.datebasetest.provider.book";
            case CATEGORY_DIAL:
                return "vnd.anroid.cursor.dir/vnd.com.example.datebasetest.provider.category";
            case CATEGORY_ITEM:
                return "vnd.anroid.cursor.item/vnd.com.example.datebasetest.provider.category";
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Uri uri1 = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIAL:
            case BOOK_ITEM:
                long newId = db.insert("book",null,values);
                uri1 = Uri.parse("content://"+AUTHORITY+"/book/"+newId);
                break;
            case CATEGORY_DIAL:
            case CATEGORY_ITEM:
                long newId1 = db.insert("category",null,values);
                uri1 = Uri.parse("content://"+AUTHORITY+"/category/"+newId1);
                break;
            default:
                break;
        }
        return uri1;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbhelper = new MyDatabaseHelper(getContext(),"BookStore.db",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIAL:
                cursor = db.query("book",projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
                String newId = uri.getPathSegments().get(1);
                cursor = db.query("book",projection,"id = ?",
                        new String[]{newId},null,null,sortOrder);
                break;
            case CATEGORY_DIAL:
                cursor = db.query("category",projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String cateId = uri.getPathSegments().get(1);
                cursor = db.query("category",projection,"id = ?",new String[]{cateId
                },null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int newId = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIAL:
                newId = db.update("book",values,selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String newsId = uri.getPathSegments().get(1);
                newId = db.update("book",values,"id = ?",new String[]{newsId});
                break;
            case CATEGORY_DIAL:
                newId = db.update("category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String newsId1 = uri.getPathSegments().get(1);
                newId = db.update("category",values,"id = ?",new String[]{newsId1});
                break;
            default:
                break;
        }
        return newId;
    }
}
