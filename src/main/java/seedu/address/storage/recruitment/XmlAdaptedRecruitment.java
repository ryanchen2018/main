package seedu.address.storage.recruitment;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.storage.addressbook.XmlAdaptedPerson;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedRecruitment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "recruitment's %s field is missing!";

    @XmlElement(required = true)
    private String post;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecruitment() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedRecruitment(String post) {
        this.post = post;
    }

    /**
     * Converts a given Recruitment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedRecruitment(Recruitment source) {
        post = source.getPost().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Recruitment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Recruitment toModelPost() throws IllegalValueException {

        if (post == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Post.class.getSimpleName()));
        }
        if (!Post.isValidPost(post)) {
            throw new IllegalValueException(Post.MESSAGE_POST_CONSTRAINTS);
        }
        final Post modelPost = new Post(post);

        return new Recruitment(modelPost);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedRecruitment otherRecruitment = (XmlAdaptedRecruitment) other;
        return Objects.equals(post, otherRecruitment.post);
    }
}
