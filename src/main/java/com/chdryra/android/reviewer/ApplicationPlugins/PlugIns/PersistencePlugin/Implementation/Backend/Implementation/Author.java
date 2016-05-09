/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Author {
    private String name;
    private String authorId;

    public Author() {
    }

    public Author(DataAuthor author) {
        name = author.getName();
        authorId = author.getAuthorId().toString();
    }

    public String getName(){
        return name;
    }

    public String getAuthorId() {
        return authorId;
    }
}
