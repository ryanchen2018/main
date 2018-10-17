package seedu.address.model.recruitment;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Post {

    public static final String MESSAGE_POST_CONSTRAINTS =
            "POST must be a post and it should not be blank";


    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String POST_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";


    public final String value;
    public Post(String post) {
        requireNonNull(post);
        checkArgument(isValidPost(post), MESSAGE_POST_CONSTRAINTS);
        value = post;
    }
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPost(String test) {
        return test.matches(POST_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Post // instanceof handles nulls
                && value.equals(((Post) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
