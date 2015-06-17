package com.nixsolutions.cupboard.database.converters.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.nixsolutions.cupboard.entities.GitHubUserWithOrganizationUnion;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.convert.EntityConverter;

public class GitHubUserWithOrganizationUnionConverter implements EntityConverter<GitHubUserWithOrganizationUnion> {
    @Override
    public GitHubUserWithOrganizationUnion fromCursor(Cursor cursor) {
        GitHubUserWithOrganizationUnion union = new GitHubUserWithOrganizationUnion();

        union.setLogin(cursor.getString(0));
        union.setAvatarUrl(cursor.getString(1));
        union.setOrganizationAvatarUrl(cursor.getString(2));
        union.setDescription(cursor.getString(3));

        return union;
    }

    @Override
    public void toValues(GitHubUserWithOrganizationUnion union, ContentValues contentValues) {
        contentValues.put("login", union.getLogin());
        contentValues.put("avatarUrl", union.getAvatarUrl());
        contentValues.put("organizationAvatarUrl", union.getOrganizationAvatarUrl());
        contentValues.put("description", union.getDescription());
    }

    @Override
    public List<Column> getColumns() {
        ArrayList<Column> columns = new ArrayList<>();

        columns.add(new Column("login", ColumnType.JOIN));
        columns.add(new Column("avatarUrl", ColumnType.JOIN));
        columns.add(new Column("organizationAvatarUrl", ColumnType.JOIN));
        columns.add(new Column("description",                ColumnType.JOIN));

        return columns;
    }

    @Override
    public void setId(Long aLong, GitHubUserWithOrganizationUnion union) {
        union.setId(aLong);
    }

    @Override
    public Long getId(GitHubUserWithOrganizationUnion union) {
        return union.getId();
    }

    @Override
    public String getTable() {
        return GitHubUserWithOrganizationUnion.class.getSimpleName();
    }
}
