/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.Path;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.testutils.RandomString;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Map;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomReview;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 19/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class StructureTestBasic<T> {
    private static final int NUM = RandomReview.NUM;
    protected Map<String, Object> mUpdatesMap;
    protected DbUpdater.UpdateType mUpdateType;
    private DbUpdater<T> mStructure;
    private T mData;

    protected void setStructure(DbUpdater<T> structure) {
        mStructure = structure;
        setUpdatesMapIfReady();
    }

    protected T setData(T data) {
        mData = data;
        setUpdatesMapIfReady();
        return data;
    }

    private void setUpdatesMapIfReady() {
        if(mUpdateType != null && mStructure != null&& mData != null) {
            setUpdatesMap(mStructure.getUpdatesMap(mData, mUpdateType));
        }
    }

    protected abstract void testStructure();

    @Test
    public void testInsert() {
        testStructureWithType(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    protected void testStructureWithType(DbUpdater.UpdateType insertOrUpdateOrDelete){
        setUpdateType(insertOrUpdateOrDelete);
        testStructure();
    }

    @Test
    public void testDelete() {
        testStructureWithType(DbUpdater.UpdateType.DELETE);
    }

    protected void setUpdatesMap(Map<String, Object> updatesMap) {
        mUpdatesMap = updatesMap;
    }

    protected void setUpdateType(DbUpdater.UpdateType updateType) {
        mUpdateType = updateType;
        setUpdatesMapIfReady();
    }

    protected void checkMapSize(int size) {
        checkMapSize(mUpdatesMap, size);
    }

    protected void checkMapSize(Map<String, Object> map, int size) {
        assertThat(map, not(nullValue()));
        assertThat(map.size(), is(size));
    }

    protected void checkKeyValue(String key, @Nullable Object value) {
        checkKeyValue(mUpdatesMap, key, value);
    }

    protected void checkKeyValue(Map<String, Object> map, String key, @Nullable Object value) {
        assertThat(map.containsKey(key), is(true));
        assertThat(map.get(key), isValue(value));
    }

    protected Matcher<Object> isValue(@Nullable Object value) {
        boolean isDelete = mUpdateType == DbUpdater.UpdateType.DELETE;
        return isDelete || value == null ? nullValue() : is(value);
    }

    @NonNull
    protected User randomUser() {
        return new User(RandomString.nextWord(), RandomString.nextWord(), randomProfile());
    }

    @NonNull
    protected Profile randomProfile() {
        return new Profile(new AuthorProfile(RandomAuthor.nextAuthor(),
                    RandomDataDate.nextDate()));
    }

    @NonNull
    protected ReviewDb randomReview() {
        return new ReviewDb(RandomReview.nextReview(), RandomString.nextWordArray(NUM));
    }

    protected String path(String root, String... elements) {
        return Path.path(root, elements);
    }
}
