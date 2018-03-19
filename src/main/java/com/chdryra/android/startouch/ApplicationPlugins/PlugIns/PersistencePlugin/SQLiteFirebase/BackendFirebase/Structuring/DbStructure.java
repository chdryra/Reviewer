/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbStructure<T> extends DbUpdater<T> {
    void setPathToStructure(Path<T> path);

    void setPathToStructure(String path);
}
