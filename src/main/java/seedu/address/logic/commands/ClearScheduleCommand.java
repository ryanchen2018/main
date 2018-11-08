package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.ScheduleList;

/**
 * The {@code ClearScheduleCommand} class is used for clearing the entire schedule list.
 * The entire schedule storage will be reset to a clean state.
 */
public class ClearScheduleCommand extends Command {

    public static final String COMMAND_WORD = "clearSchedules";
    public static final String COMMAND_ALIAS = "cs";
    public static final String MESSAGE_SUCCESS = "Schedule list has been cleared!";
    public static final String MESSAGE_FAILURE_CLEARED = "Schedule list is empty!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the Schedule List\n"
            + "Example: " + COMMAND_WORD;

    /**
     * ClearScheduleCommand execution.
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredScheduleList(model.PREDICATE_SHOW_ALL_SCHEDULES);
        if (model.getFilteredScheduleList().size() > 0) {
            model.resetScheduleListData(new ScheduleList());
            model.commitScheduleList();
        } else {
            throw new CommandException(MESSAGE_FAILURE_CLEARED);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
