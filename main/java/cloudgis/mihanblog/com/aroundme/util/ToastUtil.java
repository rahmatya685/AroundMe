package cloudgis.mihanblog.com.aroundme.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cloudgis.mihanblog.com.aroundme.R;


/**
 * Created by alireza on 10/27/2017.
 */
public class ToastUtil {

    public static void ShowToast(Activity activity, String string, ToastActionState actionState,int duration) {
        LayoutInflater inflater = LayoutInflater.from(activity);

        View layout = inflater.inflate(R.layout.toast, null);
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        switch (actionState) {
            case Failure:
                image.setImageResource(R.drawable.error_381599);
                break;
            case Success:
                image.setImageResource(R.drawable.success);
                break;
            case Info:
                image.setImageResource(R.drawable.ic_information);
                break;

        }

        TextView text = (TextView) layout.findViewById(R.id.text);

        text.setText(string);

        Toast toast = Toast.makeText(activity, "", duration);
        // toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 300);

        toast.setView(layout);
        toast.show();
    }

    public static void ShowToast(Activity activity, String string,  ToastActionState actionState) {
        LayoutInflater inflater = LayoutInflater.from(activity);

        View layout = inflater.inflate(R.layout.toast, null);
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        switch (actionState) {
            case Failure:
                image.setImageResource(R.drawable.error_381599);
                break;
            case Success:
                image.setImageResource(R.drawable.success);
                break;

        }

        TextView text = (TextView) layout.findViewById(R.id.text);

        text.setText(string);

        Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 300);

        toast.setView(layout);
        toast.show();
    }

    public static void ShowToast(Activity activity, int stringRes,  ToastActionState actionState) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.toast, null);
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        switch (actionState) {
            case Failure:
                image.setImageResource(R.drawable.error_381599);
                break;
            case Success:
                image.setImageResource(R.drawable.success);
                break;

        }

        TextView text = (TextView) layout.findViewById(R.id.text);


        text.setText(activity.getString(stringRes));

        Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 300);

        toast.setView(layout);
        toast.show();
    }

    public enum ToastActionState {
        Success,
        Failure,
        Info

    }
}
