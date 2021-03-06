/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Fact {
    private String label;
    private String value;
    private boolean url;

    public Fact() {
    }

    public Fact(DataFact fact) {
        label = fact.getLabel();
        value = fact.getValue();
        url = fact.isUrl();
    }

    public static int size() {
        return 3;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public boolean isUrl() {
        return url;
    }
}
