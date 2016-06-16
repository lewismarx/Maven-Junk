package JEEFiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static main.java.QuickPickDAO.myDB;

/**
 * Created by lowrc on 6/15/2016.
 */
public class LottoGenny {
    ResultSet user_picks = null;
    public int[] pick_numbers(int num) {
        Random rand = new Random();
        int[] new_picks = new int[num];  // establish new picks array
        int new_pick;

        for (int i=0; i < num -1; i++) {
            do {
                new_pick = rand.nextInt(54) + 1;
            } while (already_have(new_picks, new_pick)) ;  // generate again if already have this number
            new_picks[i] = new_pick;  // have a unique one so go with it
        }
        return new_picks;
    }
    public boolean already_have(int[] a, int n) {
        for (int next_a : a) {
            if (next_a == n)  return true;
        }
        return false;
    }
    protected List user_numbers() {
        usernums();
        // returns one row of user picks from database
        if (user_picks != null) {
            try {
                user_picks.next();
               int[] picks = new int[] {
                        user_picks.getInt("n1"),
                        user_picks.getInt("n2"),
                        user_picks.getInt("n3"),
                        user_picks.getInt("n4"),
                        user_picks.getInt("n5"),
                        user_picks.getInt("n6")
                };
                List pickslist = new ArrayList();
                Collections.addAll(pickslist, picks);
                return pickslist;
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Invalid simulation results!");
        return null;
    }
    private void usernums() {
        try {
            this.user_picks = myDB.executeQuery("select * from quickpicks", null);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }


