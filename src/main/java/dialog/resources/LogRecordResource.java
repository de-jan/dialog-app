package dialog.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import dialog.api.LogRecord;
import dialog.jdbi.LogRecordDao;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/views")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogRecordResource {

    private static final long TENDAYS = 864000000L;

    private final LogRecordDao logDao;

    public LogRecordResource(LogRecordDao logDao) {
        this.logDao = logDao;
    }

    @POST
    @Path("/{profileid}/by/{viewid}")
    @Timed
    @UnitOfWork
    @ExceptionMetered
    public void addLogRecord(
            @PathParam("profileid") LongParam profileid,
            @PathParam("viewid") LongParam viewid) {
        this.logDao.addLogRecord(profileid.get(), viewid.get(), System.currentTimeMillis());
    }

    @GET
    @Timed
    @ExceptionMetered
    @Path("/{profileid}/recent")
    public Set<LogRecord> getLast10in10(@PathParam("profileid") LongParam profileid) {
        return this.logDao.getLast10in10(profileid.get(), System.currentTimeMillis() - TENDAYS);
    }
    
}
