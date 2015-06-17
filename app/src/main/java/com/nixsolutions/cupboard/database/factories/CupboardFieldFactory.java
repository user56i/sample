package com.nixsolutions.cupboard.database.factories;

import com.google.gson.reflect.TypeToken;
import com.nixsolutions.cupboard.entities.TestBean;

import java.lang.reflect.Type;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.FieldConverterFactory;

public class CupboardFieldFactory implements FieldConverterFactory {

    @Override
    public FieldConverter<?> create(Cupboard cupboard, Type type) {

        if(type.equals(TypeToken.get(TestBean.State.class).getType())){
            return null;
        }



        return null;
    }
}
