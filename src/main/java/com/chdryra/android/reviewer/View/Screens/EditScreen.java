/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreen {
    private EditScreen() {

    }

    //Static methods
    public static ReviewView newScreen(Context context, GvDataType dataType) {
        ReviewBuilderAdapter builder = Administrator.get(context).getReviewBuilder();
        String buttonLabel = context.getResources().getString(R.string.button_add);
        buttonLabel += " " + dataType.getDatumName();

        ReviewBuilderAdapter.DataBuilderAdapter adapter = builder.getDataBuilder(dataType);
        ReviewViewActionCollection actions = getActions(dataType, buttonLabel);
        ReviewViewParams params = DefaultParameters.getParams(dataType);
        return new ReviewEditor(adapter, params, actions);
    }

    private static ReviewViewActionCollection getActions(GvDataType dataType, String buttonTitle) {
        ReviewViewActionCollection actions = new ReviewViewActionCollection();
        actions.setAction(new RatingBar());
        actions.setAction(newBannerButtonAction(dataType, buttonTitle));
        actions.setAction(newGridItemAction(dataType));
        actions.setAction(newMenuAction(dataType));
        return actions;
    }

    private static ReviewViewAction.MenuAction newMenuAction(GvDataType dataType) {
        if (dataType == GvCommentList.GvComment.TYPE) {
            return new EditScreenComments.Menu();
        } else if (dataType == GvCriterionList.GvCriterion.TYPE) {
            return new EditScreenCriteria.Menu();
        } else {
            return new Menu(dataType.getDataName());
        }
    }

    private static ReviewViewAction.GridItemAction newGridItemAction(GvDataType
                                                                             dataType) {
        if (dataType == GvCommentList.GvComment.TYPE) {
            return new EditScreenComments.GridItem();
        } else if (dataType == GvImageList.GvImage.TYPE) {
            return new EditScreenImages.GridItem();
        } else if (dataType == GvLocationList.GvLocation.TYPE) {
            return new EditScreenLocations.GridItem();
        } else if (dataType == GvFactList.GvFact.TYPE) {
            return new EditScreenFacts.GridItem();
        } else {
            return new GridItem(ConfigGvDataUi.getConfig(dataType).getEditorConfig());
        }
    }

    private static ReviewViewAction.BannerButtonAction newBannerButtonAction(
            GvDataType dataType, String title) {
        if (dataType == GvImageList.GvImage.TYPE) {
            return new EditScreenImages.BannerButton(title);
        } else if (dataType == GvLocationList.GvLocation.TYPE) {
            return new EditScreenLocations.BannerButton(title);
        } else if (dataType == GvFactList.GvFact.TYPE) {
            return new EditScreenFacts.BannerButton(title);
        } else {
            return new BannerButton(ConfigGvDataUi.getConfig(dataType).getAdderConfig(), title);
        }
    }

    //Classes
    public static class BannerButton extends ReviewViewAction.BannerButtonAction {
        private static final String TAG = "ActionBannerButtonAddListener";

        private final ConfigGvDataUi.LaunchableConfig mConfig;
        private Fragment mListener;

        protected BannerButton(ConfigGvDataUi.LaunchableConfig config, String title) {
            super(title);
            mConfig = config;
            setListener(new AddListener(config.getGVType()) {
            });
        }

        //protected methods
        protected Fragment getListener() {
            return mListener;
        }

        protected void setListener(Fragment listener) {
            mListener = listener;
            super.registerActionListener(listener, TAG);
        }

        protected int getRequestCode() {
            return mConfig.getRequestCode();
        }

        //TODO make type safe
        protected boolean addData(GvData data) {
            boolean added = getDataBuilder().add(data);
            getReviewView().updateView();
            return added;
        }

        protected void showAlertDialog(String alert, int requestCode) {
            DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
            DialogShower.show(dialog, getListener(), requestCode, DialogAlertFragment.ALERT_TAG);
        }

        protected void onDialogAlertNegative(int requestCode) {

        }

        protected void onDialogAlertPositive(int requestCode) {

        }

        //private methods
        private ReviewBuilderAdapter.DataBuilderAdapter getDataBuilder() {
            return ((ReviewBuilderAdapter.DataBuilderAdapter) getAdapter());
        }

        //Overridden
        @Override
        public void onClick(View v) {
            LauncherUi.launch(mConfig.getLaunchable(), getListener(), getRequestCode(),
                    mConfig.getTag(), new Bundle());
        }

        // /Dialogs expected to communicate directly with target fragments so using "invisible"
        // fragment as listener.
        //Restrictions on how fragments are constructed mean I have to use an abstract class...
        protected abstract class AddListener extends Fragment
                implements DialogGvDataAdd.GvDataAddListener,
                DialogAlertFragment.DialogAlertListener {

            private GvDataType mDataType;
            private GvDataList<GvData> mAdded;

            private AddListener(GvDataType dataType) {
                mDataType = dataType;
                initDataList();
            }

            //TODO make type safe
            private void initDataList() {
                mAdded = FactoryGvData.newDataList(mDataType);
            }

            //Overridden
            @Override
            public boolean onGvDataAdd(GvData data) {
                boolean success = addData(data);
                if (success) mAdded.add(data);
                return success;
            }

            @Override
            public void onGvDataCancel() {
                //TODO make type safe
                for (GvData added : mAdded) {
                    getDataBuilder().delete(added);
                }
            }

            @Override
            public void onGvDataDone() {
                initDataList();
            }

            @Override
            public void onAlertNegative(int requestCode, Bundle args) {
                onDialogAlertNegative(requestCode);
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == getRequestCode() && data != null
                        && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                    onGvDataAdd(GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data));
                }
            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                onDialogAlertPositive(requestCode);
            }


        }
    }

    @SuppressWarnings("EmptyMethod")
    public static class GridItem extends ReviewViewAction.GridItemAction {
        private static final String TAG = "GridItemEditListener";
        private final ConfigGvDataUi.LaunchableConfig mConfig;
        private Fragment mListener;
        private ReviewEditor mEditor;

        protected GridItem(ConfigGvDataUi.LaunchableConfig config) {
            mConfig = config;
            setListener(new EditListener() {
            });
        }

        //protected methods
        protected Fragment getListener() {
            return mListener;
        }

        protected void setListener(Fragment listener) {
            mListener = listener;
            super.registerActionListener(listener, TAG);
        }

        protected int getLaunchableRequestCode() {
            return mConfig.getRequestCode();
        }

        protected ReviewEditor getEditor() {
            return mEditor;
        }

        //TODO make type safe
        protected void editData(GvData oldDatum, GvData newDatum) {
            getDataBuilder().replace(oldDatum, newDatum);
            getReviewView().updateView();
        }

        //TODO make type safe
        protected void deleteData(GvData datum) {
            getDataBuilder().delete(datum);
            getReviewView().updateView();
        }

        protected void showAlertDialog(String alert, int requestCode, GvData item) {
            Bundle args = new Bundle();
            if (item != null) {
                GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
            }
            DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
            DialogShower.show(dialog, getListener(), requestCode, DialogAlertFragment.ALERT_TAG);
        }

        protected void onDialogAlertNegative(int requestCode, Bundle args) {

        }

        protected void onDialogAlertPositive(int requestCode, Bundle args) {

        }

        //private methods
        private ReviewBuilderAdapter.DataBuilderAdapter getDataBuilder() {
            return ((ReviewBuilderAdapter.DataBuilderAdapter) getAdapter());
        }

        //Overridden
        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewEditor.cast(getReviewView());
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            Bundle args = new Bundle();
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);

            LauncherUi.launch(mConfig.getLaunchable(), mListener, getLaunchableRequestCode(),
                    mConfig.getTag(), args);
        }

        protected abstract class EditListener extends Fragment
                implements DialogGvDataEdit.GvDataEditListener,
                DialogAlertFragment.DialogAlertListener {

            //Overridden
            @Override
            public void onGvDataDelete(GvData data) {
                deleteData(data);
            }

            @Override
            public void onGvDataEdit(GvData oldDatum, GvData newDatum) {
                editData(oldDatum, newDatum);
            }

            @Override
            public void onAlertNegative(int requestCode, Bundle args) {
                onDialogAlertNegative(requestCode, args);
            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                onDialogAlertPositive(requestCode, args);
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == getLaunchableRequestCode() && data != null) {
                    GvData oldDatum = GvDataPacker.unpackItem(GvDataPacker
                            .CurrentNewDatum.CURRENT, data);
                    if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                        onGvDataEdit(oldDatum, GvDataPacker.unpackItem(GvDataPacker
                                .CurrentNewDatum.NEW, data));
                    } else if (ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                        onGvDataDelete(oldDatum);
                    }
                }
            }
        }
    }

    public static class Menu extends ReviewViewAction.MenuAction {
        public static final int MENU_DELETE_ID = R.id.menu_item_delete;
        public static final int MENU_DONE_ID = R.id.menu_item_done;
        public static final ActivityResultCode RESULT_DELETE = ActivityResultCode.DELETE;
        public static final ActivityResultCode RESULT_DONE = ActivityResultCode.DONE;
        private static final int MENU = R.menu.menu_delete_done;
        private static final int DELETE_CONFIRM = 314;
        private static final String TAG = "ActionMenuDeleteDoneGridListener";

        private final MenuActionItem mDeleteAction;
        private final MenuActionItem mDoneAction;

        private final String mDeleteWhat;
        private final boolean mDismissOnDelete;
        private final boolean mDismissOnDone;

        private final Fragment mListener;
        private boolean mRatingIsAverage;
        private ReviewEditor mEditor;

        //Constructors
        public Menu(String title) {
            this(title, title);
        }

        public Menu(String title, String deleteWhat) {
            this(title, deleteWhat, false);
        }

        public Menu(String title, String deleteWhat, boolean dismissOnDelete) {
            this(title, deleteWhat, dismissOnDelete, true, MENU);
        }

        public Menu(String title, String deleteWhat, boolean dismissOnDelete,
                    boolean dismissOnDone, int menuId) {
            super(menuId, title, true);
            mDeleteWhat = deleteWhat;
            mDismissOnDelete = dismissOnDelete;
            mDismissOnDone = dismissOnDone;

            mDeleteAction = new MenuActionItem() {
                //Overridden
                @Override
                public void doAction(Context context, MenuItem item) {
                    if (hasDataToDelete()) showDeleteConfirmDialog();
                }
            };

            mDoneAction = new MenuActionItem() {
                @Override
                public void doAction(Context context, MenuItem item) {
                    doDoneSelected();
                    sendResult(RESULT_DONE);
                }
            };

            addMenuItems();
            mListener = new DeleteConfirmListener() {
            };
            registerActionListener(mListener, TAG);
        }

        //protected methods
        protected MenuActionItem getDeleteAction() {
            return mDeleteAction;
        }

        protected MenuActionItem getDoneAction() {
            return mDoneAction;
        }

        protected ReviewBuilderAdapter.DataBuilderAdapter getBuilder() {
            return (ReviewBuilderAdapter.DataBuilderAdapter) getAdapter();
        }

        protected ReviewEditor getEditor() {
            return mEditor;
        }

        protected void bindDefaultDeleteActionItem(int deleteId) {
            bindMenuActionItem(getDeleteAction(), deleteId, false);
        }

        protected void bindDefaultDoneActionItem(int doneId) {
            bindMenuActionItem(getDoneAction(), doneId, mDismissOnDone);
        }

        protected void doDeleteSelected() {
            if (hasDataToDelete()) {
                getBuilder().deleteAll();
                getReviewView().updateView();
                if (mDismissOnDelete) {
                    sendResult(RESULT_DELETE);
                    getActivity().finish();
                }
            }
        }

        private void doDoneSelected() {
            ReviewView view = getReviewView();
            ReviewBuilderAdapter.DataBuilderAdapter builder = getBuilder();

            builder.setData();
            builder.setSubject(view.getFragmentSubject());
            builder.getParentBuilder().setRatingIsAverage(mEditor.isRatingAverage());
            builder.setRating(view.getFragmentRating());
        }

        private void showDeleteConfirmDialog() {
            String deleteWhat = "all " + mDeleteWhat;
            DialogDeleteConfirm.showDialog(deleteWhat, mListener, DELETE_CONFIRM,
                    getActivity().getFragmentManager());
        }

        private boolean hasDataToDelete() {
            return getGridData() != null && getGridData().size() > 0;
        }

        //Overridden
        @Override
        protected void addMenuItems() {
            bindDefaultDeleteActionItem(MENU_DELETE_ID);
            bindDefaultDoneActionItem(MENU_DONE_ID);
        }

        @Override
        protected void doUpSelected() {
            ReviewBuilderAdapter builder = getBuilder().getParentBuilder();
            builder.setRatingIsAverage(mRatingIsAverage);
            builder.resetDataBuilder(getGridData().getGvDataType());
            super.doUpSelected();
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewEditor.cast(getReviewView());
            mRatingIsAverage = getBuilder().getParentBuilder().isRatingAverage();
        }

        private abstract class DeleteConfirmListener extends Fragment implements DialogAlertFragment
                .DialogAlertListener {
            @Override
            public void onAlertNegative(int requestCode, Bundle args) {

            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                if (requestCode == DELETE_CONFIRM) doDeleteSelected();
            }
        }

    }

    public static class RatingBar extends ReviewViewAction.RatingBarAction {
        private ReviewEditor mEditor;

        protected RatingBar() {

        }

        //Overridden
        @Override
        public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            if (fromUser) {
                mEditor.setRatingAverage(false);
                mEditor.setRating(rating, true);
            }
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewEditor.cast(getReviewView());
        }


    }
}
