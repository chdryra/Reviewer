/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;



import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TitleDecorator;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefAuthorList;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.startouch.R;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentProfileView extends Fragment implements ReferenceBinder<AuthorProfile>, ActivityResultListener {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentProfileView.class, "Args");
    private static final int LAYOUT = R.layout.fragment_profile_view;
    private static final int PROFILE_IMAGE = R.id.profile_image;
    private static final int PROFILE_AUTHOR = R.id.profile_name;
    private static final int RATINGS_BUTTON = R.id.wide_button;
    private static final int SOCIAL_BUTTON = R.id.wide_button;
    private static final int FOLLOWERS_VIEW = R.id.followers_view;
    private static final int FOLLOWING_VIEW = R.id.following_view;
    private static final int IMAGE_PLACEHOLDER = R.drawable.ic_face_black_36dp;

    private static final TitleDecorator DECORATOR = Styles.TitleDecorators.DONE_BUTTON;
    private static final String PROFILE_DELETED = "Profile deleted";

    private AuthorId mAuthor;
    private TextView mName;
    private ImageView mPhoto;
    private SocialBinder mFollwers;
    private SocialBinder mFollowing;

    public static FragmentProfileView newInstance(GvAuthorId authorId) {
        FragmentProfileView fragment = new FragmentProfileView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS, authorId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mPhoto = view.findViewById(PROFILE_IMAGE);
        mName = view.findViewById(PROFILE_AUTHOR);
        Button ratingsButton = view.findViewById(RATINGS_BUTTON);
        ratingsButton.setText(DECORATOR.decorate(Strings.REVIEWS));
        ratingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAuthorRatings();
            }
        });

        Button followers = view.findViewById(FOLLOWERS_VIEW).findViewById(SOCIAL_BUTTON);
        Button following = view.findViewById(FOLLOWING_VIEW).findViewById(SOCIAL_BUTTON);

        mPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(), IMAGE_PLACEHOLDER));

        if(!setAuthor()) {
            getApp().getUi().getCurrentScreen().showToast("No author...");
        } else {
            getProfile(followers, following);
        }

        return view;
    }

    private void launchAuthorRatings() {
        getApp().getUi().getLauncher().getReviewLauncher().launchAsList(mAuthor);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onReferenceValue(AuthorProfile value) {
        setProfile(value);
    }

    @Override
    public void onInvalidated(DataReference<AuthorProfile> reference) {
        mName.setText(PROFILE_DELETED);
    }

    private void getProfile(Button followers, Button following) {
        AppInstanceAndroid app = getApp();
        AuthorProfileRef profile = app.getRepository().getAuthors().getAuthorProfile(mAuthor);
        profile.bindToValue(this);
        SocialProfileRef social = app.getRepository().getAuthors().getSocialProfile(mAuthor);
        mFollwers = new SocialBinder(social.getFollowers(), followers, "followers");
        mFollowing = new SocialBinder(social.getFollowing(), following, "following");
    }

    private class SocialBinder implements ListItemBinder<AuthorId> {
        private final RefAuthorList mList;
        private final Button mView;
        private final String mStem;
        private int mNumber = 0;

        private SocialBinder(RefAuthorList list, Button view, String stem) {
            mList = list;
            mView = view;
            mStem = stem;
            mList.bindToItems(this);
        }

        @Override
        public void onItemAdded(AuthorId value) {
            ++mNumber;
            setText();
        }

        @Override
        public void onItemRemoved(AuthorId value) {
            --mNumber;
            setText();
        }

        @Override
        public void onListChanged(Collection<AuthorId> newItems) {
            mNumber = newItems.size();
            setText();
        }

        private void setText() {
            String text = String.valueOf(mNumber) + " " + mStem;
            mView.setText(text);
        }

        @Override
        public void onInvalidated(ListReference<AuthorId, ?> reference) {
            mNumber = 0;
            setText();
            mList.unbindFromItems(this);
        }

        private void unbind() {
            mList.unbindFromItems(this);
        }
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private void setProfile(AuthorProfile profile) {
        mName.setText(profile.getAuthor().getName());
        ProfileImage image = profile.getImage();
        Bitmap bitmap = image.getBitmap();
        if(bitmap != null) {
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

}
