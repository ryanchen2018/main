package seedu.address.testutil.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;

/**
 * A utility class containing a list of {@code schedule} objects to be used in tests.
 */
public class TypicalSchedules {

    public static final String VALID_TYPE_ALICE = "WORK";
    public static final String VALID_DATE_ALICE = "01/01/2019";
    public static final String VALID_EMPLOYEEID_ALICE = "000001";

    public static final Schedule ALICE_WORK = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("01/01/2019").withType("WORK").build();
    public static final Schedule BENSON_WORK = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("10/10/2020").withType("WORK").build();
    public static final Schedule CARL_WORK = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("20/03/2099").withType("WORK").build();
    public static final Schedule DANIEL_LEAVE = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("04/04/2019").withType("LEAVE").build();
    public static final Schedule ELLE_LEAVE = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("05/05/2019").withType("LEAVE").build();
    public static final Schedule FIONA_LEAVE = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("06/06/2019").withType("LEAVE").build();
    public static final Schedule GEORGE_LEAVE = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("07/07/2019").withType("LEAVE").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Schedule AMY = new ScheduleBuilder().withEmployeeId("000010")
            .withDate("01/01/2019").withType("WORK").build();
    public static final Schedule BOB = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("01/01/2019").withType("WORK").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSchedules() {} // prevents instantiation
    /**
     * Returns an {@code ScheduleList} with all the typical schedules.
     */
    public static ScheduleList getTypicalScheduleList() {
        ScheduleList sl = new ScheduleList();
        for (Schedule schedule : getTypicalSchedules()) {
            sl.addSchedule(schedule);
        }
        return sl;
    }

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(ALICE_WORK, BENSON_WORK, CARL_WORK, DANIEL_LEAVE,
                ELLE_LEAVE, FIONA_LEAVE, GEORGE_LEAVE));
    }

}
