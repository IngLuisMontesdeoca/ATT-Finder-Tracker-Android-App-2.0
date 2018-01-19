package io.github.ingluismontesdeoca.location.include;

/**
 * Created by Aministrador on 24/11/2016.
 */
public  final class validate {

    public final static boolean isEmailValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
