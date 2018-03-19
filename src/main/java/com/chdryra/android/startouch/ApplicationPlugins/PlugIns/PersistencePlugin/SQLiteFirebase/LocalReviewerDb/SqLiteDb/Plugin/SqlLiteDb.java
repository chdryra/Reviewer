/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Plugin;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.FactoryContractor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.RelationalDbPlugin;


/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SqlLiteDb implements RelationalDbPlugin {
    private static final String EXT = "db";
    private static final FactoryContractorSqLite FACTORY = new FactoryContractorSqLite();

    @Override
    public FactoryContractor getContractorFactory() {
        return FACTORY;
    }

    @Override
    public String getDbNameExtension() {
        return EXT;
    }
}
