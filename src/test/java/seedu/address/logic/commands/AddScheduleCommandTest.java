package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.testutil.schedule.ScheduleBuilder;

public class AddScheduleCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddScheduleCommand(null);
    }

    @Test
    public void execute_scheduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingScheduleAdded modelStub = new ModelStubAcceptingScheduleAdded();
        Schedule validSchedule = new ScheduleBuilder().build();

        CommandResult commandResult = new AddScheduleCommand(validSchedule).execute(modelStub, commandHistory);

        assertEquals(String.format(AddScheduleCommand.MESSAGE_SUCCESS, validSchedule), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validSchedule), modelStub.schedulesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateSchedule_throwsCommandException() throws Exception {
        Schedule validSchedule = new ScheduleBuilder().build();
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(validSchedule);
        ModelStub modelStub = new ModelStubWithSchedule(validSchedule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddScheduleCommand.MESSAGE_DUPLICATE_SCHEDULE);
        addScheduleCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Schedule alice = new ScheduleBuilder().withEmployeeId("000001").withType("LEAVE").build();
        Schedule bob = new ScheduleBuilder().withEmployeeId("000002").withType("WORK").build();
        AddScheduleCommand addAliceCommand = new AddScheduleCommand(alice);
        AddScheduleCommand addBobCommand = new AddScheduleCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddScheduleCommand addAliceCommandCopy = new AddScheduleCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand == null);

        // different schedule -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void addExpenses(Expenses expenses) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }


        //------------------------------------------------
        @Override
        public void resetAddressBookData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDataExpenses(ReadOnlyExpensesList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetScheduleListData(ReadOnlyScheduleList newData) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public ReadOnlyExpensesList getExpensesList() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyScheduleList getScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean hasExpenses(Expenses expenses) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployeeId(Person person) {
            return true;
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void deleteExpenses(Expenses target) {
            throw new AssertionError("This method should not be called."); }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSchedule(Schedule target) {
            throw new AssertionError("This method should not be called.");
        }


        //------------------------------------------------
        @Override
        public void updateExpenses(Expenses target, Expenses editedExpenses) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSchedule(Schedule target, Schedule editedSchedule) {
            throw new AssertionError("This method should not be called.");
        }


        //------------------------------------------------
        @Override
        public ObservableList<Expenses> getFilteredExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Schedule> getFilteredScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void updateFilteredExpensesList(Predicate<Expenses> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitExpensesList() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void commitScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single schedule.
     */
    private class ModelStubWithSchedule extends ModelStub {
        private final Schedule schedule;

        ModelStubWithSchedule(Schedule schedule) {
            requireNonNull(schedule);
            this.schedule = schedule;
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            requireNonNull(schedule);
            return this.schedule.isSameSchedule(schedule);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingScheduleAdded extends ModelStub {
        private final ArrayList<Schedule> schedulesAdded = new ArrayList<>();

        @Override
        public boolean hasSchedule(Schedule schedule) {
            requireNonNull(schedule);
            return schedulesAdded.stream().anyMatch(schedule::isSameSchedule);
        }

        @Override
        public void addSchedule(Schedule schedule) {
            requireNonNull(schedule);
            schedulesAdded.add(schedule);
        }

        @Override
        public void commitScheduleList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyScheduleList getScheduleList() {
            return new ScheduleList();
        }
    }
}
