package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String date = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(date));
    }

    @Test
    public void isValidDate() {
        // null date of birth
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("a/12/2018")); // day is invalid
        assertFalse(Date.isValidDate("01/a/2018")); // month is invalid
        assertFalse(Date.isValidDate("01/12/aaaa")); // year is invalid

        assertFalse(Date.isValidDate("32/12/2018")); // more than 31 days [out of range]
        assertFalse(Date.isValidDate("0/12/2018")); // 0 days [out of range]
        assertFalse(Date.isValidDate("01/13/2018")); // more than 12 months [out of range]
        assertFalse(Date.isValidDate("01/0/2018")); // 0 months [out of range]
        assertFalse(Date.isValidDate("01/12/2100")); // more than 2099 years [out of range]
        assertFalse(Date.isValidDate("01/12/1999")); // less than 2000 years [out of range]

        assertFalse(Date.isValidDate(" 01/01/2018")); // spaces within date
        assertFalse(Date.isValidDate("01 /01/2018")); // spaces within date
        assertFalse(Date.isValidDate("01/ 01/2018")); // spaces within date
        assertFalse(Date.isValidDate("01/01 /2018")); // spaces within date
        assertFalse(Date.isValidDate("01/01/ 2018")); // spaces within date
        assertFalse(Date.isValidDate("01 /01/2018 ")); // spaces within date

        assertFalse(Date.isValidDate("29/2/2017")); //non-leap year does not have 29 days in feb
        assertFalse(Date.isValidDate("30/02/2020")); //leap year does not have only 30 days in feb
        assertFalse(Date.checkValidDate("2020", "02", "30"));


        assertFalse(Date.isValidDate("31/04/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.isValidDate("31/06/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.isValidDate("31/09/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.isValidDate("31/11/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.checkValidDate("2020", "11", "31"));

        assertFalse(Date.isValidDate("32/01/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidDate("32/03/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidDate("32/05/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidDate("32/07/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidDate("32/08/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidDate("32/10/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidDate("32/12/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days

        // valid dates
        assertTrue(Date.isValidDate("1/1/2010")); //omitting leading 0 is a valid date
        assertTrue(Date.isValidDate("01/01/2011")); //including 1 leading 0 is a valid date

        assertTrue(Date.isValidDate("29/2/2016")); //leap year has 29 days in feb
        assertTrue(Date.isValidDate("28/02/2021")); //non-leap year has only 28 days in feb

        assertTrue(Date.isValidDate("30/04/2020")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidDate("30/06/2020")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidDate("30/09/2020")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidDate("30/11/2020")); //april, june, sep, nov have 30 days

        assertTrue(Date.isValidDate("31/01/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidDate("31/03/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidDate("31/05/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidDate("31/07/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidDate("31/08/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidDate("31/10/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidDate("31/12/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
    }

    @Test
    public void dateConstraintsErrorToString_validString_correctStringRepresentation() {
        Date expected = new Date("01/01/2018");
        expected.setDateConstraintsError("expected string");
        assertEquals("expected string", expected.getDateConstraintsError());
    }

    @Test
    public void dateToString_validString_correctStringRepresentation() {
        Date expected = new Date("01/01/2018");
        assertEquals("01/01/2018", expected.toString());
    }

    @Test
    public void typeComparable_validType_hashCodeIsCorrect() {
        Date expected = new Date("01/01/2018");
        assertEquals("01/01/2018".hashCode(), expected.hashCode());

    }

}
