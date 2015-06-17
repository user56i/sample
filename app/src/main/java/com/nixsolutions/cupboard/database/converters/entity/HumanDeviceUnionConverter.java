package com.nixsolutions.cupboard.database.converters.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.nixsolutions.cupboard.entities.HumanDeviceUnion;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.convert.EntityConverter;

public class HumanDeviceUnionConverter implements EntityConverter<HumanDeviceUnion> {

    private static final String NAME = "name";
    private static final String DEVICE_NAME = "deviceName";

    @Override
    public HumanDeviceUnion fromCursor(Cursor cursor) {
        HumanDeviceUnion view = new HumanDeviceUnion();


        Log.e("qq", DatabaseUtils.dumpCurrentRowToString(cursor));

        view.name = cursor.getString(0);
        view.deviceName = cursor.getString(1);

        return view;
    }

    @Override
    public void toValues(HumanDeviceUnion humanDeviceUnion, ContentValues contentValues) {

        contentValues.put(NAME, humanDeviceUnion.name);
        contentValues.put(DEVICE_NAME, humanDeviceUnion.deviceName);
    }

    @Override
    public List<Column> getColumns() {
        ArrayList<Column> list = new ArrayList<>();

        list.add(new Column(NAME, ColumnType.JOIN));
        list.add(new Column(DEVICE_NAME, ColumnType.JOIN));

        return list;
    }

    @Override
    public void setId(Long aLong, HumanDeviceUnion humanDeviceUnion) {
        humanDeviceUnion._id = aLong;
    }

    @Override
    public Long getId(HumanDeviceUnion humanDeviceUnion) {
        return humanDeviceUnion._id;
    }

    @Override
    public String getTable() {
        return HumanDeviceUnion.class.getSimpleName();
    }
}
