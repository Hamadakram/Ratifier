package com.irozon.ratifier;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hammad Akram on 3/10/2017.
 */

public class Ratifier {

    public static Valid getValidity(Context context) {
        // Reset last saved password
        Password.setFirstPassword("");

        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            return checkViewGroupValidity(viewGroup);
        }
        return new Valid(true);
    }

    private static Valid checkViewGroupValidity(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RatifierEditText) {
                RatifierEditText ratifierEditText = (RatifierEditText) view;
                if (!ratifierEditText.getValidity().isValid()) {
                    return ratifierEditText.getValidity();
                }
            } else if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                Valid validity = checkViewGroupValidity(vg);
                if (!validity.isValid()) {
                    return validity;
                }
            }
        }
        return new Valid(true);
    }

    public static class Valid {
        private String errorMsg = "";
        private boolean valid = false;
        private View view;

        public Valid(boolean valid) {
            this.valid = valid;
        }

        public Valid(String errorMsg, boolean valid, View view) {
            this.errorMsg = errorMsg;
            this.valid = valid;
            this.view = view;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public RatifierEditText getField() {
            return (RatifierEditText) view;
        }
    }

    public static class Password {
        private static String mPassword = "";

        public static String getFirstPassword() {
            return mPassword;
        }

        public static void setFirstPassword(String password) {
            mPassword = password;
        }
    }
}
