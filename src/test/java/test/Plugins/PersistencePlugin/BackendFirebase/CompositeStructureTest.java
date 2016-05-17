/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.BackendFirebase;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring
        .CompositeStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring
        .DbStructureBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.Path;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CompositeStructureTest {

    @Test
    public void testBuildCompositeInsert() {
        testBuildComposite(DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Test
    public void testBuildCompositeDelete() {
        testBuildComposite(DbUpdater.UpdateType.DELETE);
    }

    private void testBuildComposite(DbUpdater.UpdateType type) {
        boolean isDelete = type == DbUpdater.UpdateType.DELETE;

        NameToAddress n2a = new NameToAddress();
        AddressToName a2n = new AddressToName();

        CompositeStructure.Builder<NameAddress> builder = new CompositeStructure.Builder<>();
        DbStructure<NameAddress> structure = builder.add(n2a).add(a2n).build();

        structure.setPathToStructure(new Path<NameAddress>() {
            @Override
            public String getPath(NameAddress item) {
                return "a/b/c";
            }
        });

        NameAddress item = new NameAddress();
        Map<String, Object> updatesMap = structure.getUpdatesMap(item, type);

        assertThat(updatesMap, not(nullValue()));
        assertThat(updatesMap.size(), is(4));
        String key1 = "a/b/c/" + item.getName() + "/" + item.getAddress();
        String key2 = "a/b/c/" + item.getName();
        String key3 = "a/b/c/" + item.getAddress() + "/" + item.getName();
        String key4 = "a/b/c/" + item.getAddress();

        assertThat(updatesMap.containsKey(key1), is(true));
        assertThat(updatesMap.containsKey(key2), is(true));
        assertThat(updatesMap.containsKey(key3), is(true));
        assertThat(updatesMap.containsKey(key4), is(true));

        assertThat(updatesMap.get(key1), isDelete ? nullValue() : is((Object)item.getName()));
        assertThat(updatesMap.get(key2), isDelete ? nullValue() : is((Object)item.getAddress()));
        assertThat(updatesMap.get(key3), isDelete ? nullValue() : is((Object)item.getAddress()));
        assertThat(updatesMap.get(key4), isDelete ? nullValue() : is((Object)item.getName()));
    }

    private class NameToAddress extends DbStructureBasic<NameAddress> {
        @NonNull
        @Override
        public Map<String, Object> getUpdatesMap(NameAddress item, UpdateType updateType) {
            boolean delete = updateType == UpdateType.DELETE;
            Map<String, Object> updatesMap = new HashMap<>();
            updatesMap.put(path(item.getName(), item.getAddress()), delete ? null : item.getName());
            updatesMap.put(item.getName(), delete ? null : item.getAddress());

            return updatesMap;
        }
    }

    private class AddressToName extends DbStructureBasic<NameAddress> {
        @NonNull
        @Override
        public Map<String, Object> getUpdatesMap(NameAddress item, UpdateType updateType) {
            boolean delete = updateType == UpdateType.DELETE;
            Map<String, Object> updatesMap = new HashMap<>();
            updatesMap.put(path(item.getAddress(), item.getName()), delete ? null : item.getAddress());
            updatesMap.put(item.getAddress(), delete ? null : item.getName());

            return updatesMap;
        }
    }

    private class NameAddress {
        private String name;
        private String address;

        public NameAddress() {
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
