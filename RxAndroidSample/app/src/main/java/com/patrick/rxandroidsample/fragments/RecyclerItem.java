package com.patrick.rxandroidsample.fragments;

import android.graphics.drawable.Drawable;

import lombok.AllArgsConstructor;
import lombok.Data;

// DataSet 클래스
@Data
@AllArgsConstructor(staticName = "of")
class RecyclerItem {
    private Drawable image;
    private String title;
}
