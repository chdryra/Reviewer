/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation;


import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Profile {
    private Author author;
    private long dateJoined;

    public Profile() {
    }

    public Profile(AuthorProfile profile) {
        this.author = new Author(profile.getAuthor());
        this.dateJoined = profile.getDateJoined().getTime();
    }

    public Author getAuthor() {
        return author;
    }

    public long getDateJoined() {
        return dateJoined;
    }
}
