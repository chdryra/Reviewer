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
        .DbStructureBasic;
import com.chdryra.android.testutils.RandomString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CompositeStructureTest {

    private class NameToAddress extends DbStructureBasic<NameAddress> {
        @NonNull
        @Override
        public Map<String, Object> getUpdatesMap(NameAddress item, UpdateType updateType) {
            boolean delete = updateType == UpdateType.DELETE;
            Map<String, Object> updatesMap = new HashMap<>();
            updatesMap.put(item.getName(), delete ? item.getAddress() : null);

            return updatesMap;
        }
    }

    private class AddressToName extends DbStructureBasic<NameAddress> {
        @NonNull
        @Override
        public Map<String, Object> getUpdatesMap(NameAddress item, UpdateType updateType) {
            boolean delete = updateType == UpdateType.DELETE;
            Map<String, Object> updatesMap = new HashMap<>();
            updatesMap.put(item.getAddress(), delete ? item.getName() : null);

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
