package gupta.kumar.arup.tkdloco.loco;

import java.util.ArrayList;

/**
 * Created by arups on 05-01-2018.
 */

public class LocoReadings {

    private ArrayList<Locomotive> readings;

    public LocoReadings(){}
    public String getLocoNumber() {
        return locoNumber;
    }

    public void setLocoNumber(String locoNumber) {
        this.locoNumber = locoNumber;
    }

    private String locoNumber;
    public LocoReadings(String locoNumber) {
        this.readings = new ArrayList<>();
        this.locoNumber = locoNumber;
    }
    public void addReading(Locomotive l){
        if (readings.size() > 15){
            ArrayList<Locomotive> temp = new ArrayList<>();
                temp.addAll(readings.subList(1,15));
                readings.clear();
                readings = temp;
        }
        readings.add(l);
    }

    public ArrayList<Locomotive> getReadings() {
        return readings;
    }

    public void setReadings(ArrayList<Locomotive> readings) {
        this.readings = readings;
    }

}
