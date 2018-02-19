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
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.FollowSubscriber;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorId;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentAuthorView extends Fragment implements
        ActivityResultListener, FollowSubscriber.FollowListener, SocialProfileRef
                .FollowCallback {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentAuthorView.class, "Args");
    private static final String PROFILE_DELETED = "Profile deleted";

    private static final int LAYOUT = R.layout.fragment_author_view;
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
    private static final SocialProfileRef.FollowUnfollow FOLLOW
            = SocialProfileRef.FollowUnfollow.FOLLOW;
    private static final SocialProfileRef.FollowUnfollow UNFOLLOW
            = SocialProfileRef.FollowUnfollow.UNFOLLOW;

    private AuthorId mAuthor;
    private ImageView mPhoto;
    private TextView mName;
    private Button mFollowEdit;
    private SizeSubscriber mRatings;
    private SizeSubscriber mFollowers;
    private SizeSubscriber mFollowing;
    private FollowSubscriber mFollowBinder;

    public static FragmentAuthorView newInstance(GvAuthorId authorId) {
        FragmentAuthorView fragment = new FragmentAuthorView();
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
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mPhoto = view.findViewById(PROFILE_IMAGE);
        mName = view.findViewById(PROFILE_AUTHOR);
        mFollowEdit = view.findViewById(FOLLOW_EDIT_BUTTON);
        mPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(), IMAGE_PLACEHOLDER));

        if (!setAuthor()) {
            getCurrentScreen().showToast(NO_AUTHOR);
        } else {
            getProfile();
            setFollowEditButton();
            setButtonBar(view);
        }

        return view;
    }

    @Override
    public void onFollowing(AuthorId authorId) {
        if (authorId.equals(mAuthor)) showUnfollow();
    }

    @Override
    public void onUnfollowing(AuthorId authorId) {
        if (authorId.equals(mAuthor)) showFollow();
    }

    @Override
    public void onFollow(AuthorId authorId, SocialProfileRef.FollowUnfollow type, CallbackMessage
            message) {
        if (message.isOk() && type.equals(FOLLOW)) {
            showUnfollow();
        } else {
            showFollow();
        }
    }

    @Override
    public void onFollowInvalidate() {
        showFollow();
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
        if (item.getItemId() == android.R.id.home) {
            getCurrentScreen().closeAndGoUp();
            return true;
        }

        return false;
    }

    private void getProfile() {
        AuthorProfileRef profile = getRepository().getAuthors().getAuthorProfile(mAuthor);
        profile.subscribe(new DataReference.ValueSubscriber<AuthorProfile>() {
            @Override
            public void onReferenceValue(AuthorProfile value) {
                setProfile(value);
            }

            @Override
            public void onInvalidated(DataReference<AuthorProfile> reference) {
                mName.setText(PROFILE_DELETED);
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

    private void showUnfollow() {
        setFollowUnfollow(UNFOLLOW);
    }

    private void showFollow() {
        setFollowUnfollow(FOLLOW);
    }

    private void setFollowEditButton() {
        if (getUserAuthorId().equals(mAuthor)) {
            setButtonEdit();
        } else {
            mFollowEdit.setText(Strings.Buttons.FOLLOW);
            mFollowBinder = new FollowSubscriber(getSocialProfile(getUserAuthorId()).getFollowing(),
                    this);
        }
    }

    private void setButtonEdit() {
        mFollowEdit.setText(Strings.Buttons.EDIT_PROFILE);
        mFollowEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUi().getCommandsFactory().newLaunchProfileCommand().execute();
            }
        });
    }

    private void setFollowUnfollow(final SocialProfileRef.FollowUnfollow followUnfollow) {
        mFollowEdit.setText(followUnfollow == SocialProfileRef.FollowUnfollow.UNFOLLOW ? Strings
                .Buttons.UNFOLLOW : Strings.Buttons.FOLLOW);
        mFollowEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSocialProfile(getUserAuthorId()).followUnfollow(mAuthor, followUnfollow,
                        FragmentAuthorView.this);
            }
        });
    }

    private boolean setAuthor() {
        Bundle args = getArguments();
        if (args == null) return false;
        mAuthor = args.getParcelable(ARGS);
        return mAuthor != null;
    }

    private void setButtonBar(View view) {
        Button ratings = view.findViewById(RATINGS_BUTTON);
        Button followers = view.findViewById(FOLLOWERS_BUTTON);
        Button following = view.findViewById(FOLLOWING_BUTTON);

        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAuthorRatings();
            }
        });
        ReviewsNodeRepo reviews = getRepository().getReviews();
        SocialProfileRef social = getSocialProfile(mAuthor);
        mRatings = new SizeSubscriber(reviews.getReviewsByAuthor(mAuthor).getSize(), ratings, REVIEWS);
        mFollowers = new SizeSubscriber(social.getFollowers().getSize(), followers, FOLLOWERS);
        mFollowing = new SizeSubscriber(social.getFollowing().getSize(), following, FOLLOWING);

    }

    private SocialProfileRef getSocialProfile(AuthorId authorId) {
        return getRepository().getAuthors().getSocialProfile(authorId);
    }

    private void launchAuthorRatings() {
        getUi().getLauncher().getReviewLauncher().launchAsList(mAuthor);
    }


    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private AuthorId getUserAuthorId() {
        return getApp().getAccounts().getUserSession().getAuthorId();
    }

    private CurrentScreen getCurrentScreen() {
        return getUi().getCurrentScreen();
    }

    private UiSuite getUi() {
        return getApp().getUi();
    }

    private RepositorySuite getRepository() {
        return getApp().getRepository();
    }

    private static class SizeSubscriber implements DataReference.ValueSubscriber<Size> {
        private final DataReference<Size> mSize;
        private final Button mView;
        private final String mSizeType;

        private SizeSubscriber(DataReference<Size> size, Button view, String sizeType) {
            mSize = size;
            mView = view;
            mSizeType = sizeType;
        }

        @Override
        public void onReferenceValue(Size value) {
            setText(value.getSize());
        }

        @Override
        public void onInvalidated(DataReference<Size> reference) {
            setText(0);
            unbind();
        }

        private void setText(int number) {
            String text = String.valueOf(number) + "\n" + mSizeType;
            mView.setText(text);
        }

        private void unbind() {
            mSize.unsubscribe(this);
        }

        private void bind() {
            mView.setText(Strings.EditTexts.FETCHING);
            mSize.subscribe(this);
        }
    }
}
