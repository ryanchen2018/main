package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.schedule.XmlAdaptedSchedule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Type;
import seedu.address.storage.schedule.XmlAdaptedSchedule;
import seedu.address.testutil.Assert;

public class XmlAdaptedScheduleTest {

    private static final String INVALID_TYPE = "R@chel";
    private static final String INVALID_DATE = "40/13/9999";
    private static final String INVALID_EMPLOYEEID = " ";

    private static final String VALID_EMPLOYEEID = BENSON_WORK.getEmployeeId().toString();
    private static final String VALID_DATE = BENSON_WORK.getScheduleDate().toString();
    private static final String VALID_TYPE = BENSON_WORK.getType().toString();

    @Test
    public void toModelType_validScheduleDetails_returnsSchedule() throws Exception {
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(BENSON_WORK);
        assertEquals(BENSON_WORK, schedule.toModelType());
    }

    //-------------------------------------------//
    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, INVALID_TYPE, VALID_DATE);
        String expectedMessage = Type.MESSAGE_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    //-------------------------------------------//
    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, VALID_TYPE, INVALID_DATE);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS_DEFAULT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, VALID_TYPE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    //-------------------------------------------//

    @Test
    public void toModelType_invalidEmployeeId_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule(INVALID_EMPLOYEEID, VALID_TYPE, VALID_DATE);
        String expectedMessage = EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_nullEmployeeId_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule(null, VALID_TYPE, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmployeeId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }
}
