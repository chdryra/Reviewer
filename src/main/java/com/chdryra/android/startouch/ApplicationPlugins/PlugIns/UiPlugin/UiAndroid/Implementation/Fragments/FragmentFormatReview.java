/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DereferencableBasic;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.Ui.PagerAdapterBasic;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityFormatReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.CellDimensionsCalculator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.CommentFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.CoverFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.DataExpandableUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.GvDataAdapterBindable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.HorizontalGridUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ImagesSizeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.MenuOptionsAppLevel;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.MenuUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.MenuUpAppLevel;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ProfileFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.RatingFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.StampFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.SubjectFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.TagsFormattedUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.DataBinder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.CompoundBinder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ViewUi;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MaiOptionsCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MaiUpAppLevel;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhCriterionFormatted;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhFactFormatted;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhFactory;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhLocationFormatted;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

//TODO refactor into MVP pattern
public class FragmentFormatReview extends PagerAdapterBasic.PageableFragment implements
        DataReference.InvalidationListener,
        OptionSelectListener {
    private static final String ID = TagKeyGenerator.getKey(FragmentFormatReview.class,
            "ReviewId");
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentFormatReview.class,
            "published");

    private static final int LAYOUT = R.layout.fragment_review_formatted;
    private static final int IMAGE = R.id.image_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int HEADLINE = R.id.headline_formatted;
    private static final int STAMP = R.id.stamp_formatted;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int FACTS = R.id.facts_formatted;
    private static final int LOCATIONS = R.id.locations_formatted;
    private static final int IMAGES = R.id.images_formatted;
    private static final int DATA = R.id.section_data;
    private static final int IMAGE_PADDING = R.dimen.formatted_image_padding;
    private static final int IMAGE_PLACEHOLDER = R.drawable.image_placeholder;

    private static final ReviewViewParams.CellDimension FULL
            = ReviewViewParams.CellDimension.FULL;
    private static final ReviewViewParams.CellDimension HALF
            = ReviewViewParams.CellDimension.HALF;

    private boolean mIsPublished = true;
    private ReviewNode mNode;
    //private RepositorySuite getRepo().
    private MenuUi mMenu;

    private DataBinder<DataImage> mCover;
    private DataBinder<DataSubject> mSubject;
    private DataBinder<DataRating> mRating;
    private DataBinder<AuthorName> mStamp;
    private DataBinder<IdableList<DataTag>> mTags;
    private DataBinder<IdableList<DataComment>> mComment;
    private DataBinder<IdableList<DataLocation>> mLocations;
    private DataBinder<IdableList<DataCriterion>> mCriteria;
    private DataBinder<IdableList<DataFact>> mFacts;
    private DataBinder<DataSize> mImages;

    public static FragmentFormatReview newInstance(String nodeId, boolean isPublished) {
        //Can't use FactoryFragment as Support fragment rather than normal fragment
        Bundle args = new Bundle();
        args.putString(ID, nodeId);
        args.putBoolean(PUBLISHED, isPublished);
        FragmentFormatReview fragment = new FragmentFormatReview();
        fragment.setArguments(args);

        return fragment;
    }

    public boolean isPublished() {
        return mIsPublished;
    }

    @Override
    public String getPageId() {
        return mNode.getReviewId().toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        Bundle args = getArguments();
        if (args == null) {
            noReview();
            return v;
        }

        mIsPublished = args.getBoolean(PUBLISHED);

        setNode(args);
        setMenu();

        bindCover((ImageView) v.findViewById(IMAGE));
        bindSubject((TextView) v.findViewById(SUBJECT));
        bindRating((RatingBar) v.findViewById(RATING));
        bindStamp((TextView) v.findViewById(STAMP));
        bindTags((TextView) v.findViewById(TAGS));
        bindComments(getSection(v, COMMENT), (TextView) v.findViewById(HEADLINE));
        bindCriteria(getSection(v, CRITERIA));
        bindFacts(getSection(v, FACTS));
        bindLocations(getSection(v, LOCATIONS));
        bindImages(getSection(v, IMAGES));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mMenu.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mMenu.onOptionsCancelled(requestCode);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCover.bind();
        mSubject.bind();
        mRating.bind();
        mStamp.bind();
        mTags.bind();
        mComment.bind();
        mLocations.bind();
        mCriteria.bind();
        mFacts.bind();
        mImages.bind();

    }

    @Override
    public void onStop() {
        super.onStop();
        mCover.unbind();
        mSubject.unbind();
        mRating.unbind();
        mStamp.unbind();
        mTags.unbind();
        mComment.unbind();
        mLocations.unbind();
        mCriteria.unbind();
        mFacts.unbind();
        mImages.unbind();
    }

    @Override
    public void onInvalidated(DataReference<?> reference) {
        if (getContainer() != null) getContainer().remove(this);
    }

    @Override
    @NonNull
    public Context getContext() {
        Context context = super.getContext();
        if (context == null) {
            throw new RuntimeException("No Context attached to FragmentReviewView");
        }

        return context;
    }

    private ActivityFormatReview getContainer() {
        try {
            return (ActivityFormatReview) getActivity();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getContext());
    }

    private RepositorySuite getRepo() {
        return getApp().getRepository();
    }

    private UiSuite getUi() {
        return getApp().getUi();
    }

    private FactoryLaunchCommands getCommandsFactory() {
        return getUi().getCommandsFactory();
    }

    private ConverterGv getConverter() {
        return getUi().getGvConverter();
    }

    private void setNode(Bundle args) {
        String reviewId = args.getString(ID);
        if (reviewId == null) {
            noReview();
            return;
        }

        mNode = getContainer().getNode(new DatumReviewId(reviewId));
        ReviewReference reference = mNode.getReference();
        if (mNode.isLeaf() && reference != null) reference.registerListener(this);
    }

    private void setMenu() {
        AppInstanceAndroid app = getApp();
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);

        MenuAction<?> action;
        if (mIsPublished) {
            OptionsCommand command
                    = getCommandsFactory().getOptionsFactory().newReviewOptionsSelector
                    (ReviewOptionsSelector.SelectorType.ALL, new NodeAuthorId(mNode));
            MaiOptionsCommand<GvData> mai = new MaiOptionsCommand<>(command);
            action = new MenuOptionsAppLevel(Strings.Menu.REVIEWS, upAction, mai, ui);
        } else {
            action = new MenuUpAppLevel(Strings.Screens.PREVIEW, upAction, ui);
        }

        mMenu = new MenuUi(action);
    }

    @Nullable
    private Command launchView(GvDataType<?> type) {
        return mIsPublished ? newLaunchViewCommand(type) : null;
    }

    private void bindCover(ImageView view) {
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getContainer());
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(FULL, FULL, 0);
        view.getLayoutParams().width = dims.getCellWidth();
        view.getLayoutParams().height = dims.getCellHeight();

        LaunchBespokeViewCommand images = getCommandsFactory().newLaunchBespokeViewCommand(mNode,
                "Images", GvImage.TYPE);

        CoverFormattedUi coverUi = new CoverFormattedUi(view, IMAGE_PLACEHOLDER, images);
        ProfileFormattedUi profileUi = new ProfileFormattedUi(view, IMAGE_PLACEHOLDER, images);

        DataReference<DataImage> cover;
        if (mNode.isLeaf() && mNode.getReference() != null) {
            //More efficient this way.
            cover = mNode.getReference().getCover();
        } else {
            cover = mNode.getCover();
        }
        DataReference<ProfileImage> profile = getRepo().getAuthors().getAuthorProfile(mNode
                .getAuthorId()).getProfileImage();

        DataBinder<ProfileImage> profileBinder = new DataBinder<>(profileUi, profile);
        mCover = new CompoundBinder<>(coverUi, cover, profileBinder,
                new CompoundBinder.BinderEvent<DataImage>() {
                    @Override
                    public boolean bindOnValue(DataImage value) {
                        return value.getBitmap() == null;
                    }
                });
    }

    private void bindSubject(TextView view) {
        mSubject = new DataBinder<>(new SubjectFormattedUi(view, null),
                new SubjectReference(mNode));
    }

    private void bindRating(RatingBar view) {
        mRating = new DataBinder<>(new RatingFormattedUi(view, null),
                new RatingReference(mNode));
    }

    private void bindStamp(TextView view) {
        mStamp = new DataBinder<>(new StampFormattedUi(view, mNode.getPublishDate(), launchAuthor
                ()),
                getRepo().getAuthors().getReference(mNode.getAuthorId()));
    }

    private void bindTags(TextView view) {
        mTags = new DataBinder<>(new TagsFormattedUi(view, launchView(GvTag.TYPE)),
                mNode.getTags());
    }

    private void bindComments(LinearLayout section, TextView headline) {
        mComment = new DataBinder<>(new CommentFormattedUi(section, headline,
                launchView(GvComment.TYPE)), mNode.getComments());
    }

    private void bindLocations(LinearLayout section) {
        mLocations = new DataBinder<>(newDataUi(section, Strings.Formatted.LOCATIONS, GvLocation
                        .TYPE,
                getConverter().newConverterLocations(), VhLocationFormatted.class, true),
                mNode.getLocations());
    }

    private void bindCriteria(LinearLayout section) {
        mCriteria = new DataBinder<>(newDataUi(section, Strings.Formatted.CRITERIA, GvCriterion
                        .TYPE,
                getConverter().newConverterCriteria(), VhCriterionFormatted.class, false),
                mNode.getCriteria());
    }

    private void bindFacts(LinearLayout section) {
        mFacts = new DataBinder<>(newDataUi(section, Strings.Formatted.FACTS, GvFact.TYPE,
                getConverter().newConverterFacts(), VhFactFormatted.class, false),
                mNode.getFacts());
    }

    private void bindImages(LinearLayout section) {
        float padding = getResources().getDimensionPixelSize(IMAGE_PADDING);

        FragmentActivity activity = getActivity();
        if (activity == null) return;

        CellDimensionsCalculator calculator = new CellDimensionsCalculator(activity);
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(HALF, HALF, (int)
                padding);

        RecyclerView grid = section.findViewById(DATA);
        ViewUi<RecyclerView, IdableList<DataImage>> imagesView = newGridUi
                (grid, VhImage.class, (int) padding, dims, getConverter().newConverterImages());

        ImagesSizeUi imagesSizeUi = new ImagesSizeUi(section);
        DataListRef<DataImage> images = mNode.getImages();
        DataBinder<IdableList<DataImage>> imagesBinder = new DataBinder<>(imagesView, images);

        mImages = new CompoundBinder<>(imagesSizeUi, images.getSize(), imagesBinder,
                new CompoundBinder.BinderEvent<DataSize>() {
                    @Override
                    public boolean bindOnValue(DataSize value) {
                        return value.getSize() > 0;
                    }
                });

    }

    private void setLaunchOnClick(ViewUi<?, ?> layout, @Nullable Command onClick) {
        if (mIsPublished && onClick != null) layout.setOnClickCommand(onClick);
    }

    private <T1 extends HasReviewId, T2 extends GvData, Vh extends ViewHolder>
    ViewUi<RecyclerView, IdableList<T1>>
    newGridUi(RecyclerView view, Class<Vh> vhClass,
              int padding, CellDimensionsCalculator.Dimensions dims,
              DataConverter<T1, T2, ? extends GvDataList<T2>> converter) {

        GvDataType<T2> dataType = converter.convert(new IdableDataList<T1>()).getGvDataType();

        GvDataAdapterBindable<T1, T2, Vh> adapter
                = new GvDataAdapterBindable<>(converter, dims.getCellHeight(), dims.getCellHeight(),
                new VhFactory<>(vhClass));

        Command onClick = mIsPublished ? newLaunchViewCommand(dataType) : null;
        view.addItemDecoration(new PaddedItemDecoration(padding));

        return new HorizontalGridUi<>(getContext(), view, adapter, 1, onClick);
    }

    private Command newLaunchViewCommand(GvDataType<?> dataType) {
        return getCommandsFactory().newLaunchViewCommand(getUi().newDataView(mNode, dataType));
    }

    private Command launchAuthor() {
        return getUi().getCommandsFactory().newLaunchProfileCommand(mNode.getAuthorId());
    }

    private <T extends HasReviewId, G extends GvData> DataExpandableUi<T>
    newDataUi(LinearLayout layout,
              String title,
              GvDataType<G> dataType,
              DataConverter<T, G, ?> converter,
              Class<? extends ViewHolder> vhClass,
              boolean setLaunchOnClick) {
        DataExpandableUi<T> ui
                = new DataExpandableUi<>(getContext(), layout, title, newVhFactory(vhClass),
                converter);

        if (setLaunchOnClick) setLaunchOnClick(ui, launchView(dataType));

        return ui;
    }

    @NonNull
    private ViewHolderFactory<ViewHolder> newVhFactory(final Class<? extends ViewHolder> vhClass) {
        return new ViewHolderFactory<ViewHolder>() {
            @Override
            public ViewHolder newViewHolder() {
                try {
                    return vhClass.newInstance();
                } catch (java.lang.InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private void noReview() {
        CurrentScreen currentScreen = getUi().getCurrentScreen();
        currentScreen.showToast(Strings.Toasts.REVIEW_NOT_FOUND);
        currentScreen.close();
    }

    private LinearLayout getSection(View v, int id) {
        return (LinearLayout) v.findViewById(id);
    }

    private static class SubjectReference extends DereferencableBasic<DataSubject> {
        private final ReviewNode mNode;

        private SubjectReference(ReviewNode node) {
            mNode = node;
        }

        @Override
        protected void doDereferencing(DereferenceCallback<DataSubject> callback) {
            callback.onDereferenced(new DataValue<>(mNode.getSubject()));
        }
    }

    private static class RatingReference extends DereferencableBasic<DataRating> {
        private final ReviewNode mNode;

        private RatingReference(ReviewNode node) {
            mNode = node;
        }

        @Override
        protected void doDereferencing(DereferenceCallback<DataRating> callback) {
            callback.onDereferenced(new DataValue<>(mNode.getRating()));
        }
    }

    private static class NodeAuthorId extends DatumAuthorId {
        private ReviewNode mNode;

        private NodeAuthorId(ReviewNode node) {
            super(node.getReviewId(), node.getAuthorId().toString());
            mNode = node;
        }

        @Override
        public ReviewId getReviewId() {
            return mNode.getReviewId();
        }

        @Override
        public String toString() {
            return mNode.getAuthorId().toString();
        }
    }

    private static class PaddedItemDecoration extends RecyclerView.ItemDecoration {
        private final int mPadding;

        PaddedItemDecoration(int padding) {
            mPadding = padding;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.right = mPadding;
        }
    }
}
