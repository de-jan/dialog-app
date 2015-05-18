package dialog.jdbi;

import dialog.api.LogRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class LogRecordMapper implements ResultSetMapper<LogRecord> {

    @Override
    public LogRecord map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new LogRecord(r.getLong("profileid"), r.getLong("viewid"), r.getLong("vtime"));
    }
    
}