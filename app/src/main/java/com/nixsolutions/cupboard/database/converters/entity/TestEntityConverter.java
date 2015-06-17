package com.nixsolutions.cupboard.database.converters.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.nixsolutions.cupboard.entities.TestBean;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.convert.EntityConverter;

public class TestEntityConverter implements EntityConverter<TestBean> {

    private static final String ID = "_id";
    private static final String POS = "pos";
    private static final String POS_1 = "pos1";
    private static final String POS_2 = "pos2";

    @Override
    public TestBean fromCursor(Cursor cursor) {
        TestBean testBean = new TestBean();

        testBean.set_id(cursor.getLong(0));


        TestBean.State state = new TestBean.State();
        state.pos = (cursor.getInt(1));
        state.pos1 = (cursor.getInt(2));
        state.pos2 = (cursor.getInt(3));

        testBean.setState(state);

        return testBean;
    }

    @Override
    public void toValues(TestBean testBean, ContentValues contentValues) {
        contentValues.put(ID, testBean._id);
        contentValues.put(POS, testBean.getState().pos);
        contentValues.put(POS_1, testBean.getState().pos1);
        contentValues.put(POS_2, testBean.getState().pos2);
    }

    @Override
    public List<Column> getColumns() {
        ArrayList<Column> columns = new ArrayList<>();

        columns.add(new Column(ID, ColumnType.INTEGER));
        columns.add(new Column(POS, ColumnType.TEXT));
        columns.add(new Column(POS_1, ColumnType.TEXT));
        columns.add(new Column(POS_2, ColumnType.TEXT));

        return columns;
    }

    @Override
    public void setId(Long aLong, TestBean testBean) {
        testBean.set_id(aLong);
    }

    @Override
    public Long getId(TestBean testBean) {
        return testBean.get_id();
    }

    @Override
    public String getTable() {
        return TestBean.class.getSimpleName();
    }
}
