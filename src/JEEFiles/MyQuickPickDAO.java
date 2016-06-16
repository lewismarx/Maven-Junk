package JEEFiles;

import main.java.QuickPickBean;
import main.java.QuickPickDAO;
import simplemysql.SimpleMySQL;
import simplemysql.SimpleMySQLResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lowrc on 6/15/2016.
 */
public class MyQuickPickDAO implements QuickPickDAO {
    LottoGenny gen = new LottoGenny();
    @Override
    public QuickPickBean getQuickPick() throws SQLException {
        QuickPickBean bean = new QuickPickBean();
        LottoGenny gen = new LottoGenny();
        LocalMySQLCP lcp = new LocalMySQLCP();
        bean.setALL((gen.pick_numbers(6)));
        ArrayList<Integer> values = new ArrayList<>();
        values = bean.getALL();
        String texttrunc = values.toString();
        texttrunc = texttrunc.substring(1, texttrunc.length()-1);
        lcp.executeQuery("INSERT INTO quickpicks (n1,n2,n3,n4,n5,n6) VALUES (" + texttrunc + ") ");
        return bean;
    }

    @Override
    public List findAllQuickPicks() {
        LottoGenny out = new LottoGenny();
        return out.user_numbers();
    }

    @Override
    public boolean createQuickPick(int max_num) throws SQLException {
        Random rand = new Random();
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect("localhost:3306", "root", "javaee6", "mysql");
        SimpleMySQLResult result = mysql.Query("SELECT * FROM quickpicks");

        result.

        int[] new_picks = new int[6];  // establish new picks array
        int new_pick;

        for (int i=0; i < 6 -1; i++) {
            do {
                new_pick = rand.nextInt(max_num) + 1;
            } while (gen.already_have(new_picks, new_pick)) ;  // generate again if already have this number
            new_picks[i] = new_pick;  // have a unique one so go with it
        }

        return true;

    }
}
