package main.java;

import JEEFiles.LocalMySQLCP;
import JEEFiles.LottoGenny;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lowrc on 6/15/2016.
 */
public interface QuickPickDAO {
    LocalMySQLCP myDB = new LocalMySQLCP();
    default boolean isAvailable(){ return true; }
    QuickPickBean getQuickPick() throws SQLException;
    List findAllQuickPicks();
    boolean createQuickPick(int max_num) throws SQLException;
}
