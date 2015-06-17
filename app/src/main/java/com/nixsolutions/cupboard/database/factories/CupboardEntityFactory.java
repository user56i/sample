package com.nixsolutions.cupboard.database.factories;

import com.nixsolutions.cupboard.database.converters.entity.GitHubUserWithOrganizationUnionConverter;
import com.nixsolutions.cupboard.database.converters.entity.HumanDeviceUnionConverter;
import com.nixsolutions.cupboard.database.converters.entity.HumanDevicesEntityConverter;
import com.nixsolutions.cupboard.database.converters.entity.TestEntityConverter;
import com.nixsolutions.cupboard.entities.GitHubUserWithOrganizationUnion;
import com.nixsolutions.cupboard.entities.HumanDeviceUnion;
import com.nixsolutions.cupboard.entities.HumanDeviceView;
import com.nixsolutions.cupboard.entities.TestBean;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverterFactory;

public class CupboardEntityFactory implements EntityConverterFactory {
    @Override
    public <T> EntityConverter<T> create(Cupboard cupboard, Class<T> aClass) {

        EntityConverter<T> converter = null;

        if (aClass.equals(HumanDeviceView.class)) {
            converter = (EntityConverter<T>) new HumanDevicesEntityConverter();
        }

        if (aClass.equals(HumanDeviceUnion.class)) {
            converter = (EntityConverter<T>) new HumanDeviceUnionConverter();
        }

        if (aClass.equals(GitHubUserWithOrganizationUnion.class)) {
            converter = (EntityConverter<T>) new GitHubUserWithOrganizationUnionConverter();
        }

        if (aClass.equals(TestBean.class)) {
            converter = (EntityConverter<T>) new TestEntityConverter();
        }

        return converter;
    }
}
