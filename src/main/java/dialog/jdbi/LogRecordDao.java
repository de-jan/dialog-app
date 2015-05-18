package dialog.jdbi;

import dialog.api.LogRecord;
import java.util.Set;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(LogRecordMapper.class)
public interface LogRecordDao {

    // @todo may it more flexible by fetching value from yaml file?
    public static final String LOG_TABLE = "logrecords";

    /**
     * Init the table
     */
    @SqlUpdate("CREATE TABLE " + LOG_TABLE + " (ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY, PROFILEID BIGINT NOT NULL, VIEWID BIGINT NOT NULL, VTIME BIGINT NOT NULL, PRIMARY KEY (ID))")
    void createLogTable();

    /**
     * Try to create index.
     */
    @SqlUpdate("CREATE INDEX " + LOG_TABLE + "_profileid_vtime ON LOG_TABLE(ID,PROFILEID,VTIME)")
    public void createIndex();

    /**
     * Log profile visit from viewid to profileid occurred at time vtime.
     *
     * @param profileid
     * @param viewid
     * @param vtime
     */
    @SqlUpdate("INSERT INTO " + LOG_TABLE + " (PROFILEID, VIEWID, VTIME) VALUES (:profileid, :viewid, :vtime)")
//    @GetGeneratedKeys()
    public void addLogRecord(@Bind("profileid") long profileid, @Bind("viewid") long viewid, @Bind("vtime") long vtime);

    /**
     * Retrieve last 10 callers to profileid not older then 10 days.
     *
     * @param profileid
     * @param tendays
     * @return
     */
    @SqlQuery("SELECT * FROM " + LOG_TABLE + " WHERE profileid = :profileid AND vtime > :tendays ORDER BY ID DESC FETCH NEXT 10 ROWS ONLY")
    public Set<LogRecord> getLast10in10(@Bind("profileid") long profileid, @Bind("tendays") long tendays);

    /**
     * Purge database from old records
     *
     * @param vtime
     */
    @SqlUpdate("delete from " + LOG_TABLE + " where vtime < :vtime")
    void purgeOld(@Bind long vtime);

}
