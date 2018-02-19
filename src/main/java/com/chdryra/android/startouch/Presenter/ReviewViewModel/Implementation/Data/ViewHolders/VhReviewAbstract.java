/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chdryra.android.corelibrary.AsyncUtils.DelayTask;
import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryReviewOptions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.BookmarkCommand;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchProfileCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ShareCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters.CacheVhNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;
import com.chdryra.android.startouch.R;
import com.chdryra.android.startouch.Utils.RatingFormatter;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 * <p>
 * This class is messy and poorly implemented. Highlights deficiencies in my ViewHolder pattern.
 * Ripe for a refactor and design rethink.
 */

//TODO refactor and think about ViewHolder redesign.
public class VhReviewAbstract extends ViewHolderBasic implements ReviewSelector
        .ReviewSelectorCallback, VhNode,
        ReviewReference.ReviewReferenceObserver,
        OptionSelectListener, BookmarkCommand.BookmarkListener {
    private static final int LAYOUT = R.layout.grid_cell_review_abstract;
    private static final int PROFILE = R.id.profile;
    private static final int OPTIONS = R.id.social_options;
    private static final int PROFILE_IMAGE = R.id.profile_image;
    private static final int PROFILE_NAME = R.id.profile_name;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int SUBJECT_RATING = R.id.subject_rating;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int DATE_LOCATION = R.id.review_date_location;
    private static final int LIKE_BUTTON = R.id.like_button;
    private static final int COMMENT_BUTTON = R.id.comment_button;
    private static final int SHARE_BUTTON = R.id.share_button;
    private static final int BOOKMARK_BUTTON = R.id.bookmark_button;
    private static final int MENU_BUTTON = R.id.menu_button;
    private static final int BOOKMARKED = R.drawable.ic_bookmark_black_24dp;
    private static final int UNBOOKMARKED = R.drawable.ic_bookmark_border_black_24dp;
    private static final int LIKED = R.drawable.ic_favorite_black_24dp;
    private static final int UNLIKED = R.drawable.ic_favorite_border_black_24dp;

    private static final long WAIT_TIME = 250L;

    private final static int[] VIEWS =
            {LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, DATE_LOCATION, PROFILE_IMAGE,
                    PROFILE_NAME, PROFILE, MENU_BUTTON, OPTIONS, LIKE_BUTTON, SHARE_BUTTON,
                    COMMENT_BUTTON, BOOKMARK_BUTTON};
    private static final int PROFILE_PLACEHOLDER = R.drawable.ic_face_black_36dp;

    private final AuthorsRepo mAuthorsRepo;
    private final FactoryReviewOptions mOptionsFactory;
    private final LaunchProfileCommand mLaunchProfile;
    private final ReviewSelector mSelector;
    private final CacheVhNode mCache;

    private TagsSubscriber mTagsBinder;
    private CommentsSubscriber mCommentsBinder;
    private LocationsSubscriber mLocationsBinder;
    private NameSubscriber mNameBinder;
    private ProfileSubscriber mProfileBinder;
    private CoverSubscriber mCoverBinder;

    private ReviewOptionsSelector mOptions;
    private ReviewId mNodeId;
    private ReviewReference mReview;
    private String mLocation;

    private int mNumReturned = 0;
    private DelayedSelectTask mBindingTask;
    private boolean mSelecting = false;
    private boolean mCancelBinding = false;

    private boolean mLiked = false;

    public VhReviewAbstract(AuthorsRepo authorsRepo,
                            FactoryReviewOptions optionsFactory,
                            LaunchProfileCommand launchProfile,
                            ReviewSelector selector,
                            CacheVhNode cache) {
        super(LAYOUT, VIEWS);
        mAuthorsRepo = authorsRepo;
        mOptionsFactory = optionsFactory;
        mLaunchProfile = launchProfile;
        mSelector = selector;
        mCache = cache;
    }

    @Override
    public boolean isBoundTo(ReviewNode node) {
        return mNodeId != null && mNodeId.equals(node.getReviewId());
    }

    @Override
    public void unbind() {
        if (mReview == null) return;
        cancelBindingTask();
        unbindFromBookmark();
        if (mCoverBinder != null) mCoverBinder.unbind();
        if (mProfileBinder != null) {
            mAuthorsRepo.getAuthorProfile(mReview.getAuthorId()).getProfileImage().unsubscribe
                    (mProfileBinder);
        }
        if (mCommentsBinder != null) mReview.getComments().unsubscribe(mCommentsBinder);
        if (mLocationsBinder != null) mReview.getLocations().unsubscribe(mLocationsBinder);
        if (mTagsBinder != null) mReview.getTags().unsubscribe(mTagsBinder);
        if (mNameBinder != null)
            mAuthorsRepo.getReference(mReview.getAuthorId()).unsubscribe(mNameBinder);
        if (mReview != null) mReview.unregisterObserver(this);

        mSelector.unregister(mNodeId);
        mReview = null;
        mNodeId = null;
    }

    @Override
    public void updateView(ViewHolderData data) {
        setNode(((GvNode) data));
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        mSelecting = false;
        if (review != null) {
            if (notReinitialising()) bindToReview(review);
        } else {
            unbind();
        }
    }

    @Override
    public void onSubjectChanged(DataSubject newSubject) {
        mCache.addSubject(newSubject);
        setSubject(newSubject);
    }

    @Override
    public void onRatingChanged(DataRating newRating) {
        mCache.addRating(newRating);
        setRating(newRating);
    }

    @Override
    public void refresh(ReviewNode node) {
        cancelBindingTask();
        mBindingTask = new DelayedSelectTask(this, node);
        mSelecting = true;
        mCancelBinding = false;
        mBindingTask.execute(WAIT_TIME);
    }

    @Override
    public void inflate(final Context context, ViewGroup parent) {
        super.inflate(context, parent);
        View view = getView();
        view.findViewById(SUBJECT_RATING).setAlpha(0.7f);
        view.findViewById(TAGS).setAlpha(0.7f);
        setButtons();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mOptions.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mOptions.onOptionsCancelled(requestCode);
    }

    @Override
    public void onBookmarked(boolean isBookmarked) {
        getView(BOOKMARK_BUTTON, ImageButton.class)
                .setBackgroundResource(isBookmarked ? BOOKMARKED : UNBOOKMARKED);
    }

    private void cancelBindingTask() {
        if (mBindingTask != null) {
            mBindingTask.cancel(true);
        }
        deleteTask();
        mCancelBinding = true;
    }

    private void deleteTask() {
        mBindingTask = null;
    }

    private void setButtons() {
        getView(PROFILE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickProfile();
            }
        });

        getView(OPTIONS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getView(MENU_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMenu();
            }
        });

        getView(LIKE_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLike();
            }
        });

        getView(COMMENT_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickComment();
            }
        });

        getView(SHARE_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickShare();
            }
        });

        getView(BOOKMARK_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBookmark();
            }
        });
    }

    private void onLiked(boolean isLiked) {
        getView(LIKE_BUTTON, ImageButton.class)
                .setBackgroundResource(isLiked ? LIKED : UNLIKED);
    }

    private void setNode(GvNode gvNode) {
        ReviewNode node = gvNode.getNode();
        if (isBoundTo(node)) return;

        gvNode.setViewHolder(this);
        mNodeId = gvNode.getReviewId();
        initialiseView();
        refresh(node);
    }

    private void clickBookmark() {
        BookmarkCommand bookmarkCommand = mOptions.getOptions().getBookmarkCommand();
        if (bookmarkCommand != null) {
            bookmarkCommand.execute();
        } else {
            offline();
        }
    }

    private void clickShare() {
        ShareCommand shareCommand = mOptions.getOptions().getShareCommand();
        if (shareCommand != null) {
            shareCommand.execute();
        } else {
            offline();
        }
    }

    private void clickComment() {
        underConstruction();
    }

    private void clickLike() {
        mLiked = !mLiked;
        onLiked(mLiked);
        underConstruction();
    }

    private void clickMenu() {
        mOptions.execute();
    }

    private void clickProfile() {
        if(mReview != null) mLaunchProfile.execute(mReview.getAuthorId());
    }

    private void underConstruction() {
        Toast.makeText(getView().getContext(), "Under construction...", Toast.LENGTH_SHORT).show();
    }

    private void offline() {
        Toast.makeText(getView().getContext(), "offline...", Toast.LENGTH_SHORT).show();
    }

    private void selectAndBind(ReviewNode node) {
        if (mCancelBinding) return;
        deleteTask();
        mSelector.select(node, this);
    }

    private void initialiseView() {
        setSubject(mCache.containsSubject(mNodeId) ?
                mCache.getSubject(mNodeId) : new DatumSubject(mNodeId, Strings.EditTexts.FETCHING));
        setRating(mCache.containsRating(mNodeId) ?
                mCache.getRating(mNodeId) : new DatumRating(mNodeId, 0f, 1));

        setCover(mCache.containsCover(mNodeId) ? mCache.getCover(mNodeId) : null);
        setComments(mCache.containsComments(mNodeId) ? mCache.getComments(mNodeId) : new
                IdableDataList<DataComment>(mNodeId));
        setTags(mCache.containsTags(mNodeId) ? mCache.getTags(mNodeId) : new
                IdableDataList<DataTag>(mNodeId));
        setLocations(mCache.containsLocations(mNodeId) ? mCache.getLocations(mNodeId) : new
                IdableDataList<DataLocation>(mNodeId));
        setAuthor(mCache.containsAuthor(mNodeId) ? mCache.getAuthor(mNodeId) : new
                AuthorNameDefault());
        setDateLocation(mCache.containsDate(mNodeId) ? mCache.getDate(mNodeId) : null);
    }

    private void setSubject(DataSubject subject) {
        setText(SUBJECT, subject.getSubject());
    }

    private void setRating(DataRating rating) {
        setText(RATING, RatingFormatter.upToTwoSignificantDigits(rating.getRating()));
    }

    private void setDateLocation(@Nullable DataDate publishDate) {
        String date = publishDate != null ? formatDate(publishDate) : "";
        String text = date + (validateString(mLocation) ? " @" + mLocation : "");
        setText(DATE_LOCATION, text);
    }

    private void setAuthor(@Nullable AuthorName author) {
        if (author != null) setText(PROFILE_NAME, author.getName());
    }

    private void setComments(IdableList<DataComment> value) {
        String headline = DataFormatter.getHeadlineQuote(value);
        if (headline.length() == 0) headline = Strings.Formatted.NO_COMMENT;
        setText(HEADLINE, headline);
    }

    private void setTags(IdableList<? extends DataTag> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        TextView tagsView = getView(TAGS, TextView.class);
        while (i > -1 && TextUtils.isTooLargeForTextView(tagsView, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        tagsView.setText(tagsString);
    }

    private void setLocations(IdableList<? extends DataLocation> value) {
        mLocation = DataFormatter.formatLocationsShort(value);
        setDateLocation(mReview != null ? mCache.containsDate(mReview.getReviewId()) ? mCache
                .getDate(mReview.getReviewId()) : mReview.getPublishDate() : null);
    }

    private void setCover(@Nullable Bitmap cover) {
        setImage(IMAGE, cover);
    }

    private void setProfileImage(@Nullable Bitmap profile) {
        if(profile == null) {
            getView(PROFILE_IMAGE).setBackgroundResource(PROFILE_PLACEHOLDER);
        } else {
            setImage(PROFILE_IMAGE, profile);
        }
    }

    private void bindToReview(ReviewReference review) {
        mReview = review;
        ReviewId id = mReview.getReviewId();

        setReviewOptions();

        mCache.addDate(mReview.getPublishDate());
        mCache.addSubject(mReview.getSubject());
        mCache.addRating(mReview.getRating());
        setDateLocation(mCache.getDate(id));
        setSubject(mCache.getSubject(id));
        setRating(mCache.getRating(id));

        mReview.registerObserver(this);

        bindToReviewData();
    }

    private void bindToReviewData() {
        mNumReturned = 0;
        bindAuthor();
        bindComments();
        bindLocations();
        bindTags();
    }

    private void bindAuthor() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsAuthor(id)) {
            setAuthor(mCache.getAuthor(id));
            returned();
        } else {
            mNameBinder = new NameSubscriber(id);
            mAuthorsRepo.getReference(mReview.getAuthorId()).subscribe(mNameBinder);
        }
    }

    private void bindTags() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsTags(id)) {
            setTags(mCache.getTags(id));
            returned();
        } else {
            mTagsBinder = new TagsSubscriber(id);
            mReview.getTags().subscribe(mTagsBinder);
        }
    }

    private void bindLocations() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsLocations(id)) {
            setLocations(mCache.getLocations(id));
            returned();
        } else {
            mLocationsBinder = new LocationsSubscriber(id);
            mReview.getLocations().subscribe(mLocationsBinder);
        }
    }

    private void bindComments() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsComments(id)) {
            setComments(mCache.getComments(id));
            returned();
        } else {
            mCommentsBinder = new CommentsSubscriber(id);
            mReview.getComments().subscribe(mCommentsBinder);
        }
    }

    private void bindProfile() {
        AuthorId id = mReview.getAuthorId();
        if (mCache.containsProfile(id)) {
            setProfileImage(mCache.getProfile(id).getBitmap());
            returned();
        } else {
            mProfileBinder = new ProfileSubscriber(id);
            mAuthorsRepo.getAuthorProfile(id).getProfileImage().subscribe(mProfileBinder);
        }
    }

    private void bindCover() {
        ReviewId reviewId = mReview.getReviewId();
        if (mCache.containsCover(reviewId)) {
            setCover(mCache.getCover(reviewId));
        } else {
            mCoverBinder = new CoverSubscriber(reviewId, mReview.getAuthorId());
            mReview.getCover().subscribe(mCoverBinder);
        }
    }

    private void returned() {
        //Set images only if everything else done and no more 'fast' scrolling
        if (++mNumReturned == 4 && notReinitialising()) {
            bindProfile();
            bindCover();
        }
    }

    private void setReviewOptions() {
        if (mOptions != null) unbindFromBookmark();
        mOptions = mOptionsFactory.newReviewOptionsSelector
                (ReviewOptionsSelector.SelectorType.BASIC, mReview.getAuthorId());

        BookmarkCommand bookmarkCommand = mOptions.getOptions().getBookmarkCommand();
        if (bookmarkCommand != null) bookmarkCommand.addListener(this);
        onLiked(mLiked);
    }

    private void unbindFromBookmark() {
        BookmarkCommand bookmarkCommand = mOptions.getOptions().getBookmarkCommand();
        if (bookmarkCommand != null) bookmarkCommand.removeListener(this);

    }

    private String formatDate(DataDate publishDate) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date(publishDate.getTime
                ()));
    }

    private String getTagString(IdableList<? extends DataTag> tags, int maxTags) {
        String ignoreTag = TextUtils.toTag(getView(SUBJECT, TextView.class).getText().toString());
        return DataFormatter.formatTags(tags, maxTags, ignoreTag);
    }

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }

    private boolean notReinitialising() {
        return !mCancelBinding && !mSelecting;
    }

    //hacky way of avoiding downloads spooling up if view is being scrolled fast.
    private static class DelayedSelectTask extends DelayTask {
        private final VhReviewAbstract mViewholder;
        private final ReviewNode mNode;

        private DelayedSelectTask(VhReviewAbstract viewholder, ReviewNode node) {
            mViewholder = viewholder;
            mNode = node;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mViewholder.selectAndBind(mNode);
        }
    }

    private class NameSubscriber implements DataReference.ValueSubscriber<AuthorName> {
        private final ReviewId mId;

        private NameSubscriber(ReviewId id) {
            mId = id;
        }

        @Override
        public void onInvalidated(DataReference<AuthorName> reference) {
            returned();
            mCache.removeAuthor(mId);
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(AuthorName value) {
            returned();
            mCache.addAuthor(mId, value);
            if (notReinitialising()) setAuthor(value);
        }
    }

    private class TagsSubscriber extends ListSubscriber<DataTag> {
        private TagsSubscriber(ReviewId reviewId) {
            super(reviewId);
        }

        @Override
        protected void addToCache(IdableList<DataTag> data) {
            mCache.addTags(data);
        }

        @Override
        protected void removeFromCache(ReviewId reviewId) {
            mCache.removeTags(reviewId);
        }

        @Override
        protected void onList(IdableList<DataTag> data) {
            setTags(data);
        }
    }

    private class LocationsSubscriber extends ListSubscriber<DataLocation> {
        private LocationsSubscriber(ReviewId reviewId) {
            super(reviewId);
        }

        @Override
        protected void addToCache(IdableList<DataLocation> data) {
            mCache.addLocations(data);
        }

        @Override
        protected void removeFromCache(ReviewId reviewId) {
            mCache.removeLocations(reviewId);
        }

        @Override
        protected void onList(IdableList<DataLocation> data) {
            setLocations(data);
        }
    }

    private class CommentsSubscriber extends ListSubscriber<DataComment> {
        private CommentsSubscriber(ReviewId reviewId) {
            super(reviewId);
        }

        @Override
        protected void addToCache(IdableList<DataComment> data) {
            mCache.addComments(data);
        }

        @Override
        protected void removeFromCache(ReviewId reviewId) {
            mCache.removeComments(reviewId);
        }

        @Override
        protected void onList(IdableList<DataComment> data) {
            setComments(data);
        }
    }

    private abstract class ListSubscriber<T extends HasReviewId> implements
            DataReference.ValueSubscriber<IdableList<T>> {
        private final ReviewId mReviewId;

        protected abstract void addToCache(IdableList<T> data);

        protected abstract void removeFromCache(ReviewId reviewId);

        protected abstract void onList(IdableList<T> data);

        private ListSubscriber(ReviewId reviewId) {
            mReviewId = reviewId;
        }

        @Override
        public void onReferenceValue(IdableList<T> value) {
            returned();
            if (value.size() > 0) {
                addToCache(value);
            } else {
                removeFromCache(value.getReviewId());
            }

            if (notReinitialising()) onList(value);
        }

        @Override
        public void onInvalidated(DataReference<IdableList<T>> reference) {
            returned();
            removeFromCache(mReviewId);
            onList(new IdableDataList<T>(mNodeId));
        }
    }

    private class CoverSubscriber implements DataReference.ValueSubscriber<DataImage> {
        private final ReviewId mReviewId;
        private final AuthorId mAuthorId;

        private boolean mHasCover = false;
        private boolean mCancel = false;

        private CoverSubscriber(ReviewId reviewId, AuthorId authorId) {
            mReviewId = reviewId;
            mAuthorId = authorId;
        }

        @Override
        public void onInvalidated(DataReference<DataImage> reference) {
            bindToProfileImage();
        }

        @Override
        public void onReferenceValue(DataImage value) {
            Bitmap bitmap = value.getBitmap();
            if (bitmap == null) {
                if (!mCancel) bindToProfileImage();
            } else {
                unbindFromProfile();
                notifyOnCover(bitmap);
                mHasCover = true;
            }
        }

        private void unbind() {
            mHasCover = false;
            mCancel = true;
            mReview.getCover().unsubscribe(this);
            unbindFromProfile();
        }

        private void unbindFromProfile() {
            if (mProfileBinder != null) mProfileBinder.setCoverBinder(null);
        }

        private void bindToProfileImage() {
            mHasCover = false;
            if (mCache.containsProfile(mAuthorId)) {
                notifyOnCover(mCache.getProfile(mAuthorId).getBitmap());
            } else {
                mProfileBinder.setCoverBinder(this);
            }
        }

        private void notifyOnCover(@Nullable Bitmap bitmap) {
            if (bitmap == null) {
                mCache.removeCover(mReviewId);
            } else {
                mCache.addCover(mReviewId, bitmap);
            }

            if (notReinitialising()) setCover(bitmap);
        }

        private void onProfileImage(@Nullable Bitmap profileImage) {
            if (mHasCover) return;
            notifyOnCover(profileImage);
            if (profileImage == null) {
                mCache.removeCover(mReviewId);
            } else {
                mCache.addCover(mReviewId, profileImage);
            }
        }
    }

    private class ProfileSubscriber implements DataReference.ValueSubscriber<ProfileImage> {
        private final AuthorId mAuthorId;
        private CoverSubscriber mCoverBinder;
        private boolean mProfileRetrieved = false;

        private ProfileSubscriber(AuthorId authorId) {
            mAuthorId = authorId;
        }

        @Override
        public void onInvalidated(DataReference<ProfileImage> reference) {
            setProfileImage(null);
            mCache.removeProfile(mAuthorId);
            if (mCoverBinder != null) mCoverBinder.onProfileImage(null);
        }

        @Override
        public void onReferenceValue(ProfileImage value) {
            mCache.addProfile(value);
            setProfileImage(value.getBitmap());
            if (mCoverBinder != null) mCoverBinder.onProfileImage(value.getBitmap());
            mProfileRetrieved = true;
        }

        private void setCoverBinder(@Nullable CoverSubscriber coverBinder) {
            if (mProfileRetrieved && coverBinder != null) {
                Bitmap bitmap = null;
                if (mCache.containsProfile(mAuthorId)) {
                    bitmap = mCache.getProfile(mAuthorId).getBitmap();
                }
                coverBinder.onProfileImage(bitmap);
            }

            mCoverBinder = coverBinder;
        }
    }
}