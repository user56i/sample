package com.nixsolutions.cupboard.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.nixsolutions.cupboard.database.factories.CupboardEntityFactory;
import com.nixsolutions.cupboard.database.factories.CupboardFieldFactory;
import com.nixsolutions.cupboard.entities.Device;
import com.nixsolutions.cupboard.entities.Family;
import com.nixsolutions.cupboard.entities.GitHubOrganization;
import com.nixsolutions.cupboard.entities.GitHubUser;
import com.nixsolutions.cupboard.entities.GitHubUserWithOrganizationUnion;
import com.nixsolutions.cupboard.entities.Human;
import com.nixsolutions.cupboard.database.converters.field.HumanConverter;
import com.nixsolutions.cupboard.entities.HumanDeviceUnion;
import com.nixsolutions.cupboard.entities.HumanDeviceView;
import com.nixsolutions.cupboard.entities.TestBean;
import com.nixsolutions.cupboard.entities.User;

import java.util.HashMap;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class CupboardContentProvider extends ContentProvider {

    private static final String DB_NAME = "simple.db";
    private static final int DB_VERSION = 8;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public static HashMap<Uri, String> complexSelection = new HashMap<>();

    static {
        Cupboard cupboard = new CupboardBuilder()
                .registerEntityConverterFactory(new CupboardEntityFactory())
                .registerFieldConverterFactory(new CupboardFieldFactory())
                .registerFieldConverter(Human.class, new HumanConverter()).build();

        cupboard.register(Human.class);
        cupboard.register(Device.class);
        cupboard.register(Family.class);
        cupboard.register(HumanDeviceView.class);
        cupboard.register(HumanDeviceUnion.class);
        cupboard.register(GitHubUser.class);
        cupboard.register(GitHubOrganization.class);
        cupboard.register(GitHubUserWithOrganizationUnion.class);
        cupboard.register(TestBean.class);
        cupboard.register(User.class);
        CupboardFactory.setCupboard(cupboard);

        complexSelection.put(ContentDescriptor.getUri(HumanDeviceUnion.class),
                cupboard().getTable(Human.class) + " join " + cupboard().getTable(Device.class) +
                        " ON " + cupboard().getTable(Device.class) + "." + " humanId= "
                        + cupboard().getTable(Human.class) + "." + "_id ");
        complexSelection.put(ContentDescriptor.getUri(HumanDeviceUnion.class),"(" +
                " SELECT " +
                    "name, " +
                    "null as deviceName " +
                " FROM " + cupboard().getTable(Human.class) +
                " UNION ALL " +
                " SELECT " +
                    "null as name," +
                    "deviceName" +
                " FROM " + cupboard().getTable(Device.class) +
                " )");
        complexSelection.put(ContentDescriptor.getUri(GitHubUserWithOrganizationUnion.class),"(" +
                " SELECT " +
                    " login, " +
                    cupboard().getTable(GitHubUser.class) + ".avatarUrl, " +
                    " null as organizationAvatarUrl, " +
                    " null as description " +
                " FROM " + cupboard().getTable(GitHubUser.class) +
                " UNION ALL " +
                " SELECT " +
                    " null as login, " +
                    " null as avatarUrl, " +
                    cupboard().getTable(GitHubOrganization.class) +".avatarUrl as organizationAvatarUrl, " +
                    " description " +
                " FROM " + cupboard().getTable(GitHubOrganization.class) +
                " )");
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }


    public static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            CupboardFactory.getInstance().withDatabase(db).createTables();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            CupboardFactory.getInstance().withDatabase(db).upgradeTables();
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        if (isComplexUri(uri)) {
            cursor = queryComplex(uri, projection, selection, selectionArgs, sortOrder);
        } else {
            db = dbHelper.getReadableDatabase();
            String tableName = ContentDescriptor.getTableName(uri);

            String lastPathSegment = uri.getLastPathSegment();

            if (lastPathSegment != null && TextUtils.isDigitsOnly(lastPathSegment)) {
                selection = appendIdSelection(selection);
                selectionArgs = appendIdSelectionArgs(selection, selectionArgs, lastPathSegment);
            }
            cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Cursor queryComplex(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = dbHelper.getReadableDatabase();
        String sql = complexSelection.get(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(sql);

        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    private boolean isComplexUri(Uri uri) {
        return complexSelection.get(uri) != null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = ContentDescriptor.getTableName(uri);
        db = dbHelper.getWritableDatabase();

        long rowId = db.insert(tableName, null, values);
        Uri resultUri = ContentUris.withAppendedId(uri, rowId);

        notify(resultUri);

        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = ContentDescriptor.getTableName(uri);
        db = dbHelper.getWritableDatabase();

        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null && TextUtils.isDigitsOnly(lastPathSegment)) {
            selection = appendIdSelection(selection);
            selectionArgs = appendIdSelectionArgs(selection, selectionArgs, lastPathSegment);
        }

        int deletedCount = db.delete(tableName, selection, selectionArgs);
        notify(uri);

        return deletedCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = ContentDescriptor.getTableName(uri);
        db = dbHelper.getWritableDatabase();

        String lastPathSegment = uri.getLastPathSegment();

        if (lastPathSegment != null && TextUtils.isDigitsOnly(lastPathSegment)) {
            selection = appendIdSelection(selection);
            selectionArgs = appendIdSelectionArgs(selection, selectionArgs, lastPathSegment);
        }

        int updatedRows = db.update(tableName, values, selection, selectionArgs);
        notify(uri);

        return updatedRows;
    }

    private String appendIdSelection(String selection) {
        if (!TextUtils.isEmpty(selection)) {
            selection = " _id=?";
        } else {
            selection = selection + " AND _id =?";
        }
        return selection;
    }

    private String[] appendIdSelectionArgs(String selection, String[] selectionArgs, String lastPathSegment) {
        String[] newSelectionArgs;
        if (!TextUtils.isEmpty(selection)) {
            selectionArgs = new String[]{lastPathSegment};
        } else {
            newSelectionArgs = new String[selectionArgs.length + 1];
            System.arraycopy(selectionArgs, 0, newSelectionArgs, 0,
                    selectionArgs.length);
            newSelectionArgs[selectionArgs.length] = lastPathSegment;

            selectionArgs = newSelectionArgs;
        }
        return selectionArgs;
    }

    private void notify(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);

        String viewNameParam = uri.getQueryParameter(ContentDescriptor.QUERY_VIEW_NAME);
        if (!TextUtils.isEmpty(viewNameParam)) {
            getContext().getContentResolver().notifyChange(Uri.parse(viewNameParam), null);
        }
    }
}
