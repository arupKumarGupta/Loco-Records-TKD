package gupta.kumar.arup.tkdloco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import gupta.kumar.arup.tkdloco.addToDb.NewEntry;
import gupta.kumar.arup.tkdloco.loco.LocoReadings;
import gupta.kumar.arup.tkdloco.loco.Locomotive;
import gupta.kumar.arup.tkdloco.logincreds.LoginActivity;
import gupta.kumar.arup.tkdloco.viewFromDb.ViewFromFBDb;


public class Home extends AppCompatActivity {
    String locoNumber;
    Toolbar toolbar;
    TextView tbTitle;
    SharedPreferences pref;
    EditText getLocoNumber;
    LocoReadings loco=null;
    ProgressBar retPBar;
    private boolean flag=false;
    View v;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        pref = getSharedPreferences("LoginPref", MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        tbTitle = findViewById(R.id.tbTitle);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        tbTitle.setText("HOME");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getLocoNumber = findViewById(R.id.getLocoNumber);
        retPBar = findViewById(R.id.retPBar);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getLocoNumber, InputMethodManager.SHOW_IMPLICIT);
    }

    public void handleClick(View view) {
        v = view;
        view.setClickable(false);
        retPBar.setVisibility(View.VISIBLE);
        switch (view.getId()) {
            case R.id.viewLoco:
                //TODO
                locoNumber = getLocoNumber.getText().toString();
                flag = has(locoNumber);
                getLocoNumber.clearFocus();
                break;
             }

    }

    private boolean has(final String locoNumber) {

        if (locoNumber.isEmpty()) {
            getLocoNumber.setError("Enter a valid loco number to continue.");
            retPBar.setVisibility(View.INVISIBLE);
            v.setClickable(true);
            return false;
        } else if (!locoNumber.matches("\\d*\\.?\\d+")) {
            getLocoNumber.setError("Invalid Loco Number... Try Again");
            retPBar.setVisibility(View.INVISIBLE);
            v.setClickable(true);
            return false;
        }
        //Todo fetch list from fire base
        Firebase ref = new Firebase("https://fir-appdemo-7fb37.firebaseio.com/locomotives");
        final ArrayList<Locomotive> list = new ArrayList<>();
        ref.child(locoNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Log.d("locon", "found" + locoNumber);

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        list.add(d.getValue(Locomotive.class));
                    }
                    retPBar.setVisibility(View.INVISIBLE);
                    loco = new LocoReadings(locoNumber);
                    //Log.d("loconNow",list.toString());
                    loco.setReadings(list);
                    if(loco == null){
                        //Log.d("locon","nullptr");
                    }
                    flag = (loco.getReadings().size() == 0 ? false:true);
                    //Log.d("loconNow","flag:"+flag);
                    v.setClickable(true);
                } else {
                    //Log.d("locon", "not found" + locoNumber);
                    v.setClickable(true);
                }

                checkFlag();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //Log.d("locon", "not found");
            }

        });

       return flag;
    }

    private void checkFlag() {
        if(flag){
            //Log.d("loconFinal",loco.getReadings().toString());

            Intent i = new Intent(this, ViewFromFBDb.class);
            Bundle b = new Bundle();
            b.putString("lcoNum",locoNumber);
            b.putSerializable("lcoReads",loco.getReadings());
            i.putExtra("DATA",b);
            startActivity(i);
            retPBar.setVisibility(View.INVISIBLE);
        }
        else{
            retPBar.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(this).setTitle("Oops").setMessage("No records for "+ locoNumber).
                    setPositiveButton("ok",null).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (pref.getBoolean("isLoggedIn", false)) {
            menu.add(0, 0, 0, "Add New Entry");
            menu.add(0, 1, 0, "LogOut");
            menu.add(0,2,0,"About");
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logIn:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case 0:
                startActivity(new Intent(this, NewEntry.class));
                break;

            case 1:
                SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean("isLoggedIn", false);
                ed.commit();

                finish();
                startActivity(getIntent());
                break;
            case 2:
            case R.id.about:
                showAboutMe(this);

        }
        return false;
    }

    public  void showAboutMe(Context c) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(c);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View dV = inflater.inflate(R.layout.about_me, null);
        builder.setView(dV);
        final TextView tv = findViewById(R.id.abtMe);
        //builder.setTitle("").setPositiveButton("Close",null).setCancelable(true);
        android.app.AlertDialog b = builder.create();
        b.show();
    }


    public void abtClick(View view) {
        Intent browserIntent;

        switch (view.getId()){
            case R.id.fb:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/shibai.arup"));
                startActivity(browserIntent);
                break;
            case R.id.gp:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/arupKumarGupta"));
                startActivity(browserIntent);
                break;
        }
    }
}
