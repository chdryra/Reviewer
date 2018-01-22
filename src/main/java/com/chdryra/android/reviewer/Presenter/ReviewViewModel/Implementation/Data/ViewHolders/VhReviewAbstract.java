/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.AsyncUtils.DelayTask;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.BookmarkCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ShareCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.CacheVhNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

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
    private static final int BOOKMARKED = R.drawable
            .ic_bookmark_black_24dp;
    private static final int UNBOOKMARKED = R.drawable
            .ic_bookmark_border_black_24dp;
    private static final int LIKED = R.drawable
            .ic_favorite_black_24dp;
    private static final int UNLIKED = R.drawable
            .ic_favorite_border_black_24dp;

    private static final long WAIT_TIME = 50L;

    private final static int[] VIEWS =
            {LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, DATE_LOCATION, PROFILE_IMAGE,
                    PROFILE_NAME,
                    PROFILE, MENU_BUTTON, OPTIONS, LIKE_BUTTON, SHARE_BUTTON, COMMENT_BUTTON,
                    BOOKMARK_BUTTON};

    private final AuthorsRepository mAuthorsRepo;
    private final FactoryCommands mCommandsFactory;
    private final ReviewSelector mSelector;
    private final CacheVhNode mCache;

    private TagsBinder mTagsBinder;
    private CommentsBinder mCommentsBinder;
    private LocationsBinder mLocationsBinder;
    private NameBinder mNameBinder;
    private CoverBinder mCoverBinder;

    private ReviewOptionsSelector mOptions;
    private ReviewId mNodeId;
    private ReviewReference mReview;
    private String mLocation;

    private int mNumReturned = 0;
    private DelayedSelectTask mBindingTask;
    private boolean mSelecting = false;
    private boolean mCancelBinding = false;

    private boolean mLiked = false;

    public VhReviewAbstract(AuthorsRepository authorsRepo,
                            FactoryCommands commandsFactory,
                            ReviewSelector selector,
                            CacheVhNode cache) {
        super(LAYOUT, VIEWS);
        mAuthorsRepo = authorsRepo;
        mCommandsFactory = commandsFactory;
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
        mCancelBinding = true;
        unbindFromBookmark();
        if (mCoverBinder != null) unbindFromCover();
        mReview.getComments().unbindFromValue(mCommentsBinder);
        mReview.getLocations().unbindFromValue(mLocationsBinder);
        mReview.getTags().unbindFromValue(mTagsBinder);
        mReview.unregisterObserver(this);
        mAuthorsRepo.getReference(mReview.getAuthorId()).unbindFromValue(mNameBinder);
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
        if (mBindingTask != null) mBindingTask.cancel(true);
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

        unbind();
        mNodeId = gvNode.getReviewId();
        gvNode.setViewHolder(this);
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
        underConstruction();
    }

    private void underConstruction() {
        Toast.makeText(getView().getContext(), "Under construction...", Toast.LENGTH_SHORT).show();
    }

    private void offline() {
        Toast.makeText(getView().getContext(), "offline...", Toast.LENGTH_SHORT).show();
    }

    private void selectAndBind(ReviewNode node) {
        if (mCancelBinding) return;
        mBindingTask = null;
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
                DefaultNamedAuthor());
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

    private void setAuthor(@Nullable NamedAuthor author) {
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

        mNumReturned = 0;
        bindComments();
        bindLocations();
        bindTags();
        bindAuthor();
    }

    private void bindAuthor() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsAuthor(id)) {
            setAuthor(mCache.getAuthor(id));
            returned();
        } else {
            mNameBinder = new NameBinder(id);
            mAuthorsRepo.getReference(mReview.getAuthorId()).bindToValue(mNameBinder);
        }
    }

    private void bindTags() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsTags(id)) {
            setTags(mCache.getTags(id));
            returned();
        } else {
            mTagsBinder = new TagsBinder(id);
            mReview.getTags().bindToValue(mTagsBinder);
        }
    }

    private void bindLocations() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsLocations(id)) {
            setLocations(mCache.getLocations(id));
            returned();
        } else {
            mLocationsBinder = new LocationsBinder(id);
            mReview.getLocations().bindToValue(mLocationsBinder);
        }
    }

    private void bindComments() {
        ReviewId id = mReview.getReviewId();
        if (mCache.containsComments(id)) {
            setComments(mCache.getComments(id));
            returned();
        } else {
            mCommentsBinder = new CommentsBinder(id);
            mReview.getComments().bindToValue(mCommentsBinder);
        }
    }

    private void bindCover() {
        ReviewId reviewId = mReview.getReviewId();
        if (mCache.containsCover(reviewId)) {
            setCover(mCache.getCover(reviewId));
        } else {
            mCoverBinder = new CoverBinder(reviewId, mReview.getAuthorId());
            mReview.getCover().bindToValue(mCoverBinder);
        }
    }

    private void unbindFromCover() {
        mCoverBinder.unbind();
        mCoverBinder = null;
    }

    private void returned() {
        //Set cover only if everything else done and no more 'fast' scrolling
        if (++mNumReturned == 4 && notReinitialising()) bindCover();
    }

    private void setReviewOptions() {
        if (mOptions != null) unbindFromBookmark();
        mOptions = mCommandsFactory.newReviewOptionsSelector
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

    private class NameBinder implements ReferenceBinder<NamedAuthor> {
        private final ReviewId mId;

        private NameBinder(ReviewId id) {
            mId = id;
        }

        @Override
        public void onInvalidated(DataReference<NamedAuthor> reference) {
            returned();
            mCache.removeAuthor(mId);
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(NamedAuthor value) {
            returned();
            mCache.addAuthor(mId, value);
            if (notReinitialising()) setAuthor(value);
        }
    }

    private class TagsBinder extends ListBinder<DataTag> {
        private TagsBinder(ReviewId reviewId) {
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

    private class LocationsBinder extends ListBinder<DataLocation> {
        private LocationsBinder(ReviewId reviewId) {
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

    private class CommentsBinder extends ListBinder<DataComment> {
        private CommentsBinder(ReviewId reviewId) {
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

    private abstract class ListBinder<T extends HasReviewId> implements
            ReferenceBinder<IdableList<T>> {
        private final ReviewId mReviewId;

        protected abstract void addToCache(IdableList<T> data);

        protected abstract void removeFromCache(ReviewId reviewId);

        protected abstract void onList(IdableList<T> data);

        private ListBinder(ReviewId reviewId) {
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

    private class CoverBinder implements ReferenceBinder<DataImage> {
        private final ReviewId mReviewId;
        private final AuthorId mAuthorId;

        private DataReference<ProfileImage> mProfileImage;
        private ProfileImageBinder mProfileImageBinder;
        private boolean mCancel = false;

        private CoverBinder(ReviewId reviewId, AuthorId authorId) {
            mReviewId = reviewId;
            mAuthorId = authorId;
        }

        public void unbind() {
            mCancel = true;
            mReview.getCover().unbindFromValue(this);
            unbindFromProfile();
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
            }
        }

        private void unbindFromProfile() {
            if (mProfileImage != null) mProfileImage.unbindFromValue(mProfileImageBinder);
        }

        private void bindToProfileImage() {
            if(mCache.containsProfile(mAuthorId)) {
                notifyOnCover(mCache.getProfile(mAuthorId).getBitmap());
            } else {
                mProfileImage = mAuthorsRepo.getProfile(mAuthorId).getProfileImage();
                mProfileImageBinder = new ProfileImageBinder();
                mProfileImage.bindToValue(mProfileImageBinder);
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

        private class ProfileImageBinder implements ReferenceBinder<ProfileImage> {
            @Override
            public void onInvalidated(DataReference<ProfileImage> reference) {
                notifyOnCover(null);
                mCache.removeProfile(mAuthorId);
            }

            @Override
            public void onReferenceValue(ProfileImage value) {
                mCache.addProfile(value);
                notifyOnCover(value.getBitmap());
            }
        }
    }
}