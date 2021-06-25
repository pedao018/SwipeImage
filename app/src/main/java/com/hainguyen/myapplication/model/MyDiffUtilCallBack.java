package com.hainguyen.myapplication.model;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyDiffUtilCallBack extends DiffUtil.Callback {
    List<ImageItem> newList;
    List<ImageItem> oldList;

    public MyDiffUtilCallBack(List<ImageItem> newList, List<ImageItem> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).name.equals(oldList.get(oldItemPosition).name);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        ImageItem newModel = newList.get(newItemPosition);
        ImageItem oldModel = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

        if (newModel.isSelected != (oldModel.isSelected)) {
            diff.putBoolean(ImageItem.key, newModel.isSelected);
        }
        if (diff.size() == 0) {
            return null;
        }
        return diff;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
