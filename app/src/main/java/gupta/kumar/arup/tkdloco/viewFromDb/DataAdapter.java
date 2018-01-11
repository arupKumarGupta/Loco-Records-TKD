package gupta.kumar.arup.tkdloco.viewFromDb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gupta.kumar.arup.tkdloco.R;
import gupta.kumar.arup.tkdloco.loco.Locomotive;

/**
 * Created by arups on 10-01-2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataVH> {


    private List<Locomotive> items;

    private Context context;

    public DataAdapter(Context c, List<Locomotive> items) {
        this.items = items;
        this.context = c;
    }

    @Override
    public DataVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_row, parent, false);
        return new DataVH(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(DataVH holder, int position) {

        int color;
        //holder.singleRowBack.setBackgroundColor((position%2==0?R.color.lighter:R.color.darker));
        //Log.d("colorNow","position:"+position);
        if (position % 2 == 0) {
            color = Color.argb(0,244,67,54);
            //Log.d("colorRow","lighter");
        } else {
            //Log.d("colorRow","darker");
            color = R.color.darker;
        }
        holder.singleRowBack.setBackgroundColor(color);
        Locomotive l = items.get(position);

        holder.dos.setText(l.getDos());
        holder.dot.setText(l.getDot().length()==0?"-":l.getDot());
        holder.pb.setText(l.getPb() + "");
        holder.al.setText(l.getAl() + "");
        holder.cu.setText(l.getCu() + "");
        holder.si.setText(l.getSi() + "");
        holder.fe.setText(l.getFe() + "");
        holder.cr.setText(l.getCr() + "");
        holder.na.setText(l.getNa() + "");
        holder.sn.setText(l.getSn() + "");
        holder.b.setText(l.getB() + "");


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class DataVH extends RecyclerView.ViewHolder {

        TextView dos, dot, pb, al, cu, si, fe, cr, na, sn, b;
        LinearLayout singleRowBack;


        public DataVH(View itemView) {
            super(itemView);
            dos = itemView.findViewById(R.id.dos);
            dot = itemView.findViewById(R.id.dot);
            pb = itemView.findViewById(R.id.pb);
            al = itemView.findViewById(R.id.al);
            cu = itemView.findViewById(R.id.cu);
            si = itemView.findViewById(R.id.si);
            fe = itemView.findViewById(R.id.fe);
            cr = itemView.findViewById(R.id.cr);
            na = itemView.findViewById(R.id.na);
            sn = itemView.findViewById(R.id.sn);
            b = itemView.findViewById(R.id.b);
            singleRowBack = itemView.findViewById(R.id.singleRowBack);


        }
    }
}
