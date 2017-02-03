/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;



import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Profile {
    public static String AUTHOR = "author";

    private Author author;
    private long dateJoined;
    private String photo;

    public Profile() {
    }

    public Profile(AuthorProfileSnapshot profile) {
        this.author = new Author(profile.getNamedAuthor());
        this.dateJoined = profile.getJoined().getTime();
        this.photo = ImageData.asString(profile.getProfilePhoto());
    }

    public Author getAuthor() {
        return author;
    }

    public long getDateJoined() {
        return dateJoined;
    }

    public String getPhoto() {
        return photo;
    }
}
