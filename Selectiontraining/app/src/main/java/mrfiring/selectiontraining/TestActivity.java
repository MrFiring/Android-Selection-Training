package mrfiring.selectiontraining;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;

import mrfiring.selectiontraining.tests.Test;
import mrfiring.selectiontraining.tests.TestsHolder;

public class TestActivity extends AppCompatActivity {

    ListView lvAnswers;
    TextView tvQuestion;
    private TestsHolder testHld;
    private AdView mAdView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mAdView3 = (AdView)findViewById(R.id.adView3);
        AdRequest request = new AdRequest.Builder().build();
        mAdView3.loadAd(request);

        lvAnswers = (ListView)this.findViewById(R.id.lvAnswers);
        tvQuestion = (TextView)this.findViewById(R.id.tvQuestion);

        testHld = new TestsHolder();
        testHld.addTest("Ваш возраст?", new String[]{"до 18", "от 18 до 40", "от 40 и старше"});
        testHld.addTest("Ваш пол?", new String[]{"Мужской", "Женский"});
        testHld.addTest("Ваша цель?", new String[]{"Набрать массу","Похудеть","Увеличить силовые"});


        Test curTest = testHld.getNextTest();

        tvQuestion.setText(curTest.getQuestion());
        MyTestListAdapter adapter = new MyTestListAdapter(this, curTest.getAnswers(), R.layout.list_view_center);

        lvAnswers.setAdapter(adapter);
        lvAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(testHld.getCurTestNum()-1 == 1) //Проверка если во втором тесте был ответ "Женский" тогда флаг в 1
                    if(position == 1)
                        testHld.setFemale(true);
                if(testHld.getCurTestNum()-1 == 2) //Проверка если в третьем тесте был ответ "Похудеть" тогда флаг в 1
                    if(position == 1)
                        testHld.setLoseWeight(true);

                if(!testHld.isFinish()) {
                    Test test = testHld.getNextTest();

                    tvQuestion.setText(test.getQuestion());
                    MyTestListAdapter adapter = new MyTestListAdapter(parent.getContext(), test.getAnswers(), R.layout.list_view_center);

                    lvAnswers.setAdapter(adapter);
                }
                else{

                    lvAnswers.setVisibility(View.INVISIBLE);
                    tvQuestion.setText(getResources().getString(R.string.test_completed));
                    final     Intent pgA = new Intent(parent.getContext(), ProgramActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                    builder.setTitle(getResources().getString(R.string.program_found))
                            .setMessage(getResources().getString(R.string.program_comment))
                            .setCancelable(false)
                            .setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    pgA.putExtra("female", testHld.isFemale());
                                    pgA.putExtra("lose_weight", testHld.isLoseWeight());
                                    saveResult(testHld.isFemale(), testHld.isLoseWeight());
                                    startActivity(pgA);
                                }
                            });
                    AlertDialog dlg = builder.create();
                    dlg.show();
                }

            }
        });
    }

    private void saveResult(boolean isFemale, boolean isLoseWeight){
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("data.bin", MODE_PRIVATE)));
            bw.write(Boolean.toString(isFemale) + " "+ Boolean.toString(isLoseWeight) +"\n") ;

            bw.close();

        }catch(Exception ex){

        }


    }



    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView3 != null) {
            mAdView3.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView3 != null) {
            mAdView3.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView3 != null) {
            mAdView3.destroy();
        }
        super.onDestroy();
    }

}
