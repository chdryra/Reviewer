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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.corelibrary.OtherUtils.DataGetter;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.DataBinder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.FollowBinder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ImageUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.SizeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.TextUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.WrapperBinder;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorId;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
//TODO refactor into presenter framework
public class FragmentAuthorView extends Fragment implements ActivityResultListener {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentAuthorView.class, "Args");

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

    private AuthorId mAuthorId;
    private FollowBinder mFollow;
    private DataBinder<AuthorName> mName;
    private DataBinder<ProfileImage> mPhoto;
    private DataBinder<Size> mRatings;
    private DataBinder<Size> mFollowers;
    private DataBinder<Size> mFollowing;

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

        ImageView photo = view.findViewById(PROFILE_IMAGE);
        TextView name = view.findViewById(PROFILE_AUTHOR);
        Button followEdit = view.findViewById(FOLLOW_EDIT_BUTTON);

        Button ratings = view.findViewById(RATINGS_BUTTON);
        Button followers = view.findViewById(FOLLOWERS_BUTTON);
        Button following = view.findViewById(FOLLOWING_BUTTON);

        if (!setAuthor()) {
            getUi().getCurrentScreen().showToast(NO_AUTHOR);
        } else {
            bindProfile(name, photo);
            bindFollowEditButton(followEdit);
            bindButtons(ratings, followers, following);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mName != null) mName.bind();
        if (mPhoto != null) mPhoto.bind();
        if (mFollow != null) mFollow.bind();
        if (mRatings != null) mRatings.bind();
        if (mFollowers != null) mFollowers.bind();
        if (mFollowing != null) mFollowing.bind();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mName != null) mName.unbind();
        if (mPhoto != null) mPhoto.unbind();
        if (mFollow != null) mFollow.unbind();
        if (mRatings != null) mRatings.unbind();
        if (mFollowers != null) mFollowers.unbind();
        if (mFollowing != null) mFollowing.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getUi().getCurrentScreen().closeAndGoUp();
            return true;
        }

        return false;
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private AuthorId getUserId() {
        return getApp().getAccounts().getUserSession().getAuthorId();
    }

    private UiSuite getUi() {
        return getApp().getUi();
    }

    private RepositorySuite getRepository() {
        return getApp().getRepository();
    }

    private void bindButtons(Button ratings, Button followers, Button following) {
        ReviewNodeRepo reviews = getRepository().getReviews();
        SocialProfileRef social = getSocialProfile(mAuthorId);
        mRatings = bindButton(ratings, reviews.getRepoForAuthor(mAuthorId).getSize(), REVIEWS);
        mFollowers = bindButton(followers, social.getFollowers().getSize(), FOLLOWERS);
        mFollowing = bindButton(following, social.getFollowing().getSize(), FOLLOWING);
        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUi().getLauncher().getReviewLauncher().launchAsList(mAuthorId);
            }
        });
    }

    private boolean setAuthor() {
        Bundle args = getArguments();
        if (args == null) return false;
        mAuthorId = args.getParcelable(ARGS);
        return mAuthorId != null;
    }

    private void bindProfile(TextView name, ImageView photo) {
        AuthorProfileRef profile = getRepository().getAuthors().getAuthorProfile(mAuthorId);

        mName = new WrapperBinder<>(new TextUi<>(name), profile.getAuthor(),
                new DataGetter<AuthorName, String>() {
                    @Override
                    public String getData(AuthorName item) {
                        return item.getName();
                    }
                });

        mPhoto = new WrapperBinder<>(new ImageUi(photo, IMAGE_PLACEHOLDER, null),
                profile.getImage(), new DataGetter<ProfileImage, Bitmap>() {
            @Override
            @Nullable
            public Bitmap getData(ProfileImage item) {
                return item.getBitmap();
            }
        });
    }

    private void bindFollowEditButton(final Button followEdit) {
        AuthorId userId = getUserId();
        if (userId.equals(mAuthorId)) {
            followEdit.setText(Strings.Buttons.EDIT_PROFILE);
            followEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getUi().getCommandsFactory().newLaunchProfileCommand().execute();
                }
            });
        } else {
            mFollow = new FollowBinder(mAuthorId, getSocialProfile(userId), followEdit);
        }
    }

    @NonNull
    private DataBinder<Size> bindButton(Button button, DataReference<Size> reference, String stem) {
        return new DataBinder<>(new SizeUi(button, stem, null), reference);
    }

    private SocialProfileRef getSocialProfile(AuthorId authorId) {
        return getRepository().getAuthors().getSocialProfile(authorId);
    }
}
