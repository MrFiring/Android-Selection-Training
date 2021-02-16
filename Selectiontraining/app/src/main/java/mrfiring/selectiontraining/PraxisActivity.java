package mrfiring.selectiontraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PraxisActivity extends AppCompatActivity {

    WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praxis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent curIntent  = getIntent();
        String url = curIntent.getStringExtra("url");



        webView = (WebView)findViewById(R.id.webview);
        WebSettings setts = webView.getSettings();
        setts.setSupportZoom(true);
        setts.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       this.finish();
    }
}
