/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Plugin;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.FactoryContractor;


/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DatabaseAndroidSqlLite implements DatabasePlugin {
    private static final String EXT = "db";
    public static final FactoryContractorSqLite FACTORY = new FactoryContractorSqLite();

    @Override
    public FactoryContractor getContractorFactory() {
        return FACTORY;
    }

    @Override
    public String getDbNameExtension() {
        return EXT;
    }
}
