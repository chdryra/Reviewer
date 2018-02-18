/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Fragments;


import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.FollowBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentProfileView extends Fragment implements ReferenceBinder<AuthorProfile>,
        ActivityResultListener, FollowBinder.FollowListener, SocialProfileRef
                .FollowCallback {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentProfileView.class, "Args");
    private static final String PROFILE_DELETED = "Profile deleted";

    private static final int LAYOUT = R.layout.fragment_profile_view;
    private static final int PROFILE_IMAGE = R.id.profile_image;
    private static final int PROFILE_AUTHOR = R.id.profile_name;
    private static final int FOLLOW_EDIT_BUTTON = R.id.follow_button;
    private static final int FOLLOWERS_BUTTON = R.id.left_button;
    private static final int RATINGS_BUTTON = R.id.middle_button;
    private static final int FOLLOWING_BUTTON = R.id.right_button;
    private static final int IMAGE_PLACEHOLDER = R.drawable.ic_face_black_36dp;
    private static final String NO_AUTHOR = "No author...";
    private static final String REVIEWS = Strings.REVIEWS_CAP;
    private static final String FOLLOWING = Strings.FOLLOWING;
    private static final String FOLLOWERS = Strings.FOLLOWERS;

    private AuthorId mAuthor;
    private TextView mName;
    private ImageView mPhoto;
    private SizeBinder mRatings;
    private SizeBinder mFollowers;
    private SizeBinder mFollowing;
    private Button mFollow_edit;
    private FollowBinder mFollowBinder;

    public static FragmentProfileView newInstance(GvAuthorId authorId) {
        FragmentProfileView fragment = new FragmentProfileView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS, authorId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mPhoto = view.findViewById(PROFILE_IMAGE);
        mName = view.findViewById(PROFILE_AUTHOR);
        mPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(), IMAGE_PLACEHOLDER));

        Button ratings = view.findViewById(RATINGS_BUTTON);
        Button followers = view.findViewById(FOLLOWERS_BUTTON);
        Button following = view.findViewById(FOLLOWING_BUTTON);
        mFollow_edit = view.findViewById(FOLLOW_EDIT_BUTTON);

        if (!setAuthor()) {
            getApp().getUi().getCurrentScreen().showToast(NO_AUTHOR);
        } else {
            setFollowEditButton();
            setButtonBar(ratings, followers, following);
        }

        return view;
    }

    @Override
    public void onFollowing(AuthorId authorId) {
        if (authorId.equals(mAuthor)) setButtonUnfollow();
    }

    @Override
    public void onUnfollowing(AuthorId authorId) {
        if (authorId.equals(mAuthor)) setButtonFollow();
    }

    @Override
    public void onFollow(AuthorId authorId, SocialProfileRef.FollowUnfollow type, CallbackMessage
            message) {
        if (message.isOk() && type.equals(SocialProfileRef.FollowUnfollow.FOLLOW)) {
            setButtonUnfollow();
        } else {
            setButtonFollow();
        }
    }

    @Override
    public void onFollowInvalidate() {
        setButtonFollow();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRatings.unbind();
        mFollowers.unbind();
        mFollowing.unbind();
        if (mFollowBinder != null) mFollowBinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        mRatings.bind();
        mFollowers.bind();
        mFollowing.bind();
        if (mFollowBinder != null) mFollowBinder.bind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            getApp().getUi().getCurrentScreen().closeAndGoUp();
            return true;
        }

        return false;
    }

    @Override
    public void onReferenceValue(AuthorProfile value) {
        setProfile(value);
    }

    @Override
    public void onInvalidated(DataReference<AuthorProfile> reference) {
        mName.setText(PROFILE_DELETED);
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private SocialProfileRef getSocialProfile(AuthorId authorId) {
        return getApp().getRepository().getAuthors().getSocialProfile(authorId);
    }

    private AuthorId getUserAuthorId() {
        return getApp().getAccounts().getUserSession().getAuthorId();
    }

    private void setFollowEditButton() {
        mFollow_edit.setText(Strings.Buttons.FOLLOW);
        if (getUserAuthorId().equals(mAuthor)) {
            setButtonEdit();
        } else {
            mFollowBinder = new FollowBinder(getSocialProfile(getUserAuthorId()).getFollowing(), this);
        }
    }

    private void setButtonEdit() {
        mFollow_edit.setText(Strings.Buttons.EDIT_PROFILE);
        mFollow_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApp().getUi().getCommandsFactory().newLaunchProfileCommand().execute();
            }
        });
    }

    private void setButtonUnfollow() {
        mFollow_edit.setText(Strings.Buttons.UNFOLLOW);
        mFollow_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSocialProfile(getUserAuthorId()).followUnfollow(mAuthor, SocialProfileRef.FollowUnfollow
                        .UNFOLLOW, FragmentProfileView.this);
            }
        });
    }

    private void setButtonFollow() {
        mFollow_edit.setText(Strings.Buttons.FOLLOW);
        mFollow_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSocialProfile(getUserAuthorId()).followUnfollow(mAuthor, SocialProfileRef.FollowUnfollow
                        .FOLLOW, FragmentProfileView.this);
            }
        });
    }

    private void launchAuthorRatings() {
        getApp().getUi().getLauncher().getReviewLauncher().launchAsList(mAuthor);
    }

    private void setButtonBar(Button ratings, Button followers, Button following) {
        RepositorySuite repository = getApp().getRepository();
        AuthorsRepo authors = repository.getAuthors();
        ReviewsNodeRepo reviews = repository.getReviews();

        AuthorProfileRef author = authors.getAuthorProfile(mAuthor);
        SocialProfileRef social = getSocialProfile(mAuthor);

        author.bindToValue(this);
        mRatings = new SizeBinder(reviews.getReviewsByAuthor(mAuthor).getSize(), ratings, REVIEWS);
        mFollowers = new SizeBinder(social.getFollowers().getSize(), followers, FOLLOWERS);
        mFollowing = new SizeBinder(social.getFollowing().getSize(), following, FOLLOWING);

        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAuthorRatings();
            }
        });
    }

    private void setProfile(AuthorProfile profile) {
        mName.setText(profile.getAuthor().getName());
        ProfileImage image = profile.getImage();
        Bitmap bitmap = image.getBitmap();
        if (bitmap != null) {
            mPhoto.setImageBitmap(bitmap);
        } else {
            mPhoto.setImageResource(IMAGE_PLACEHOLDER);
        }
    }

    private boolean setAuthor() {
        Bundle args = getArguments();
        if (args == null) return false;
        mAuthor = args.getParcelable(ARGS);
        return mAuthor != null;
    }

    private static class SizeBinder implements ReferenceBinder<Size> {
        private final DataReference<Size> mSize;
        private final Button mView;
        private final String mSizeType;
        private int mNumber = 0;

        private SizeBinder(DataReference<Size> size, Button view, String sizeType) {
            mSize = size;
            mView = view;
            mSizeType = sizeType;
        }

        @Override
        public void onReferenceValue(Size value) {
            mNumber = value.getSize();
            setText();
        }

        @Override
        public void onInvalidated(DataReference<Size> reference) {
            mNumber = 0;
            setText();
            unbind();
        }

        private void setText() {
            String text = String.valueOf(mNumber) + "\n" + mSizeType;
            mView.setText(text);
        }

        private void unbind() {
            mSize.unbindFromValue(this);
        }

        private void bind() {
            mView.setText(Strings.EditTexts.FETCHING);
            mSize.bindToValue(this);
        }
    }
}
