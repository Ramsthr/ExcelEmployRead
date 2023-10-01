import java.util.Date;

public class ShiftRecord {
    private final String fileId;
    private final String name;
    private final String positionId;
    private final Date payCycleStart;
    private final Date payCycleEnd;
    private final int uptime;

    private final Date inTime;
    private final Date outTime;

    private ShiftRecord(Builder builder) {
        this.fileId = builder.fileId;
        this.name = builder.name;
        this.positionId = builder.positionId;
        this.payCycleStart = builder.payCycleStart;
        this.payCycleEnd = builder.payCycleEnd;
        this.uptime = builder.uptime;
        this.inTime = builder.inTime;
        this.outTime = builder.outTime;
    }

    public static class Builder {
        private String fileId;
        private String name;
        private String positionId;
        private Date payCycleStart;
        private Date payCycleEnd;
        private int uptime;
        private Date inTime;
        private Date outTime;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPayCycleStart(Date payCycleStart) {
            this.payCycleStart = payCycleStart;
            return this;
        }

        public Builder setPayCycleEnd(Date payCycleEnd) {
            this.payCycleEnd = payCycleEnd;
            return this;
        }

        public Builder setUptime(int uptime) {
            this.uptime = uptime;
            return this;
        }

        public Builder setInTime(Date inTime) {
            this.inTime = inTime;
            return this;
        }

        public Builder setOutTime(Date outTime) {
            this.outTime = outTime;
            return this;
        }

        public ShiftRecord build(String positionId, String fileId) {
            this.fileId = fileId;
            this.positionId = positionId;
            return new ShiftRecord(this);
        }
    }

    public String getFileId() {
        return fileId;
    }
    public String getName() {
        return name;
    }

    public String getPositionId() {
        return positionId;
    }

    public Date getPayCycleStart() {
        return payCycleStart;
    }

    public Date getPayCycleEnd() {
        return payCycleEnd;
    }

    public int getUptime() {
        return uptime;
    }

    public Date getInTime() {
        return inTime;
    }

    public Date getOutTime() {
        return outTime;
    }
}
