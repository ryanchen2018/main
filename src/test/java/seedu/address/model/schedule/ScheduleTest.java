package seedu.address.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_DATE_ALICE;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_EMPLOYEEID_ALICE;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_TYPE_ALICE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.schedule.ScheduleBuilder;


public class ScheduleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameSchedule() {
        // same object -> returns true
        assertTrue(ALICE_WORK.isSameSchedule(ALICE_WORK));

        // null object -> returns true
        assertFalse(ALICE_WORK.isSameSchedule(null));

        // different employee id -> returns false
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).withEmployeeId(VALID_EMPLOYEEID_CARL).build();
        assertFalse(ALICE_WORK.isSameSchedule(editedAlice));

        // different type -> returns false
        editedAlice = new ScheduleBuilder(ALICE_WORK).withType(VALID_TYPE_BOB).build();
        assertFalse(ALICE_WORK.isSameSchedule(editedAlice));

        // different date -> returns false
        editedAlice = new ScheduleBuilder(ALICE_WORK).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE_WORK.isSameSchedule(editedAlice));

        // same employee id, type, date -> returns true
        editedAlice = new ScheduleBuilder(ALICE_WORK).withDate(VALID_DATE_ALICE).withEmployeeId(VALID_EMPLOYEEID_ALICE)
                .withType(VALID_TYPE_ALICE).build();
        assertTrue(ALICE_WORK.isSameSchedule(editedAlice));
    }
}
