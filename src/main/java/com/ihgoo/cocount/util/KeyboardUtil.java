package com.ihgoo.cocount.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by ihgoo on 2015/2/10.
 */
public class KeyboardUtil {


    public static void toggle(Context context,EditText editText){

        InputMethodManager imm1 = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm1.isActive()) {
            // 如果开启
//            imm1.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
//                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的



//            imm1.hideSoftInputFromWindow(
//                    editText.getWindowToken(), 0);




            InputMethodManager inputManager =

                    (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.showSoftInput(editText, 0);
        }

    }
}
