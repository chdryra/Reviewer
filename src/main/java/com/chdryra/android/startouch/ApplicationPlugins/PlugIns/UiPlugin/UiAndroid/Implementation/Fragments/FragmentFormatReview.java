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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.chdryra.android.corelibrary.Ui.PagerAdapterBasic;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFormatReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CommentNodeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverNodeBannerUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.DataExpandableUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.FormattedSectionUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.GvDataRefAdapter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalGridUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ImagesNodeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuOptionsAppLevel;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUpAppLevel;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarTouchable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SimpleViewUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.StampNodeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectNodeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TagsNodeUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhCriterionFormatted;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhFactFormatted;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhFactory;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhLocationFormatted;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentFormatReview extends PagerAdapterBasic.PageableFragment implements
        ReviewNode.NodeObserver,
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
    private UiSuite mUi;
    private RepositorySuite mRepo;
    private MenuUi mMenu;

    private CoverNodeBannerUi mCover;
    private ViewUi<TextView, String> mSubject;
    private ViewUi<RatingBar, Float> mRating;
    private ViewUi<TextView, DataListRef<DataTag>> mTags;
    private ViewUi<TextView, AuthorRef> mStamp;
    private FormattedSectionUi<CommentListRef> mComment;
    private FormattedSectionUi<DataListRef<DataCriterion>> mCriteria;
    private FormattedSectionUi<DataListRef<DataFact>> mFacts;
    private FormattedSectionUi<DataListRef<DataLocation>> mLocations;
    private FormattedSectionUi<ReviewItemReference<DataSize>> mImages;

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
        mUi = getApp().getUi();
        mRepo = getApp().getRepository();

        setNode(args);
        setMenu();

        setCover(v);
        setSubject(v);
        setRating(v);
        setStamp(v);
        setComment(v);
        setTags(v);
        setCriteria(v);
        setFacts(v);
        setLocations(v);
        setImages(v);

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
    public void onChildAdded(ReviewNode child) {
        update();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        update();
    }

    @Override
    public void onNodeChanged() {
        update();
    }

    @Override
    public void onTreeChanged() {
        update();
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
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNode.unregisterObserver(this);
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        if (getContainer() != null) getContainer().remove(this);
    }

    @Override
    @NonNull
    public Context getContext() {
        Context context = super.getContext();
        if(context == null) {
            throw new RuntimeException("No Context attached to FragmentReviewView");
        }

        return context;
    }

    private FactoryLaunchCommands getCommandsFactory() {
        return mUi.getCommandsFactory();
    }

    private ConverterGv getConverter() {
        return mUi.getGvConverter();
    }

    private ActivityFormatReview getContainer() {
        try {
            return (ActivityFormatReview) getActivity();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void noReview() {
        CurrentScreen currentScreen = getApp().getUi().getCurrentScreen();
        currentScreen.showToast(Strings.Toasts.REVIEW_NOT_FOUND);
        currentScreen.close();
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getContext());
    }

    private void setNode(Bundle args) {
        String reviewId = args.getString(ID);
        if (reviewId == null) {
            noReview();
            return;
        }

        mNode = getContainer().getNode(new DatumReviewId(reviewId));
        mNode.registerObserver(this);
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
                    (ReviewOptionsSelector
                    .SelectorType.ALL, new NodeAuthorId(mNode));
            MaiOptionsCommand<GvData> mai = new MaiOptionsCommand<>(command);
            action = new MenuOptionsAppLevel(Strings.Menu.REVIEWS, upAction, mai, ui);
        } else {
            action = new MenuUpAppLevel(Strings.Screens.PREVIEW, upAction, ui);
        }

        mMenu = new MenuUi(action);
    }

    private void update() {
        mCover.update();
        mImages.update();
        mSubject.update();
        mRating.update();
        mStamp.update();
        mTags.update();
        mComment.update();
        mLocations.update();
        mCriteria.update();
        mFacts.update();
    }

    @Nullable
    private Command launchView(GvDataType<?> type) {
        return mIsPublished ? newLaunchViewCommand(type) : null;
    }

    private void setCover(View v) {
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        CellDimensionsCalculator calculator = new CellDimensionsCalculator(activity);
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(FULL, FULL, 0);
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(), IMAGE_PLACEHOLDER);

        mCover = new CoverNodeBannerUi((ImageView) v.findViewById(IMAGE), mNode,
                mRepo.getAuthors().getAuthorProfile(mNode.getAuthorId()).getProfileImage(),
                placeholder, dims);
        setLaunchOnClick(mCover, getCommandsFactory().newLaunchBespokeViewCommand(mNode,
                "Images", GvImage.TYPE));
    }

    private void setSubject(View v) {
        mSubject = new SubjectNodeUi((TextView) v.findViewById(SUBJECT), mNode, null);
    }

    private void setRating(View v) {
        mRating = new RatingBarTouchable((RatingBar) v.findViewById(RATING), mNode, null);
    }

    private void setStamp(View v) {
        mStamp = new StampNodeUi((TextView) v.findViewById(STAMP), mNode, mRepo.getAuthors());
        setLaunchOnClick(mStamp, launchAuthor());
    }

    private void setTags(View v) {
        mTags = new TagsNodeUi((TextView) v.findViewById(TAGS), mNode);
    }

    private void setComment(View v) {
        mComment = new CommentNodeUi(getSection(v, COMMENT), (TextView) v.findViewById(HEADLINE),
                mNode, null);
    }

    private void setLocations(View v) {
        mLocations = newDataUi(v, LOCATIONS, Strings.Formatted.LOCATIONS, GvLocation.TYPE,
                getConverter().newConverterLocations(), VhLocationFormatted.class, true,
                new SimpleViewUi.ReferenceValueGetter<DataListRef<DataLocation>>() {
                    @Override
                    public DataListRef<DataLocation> getValue() {
                        return mNode.getLocations();
                    }
                });
    }

    private void setCriteria(View v) {
        mCriteria = newDataUi(v, CRITERIA, Strings.Formatted.CRITERIA, GvCriterion.TYPE,
                getConverter().newConverterCriteria(), VhCriterionFormatted.class,
                false, new SimpleViewUi.ReferenceValueGetter<DataListRef<DataCriterion>>() {
                    @Override
                    public DataListRef<DataCriterion> getValue() {
                        return mNode.getCriteria();
                    }
                });
    }

    private void setFacts(View v) {
        mFacts = newDataUi(v, FACTS, Strings.Formatted.FACTS, GvFact.TYPE,
                getConverter().newConverterFacts(), VhFactFormatted.class, false,
                new SimpleViewUi.ReferenceValueGetter<DataListRef<DataFact>>() {
                    @Override
                    public DataListRef<DataFact> getValue() {
                        return mNode.getFacts();
                    }
                });
    }

    private void setImages(View v) {
        LinearLayout section = getSection(v, IMAGES);
        RecyclerView grid = section.findViewById(DATA);
        float padding = getResources().getDimensionPixelSize(IMAGE_PADDING);

        FragmentActivity activity = getActivity();
        if(activity == null) return;

        CellDimensionsCalculator calculator = new CellDimensionsCalculator(activity);
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(HALF, HALF, (int)
                padding);

        ViewUi<RecyclerView, DataListRef<DataImage>> dataView = newGridUi
                (grid, VhImage.class, (int) padding, dims, getConverter().newConverterImages(),
                        new SimpleViewUi.ReferenceValueGetter<DataListRef<DataImage>>() {
                            @Override
                            public DataListRef<DataImage> getValue() {
                                return mNode.getImages();
                            }
                        });

        mImages = new ImagesNodeUi(section, dataView, mNode);
    }

    private void setLaunchOnClick(ViewUi<?, ?> layout, @Nullable Command onClick) {
        if (mIsPublished && onClick != null) layout.setOnClickCommand(onClick);
    }

    private <T1 extends HasReviewId, T2 extends GvData, Vh extends ViewHolder>
    ViewUi<RecyclerView, DataListRef<T1>>
    newGridUi(RecyclerView view, Class<Vh> vhClass,
              int padding, CellDimensionsCalculator.Dimensions dims,
              DataConverter<T1, T2, ? extends GvDataList<T2>> converter,
              SimpleViewUi.ReferenceValueGetter<DataListRef<T1>> getter) {
        IdableDataList<T1> empty = new IdableDataList<>(getter.getValue().getReviewId());
        GvDataType<T2> dataType = converter.convert(empty).getGvDataType();

        GvDataRefAdapter<T1, T2, Vh> adapter
                = new GvDataRefAdapter<>(getter, converter, new VhFactory<>(vhClass), dims);

        Command onClick = mIsPublished ? newLaunchViewCommand(dataType) : null;
        view.addItemDecoration(new PaddedItemDecoration(padding));
        return new HorizontalGridUi<>(getContext(), view, adapter, 1, onClick);
    }

    private Command newLaunchViewCommand(GvDataType<?> dataType) {
        return getCommandsFactory().newLaunchViewCommand(mUi.newDataView(mNode, dataType));
    }

    private Command launchAuthor() {
        return mUi.getCommandsFactory().newLaunchProfileCommand(mNode.getAuthorId());
    }

    private <T extends HasReviewId, G extends GvData> DataExpandableUi<T>
    newDataUi(View v, int sectionId, String title, GvDataType<G> dataType,
              DataConverter<T, G, ?> converter, Class<? extends ViewHolder> vhClass,
              boolean setLaunchOnClick, SimpleViewUi.ReferenceValueGetter<DataListRef<T>> getter) {
        DataExpandableUi<T> ui = new DataExpandableUi<>(getContext(), getSection
                (v, sectionId), title, getter, newVhFactory(vhClass), converter);

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

    private LinearLayout getSection(View v, int id) {
        return (LinearLayout) v.findViewById(id);
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
