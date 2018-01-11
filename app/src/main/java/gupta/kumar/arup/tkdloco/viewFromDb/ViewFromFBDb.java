package gupta.kumar.arup.tkdloco.viewFromDb;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;
import gupta.kumar.arup.tkdloco.R;
import gupta.kumar.arup.tkdloco.loco.LocoReadings;
import gupta.kumar.arup.tkdloco.loco.Locomotive;

public class ViewFromFBDb extends AppCompatActivity {


    LocoReadings readings;
    DataAdapter adapter;
    String[][] spaceProbe;
    int SIZE = 0;
    ArrayList<Locomotive> list = new ArrayList<>();
    //final String[] Header = {"Loco Number","DOS","DOT","Pb","Al","Cu","Si","Fe","Cr","Na","Sn","B"};
    RecyclerView tableView;
    Toolbar toolbar;
    TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alt_view_layout);
        init();
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    private void init() {
        Bundle b = getIntent().getBundleExtra("DATA");
        readings = new LocoReadings(b.getString("lcoNum"));
        readings.setReadings((ArrayList<Locomotive>) b.getSerializable("lcoReads"));
        //Log.d("loconNewClass","Loco Num: "+readings.getLocoNumber());
        list = readings.getReadings();
        //Log.d("loconNewClass",list.toString());

       tableView = findViewById(R.id.tableViewRec);
       tableView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(this,list);
        tableView.setAdapter(adapter);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.tbTitle);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(readings.getLocoNumber());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }


}
