package com.nixsolutions.cupboard.database.converters.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.nixsolutions.cupboard.entities.HumanDeviceView;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.convert.EntityConverter;

public class HumanDevicesEntityConverter implements EntityConverter<HumanDeviceView> {

    private static final String HUMAN_ID = "humanId";
    private static final String ADDRESS = "address";
    private static final String NAME = "name";
    private static final String SECOND_NAME = "secondName";
    private static final String DEVICE_ID = "deviceId";
    private static final String DEVICE_NAME = "deviceName";

    @Override
    public HumanDeviceView fromCursor(Cursor cursor) {
        HumanDeviceView view = new HumanDeviceView();


        Log.e("qq", DatabaseUtils.dumpCurrentRowToString(cursor));

        view.humanId = cursor.getInt(0);
        view.address = cursor.getString(1);
        view.name = cursor.getString(2);
        view.secondName = cursor.getString(3);
        view.deviceId = cursor.getInt(4);
        view.deviceName = cursor.getString(5);

        return view;
    }

    @Override
    public void toValues(HumanDeviceView humanDeviceView, ContentValues contentValues) {
        contentValues.put(HUMAN_ID,humanDeviceView.humanId);
        contentValues.put(ADDRESS,humanDeviceView.address);
        contentValues.put(NAME,humanDeviceView.name);
        contentValues.put(SECOND_NAME,humanDeviceView.secondName);
        contentValues.put(DEVICE_ID,humanDeviceView.deviceId);
        contentValues.put(DEVICE_NAME,humanDeviceView.deviceName);
    }

    @Override
    public List<Column> getColumns() {
        ArrayList<Column> list = new ArrayList<>();

        list.add(new Column(HUMAN_ID,   ColumnType.JOIN));
        list.add(new Column(ADDRESS,    ColumnType.JOIN));
        list.add(new Column(NAME,       ColumnType.JOIN));
        list.add(new Column(SECOND_NAME, ColumnType.JOIN));
        list.add(new Column(DEVICE_ID,      ColumnType.JOIN));
        list.add(new Column(DEVICE_NAME, ColumnType.JOIN));

        return list;
    }

    @Override
    public void setId(Long aLong, HumanDeviceView humanDeviceView) {
        humanDeviceView._id = aLong;
    }

    @Override
    public Long getId(HumanDeviceView humanDeviceView) {
        return humanDeviceView._id;
    }

    @Override
    public String getTable() {
        return HumanDeviceView.class.getSimpleName();
    }
}
