package com.nixsolutions.cupboard.database;

import android.net.Uri;

import nl.qbusict.cupboard.CupboardFactory;

public class ContentDescriptor {

    public static final String AUTHORITY = "com.nixsolutions.cupboard.provider.app";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String TABLE_NAME_QUERY_PARAM = "table_name";
    public static final String QUERY_VIEW_NAME = "view_name";

    public static Uri getUriWithId(Class aClass, long id) {
        return getUri(aClass).buildUpon().appendPath(String.valueOf(id)).build();
    }

    public static Uri getUri(Class aClass) {
        String tableName = getTableName(aClass);

        return BASE_URI.buildUpon()
                .appendQueryParameter(TABLE_NAME_QUERY_PARAM, tableName)
                .build();
    }

    private static String getTableName(Class aClass) {
        return CupboardFactory.cupboard().getTable(aClass);
    }

    public static String getTableName(Uri uri) {
        return uri.getQueryParameter(TABLE_NAME_QUERY_PARAM);
    }
}
