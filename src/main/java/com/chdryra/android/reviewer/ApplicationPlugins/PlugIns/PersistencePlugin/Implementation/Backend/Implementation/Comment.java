/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Comment {
    private String comment;
    private boolean headline;

    public Comment() {
    }

    public Comment(DataComment comment) {
        this.comment = comment.getComment();
        headline = comment.isHeadline();
    }

    public String getComment() {
        return comment;
    }

    public boolean isHeadline() {
        return headline;
    }
}
