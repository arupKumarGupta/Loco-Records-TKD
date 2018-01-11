package gupta.kumar.arup.tkdloco.addToDb;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gupta.kumar.arup.tkdloco.Home;
import gupta.kumar.arup.tkdloco.R;
import gupta.kumar.arup.tkdloco.loco.LocoReadings;
import gupta.kumar.arup.tkdloco.loco.Locomotive;

public class NewEntry extends AppCompatActivity {


    Toolbar toolbar;
    TextView toolbarTitle;

    boolean flag = false;
    Calendar mCurrDate;
    int day, month, year;

    EditText dosEt, dotEt, PbEt, AlEt, CuEt, SiEt, FeEt, CrEt, NaEt, SnEt, BEt;
    String dos, dot, Pb, Al, Cu, Si, Fe, Cr, Na, Sn, B;

    LocoReadings reading;
    private Firebase mRef, childRef;

    ProgressBar savingPBar;
    Firebase currChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        Firebase.setAndroidContext(this);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.tbTitle);
        setSupportActionBar(toolbar);
        dosEt = findViewById(R.id.dos);
        dotEt = findViewById(R.id.dot);
        PbEt = findViewById(R.id.pbVal);
        AlEt = findViewById(R.id.alVal);
        CuEt = findViewById(R.id.cuVal);
        SiEt = findViewById(R.id.siVal);
        FeEt = findViewById(R.id.feVal);
        CrEt = findViewById(R.id.crVal);
        NaEt = findViewById(R.id.naVal);
        SnEt = findViewById(R.id.snVal);
        BEt = findViewById(R.id.bVal);

        mCurrDate = Calendar.getInstance();
        day = mCurrDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrDate.get(Calendar.MONTH);
        year = mCurrDate.get(Calendar.YEAR);
        getLocoNumberFirst();
        savingPBar = findViewById(R.id.savingPBar);
        savingPBar.setVisibility(View.INVISIBLE);
        mRef = new Firebase("https://fir-appdemo-7fb37.firebaseio.com/locomotives");


    }

    private void getLocoNumberFirst() {
        final String[] loco = {""};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dV = getLayoutInflater().inflate(R.layout.new_loco_entry, null);
        final EditText edt;
        edt = dV.findViewById(R.id.getNewLocoEntryNumber);
        builder.setView(dV);
        builder.setTitle("Enter Loco Number").setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String lcono = edt.getText().toString();
                loco[0] = lcono;
                int x = validate(lcono);
                //Log.d("value x:", x + "");
                if (x == 0) {
                    //flag = true;
                    //Log.d("Creating Key:", lcono);
                    //childRef = mRef.child(lcono);
                    reading = new LocoReadings(lcono);
                    //reading.addReading(new Locomotive("12/12/12", "10/10/17", 1, 2, 3, 45, 6, 7, 8, 9, 0));
                    //mRef.child(lcono).setValue(reading);

                    currChild = mRef.child(reading.getLocoNumber());

                    currChild.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {



                            //Map<String,ArrayList<Locomotive>> list = dataSnapshot.getValue(Map.class);
                            //reading.setReadings(list.get(0));


                            ArrayList<Locomotive> list = new ArrayList<>();
                            for(DataSnapshot d: dataSnapshot.getChildren()){
                                list.add(d.getValue(Locomotive.class));
                            }
                            reading.setReadings(list);
                            //Log.d("readings size",reading.getReadings().size()+"");
                            //Log.d("list:",list.toString());
                            //Log.d("reading:",reading.getReadings().toString());
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Toast.makeText(NewEntry.this, "Unexpected Error!", Toast.LENGTH_SHORT).show();
                        }
                    });


                    toolbarTitle.setText(lcono);

                } else {
                    Toast.makeText(NewEntry.this, "Invalid Loco number", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
            }
        }).setCancelable(false);
        AlertDialog b = builder.create();
        b.show();

        if (flag) {
            //createKey(loco[0]);
            flag = false;
        }
    }


    private int validate(final String lcono) {
        //Todo Validate loco number

        if (lcono == null || lcono.isEmpty())
            return 1;
        else if (!lcono.matches("\\d*\\.?\\d+"))
            return 2;

        return 0;
    }

    public void saveToDb(View view) {
        savingPBar.setVisibility(View.VISIBLE);
        // ArrayList<LocoReadings> list = ;
        dos = dosEt.getText().toString();
        dot = dotEt.getText().toString();
        Pb = PbEt.getText().toString();
        Al = AlEt.getText().toString();
        Fe = FeEt.getText().toString();
        Cr = CrEt.getText().toString();
        B = BEt.getText().toString();
        Sn = SnEt.getText().toString();
        Cu = CuEt.getText().toString();
        Si = SiEt.getText().toString();
        Na = NaEt.getText().toString();
        new SaveToFirebase().execute();
    }

    private boolean checkAll(String dos, String dot, String pb, String al, String cu, String si, String Ferrum, String cr, String na, String sn, String b) {

        if (dos.isEmpty()) {
            dosEt.setError("Required");
            return false;
        }
        if (dot.isEmpty()) {
            dotEt.setError("Required");
            return false;
        }
        if (pb.isEmpty()) {
            PbEt.setError("Required");
            return false;
        }
        if (al.isEmpty()) {
            AlEt.setError("Required");
            return false;
        }
        if (cu.isEmpty()) {
            CuEt.setError("Required");
            return false;
        }
        if (si.isEmpty()) {
            SiEt.setError("Required");
            return false;
        }
        if (Ferrum.isEmpty()) {
            FeEt.setError("Required");
            return false;
        }
        if (cr.isEmpty()) {
            CrEt.setError("Required");
            return false;
        }
        if (na.isEmpty()) {
            NaEt.setError("Required");
            return false;
        }
        if (sn.isEmpty()) {
            SnEt.setError("Required");
            return false;
        }
        if (b.isEmpty()) {
            BEt.setError("Required");
            return false;
        }


        return true;
    }

    public void datePickerShow(final View view) {

        final int id = view.getId();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                day = d;
                month = m + 1;
                year = y;
                switch (id) {
                    case R.id.dos:
                        dosEt.setText(day + "/" + month + "/" + year);
                        break;
                    case R.id.dot:
                        dotEt.setText(day + "/" + month + "/" + year);
                        break;
                }
            }
        }, year, month, day);

        datePickerDialog.show();


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Home.class));
        finish();
    }


    class SaveToFirebase extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            savingPBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (checkAll(dos, dot, Pb, Al, Cu, Si, Fe, Cr, Na, Sn, B)) {
                //TODO save to db


                Locomotive l = new Locomotive(dos,dot,Integer.valueOf(Pb),Integer.valueOf(Al),Integer.valueOf(Cu),
                        Integer.valueOf(Si),Integer.valueOf(Fe),Integer.valueOf(Cr),
                        Integer.valueOf(Na),Integer.valueOf(Sn),Integer.valueOf(B));
                //Log.d("readings size",reading.getReadings().size()+"");
                reading.addReading(l);
                currChild.setValue(reading.getReadings());
                //Log.d("readings size",reading.getReadings().size()+"");

            }

            return null;
        }




        @Override
        protected void onPostExecute(Void aVoid) {
            savingPBar.setVisibility(View.INVISIBLE);
            Toast.makeText(NewEntry.this, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}
