package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ModelContextBasic implements ModelContext {
    private FactoryReviews mFactoryReviews;
    private ReviewsFeedMutable mAuthorsFeed;
    private ReviewsFeed mReviewsProvider;
    private SocialPlatformList mSocialPlatforms;
    private TagsManager mTagsManager;
    private FactoryVisitorReviewNode mVisitorsFactory;
    private FactoryReviewTreeTraverser mTreeTraversersFactory;
    private DataValidator mDataValidator;

    public void setDataValidator(DataValidator dataValidator) {
        mDataValidator = dataValidator;
    }

    public void setTagsManager(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    public void setVisitorsFactory(FactoryVisitorReviewNode visitorsFactory) {
        mVisitorsFactory = visitorsFactory;
    }

    public void setTreeTraversersFactory(FactoryReviewTreeTraverser treeTraversersFactory) {
        mTreeTraversersFactory = treeTraversersFactory;
    }

    public void setFactoryReviews(FactoryReviews factoryReviews) {
        mFactoryReviews = factoryReviews;
    }

    public void setAuthorsFeed(ReviewsFeedMutable authorsFeed) {
        mAuthorsFeed = authorsFeed;
    }

    public void setReviewsProvider(ReviewsFeed reviewsProvider) {
        mReviewsProvider = reviewsProvider;
    }

    public void setSocialPlatforms(SocialPlatformList socialPlatforms) {
        mSocialPlatforms = socialPlatforms;
    }

    @Override
    public ReviewsFeedMutable getAuthorsFeed() {
        return mAuthorsFeed;
    }

    @Override
    public ReviewsFeed getReviewsProvider() {
        return mReviewsProvider;
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mSocialPlatforms;
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mFactoryReviews;
    }

    @Override
    public FactoryVisitorReviewNode getVisitorsFactory() {
        return mVisitorsFactory;
    }

    @Override
    public FactoryReviewTreeTraverser getTreeTraversersFactory() {
        return mTreeTraversersFactory;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public DataValidator getDataValidator() {
        return mDataValidator;
    }
}