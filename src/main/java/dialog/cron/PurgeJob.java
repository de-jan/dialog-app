package dialog.cron;

import com.xeiam.sundial.Job;
import com.xeiam.sundial.annotations.SimpleTrigger;
import com.xeiam.sundial.exceptions.JobInterruptException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SimpleTrigger(repeatInterval = 1, timeUnit = TimeUnit.DAYS, isConcurrencyAllowed = false)
public class PurgeJob extends Job {

    private final Logger logger = LoggerFactory.getLogger(PurgeJob.class);

    public PurgeJob() {
    }

    @Override
    public void doRun() throws JobInterruptException {
        logger.info("Job started!");
    }

}