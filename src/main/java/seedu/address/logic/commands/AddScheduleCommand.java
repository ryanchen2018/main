package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TYPE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;

/**
 * Adds a schedule to a person on the address book.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "addSchedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedule work "
            + "by specifying the Employee number. "
            + "Parameters: "
            + PREFIX_EMPLOYEEID + "[6digit] "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY] "
            + PREFIX_SCHEDULE_TYPE + "[WORK/LEAVE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "000001 "
            + PREFIX_SCHEDULE_DATE + "02/02/2018 "
            + PREFIX_SCHEDULE_TYPE + "LEAVE";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in the address book";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in address book";

    private Person toCheckEmployeeId;
    private final Schedule toAddSchedule;

    /**
     * @param schedule of the person to be updated to
     */
    public AddScheduleCommand(Schedule schedule) {
        requireAllNonNull(schedule);
        this.toAddSchedule = schedule;
        toCheckEmployeeId = new Person(schedule.getEmployeeId());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.hasSchedule(toAddSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        } else if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);
        }

        model.addSchedule(toAddSchedule);
        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && toAddSchedule.equals(((AddScheduleCommand) other).toAddSchedule));
    }
}

