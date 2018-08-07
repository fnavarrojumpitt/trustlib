package com.jumpitt.trust.ui.register;

/**
 * Created by felipe on 25-04-18.
 */

public interface RegisterNumberContract {
    interface View {

        void onPhoneTextChange(String text);

        void sendCode(String code);
    }
}
