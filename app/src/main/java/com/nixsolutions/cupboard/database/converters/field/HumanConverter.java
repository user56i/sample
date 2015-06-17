package com.nixsolutions.cupboard.database.converters.field;

import android.content.ContentValues;
import android.database.Cursor;

import com.nixsolutions.cupboard.entities.Human;

import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.FieldConverter;

public class HumanConverter implements FieldConverter<Human> {
    @Override
    public Human fromCursorValue(Cursor cursor, int i) {
        Human human = null;

        if (cursor.moveToPosition(i)) {
            String string = cursor.getString(i);
            String[] split = string.split(";");
            human = new Human();
            human.name = split[0];
            human.secondName = split[1];
            human.address = split[2];
        }

        return human;
    }

    @Override
    public void toContentValue(Human human, String s, ContentValues contentValues) {
        String string = String.format("%s+;%s+;%s+", human.name, human.secondName, human.address);
        contentValues.put(s, string);
    }

    @Override
    public EntityConverter.ColumnType getColumnType() {
        return EntityConverter.ColumnType.TEXT;
    }
}
