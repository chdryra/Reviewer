/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.TestUtils;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;

import org.hamcrest.Matcher;

import java.util.List;
import java.util.Map;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 19/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureTester<T> {
    private Map<String, Object> mUpdatesMap;
    private DbUpdater.UpdateType mUpdateType;
    private DbUpdater<T> mStructure;
    private T mData;
    private Testable<T> mTestable;

    public StructureTester(DbUpdater<T> structure, Testable<T> testable) {
        mStructure = structure;
        mTestable = testable;
    }

    public interface Testable<T> {
        void testStructure(StructureTester<T> tester);
    }

    public T getTestData() {
        return mData;
    }

    public void testInsertOrReplace(T data) {
        mData = data;
        testStructureWithType(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    public void testDelete(T data) {
        mData = data;
        testStructureWithType(DbUpdater.UpdateType.DELETE);
    }

    public void testStructureWithType(DbUpdater.UpdateType updateType){
        mUpdateType = updateType;
        if(mData != null) {
            mUpdatesMap = mStructure.getUpdatesMap(mData, mUpdateType);
        } else{
            fail("no data set!");
            return;
        }

        if(mTestable != null) {
            mTestable.testStructure(this);
        } else{
            fail("No Testable set!");
        }
    }

    public void checkMapSize(int size) {
        checkMapSize(mUpdatesMap, size);
    }

    private void checkMapSize(Map<String, Object> map, int size) {
        assertThat(map, not(nullValue()));
        assertThat(map.size(), is(size));
    }

    public void checkKeyValue(String key, @Nullable Object value) {
        checkKeyValue(mUpdatesMap, key, value);
    }

    private void checkKeyValue(Map<String, Object> map, String key, @Nullable Object value) {
        assertThat(map.containsKey(key), is(true));
        assertThat(map.get(key), isValue(value));
    }

    private Matcher<Object> isValue(@Nullable Object value) {
        boolean isDelete = mUpdateType == DbUpdater.UpdateType.DELETE;
        return isDelete || value == null ? nullValue() : is(value);
    }

    public <T> void checkKeyList(String key, List<T> reviewItems, List<DataGetter<T, ?>> getters) {
        boolean isDelete = mUpdateType == DbUpdater.UpdateType.DELETE;
        assertThat(mUpdatesMap.containsKey(key), is(true));
        if (isDelete) {
            assertThat(mUpdatesMap.get(key), nullValue());
            return;
        }

        try {
            List<Object> list = (List<Object>) mUpdatesMap.get(key);
            assertThat(list, not(nullValue()));
            assertThat(list.size(), is(reviewItems.size()));
            for (int i = 0; i < list.size(); ++i) {
                Map<String, Object> objectMap = (Map<String, Object>) list.get(i);
                T item = reviewItems.get(i);
                checkMapSize(objectMap, getters.size());
                checkMapping(objectMap, item, getters);
            }
        } catch (ClassCastException e) {
            fail();
        }
    }

    private <In> void checkMapping(Map<String, Object> objectMap,
                                   In object,
                                   List<DataGetter<In, ?>> objectDataGetters) {
        for (DataGetter<In, ?> getter : objectDataGetters) {
            checkMappingForGetter(objectMap, object, getter);
        }
    }

    private <In, Out> void checkMappingForGetter(Map<String, Object> objectMap,
                                                 In object,
                                                 DataGetter<In, Out> getter) {
        String key = getter.getDataName();
        Out value = getter.getData(object);

        List<DataGetter<Out, ?>> valueGetters = getter.getOutGetters();
        if (valueGetters.size() == 0) {
            checkKeyValue(objectMap, key, value);
        } else {
            try {
                Map<String, Object> valueMap = (Map<String, Object>) objectMap.get(key);
                checkMapping(valueMap, value, valueGetters);
            } catch (ClassCastException e) {
                fail();
            }
        }
    }

}
