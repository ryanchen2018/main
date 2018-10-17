package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.CARL_WORK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.schedule.ScheduleListBuilder;

public class VersionedScheduleListTest {

    private final ReadOnlyScheduleList scheduleListWithAlice =
            new ScheduleListBuilder().withSchedule(ALICE_WORK).build();
    private final ReadOnlyScheduleList scheduleListWithBenson =
            new ScheduleListBuilder().withSchedule(BENSON_WORK).build();
    private final ReadOnlyScheduleList scheduleListWithCarl =
            new ScheduleListBuilder().withSchedule(CARL_WORK).build();
    private final ReadOnlyScheduleList emptyScheduleList = new ScheduleListBuilder().build();

    @Test
    public void commit_singleScheduleList_noStatesRemovedCurrentStateSaved() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        versionedScheduleList.commit();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                emptyScheduleList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleScheduleListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        versionedAddressBook.commit();
        assertScheduleListListStatus(versionedAddressBook,
                Arrays.asList(emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson),
                scheduleListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplScheduleListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.commit();
        assertScheduleListListStatus(versionedAddressBook,
                Collections.singletonList(emptyScheduleList),
                emptyScheduleList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleScheduleListPointerAtEndOfStateList_returnsTrue() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleScheduleListPointerAtStartOfStateList_returnsTrue() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleScheduleList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertFalse(versionedScheduleList.canUndo());
    }

    @Test
    public void canUndo_multipleScheduleListPointerAtStartOfStateList_returnsFalse() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_multipleScheduleListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);

        assertTrue(versionedScheduleList.canRedo());
    }

    @Test
    public void canRedo_multipleScheduleListPointerAtStartOfStateList_returnsTrue() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        assertTrue(versionedScheduleList.canRedo());
    }

    @Test
    public void canRedo_singleScheduleList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertFalse(versionedScheduleList.canRedo());
    }

    @Test
    public void canRedo_multipleScheduleListPointerAtEndOfStateList_returnsFalse() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        assertFalse(versionedScheduleList.canRedo());
    }

    @Test
    public void undo_multipleScheduleListPointerAtEndOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        versionedScheduleList.undo();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                scheduleListWithAlice,
                Collections.singletonList(scheduleListWithBenson));
    }

    @Test
    public void undo_multipleScheduleListPointerNotAtStartOfStateList_success() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertScheduleListListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyScheduleList,
                Arrays.asList(scheduleListWithAlice, scheduleListWithBenson));
    }

    @Test
    public void undo_singleScheduleList_throwsNoUndoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertThrows(VersionedScheduleList.NoUndoableStateException.class, versionedScheduleList::undo);
    }

    @Test
    public void undo_multipleScheduleListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        assertThrows(VersionedScheduleList.NoUndoableStateException.class, versionedScheduleList::undo);
    }

    @Test
    public void redo_multipleScheduleListPointerNotAtEndOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 1);

        versionedScheduleList.redo();
        assertScheduleListListStatus(versionedScheduleList,
                Arrays.asList(emptyScheduleList, scheduleListWithAlice),
                scheduleListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleScheduleListPointerAtStartOfStateList_success() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedScheduleList, 2);

        versionedScheduleList.redo();
        assertScheduleListListStatus(versionedScheduleList,
                Collections.singletonList(emptyScheduleList),
                scheduleListWithAlice,
                Collections.singletonList(scheduleListWithBenson));
    }

    @Test
    public void redo_singleScheduleListAddressBook_throwsNoRedoableStateException() {
        VersionedScheduleList versionedScheduleList = prepareScheduleList(emptyScheduleList);

        assertThrows(VersionedScheduleList.NoRedoableStateException.class, versionedScheduleList::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedScheduleList versionedAddressBook = prepareScheduleList(
                emptyScheduleList, scheduleListWithAlice, scheduleListWithBenson);

        assertThrows(VersionedScheduleList.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertScheduleListListStatus(VersionedScheduleList versionedScheduleList,
                                             List<ReadOnlyScheduleList> expectedStatesBeforePointer,
                                             ReadOnlyScheduleList expectedCurrentState,
                                             List<ReadOnlyScheduleList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ScheduleList(versionedScheduleList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedScheduleList.canUndo()) {
            versionedScheduleList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyScheduleList expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new ScheduleList(versionedScheduleList));
            versionedScheduleList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyScheduleList expectedAddressBook : expectedStatesAfterPointer) {
            versionedScheduleList.redo();
            assertEquals(expectedAddressBook, new ScheduleList(versionedScheduleList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedScheduleList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedScheduleList.undo());
    }

    /**
     * Creates and returns a {@code VersionedAddressBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedAddressBook#currentStatePointer} at the end of list.
     */
    private VersionedScheduleList prepareScheduleList(ReadOnlyScheduleList... scheduleListStates) {
        assertFalse(scheduleListStates.length == 0);

        VersionedScheduleList versionedAddressBook = new VersionedScheduleList(scheduleListStates[0]);
        for (int i = 1; i < scheduleListStates.length; i++) {
            versionedAddressBook.resetData(scheduleListStates[i]);
            versionedAddressBook.commit();
        }

        return versionedAddressBook;
    }

    /**
     * Shifts the {@code versionedAddressBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedScheduleList versionedScheduleList, int count) {
        for (int i = 0; i < count; i++) {
            versionedScheduleList.undo();
        }
    }

}
