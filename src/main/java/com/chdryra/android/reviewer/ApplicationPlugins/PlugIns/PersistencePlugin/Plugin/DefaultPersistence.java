/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Plugin;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.BackendPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.LocalPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultPersistence implements PersistencePlugin {
    private final LocalPlugin mLocal;
    private final BackendPlugin mBackend;

    public DefaultPersistence(LocalPlugin local, BackendPlugin backend) {
        mLocal = local;
        mBackend = backend;
    }

    @Override
    public LocalPlugin getLocal() {
        return mLocal;
    }

    @Override
    public BackendPlugin getBackend() {
        return mBackend;
    }
}
