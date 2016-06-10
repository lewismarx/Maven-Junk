/**
 * Created by lewis on 6/8/16.
 */
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class DBUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("emailListPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }

}
