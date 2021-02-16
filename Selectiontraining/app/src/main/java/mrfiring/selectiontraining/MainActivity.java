package mrfiring.selectiontraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
        private AdView mAdView;
        private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-8871526686598569~2466776049");


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();;
        mAdView.loadAd(adRequest);

        startBtn = (Button)this.findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartBtnClick(v);

            }
        });


        /* Выкладка из ProgramActivity.addProgramms(...);
        *         boolean isFemale = intent.getBooleanExtra("female", false);
        *         boolean isLoseWeight = intent.getBooleanExtra("lose_weight", false);
        */


        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("data.bin")));
            String line[] = br.readLine().split(Pattern.quote(" "));
            br.close();
            boolean isFemale = Boolean.parseBoolean(line[0]);
            boolean isLoseWeight = Boolean.parseBoolean(line[1]);

            Intent intent = new Intent(this, ProgramActivity.class);
            intent.putExtra("female", isFemale);
            intent.putExtra("lose_weight", isLoseWeight);
            startActivity(intent);

        }catch(FileNotFoundException ex){


        }catch(Exception ex){


        }


    }


    private void onStartBtnClick(View v){

            Intent in = new Intent(this,TestActivity.class);
            startActivity(in);
    }


    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
