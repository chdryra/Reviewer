/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Author {
    private String mName;
    private String mId;

    public Author() {
    }

    public Author(DataAuthor author) {
        mName = author.getName();
        mId = author.getUserId().toString();
    }

    public String getName(){
        return mName;
    }

    public String getUserId() {
        return mId;
    }
}
