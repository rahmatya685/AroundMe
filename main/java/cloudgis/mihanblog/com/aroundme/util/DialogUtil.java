package cloudgis.mihanblog.com.aroundme.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import cloudgis.mihanblog.com.aroundme.R;

/**
 * Created by alireza on 11/27/2017.
 */

public class DialogUtil {





    public static void showInfo(Context context, String msg, String btnOkTxt, String btnCancelText, View.OnClickListener clickListener) {
        Activity activity = (Activity) context;
        LayoutInflater inflater = activity.getLayoutInflater();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        View alertBtns = inflater.inflate(R.layout.dialog_info, null);
        alertDialogBuilder.setView(alertBtns);
        alertDialogBuilder.setMessage(msg).setCancelable(false);
        TextView txtViewMsg = alertBtns.findViewById(R.id.msg);
        txtViewMsg.setText(msg);

        Button btnOk = alertBtns.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(clickListener);
        if (btnOkTxt.length() > 0)
            btnOk.setText(btnOkTxt);

        Button btnCancel = alertBtns.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(clickListener);
        if (btnCancelText == null) {
            btnCancel.setVisibility(View.GONE);
        } else {
            if (btnCancelText.length() > 0)
                btnCancel.setText(btnCancelText);
            btnCancel.setVisibility(View.VISIBLE);
        }

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(alertBtns);
        btnOk.setTag(dialog);
        btnCancel.setTag(dialog);
        dialog.show();

    }


}
