package mrfiring.selectiontraining;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class ProgramActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private AdView mAdView2;

    @Override
    public void onBackPressed() {
        //do nothing;
    }


    private void addProgramms(ProgramController pgController){
        Intent intent = getIntent();
        boolean isFemale = intent.getBooleanExtra("female", false);
        boolean isLoseWeight = intent.getBooleanExtra("lose_weight", false);
        String rest = "Отдых между подходами " + (isLoseWeight ? "40 секунд" : "1.5 минуты");




        if(!isFemale) {
            pgController.addProgram("Cпина,бицепс",new String[]{ "Становая тяга 4 х 12-15",  "Подтягивания 4 х 10 (Если не можете сами, то с помощью)" , "Тяга нижнего блока к животу 4 х 15", "Шраги 4 х 30", "Подъём штанги на бицепс (прямой) 4 х 15",
            "Подъём гантелей \"молоток\" 4 х 15", "Скручивания 3 х максимум"} , rest);
            pgController.addProgram("Грудь, трицепс", new String[]{"Жим штанги лёжа 5 х 10", "Жим гантелей 4 х 15 под углом 45 градусов",
                    "Разведение гантелей на горизонтальной скамье 4 х 15", "Сведение рук в кроссовере 4 х 15",
                    "Французский жим лежа 4 х 15", "Жим гантели сидя из-за головы 4 х 15"}, rest);
            pgController.addProgram("Ноги, плечи", new String[]{"Приседания со штангой 5 х 12-15",
                    "Жим ногами 4 х 15" ,

                    "Подъём на носки 4 х 25 (в любом тренажере)" ,

                    "Жим штанги из-за головы 4 х 15" ,

                    "Жим Арнольда 4 х 12-15",

                    "Тяга к лицу на блоке 4 х 15"}, rest);
        }
        else{
            pgController.addProgram("",new String[]{
                    "Присед с гирей 5 х 20","Поднятие таза лёжа 5 х 20 - 25","Обратные выпады 4 х 20"," \"Журавлик\" или румынская тяга 4 х 15 - 20","Гиперэкстензия 4 х 20","Пресс"
            }, rest);
            pgController.addProgram("", new String[] {"Жим гантелей на горизонтальной скамье 4 х 15 - 20",
                    "Разведение гантелей на наклонной скамье 4 х 20" ,
                    "Разгибание рук на блоке 4 х 15" ,
                    "Тяга гантели к животу в наклоне 4 х 20" ,
                    "Поднятие прямой штанги на бицепс 4 х 15" ,
                    "Разведение гантелей стоя в стороны 4 х 15 - 20" ,
                    "Гиперэкстензия 4 х 20"} , rest);

            pgController.addProgram("", new  String[] {"Присед с гирей 5 х 20" ,
                    "Поднятие таза лёжа 5 х 20 - 25" ,
                    "Обратные выпады 4 х 20" ,
                    "\"Журавлик\" или румынская тяга 4 х 15 - 20" ,
                    "Гиперэкстензия 4 х 20" ,
                    "Пресс"} , rest);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        MobileAds.initialize(this, "ca-app-pub-8871526686598569~2466776049");

        mAdView2 = (AdView)findViewById(R.id.adView2);
        AdRequest request = new AdRequest.Builder().build();
        mAdView2.loadAd(request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PlaceholderFragment.exercisesMgr = new ExercisesManager(getApplicationContext());

            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    PlaceholderFragment.exercisesMgr.parseDBFromJson(PlaceholderFragment.exercisesMgr.getJsonDataFromResource(getApplicationContext(),R.raw.data));
                }
            });
            thr.start();
            try {
                thr.join(5000);
            } catch(InterruptedException ex){
                ex.printStackTrace();
            }






        ProgramController pgController = new ProgramController();
        addProgramms(pgController);

        PlaceholderFragment.programController = pgController;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_program, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView2 != null) {
            mAdView2.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView2 != null) {
            mAdView2.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView2 != null) {
            mAdView2.destroy();
        }
        super.onDestroy();
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static ProgramController programController;
        public static ExercisesManager exercisesMgr;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_program, container, false);

            TrainingProgram pg = programController.getProgram(getArguments().getInt(ARG_SECTION_NUMBER)-1);

            TextView textView = (TextView) rootView.findViewById(R.id.target_groups);
            String target_grp = pg.getTarget_groups();
            if(target_grp.length() > 0)
                textView.setText(target_grp);
            else
                textView.setVisibility(View.GONE);




            TextView tvRest = (TextView) rootView.findViewById(R.id.tv_Rest);
            tvRest.setText(pg.getRest());
            ListView lvExercises = (ListView) rootView.findViewById(R.id.lv_exercises);
            lvExercises.setVerticalScrollBarEnabled(true);
          //  MyTestListAdapter adapter = new MyTestListAdapter(getContext(), pg.getExercises(), R.layout.exercises_list_layout);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.exercises_list_layout, pg.getExercises());



            lvExercises.setAdapter(adapter);

            lvExercises.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String curText = ((TextView)view).getText().toString();


                    Intent praxis = new Intent(getContext(), PraxisActivity.class);



                    String url = PlaceholderFragment.exercisesMgr.getUrl(curText);
                    if(url == null  ||url.length() <= 0) {
                        Log.e("PROGRAM_ACTIVITY", "URL == NULL");
                        return false;
                    }
                    else if(url.equals("page404")){
                        Toast.makeText(view.getContext(), "Code 404 | Page not found", Toast.LENGTH_SHORT).show();
                        return false;
                    }



                    praxis.putExtra("url", url);
                    startActivity(praxis);
                    return true;
                }
            });


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(R.string.day_text)+ " " + String.valueOf(position+1);
        }
    }




}
