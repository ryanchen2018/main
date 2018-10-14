package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.Arrays;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DEPARTMENT, PREFIX_POSITION);

        if (!argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()
                && !argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String trimmedDepartment;
        String trimmedPosition;
        String[] departmentKeywords = new String[]{""};
        String[] positionKeywords = new String[]{""};

        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            trimmedDepartment = (argMultimap.getValue(PREFIX_DEPARTMENT).get()).trim().toUpperCase();
            departmentKeywords = trimmedDepartment.split("\\s+");

        }

        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            trimmedPosition = (argMultimap.getValue(PREFIX_POSITION).get()).trim().toUpperCase();
            positionKeywords = trimmedPosition.split("\\s+");
        }
        
        if (!areKeywordsValid(departmentKeywords, PREFIX_DEPARTMENT.toString())) {
            throw new ParseException(Department.MESSAGE_DEPARTMENT_KEYWORD_CONSTRAINTS);
        }

        if (!areKeywordsValid(positionKeywords, PREFIX_POSITION.toString())) {
            throw new ParseException(Position.MESSAGE_POSITION_KEYWORD_CONSTRAINTS);
        }

        FilterCommand filterCommand = new FilterCommand(new DepartmentContainsKeywordsPredicate(Arrays
                .asList(departmentKeywords)), new PositionContainsKeywordsPredicate(Arrays.asList(positionKeywords)));

        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            filterCommand.setIsDepartmentPrefixPresent(true);
        } else if (!argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            filterCommand.setIsDepartmentPrefixPresent(false);
        }

        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            filterCommand.setIsPositionPrefixPresent(true);
        } else if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            filterCommand.setIsPositionPrefixPresent(false);
        }

        return filterCommand;
    }

    /**
     * Checks whether given keywords are valid department(s) or position(s)
     */
    public boolean areKeywordsValid (String[] keywords, String prefix) {
        for (String keyword: keywords) {
            if (prefix.equals(PREFIX_DEPARTMENT.toString()) && !Department.isValidDepartment(keyword)) {
                return false;
            }
            if (prefix.equals(PREFIX_POSITION.toString()) && !Position.isValidPosition(keyword)) {
                return false;
            }
        }
        return true;
    }
}
