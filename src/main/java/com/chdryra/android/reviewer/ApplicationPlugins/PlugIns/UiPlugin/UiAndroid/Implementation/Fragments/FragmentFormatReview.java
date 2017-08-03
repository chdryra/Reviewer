/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.CommentNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.CoverNodeBannerUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.DataExpandableUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.FormattedSectionUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.FormattedTextUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.GvDataRefAdapter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.HorizontalGridUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ImagesNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.MenuOptionsAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.MenuUpAppLevel;
import com.chdryra.android.mygenerallibrary.Ui.PagerAdapterBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.RatingBarTouchable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.SubjectNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.TagsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.VhFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MaiOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MaiUpAppLevel;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhCriterionFormatted;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhFactFormatted;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhLocationFormatted;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;

import java.text.DateFormat;
import java.util.Date;

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
    private static final int AUTHOR = R.id.author_formatted;
    private static final int DATE = R.id.date_formatted;
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
    private ViewUi<TextView, RefDataList<DataTag>> mTags;
    private FormattedSectionUi<String> mAuthor;
    private FormattedSectionUi<String> mDate;
    private FormattedSectionUi<RefCommentList> mComment;
    private FormattedSectionUi<RefDataList<DataCriterion>> mCriteria;
    private FormattedSectionUi<RefDataList<DataFact>> mFacts;
    private FormattedSectionUi<RefDataList<DataLocation>> mLocations;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        mUi = app.getUi();
        mRepo = app.getRepository();

        View v = inflater.inflate(LAYOUT, container, false);

        Bundle args = getArguments();
        if (args == null) {
            noReview();
            return v;
        }

        mIsPublished = args.getBoolean(PUBLISHED);

        setNode(args);
        setMenu();

        setCover(v);
        setSubject(v);
        setRating(v);
        setAuthor(v);
        setDate(v);
        setComment(v);
        setTags(v);
        setCriteria(v);
        setFacts(v);
        setLocations(v);
        setImages(v);

        return v;
    }

    private void noReview() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        CurrentScreen currentScreen = app.getUi().getCurrentScreen();
        currentScreen.showToast(Strings.Toasts.REVIEW_NOT_FOUND);
        currentScreen.close();
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

    private FactoryCommands getCommandsFactory() {
        return mUi.getCommandsFactory();
    }

    private ConverterGv getConverter() {
        return mUi.getGvConverter();
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
        if(mNode.isLeaf() && reference != null) reference.registerListener(this);
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        if(getContainer() != null) getContainer().remove(this);
    }

    private ActivityFormatReview getContainer() {
        try {
            return (ActivityFormatReview) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);

        MenuAction<?> action;
        if (mIsPublished) {
            OptionsCommand command
                    = getCommandsFactory().newReviewOptionsSelector(new NodeAuthorId(mNode));
            MaiOptionsCommand<GvData> mai = new MaiOptionsCommand<>(command);
            action = new MenuOptionsAppLevel(Strings.Menu.REVIEWS, upAction, mai, ui);
        } else {
            action = new MenuUpAppLevel(Strings.Screens.PREVIEW, upAction, ui);
        }

        mMenu = new MenuUi(action);
    }

    private void update() {
        mCover.update();
        mSubject.update();
        mRating.update();
        mAuthor.update();
        mDate.update();
        mComment.update();
        mTags.update();
        mCriteria.update();
        mFacts.update();
        mLocations.update();
        mImages.update();
    }

    @Nullable
    private Command launchView(GvDataType<?> type) {
        return mIsPublished ? newLaunchViewCommand(type) : null;
    }

    private void setCover(View v) {
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(FULL, HALF, 0);
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(), IMAGE_PLACEHOLDER);

        mCover = new CoverNodeBannerUi((ImageView) v.findViewById(IMAGE), mNode,
                mRepo.getAuthorsRepository().getProfile(mNode.getAuthorId()).getProfileImage(),
                placeholder, dims);
        setLaunchOnClick(mCover, launchSummary());
    }

    private void setTags(View v) {
        mTags = new TagsNodeUi((TextView) v.findViewById(TAGS), mNode);
        setLaunchOnClick(mTags, GvTag.TYPE);
    }

    private void setComment(View v) {
        mComment = new CommentNodeUi(getSection(v, COMMENT), (TextView) v.findViewById(HEADLINE),
                mNode, launchView(GvComment.TYPE));
    }

    private void setCriteria(View v) {
        mCriteria = newDataUi(v, CRITERIA, Strings.Formatted.CRITERIA, GvCriterion.TYPE,
                getConverter().newConverterCriteria(), VhCriterionFormatted.class,
                new ViewUi.ValueGetter<RefDataList<DataCriterion>>() {
                    @Override
                    public RefDataList<DataCriterion> getValue() {
                        return mNode.getCriteria();
                    }
                });
    }

    private void setFacts(View v) {
        mFacts = newDataUi(v, FACTS, Strings.Formatted.FACTS, GvFact.TYPE,
                getConverter().newConverterFacts(), VhFactFormatted.class,
                new ViewUi.ValueGetter<RefDataList<DataFact>>() {
                    @Override
                    public RefDataList<DataFact> getValue() {
                        return mNode.getFacts();
                    }
                });
    }

    private void setLocations(View v) {
        mLocations = newDataUi(v, LOCATIONS, Strings.Formatted.LOCATIONS, GvLocation.TYPE,
                getConverter().newConverterLocations(), VhLocationFormatted.class,
                new ViewUi.ValueGetter<RefDataList<DataLocation>>() {
                    @Override
                    public RefDataList<DataLocation> getValue() {
                        return mNode.getLocations();
                    }
                });
    }

    private void setAuthor(View v) {
        AuthorReference reference = mRepo.getAuthorsRepository().getReference(mNode.getAuthorId());
        AuthorGetter getter = new AuthorGetter(reference);
        mAuthor = newTextUi(v, AUTHOR, Strings.Formatted.AUTHOR, launchAuthor(), getter);
        getter.setUiAndDereference(mAuthor);
    }

    private void setDate(View v) {
        mDate = newTextUi(v, DATE, Strings.Formatted.DATE, launchAuthor(),
                new ViewUi.ValueGetter<String>() {
                    @Override
                    public String getValue() {
                        Date date = new Date(mNode.getPublishDate().getTime());
                        return DateFormat.getDateInstance(DateFormat.LONG).format(date);
                    }
                });
    }

    private void setRating(View v) {
        Command onTouch = mIsPublished ? launchSummary() : null;
        mRating = new RatingBarTouchable((RatingBar) v.findViewById(RATING), mNode, onTouch);
    }

    private void setSubject(View v) {
        mSubject = new SubjectNodeUi((TextView) v.findViewById(SUBJECT), mNode, launchAuthor());
        setLaunchOnClick(mSubject, launchSummary());
    }

    private void setImages(View v) {
        LinearLayout section = getSection(v, IMAGES);
        RecyclerView grid = (RecyclerView) section.findViewById(DATA);
        float padding = getResources().getDimensionPixelSize(IMAGE_PADDING);
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(HALF, HALF, (int)
                padding);

        ViewUi<RecyclerView, RefDataList<DataImage>> dataView = newGridUi
                (grid, VhImage.class, 1, (int)padding, dims, getConverter().newConverterImages(),
                        new ViewUi.ValueGetter<RefDataList<DataImage>>() {
                            @Override
                            public RefDataList<DataImage> getValue() {
                                return mNode.getImages();
                            }
                        });

        mImages = new ImagesNodeUi(section, dataView, mNode);
    }

    private void setLaunchOnClick(ViewUi<?, ?> layout, GvDataType<?> type) {
        Command onClick = launchView(type);
        setLaunchOnClick(layout, onClick);
    }

    private void setLaunchOnClick(ViewUi<?, ?> layout, @Nullable Command onClick) {
        if (mIsPublished && onClick != null) layout.setOnClickCommand(onClick);
    }

    private <T1 extends HasReviewId, T2 extends GvData, Vh extends ViewHolder>
    ViewUi<RecyclerView, RefDataList<T1>>
    newGridUi(RecyclerView view, Class<Vh> vhClass,
              int span, int padding, CellDimensionsCalculator.Dimensions dims,
              DataConverter<T1, T2, ? extends GvDataList<T2>> converter,
              ViewUi.ValueGetter<RefDataList<T1>> getter) {
        IdableDataList<T1> empty = new IdableDataList<>(getter.getValue().getReviewId());
        GvDataType<T2> dataType = converter.convert(empty).getGvDataType();

        GvDataRefAdapter<T1, T2, Vh> adapter
                = new GvDataRefAdapter<>(getter, converter, new VhFactory<>(vhClass), dims);

        Command onClick = mIsPublished ? newLaunchViewCommand(dataType) : null;
        view.addItemDecoration(new PaddedItemDecoration(padding));
        return new HorizontalGridUi<>(getActivity(), view, adapter, span, onClick);
    }

    public class PaddedItemDecoration extends RecyclerView.ItemDecoration {

        private final int mPadding;

        public PaddedItemDecoration(int padding) {
            mPadding = padding;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.right = mPadding;
        }
    }

    private Command newLaunchViewCommand(GvDataType<?> dataType) {
        return getCommandsFactory().newLaunchViewCommand(mUi.newDataView(mNode, dataType));
    }

    private Command launchAuthor() {
        return mUi.getCommandsFactory().newLaunchAuthorCommand(mNode.getAuthorId());
    }

    private Command launchSummary() {
        return mUi.getCommandsFactory().newLaunchSummaryCommand(mNode.getReviewId());
    }

    private <T extends HasReviewId, G extends GvData> DataExpandableUi<T>
    newDataUi(View v, int sectionId, String title, GvDataType<G> dataType,
              DataConverter<T, G, ?> converter, Class<? extends ViewHolder> vhClass,
              ViewUi.ValueGetter<RefDataList<T>> getter) {
        DataExpandableUi<T> ui = new DataExpandableUi<>(getActivity(), getSection
                (v, sectionId), title, getter, newVhFactory(vhClass), converter);
        setLaunchOnClick(ui, dataType);
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

    private FormattedTextUi newTextUi(View v, int sectionId, String title, Command onClick,
                                      ViewUi.ValueGetter<String> getter) {
        FormattedTextUi ui = new FormattedTextUi(getSection(v, sectionId), title, getter);
        setLaunchOnClick(ui, onClick);
        return ui;
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

    private class AuthorGetter implements ViewUi.ValueGetter<String> {
        private NamedAuthor mNamedAuthor;
        private AuthorReference mReference;
        private ViewUi<?, ?> mUi;

        private AuthorGetter(AuthorReference reference) {
            mReference = reference;
        }

        @Override
        public String getValue() {
            return mNamedAuthor != null ? mNamedAuthor.getName() : "";
        }

        private void setUiAndDereference(ViewUi<?, ?> ui) {
            mUi = ui;
            dereference();
        }

        private void dereference() {
            mReference.dereference(new DataReference.DereferenceCallback<NamedAuthor>() {
                @Override
                public void onDereferenced(DataValue<NamedAuthor> value) {
                    if (value.hasValue()) {
                        mNamedAuthor = value.getData();
                        mUi.update();
                    }
                }
            });
        }
    }
}
