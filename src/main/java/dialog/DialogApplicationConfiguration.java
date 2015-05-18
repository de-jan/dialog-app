package dialog;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.dropwizard.sundial.SundialConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Setting up resources from yaml configuration file
 */
public class DialogApplicationConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    /**
     * @return the container with the database info
     */
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @Valid
    @NotNull
    public SundialConfiguration sundialConfiguration = new SundialConfiguration();

    /**
     *
     * @return the container with the sundial cron info
     */
    @JsonProperty("sundial")
    public SundialConfiguration getSundialConfiguration() {
        return sundialConfiguration;
    }

}
