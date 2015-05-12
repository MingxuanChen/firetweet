package org.mariotaku.twidere.model;

import org.mariotaku.twidere.TwidereConstants;

import go.Go;
import go.flashlight.Flashlight;

import android.content.Context;
import android.os.AsyncTask;

import 	android.os.StrictMode;

/**
 * Created by todd on 4/25/15.
 */
public class Lantern {

    private static boolean lanternStarted = false;


    public static void start(Context context) {

        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());

        if (!lanternStarted) {
            // Initializing application context.
            try {
                Go.init(context);

                // init loads libgojni.so and starts the runtime
                Flashlight.RunClientProxy("127.0.0.1:9192", TwidereConstants.APP_NAME);
                System.setProperty("http.proxyHost", "127.0.0.1");
                System.setProperty("http.proxyPort", "9192");
                System.setProperty("https.proxyHost", "127.0.0.1");
                System.setProperty("https.proxyPort", "9192");
                // specify that all of our HTTP traffic should be routed through
                // our local proxy

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            lanternStarted = true;
            StrictMode.setThreadPolicy(old);
        }
    }
}
