/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase.HierarchyStructuring;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbStructureBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.Path;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

        TestObject1 obj1 = new TestObject1();
        TestObject2 obj2 = new TestObject2();
        Structure.UpdatesTest preUpdates = mStructure.new UpdatesTest(type);
        preUpdates.atPath("c", "d", "e").putObject(obj1);
        preUpdates.atPath("f", "g", "h").putObject(obj2);
        Map<String, Object> insertObject = preUpdates.toMap();

        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);
        updates.atPath("a", "b").putMap(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(4));
        assertThat(updatesMap.containsKey("a/b/c/d/e/name"), is(true));
        assertThat(updatesMap.containsKey("a/b/c/d/e/address"), is(true));
        assertThat(updatesMap.containsKey("a/b/f/g/h/member/name"), is(true));
        assertThat(updatesMap.containsKey("a/b/f/g/h/member/address"), is(true));
        assertThat(updatesMap.get("a/b/c/d/e/name"), isDelete ? nullValue() : is((Object) obj1.getName()));
        assertThat(updatesMap.get("a/b/c/d/e/address"), isDelete ? nullValue() : is((Object) obj1.getAddress()));
        assertThat(updatesMap.get("a/b/f/g/h/member/name"), isDelete ? nullValue() : is((Object) obj2.getMember().getName()));
        assertThat(updatesMap.get("a/b/f/g/h/member/address"), isDelete ? nullValue() : is((Object) obj2.getMember().getAddress()));
    }

    private void putObjectReturnsMappedObject(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        Structure.UpdatesTest updates = mStructure.new UpdatesTest(type);

        String path = RandomString.nextWord();
        TestObject2 insertObject = new TestObject2();
        updates.atPath(path).putObject(insertObject);
        Map<String, Object> updatesMap = updates.toMap();

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(2));
        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.containsKey(path + "/member/name"), is(true));
        assertThat(updatesMap.containsKey(path + "/member/address"), is(true));
        assertThat(updatesMap.get(path + "/member/name"), isDelete ? nullValue() : is((Object) insertObject.getMember().getName()));
        assertThat(updatesMap.get(path + "/member/address"), isDelete ? nullValue() : is((Object) insertObject.getMember().getAddress()));
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
        TestObject1 insertObject = new TestObject1();
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
        TestObject1 insertObject = new TestObject1();
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
        TestObject1 insertObject = new TestObject1();
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

    private class TestObject1 {
        private String name;
        private String address;

        public TestObject1() {
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

    private class TestObject2 {
        private TestObject1 member;

        public TestObject2() {
            member = new TestObject1();
        }

        public TestObject1 getMember() {
            return member;
        }
    }
}
