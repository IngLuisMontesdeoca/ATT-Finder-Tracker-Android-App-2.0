package io.github.ingluismontesdeoca.location.lib.debug;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Aministrador on 31/08/2016.
 */
public abstract class Debug {

    public static boolean debug = true;

    public Debug(){

    }

    public static void mkToast(String message, Context context){
        if( debug )
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();

    }

    public static void printStack(Exception e){
        if(debug)
            e.printStackTrace();
    }
}
