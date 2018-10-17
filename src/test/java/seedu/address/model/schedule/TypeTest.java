package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Type(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Type(invalidType));
    }

    @Test
    public void isValidType() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid type
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("W69K")); // middle chars wrong no match
        assertFalse(Type.isValidType("okWORK")); // extra chars in front no match
        assertFalse(Type.isValidType("WORKok")); // extra chars behind no match

        // valid type
        assertTrue(Type.isValidType("WORK"));
        assertTrue(Type.isValidType("work"));
        assertTrue(Type.isValidType("LEAVE"));
        assertTrue(Type.isValidType("leave"));
    }

    @Test
    public void typeToString_validString_correctStringRepresentation() {
        Type expectedType = new Type("LEAVE");
        assertEquals("LEAVE", expectedType.toString());
    }

    @Test
    public void typeComparable_validType_hashCodeIsCorrect() {
        Type expected = new Type("LEAVE");
        assertEquals("LEAVE".hashCode(), expected.hashCode());

    }
}
