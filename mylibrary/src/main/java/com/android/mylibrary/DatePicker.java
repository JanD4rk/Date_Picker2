package com.android.mylibrary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by JanD4rk on 1/8/2018 10:17 AM .
 */

public class DatePicker  {
    Dialog dialog;
    LibraryDialog libraryDialog;
    LibraryDialog.ConfirmListener mylistener;
    public DatePicker(@NonNull Context context,Integer selectedDay) {
        dialog=new Dialog(context,R.style.DateDialogTheme);

        Window dialogWindow;
        dialogWindow = dialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.TOP);
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_layout);

        libraryDialog= dialog.findViewById(R.id.myDialog);
        libraryDialog.setSelectedDay(selectedDay);
        libraryDialog.setOnCancelListener(new LibraryDialog.CancelListener() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void  dismiss(){
        dialog.dismiss();
    }
    public Dialog getDialog(){
        return dialog;
    }

    public void setConfirmListener(LibraryDialog.ConfirmListener listener){
        libraryDialog.setOnConfirmListener(listener);
    }
    public  void  setColors(Integer mainColor,Integer dividerColor,Integer textColor){
        libraryDialog.setColors(mainColor,dividerColor,textColor);
    }

    public LibraryDialog.ConfirmListener getConfirmListener(){
        return  mylistener;
    }
}
