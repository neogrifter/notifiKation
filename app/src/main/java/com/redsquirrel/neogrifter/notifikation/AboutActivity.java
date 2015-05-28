package com.redsquirrel.neogrifter.notifikation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Activité de la fenêtre "A Propos"
 */
public class AboutActivity extends Activity {

    /**
     * Affichage de la petite fenêtre
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
    }

    /**
     * Fermer la fenêtre (Bouton OK)
     * @param v
     */
    public void finishAbout(View v)
    {
        AboutActivity.this.finish();
    }
}
