package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenses.VersionedExpensesList;
import seedu.address.model.schedule.VersionedScheduleList;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        boolean redoCommandCommit = false;

        if (model.canRedoScheduleList()) {
            try {
                model.redoScheduleList();
                model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
            } catch (VersionedScheduleList.NoRedoableStateException e) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            redoCommandCommit = true;
        }

        if (model.canRedoExpensesList()) {
            try {
                model.redoExpensesList();
                model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
            } catch (VersionedExpensesList.NoRedoableStateException e) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            redoCommandCommit = true;
        }

        if (model.canRedoAddressBook()) {
            try {
                model.redoAddressBook();
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            } catch (VersionedScheduleList.NoRedoableStateException e) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            redoCommandCommit = true;
        }

        /*
         * Commands above must be a success, otherwise there must be no more commands to redo.
         */
        if (!redoCommandCommit) {
            throw new CommandException(MESSAGE_FAILURE);
        }


        return new CommandResult(MESSAGE_SUCCESS);
    }
}
