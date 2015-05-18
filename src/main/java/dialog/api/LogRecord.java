package dialog.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

public class LogRecord {

    @JsonProperty
    @NotEmpty
    private long profileId;

    @JsonProperty
    @NotEmpty
    private long viewId;

    @JsonProperty
    @NotEmpty
    private long vtime;

    public LogRecord() {
    }

    public LogRecord(long profileId, long viewId, long vtime) {
        this.profileId = profileId;
        this.viewId = viewId;
        this.vtime = vtime;
    }

    @Override
    public String toString() {
        return "LogRecord{" + "profileId=" + profileId + ", viewId=" + viewId + ", vtime=" + vtime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogRecord)) {
            return false;
        }

        final LogRecord that = (LogRecord) o;

        return Objects.equals(this.profileId, that.profileId)
                && Objects.equals(this.viewId, that.viewId)
                && Objects.equals(this.vtime, that.vtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, viewId, vtime);
    }

    public long getProfileId() {
        return profileId;
    }

    public long getViewId() {
        return viewId;
    }

    public long getVtime() {
        return vtime;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public void setViewId(long viewId) {
        this.viewId = viewId;
    }

    public void setVtime(long vtime) {
        this.vtime = vtime;
    }
}
