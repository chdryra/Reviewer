/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.HierarchyStructuring;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring
        .DbStructureBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.Path;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbStructureBasicTest {
    private Structure mStructure;

    @Before
    public void setUp() {
        mStructure = new Structure();
    }

    @Test
    public void pathReturnsCorrectPath() {
        assertThat(mStructure.path("a", "b", "c", "d"), is("a/b/c/d"));
    }

    @Test
    public void noUpdatesReturnsEmptyMap() {
        Map<String, Object> map = mStructure.noUpdates();
        assertThat(map, not(nullValue()));
        assertThat(map.size(), is(0));
    }

    @Test
    public void Updates_Insert_AtPathPutValue_WithNoPathSet_ReturnsPathToStringKey() {
        putValueNoPathSetReturnsPathToStringKey(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void Updates_Delete_AtPathPutValue_WithNoPathSet_ReturnsPathToStringKey() {
        putValueNoPathSetReturnsPathToStringKey(DbUpdater.UpdateType.DELETE);
    }

    @Test
    public void Updates_Insert_AtPathPutValue_WithPathSet_ReturnsSetPathReturnValueKey() {
        putValueWithPathSetReturnsCorrectPathAndValue(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void Updates_Delete_AtPathPutValue_WithPathSet_ReturnsSetPathReturnValueKey() {
        putValueWithPathSetReturnsCorrectPathAndValue(DbUpdater.UpdateType.DELETE);
    }

    @Test
    public void Updates_Insert_AtPathPutValue_WithPathSet_ReturnsSetPathPlusExtrasKey() {
        putValueWithExtraElementsReturnsCorrectPathAndValue(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void Updates_Delete_AtPathPutValue_WithPathSet_ReturnsSetPathPlusExtrasKey() {
        putValueWithExtraElementsReturnsCorrectPathAndValue(DbUpdater.UpdateType.DELETE);
    }

    @Test
    public void Updates_Insert_AtPathPutObject_ReturnsMappedObject() {
        putObjectReturnsMappedObject(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void Updates_Delete_AtPathPutObject_ReturnsMappedObject() {
        putObjectReturnsMappedObject(DbUpdater.UpdateType.DELETE);
    }

    @Test
    public void Updates_Insert_AtPathPutMap_ReturnsMapWithAbsolutePathKeys() {
        putMapReturnsMapWithAbsolutePathKeys(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void Updates_Delete_AtPathPutMap_ReturnsMapWithAbsolutePathKeys() {
        putMapReturnsMapWithAbsolutePathKeys(DbUpdater.UpdateType.DELETE);
    }

    private void putMapReturnsMapWithAbsolutePathKeys(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);

        Map<String, Object> insertObject = new HashMap<>();
        String value1 = "Value1";
        String value2 = "Value2";
        insertObject.put("c/d/e", value1);
        insertObject.put("f/g/h", value2);

        updates.atPath("a", "b").putMap(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(2));
        assertThat(updatesMap.containsKey("a/b/c/d/e"), is(true));
        assertThat(updatesMap.containsKey("a/b/f/g/h"), is(true));
        assertThat(updatesMap.get("a/b/c/d/e"), isDelete ? nullValue() : is((Object) value1));
        assertThat(updatesMap.get("a/b/f/g/h"), isDelete ? nullValue() : is((Object) value2));
    }

    private void putObjectReturnsMappedObject(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);

        String path = RandomString.nextWord();
        TestObject insertObject = new TestObject();
        updates.atPath(path).putObject(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(1));
        try {
            Map<String, Object> value = (Map<String, Object>) updatesMap.get(path);
            if(isDelete) {
                assertThat(value, is(nullValue()));
            } else {
                assertThat(value, not(nullValue()));
                assertThat(value.size(), is(2));
                assertThat(value.get("name"), is((Object) insertObject.getName()));
                assertThat(value.get("address"), is((Object) insertObject.getAddress()));
            }
        } catch (Exception e) {
            fail();
        }
    }

    private void putValueWithExtraElementsReturnsCorrectPathAndValue(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);

        final String pathEnding = RandomString.nextWord();
        mStructure.setPathToStructure(new Path<String>() {
            @Override
            public String getPath(String item) {
                return item + pathEnding;
            }
        });

        String stem = RandomString.nextWord();
        TestObject insertObject = new TestObject();
        updates.atPath(stem, "a", "b", "c").putValue(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(1));
        for (Map.Entry<String, Object> entry : updatesMap.entrySet()) {
            assertThat(entry.getKey(), is(stem + pathEnding + "/a/b/c"));
            assertThat(entry.getValue(), isDelete ? nullValue() : is((Object) insertObject));
        }
    }

    private void putValueWithPathSetReturnsCorrectPathAndValue(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;
        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);
        final String pathEnding = RandomString.nextWord();
        mStructure.setPathToStructure(new Path<String>() {
            @Override
            public String getPath(String item) {
                return item + pathEnding;
            }
        });

        String stem = RandomString.nextWord();
        TestObject insertObject = new TestObject();
        updates.atPath(stem).putValue(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(1));
        for (Map.Entry<String, Object> entry : updatesMap.entrySet()) {
            assertThat(entry.getKey(), is(stem + pathEnding));
            assertThat(entry.getValue(), isDelete ? nullValue() : is((Object) insertObject));
        }
    }

    private void putValueNoPathSetReturnsPathToStringKey(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);

        String key = RandomString.nextWord();
        TestObject insertObject = new TestObject();
        updates.atPath(key).putValue(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(1));
        for (Map.Entry<String, Object> entry : updatesMap.entrySet()) {
            assertThat(entry.getKey(), is(key));
            assertThat(entry.getValue(), isDelete ? nullValue() : is((Object) insertObject));
        }
    }

    private class Structure extends DbStructureBasic<String> {
        @NonNull
        @Override
        public Map<String, Object> getUpdatesMap(String item, UpdateType updateType) {
            Map<String, Object> updatesMap = new HashMap<>();
            return updatesMap;
        }

        @Override
        public String path(String root, String... elements) {
            return super.path(root, elements);
        }

        @Override
        protected Map<String, Object> noUpdates() {
            return super.noUpdates();
        }

        public class UpdatesTest extends Updates {
            public UpdatesTest(UpdateType type) {
                super(type);
            }
        }
    }

    private class TestObject {
        private String name;
        private String address;

        public TestObject() {
            this.name = RandomString.nextWord();
            this.address = RandomString.nextWord();
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }
    }
}
