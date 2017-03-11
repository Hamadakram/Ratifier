package com.irozon.ratifier;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hammad Akram on 3/10/2017.
 */

public class RatifierEditText extends android.support.v7.widget.AppCompatEditText {
    private final String INPUT_TYPE_EMAIL = "0x21";
    private final String INPUT_TYPE_PASSWORD = "0x81";

    private Context mContext;
    private AttributeSet mAttrs;
    private boolean required;
    private String emptyMsg = "";
    private String inputType = "";
    private String invalidMsg = "";
    private String regex = "";
    private int charLimit = -1;

    public RatifierEditText(Context context) {
        super(context);
    }

    public RatifierEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public RatifierEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        mContext = context;
        mAttrs = attrs;

        // check if required
        required = isRequired();
        if (required) {
            emptyMsg = getEmptyMessage();
            invalidMsg = getInvalidMessage();
            inputType = getInType();
            regex = getRegex();
            charLimit = getCharMinLimit();
        }
    }

    private String getEmptyMessage() {
        // get error message
        String value = "";
        TypedArray ta = mContext.obtainStyledAttributes(mAttrs, R.styleable.RatifierEditText);
        try {
            value = ta.getString(R.styleable.RatifierEditText_emptyMessage);
        } catch (Exception e) {
        } finally {
            ta.recycle();
        }
        if (value == null) value = "";
        return value;
    }

    public void setEmptyMessage(String message) {
        this.emptyMsg = message;
    }

    private String getInvalidMessage() {
        // get invalid message
        String value = "";
        TypedArray ta = mContext.obtainStyledAttributes(mAttrs, R.styleable.RatifierEditText);
        try {
            value = ta.getString(R.styleable.RatifierEditText_invalidMessage);
        } catch (Exception e) {
        } finally {
            ta.recycle();
        }
        if (value == null) value = "";
        return value;
    }

    public void setInvalidMessage(String message) {
        this.invalidMsg = message;
    }

    private String getInType() {
        // input type
        String value = "";
        try {
            value = mAttrs.getAttributeValue("http://schemas.android.com/apk/res/android", "inputType");
        } catch (Exception e) {
        }
        if (value == null) value = "";
        return value;
    }

    private String getRegex() {
        // get regex
        String value = "";
        TypedArray ta = mContext.obtainStyledAttributes(mAttrs, R.styleable.RatifierEditText);
        try {
            value = ta.getString(R.styleable.RatifierEditText_regex);
        } catch (Exception e) {
        } finally {
            ta.recycle();
        }
        if (value == null) value = "";
        return value;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    private int getCharMinLimit() {
        // get char limit
        int value = -1;
        TypedArray ta = mContext.obtainStyledAttributes(mAttrs, R.styleable.RatifierEditText);
        try {
            value = ta.getInt(R.styleable.RatifierEditText_minCharacters, -1);
        } catch (Exception e) {
        } finally {
            ta.recycle();
        }

        return value;
    }

    public void setMinCharacters(int minCharacters) {
        this.charLimit = minCharacters;
    }

    private boolean isRequired() {
        // Check if required
        boolean value = false;
        TypedArray a = mContext.getTheme().obtainStyledAttributes(mAttrs, R.styleable.RatifierEditText, 0, 0);
        try {
            value = a.getBoolean(R.styleable.RatifierEditText_required, false);
        } catch (Exception e) {
        } finally {
            a.recycle();
        }
        return value;
    }

    public Ratifier.Valid getValidity() {
        // Check if required
        if (required) {

            // Check for empty text
            String text = getText().toString();
            if (text.isEmpty()) {
                return new Ratifier.Valid(emptyMsg, false, this);
            } else { // EditText not empty
                // Check character limit
                if (charLimit != -1) { // character limit set. Default value is -1
                    if (text.length() < charLimit) { // Entered text size is not equal to character limit provided
                        return new Ratifier.Valid(invalidMsg, false, this);
                    }
                }

                // Check if user enterend reger or not
                if (!regex.isEmpty()) { // regex provided
                    boolean valid = isValidWithRegex(regex, text);
                    if (!valid) {
                        return new Ratifier.Valid(invalidMsg, false, this);
                    }
                }

                // Check if email or password
                if (inputType.equals(INPUT_TYPE_EMAIL)) {// Email
                    text = text.trim();
                    // Check if email is valid
                    if (isEmailValid(text)) { // is Valid
                        return new Ratifier.Valid(true);
                    } else { // Not a valid email
                        return new Ratifier.Valid(invalidMsg, false, this);
                    }

                } else if (inputType.equals(INPUT_TYPE_PASSWORD)) { // Password

                    // Check if password or repeat password
                    String password = Ratifier.Password.getFirstPassword();
                    if (password.isEmpty()) { // First password
                        Ratifier.Password.setFirstPassword(text); // Save first password
                    } else { // First password is set
                        // Means this is repeat password
                        // Compare it with first password
                        if (password.equals(text)) { // Match
                            return new Ratifier.Valid(true);
                        } else { // Mismatch
                            return new Ratifier.Valid(invalidMsg, false, this);
                        }
                    }
                } else {
                    return new Ratifier.Valid(true);
                }
            }
        }
        return new Ratifier.Valid(true);

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isValidWithRegex(String regex, String text) {
        boolean isValid = false;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
