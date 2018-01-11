package gupta.kumar.arup.tkdloco.loco;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by arups on 05-01-2018.
 */

public class Locomotive implements Serializable {

    private String dos;

    public String getDos() {
        return dos;
    }

    public Locomotive(){

    }
    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    private String dot;
    private int Pb;
    private int Al,Cu,Si,Fe,Cr,Na,Sn,B;

    public Locomotive(String dos, String dot, Integer pb, Integer Al, Integer cu, Integer si, Integer fe, Integer cr, Integer na, Integer sn, Integer b) {
        this.dos = dos;
        this.dot = dot;
        this.Pb = pb;
        this.Al = Al;
        this.Cu = cu;
        this.Si = si;
        this.Fe = fe;
        this.Cr = cr;
        this.Na = na;
        this.Sn = sn;
        this.B = b;

    }



    public int getPb() {
        return Pb;
    }

    public void setPb(int pb) {
        Pb = pb;
    }

    public int getAl() {
        return Al;
    }

    public void setAl(int al) {
        Al = al;
    }

    public int getCu() {
        return Cu;
    }

    public void setCu(int cu) {
        Cu = cu;
    }

    public int getSi() {
        return Si;
    }

    public void setSi(int si) {
        Si = si;
    }

    public int getFe() {
        return Fe;
    }

    public void setFe(int fe) {
        Fe = fe;
    }

    public int getCr() {
        return Cr;
    }

    public void setCr(int cr) {
        Cr = cr;
    }

    public int getNa() {
        return Na;
    }

    public void setNa(int na) {
        Na = na;
    }

    public int getSn() {
        return Sn;
    }

    public void setSn(int sn) {
        Sn = sn;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    @Override
    public String toString() {
        return "Locomotive{" +
                "dos='" + dos + '\'' +
                ", dot='" + dot + '\'' +
                ", Pb=" + Pb +
                ", Al=" + Al +
                ", Cu=" + Cu +
                ", Si=" + Si +
                ", Fe=" + Fe +
                ", Cr=" + Cr +
                ", Na=" + Na +
                ", Sn=" + Sn +
                ", B=" + B +
                '}';
    }
}


