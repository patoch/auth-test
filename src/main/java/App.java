import com.datastax.driver.core.Cluster;
import com.datastax.driver.dse.auth.DseGSSAPIAuthProvider;
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;

/**
 * Plain text and Kerberos authentication test on a DSE Cluster
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        String[] hostnames = new String[]{"127.0.0.1"};

        try {
            hostnames = args[0].split(",");

        } catch (Exception e) {
        }
        System.out.println("Contacts points: ");
        for (String cp: hostnames)
            System.out.println(cp);

        Cluster cluster = null;

        try {
            Cluster.Builder clusterBuilder = Cluster.builder()
                    .addContactPoints(hostnames).withPort(9042)
                    .withAuthProvider( new DsePlainTextAuthProvider("cassandra", "cassandra"));
            cluster =clusterBuilder.build();
            cluster.connect();

            System.out.println("[OK] Internal authentication");
        } catch (Exception e) {
            System.out.println("[KO] Internal authentication: " + e.getMessage());
            for (StackTraceElement st: e.getStackTrace()) {
                System.out.println(st.toString());
            }
        } finally {
            cluster.close();
        }


        try {
            Cluster.Builder clusterBuilder = Cluster.builder()
                    .addContactPoints(hostnames).withPort(9042)
                    .withAuthProvider(new DseGSSAPIAuthProvider("cassandra"));
            cluster = clusterBuilder.build();
            cluster.connect();
            System.out.println("[OK] Kerberos authentication");
        } catch (Exception e) {
            System.out.println("[KO] Kerberos authentication: "  + e.getMessage());
            for (StackTraceElement st: e.getStackTrace()) {
                System.out.println(st.toString());
            }
        } finally {
            cluster.close();
        }

    }
}
