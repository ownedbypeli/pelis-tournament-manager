package objects;

import help.StringHelper;

public class Tournament {
    String id;
    String name;
    String fullName;
    String scheduledDateStart;
    String getScheduledDateEnd;
    String timezone;
    String size;
    String registrationClosingDatetime;
    String description;
    String rules;
    String prize;
    String checkInParticipantStartDatetime;
    String checkInParticipantEndDatetime;
    String platform;
    String organization;

    public Tournament(String id,
                      String name,
                      String fullName,
                      String scheduledDateStart,
                      String timezone,
                      String size,
                      String registrationClosingDatetime,
                      String description,
                      String rules,
                      String prize,
                      String checkInParticipantStartDatetime,
                      String checkInParticipantEndDatetime,
                      String platform,
                      String organization) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.scheduledDateStart = scheduledDateStart;
        this.timezone = timezone;
        this.size = size;
        this.registrationClosingDatetime = registrationClosingDatetime;
        this.description = description;
        this.rules = rules;
        this.prize = prize;
        this.checkInParticipantStartDatetime = checkInParticipantStartDatetime;
        this.checkInParticipantEndDatetime = checkInParticipantEndDatetime;
        this.platform = platform;
        this.organization = organization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getScheduledDateStart() {
        return scheduledDateStart;
    }

    public String getGetScheduledDateEnd() {
        return getScheduledDateEnd;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getSize() {
        return size;
    }

    public String getRegistrationClosingDatetime() {
        return registrationClosingDatetime;
    }

    public String getDescription() {
        return description;
    }

    public String getRules() {
        return rules;
    }

    public String getPrize() {
        return prize;
    }

    public String getCheckInParticipantStartDatetime() {
        return checkInParticipantStartDatetime;
    }

    public String getCheckInParticipantEndDatetime() {
        return checkInParticipantEndDatetime;
    }

    public String getPlatform() {
        return platform;
    }

    public String getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return
                StringHelper.getTextBoldUnderlined("Timezone:") + StringHelper.getBreak(1) + getTimezone() + StringHelper.getBreak(1) +
                StringHelper.getTextBoldUnderlined("Rules:") + StringHelper.getBreak(1) + getRules() + StringHelper.getBreak(2) +
                        StringHelper.getTextBoldUnderlined("Prices:" + StringHelper.getBreak(1)) + getPrize() + StringHelper.getBreak(2) +
                        StringHelper.getTextBoldUnderlined("Description:" + StringHelper.getBreak(1))
                        + getDescription().replace("Registration takes place via Discord. All further information can be found there.", "");
    }


}
