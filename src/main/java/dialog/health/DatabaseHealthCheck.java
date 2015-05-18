package dialog.health;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.db.DataSourceFactory;
import org.skife.jdbi.v2.DBI;

public class DatabaseHealthCheck extends HealthCheck {

    private final DBI database;
    private final DataSourceFactory dataSourceFactory;

    public DatabaseHealthCheck(DBI database, DataSourceFactory dataSourceFactory) {
        this.database = database;
        this.dataSourceFactory = dataSourceFactory;
    }

    @Override
    protected Result check() throws Exception {
        if (database.open().getConnection() != null) {
            return Result.healthy();
        } else {
            return Result.unhealthy("No Database at " + dataSourceFactory.getUrl());
        }
    }

}
