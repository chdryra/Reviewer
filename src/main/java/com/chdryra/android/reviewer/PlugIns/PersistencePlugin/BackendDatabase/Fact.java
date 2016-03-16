/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendDatabase;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Fact {
    private String mLabel;
    private String mValue;
    private boolean mIsUrl;

    public Fact() {
    }

    public Fact(DataFact fact) {
        mLabel = fact.getLabel();
        mValue = fact.getValue();
        mIsUrl = fact.isUrl();
    }

    public String getLabel() {
        return mLabel;
    }

    public String getValue() {
        return mValue;
    }

    public boolean isUrl() {
        return mIsUrl;
    }

}
