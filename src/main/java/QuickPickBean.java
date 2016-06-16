package main.java;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lowrc on 6/15/2016.
 */
public class QuickPickBean {

    private int n1 = 0;
    private int n2 = 0;
    private int n3 = 0;
    private int n4 = 0;
    private int n5 = 0;
    private int n6 = 0;

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getN4() {
        return n4;
    }

    public void setN4(int n4) {
        this.n4 = n4;
    }

    public int getN5() {
        return n5;
    }

    public void setN5(int n5) {
        this.n5 = n5;
    }

    public int getN6() {
        return n6;
    }

    public void setN6(int n6) {
        this.n6 = n6;
    }
    public void setALL(int[] all) {
       this.n1 = all[0];
        this.n2 = all[1];
        this.n3 = all[2];
        this.n4 = all[3];
        this.n5 = all[4];
        this.n6 = all[5];
    }

    public ArrayList getALL() {

    int[] all = new int[6];
        all[0] = n1;
        all[1] = n2;
        all[2] = n3;
        all[3] = n4;
        all[4] = n5;
        all[5] = n6;
        ArrayList out = new ArrayList<>();
        Collections.addAll(out, all);
        return out;


    }
}
