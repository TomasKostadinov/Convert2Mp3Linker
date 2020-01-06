package com.tomaskostadinov.convert2mp3linker2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class MainActivity extends AppCompatActivity {

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.urlButton);
        final EditText url = findViewById(R.id.url);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = url.getText().toString();
                if (text.length() != 0) {
                    openLink(text);
                } else {
                    Toast.makeText(MainActivity.this, "Please add an valid url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            openLink(sharedText);
        }
    }

    void openLink(String link) {
        // Update UI to reflect text being shared
        Uri uri = Uri.parse("http://convert2mp3.net/index.php")
                .buildUpon()
                .appendQueryParameter("url", link)
                .appendQueryParameter("lang", "en")
                .build();

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setToolbarColor(this.getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .build();

        CustomTabsHelper.openCustomTab(this, customTabsIntent,
                uri,
                new WebViewFallback());

    }
}