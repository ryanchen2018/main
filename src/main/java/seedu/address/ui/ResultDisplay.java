package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.addressbook.DayHourGreeting;

import static seedu.address.logic.parser.AddressBookParser.GREETING_MESSAGE_NEWLINE;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed;

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        DayHourGreeting greeting = new DayHourGreeting();
        displayed = new SimpleStringProperty(greeting.getGreeting() + GREETING_MESSAGE_NEWLINE);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
