/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserProfile {
    private Author author;
    private long creationDate;

    public UserProfile() {
    }

    public UserProfile(DataAuthor author) {
        this.author = new Author(author);
        this.creationDate = new Date().getTime();
    }

    public Author getAuthor() {
        return author;
    }

    public long getCreationDate() {
        return creationDate;
    }
}
