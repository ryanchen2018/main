package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_EXPERIENCE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruitment.Recruitment;

/**
 * Adds a recruitment post on the address book.
 */
public class AddRecruitmentPostCommand extends Command {

    public static final String COMMAND_WORD = "recruitmentPost";

    public static final String MESSAGE_USAGE2 = COMMAND_WORD + ": Available Jobs "
            + PREFIX_JOB_POSITION + "[Job Position:]"
            + PREFIX_MINIMUM_EXPERIENCE + "[min working experience(Integer):]"
            + PREFIX_JOB_DESCRIPTION + "[Job Description:]\n"
            + "Example: " + COMMAND_WORD + PREFIX_JOB_POSITION
            + "IT Manager" + PREFIX_MINIMUM_EXPERIENCE + "3"
            + PREFIX_JOB_DESCRIPTION + "To maintain the network server in company";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Post";

    public static final String MESSAGE_FAILURE = "Recruitment Posts are failed";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Recruitment toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddRecruitmentPostCommand(Recruitment recruitment) {
        requireNonNull(recruitment);
        toAdd = recruitment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasRecruitment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addRecruitment(toAdd);
        model.commitRecruitmentList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecruitmentPostCommand // instanceof handles nulls
                && toAdd.equals(((AddRecruitmentPostCommand) other).toAdd));
    }

}
