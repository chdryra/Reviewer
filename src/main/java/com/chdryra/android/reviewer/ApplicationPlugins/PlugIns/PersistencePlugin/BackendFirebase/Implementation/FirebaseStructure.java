/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.CompositeUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.UpdaterBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 29/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructure {
    public static final String REVIEWS = "Reviews";
    public static final String REVIEWS_DATA = "Data";
    public static final String REVIEWS_LIST = "List";
    public static final String TAGS = "Tags";
    public static final String USERS = "Users";
    public static final String PROFILE = "Profile";
    public static final String FB_USERS_MAP = "FbUsersMap";
    public static final String FEED = "Feed";

    private DbUpdater<User> mUsers;
    private DbUpdater<FbReview> mOnReviewUpload;

    public FirebaseStructure() {
        UpdaterBuilder<User> builderUserUpdate = new UpdaterBuilder<>();
        builderUserUpdate.add(USERS, new UserDataUpdater(new UpdaterUserProfile(PROFILE)));
        builderUserUpdate.add(new UpdaterUsersMap(FB_USERS_MAP));
        mUsers = builderUserUpdate.build();

        UpdaterBuilder<FbReview> builderUserReviews = new UpdaterBuilder<>();
        builderUserReviews.add(USERS, new UserReviewDataUpdater(new UpdaterUserReviewData(REVIEWS, TAGS, FEED)));
        DbUpdater<FbReview> userReviews = builderUserReviews.build();
        DbUpdater<FbReview> tags = new UpdaterTags(TAGS, REVIEWS, USERS);
        DbUpdater<FbReview> reviews = new UpdaterReviews(REVIEWS, REVIEWS_DATA, REVIEWS_LIST);

        CompositeUpdater.Builder<FbReview> compositeBuilder = new CompositeUpdater.Builder<>();
        mOnReviewUpload = compositeBuilder.add(reviews).add(tags).add(userReviews).build();
    }

    public DbUpdater<User> getUsersUpdater() {
        return mUsers;
    }

    public DbUpdater<FbReview> getReviewUploadUpdater() {
        return mOnReviewUpload;
    }

    public String getReviewsDataRoot() {
        return path(REVIEWS, REVIEWS_DATA);
    }

    public String getReviewsListRoot() {
        return path(REVIEWS, REVIEWS_LIST);
    }

    private String path(String root, String...elements) {
        String path = root;
        for(String element : elements) {
            path += "/" + element;
        }

        return path;
    }
}
