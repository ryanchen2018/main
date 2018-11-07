package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private final String sortAscOrder = "asc";
    private final String sortDscOrder = "dsc";

    @Test
    public void equals() {
        DepartmentContainsKeywordsPredicate firstDepartmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("first"));
        DepartmentContainsKeywordsPredicate secondDepartmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("second"));
        PositionContainsKeywordsPredicate firstPositionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("first"));
        PositionContainsKeywordsPredicate secondPositionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("second"));


        FilterCommand filterFirstCommand = new FilterCommand(firstDepartmentPredicate, firstPositionPredicate,
                sortAscOrder);
        FilterCommand filterSecondCommand = new FilterCommand(secondDepartmentPredicate, secondPositionPredicate,
                sortAscOrder);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstDepartmentPredicate, firstPositionPredicate,
                sortAscOrder);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different type -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> return false
        assertFalse(filterFirstCommand == null);

        // different person -> return false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywordsPositionPredicate_noPersonFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DepartmentContainsKeywordsPredicate departmentPredicate = prepareDepartmentPredicate(" ");
        PositionContainsKeywordsPredicate positionPredicate = preparePositionPredicate(" ");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);
        expectedMessage += command.listAvailablePositions(expectedModel);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(false);
        expectedModel.updateFilteredPersonList(positionPredicate, sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsDepartmentPredicate_noPersonFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DepartmentContainsKeywordsPredicate departmentPredicate = prepareDepartmentPredicate(" ");
        PositionContainsKeywordsPredicate positionPredicate = preparePositionPredicate(" ");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);
        expectedMessage += command.listAvailableDepartments(expectedModel);
        command.setIsPositionPrefixPresent(false);
        command.setIsDepartmentPrefixPresent(true);
        expectedModel.updateFilteredPersonList(departmentPredicate, sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsMultiplePredicates_noPersonFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DepartmentContainsKeywordsPredicate departmentPredicate = prepareDepartmentPredicate(" ");
        PositionContainsKeywordsPredicate positionPredicate = preparePositionPredicate(" ");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);
        expectedMessage += command.listAvailableDepartments(expectedModel)
                + command.listAvailablePositions(expectedModel);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(true);
        expectedModel.updateFilteredPersonList(departmentPredicate.and(positionPredicate), sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsPositionPredicate_multiplePersonsFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        DepartmentContainsKeywordsPredicate departmentPredicate = prepareDepartmentPredicate(" ");
        PositionContainsKeywordsPredicate positionPredicate =
                preparePositionPredicate("Director Manager");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(false);
        expectedModel.updateFilteredPersonList(positionPredicate, sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(ALICE, DANIEL, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsDepartmentPredicate_multiplePersonsFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        DepartmentContainsKeywordsPredicate departmentPredicate =
                prepareDepartmentPredicate("Human IT");
        PositionContainsKeywordsPredicate positionPredicate = preparePositionPredicate(" ");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);
        command.setIsPositionPrefixPresent(false);
        command.setIsDepartmentPrefixPresent(true);
        expectedModel.updateFilteredPersonList(departmentPredicate, sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsMultiplePredicates_multiplePersonsFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        DepartmentContainsKeywordsPredicate departmentPredicate =
                prepareDepartmentPredicate("Human IT Finance");
        PositionContainsKeywordsPredicate positionPredicate =
                preparePositionPredicate("Director Manager");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(true);
        expectedModel.updateFilteredPersonList(departmentPredicate.and(positionPredicate), sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(ALICE, DANIEL, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void executeWithSetters_zeroKeywordsMultiplePredicates_noPersonFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DepartmentContainsKeywordsPredicate departmentPredicate = prepareDepartmentPredicate("Human IT");
        PositionContainsKeywordsPredicate positionPredicate = preparePositionPredicate("Director");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);

        DepartmentContainsKeywordsPredicate departmentPredicateSet =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList(" "));
        PositionContainsKeywordsPredicate positionPredicateSet =
                new PositionContainsKeywordsPredicate(Collections.singletonList(" "));

        command.setDepartmentPredicate(departmentPredicateSet);
        command.setPositionPredicate(positionPredicateSet);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(true);
        expectedMessage += command.listAvailableDepartments(expectedModel)
                + command.listAvailablePositions(expectedModel);
        expectedModel.updateFilteredPersonList(departmentPredicateSet.and(positionPredicateSet), sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void executeWithSetters_multipleKeywordsMultiplePredicates_multiplePersonsFoundAscendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        DepartmentContainsKeywordsPredicate departmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList(" "));
        PositionContainsKeywordsPredicate positionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList(" "));
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortAscOrder);

        DepartmentContainsKeywordsPredicate departmentPredicateSet =
                prepareDepartmentPredicate("Human IT Finance");
        PositionContainsKeywordsPredicate positionPredicateSet =
                preparePositionPredicate("Director Manager");

        command.setDepartmentPredicate(departmentPredicateSet);
        command.setPositionPredicate(positionPredicateSet);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(true);

        expectedModel.updateFilteredPersonList(departmentPredicateSet.and(positionPredicateSet), sortAscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(ALICE, DANIEL, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsPositionPredicate_multiplePersonsFoundDescendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        DepartmentContainsKeywordsPredicate departmentPredicate = prepareDepartmentPredicate(" ");
        PositionContainsKeywordsPredicate positionPredicate =
                preparePositionPredicate("Director Intern");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortDscOrder);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(false);
        expectedModel.updateFilteredPersonList(positionPredicate, sortDscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(GEORGE, FIONA, ELLE, CARL, BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsDepartmentPredicate_multiplePersonsFoundDescendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        DepartmentContainsKeywordsPredicate departmentPredicate =
                prepareDepartmentPredicate("Human Finance");
        PositionContainsKeywordsPredicate positionPredicate = preparePositionPredicate(" ");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortDscOrder);
        command.setIsPositionPrefixPresent(false);
        command.setIsDepartmentPrefixPresent(true);
        expectedModel.updateFilteredPersonList(departmentPredicate, sortDscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(GEORGE, DANIEL, CARL, BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsMultiplePredicates_multiplePersonsFoundDescendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        DepartmentContainsKeywordsPredicate departmentPredicate =
                prepareDepartmentPredicate("Human IT");
        PositionContainsKeywordsPredicate positionPredicate =
                preparePositionPredicate("Director Intern");
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortDscOrder);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(true);
        expectedModel.updateFilteredPersonList(departmentPredicate.and(positionPredicate), sortDscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(FIONA, ELLE, CARL, BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void executeWithSetters_multipleKeywordsMultiplePredicates_multiplePersonsFoundDescendingOrder() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        DepartmentContainsKeywordsPredicate departmentPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList(" "));
        PositionContainsKeywordsPredicate positionPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList(" "));
        FilterCommand command = new FilterCommand(departmentPredicate, positionPredicate, sortDscOrder);

        DepartmentContainsKeywordsPredicate departmentPredicateSet =
                prepareDepartmentPredicate("Human IT");
        PositionContainsKeywordsPredicate positionPredicateSet =
                preparePositionPredicate("Director Manager");

        command.setDepartmentPredicate(departmentPredicateSet);
        command.setPositionPredicate(positionPredicateSet);
        command.setIsPositionPrefixPresent(true);
        command.setIsDepartmentPrefixPresent(true);

        expectedModel.updateFilteredPersonList(departmentPredicateSet.and(positionPredicateSet), sortDscOrder);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(FIONA, ALICE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code DepartmentContainsKeywordsPredicate}.
     */
    private DepartmentContainsKeywordsPredicate prepareDepartmentPredicate(String userInput) {
        return new DepartmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PositionContainsKeywordsPredicate}.
     */
    private PositionContainsKeywordsPredicate preparePositionPredicate(String userInput) {
        return new PositionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
