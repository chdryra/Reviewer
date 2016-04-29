/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;


/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureDatabase {
    public static final String REVIEWS = "Reviews";
    public static final String REVIEWS_LIST = "ReviewsList";

    public static final String TAGS = "Tags";

    public static final String USERS = "Users";
    public static final String USERS_DATA = "UsersData";
    public static final String FB_USERS_MAP = "FbUsersMap";

    public static final String AUTHOR_REVIEWS = REVIEWS;
    public static final String AUTHOR_TAGS = TAGS;
    public static final String AUTHOR_FEED = "Feed";

    private StructureReviews mReviews;
    private StructureUsers mUsers;
    private StructureUserReviews mUserReviews;
    private StructureTags mTags;
    private StructureDenormalised mDenormalised;

    public StructureDatabase() {
        mUsers = new StructureUsers(USERS, USERS_DATA, FB_USERS_MAP);
        mUserReviews = new StructureUserReviews(AUTHOR_REVIEWS, AUTHOR_TAGS, AUTHOR_FEED);
        mTags = new StructureTags(TAGS);
        mReviews = new StructureReviews(REVIEWS, REVIEWS_LIST);
        mDenormalised = new StructureDenormalised(mTags, mUsers, mUserReviews);
    }

    public StructureReviews getReviewsStructure() {
        return mReviews;
    }

    public StructureUsers getUsersStructure() {
        return mUsers;
    }

    public StructureUserReviews getUserReviewsStructure() {
        return mUserReviews;
    }

    public StructureTags getTagsStructure() {
        return mTags;
    }

    public StructureDenormalised getDenormalised() {
        return mDenormalised;
    }
}
