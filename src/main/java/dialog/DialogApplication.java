package dialog;

import com.xeiam.dropwizard.sundial.SundialBundle;
import com.xeiam.dropwizard.sundial.SundialConfiguration;
import dialog.jdbi.LogRecordDao;
import dialog.resources.LogRecordResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.LoggerFactory;

/**
 * Entry point.
 *
 * Configures the framework. Operations include Health Checks, Resources,
 * Providers and Bundles.
 */
public class DialogApplication extends Application<DialogApplicationConfiguration> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DialogApplication.class);

    public static void main(String[] args) throws Exception {
        new DialogApplication().run(args);
    }

    @Override
    public String getName() {
        return "DiaLogApp";
    }

    @Override
    public void initialize(Bootstrap<DialogApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(new SundialBundle<DialogApplicationConfiguration>() {
            @Override
            public SundialConfiguration getSundialConfiguration(DialogApplicationConfiguration configuration) {
                return configuration.getSundialConfiguration();
            }
        });
    }

    @Override
    public void run(DialogApplicationConfiguration configuration,
            Environment environment) throws ClassNotFoundException {

        // create database connection with JDBI
        final DBIFactory factory = new DBIFactory();
        final DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        final DBI jdbi = factory.build(environment, dataSourceFactory, "derby");

        // add resources
        final LogRecordDao dao = jdbi.onDemand(LogRecordDao.class);
        try {
            dao.createLogTable();
            //   dao.createIndex();
            LOGGER.info("LogRecords table created");
        } catch (Exception e) {
            // table already exists
            if (e.getCause().getMessage().contains("already exists in Schema")) {
                LOGGER.info("Table already exists.");
            } else {
                LOGGER.info("Database was not created", e);
            }
        }
        environment.jersey().register(new LogRecordResource(dao));
    }

}