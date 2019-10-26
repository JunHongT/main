package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.eatery.Eatery;

import java.util.logging.Logger;

/**
 * Panel containing the list of eateries.
 */
public class TodoListPanel extends UiPart<Region> {
    private static final String FXML = "TodoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);

    @FXML
    private ListView<Eatery> todoListView;

    public TodoListPanel(ObservableList<Eatery> eateryList) {
        super(FXML);
        todoListView.setItems(eateryList);
        todoListView.setCellFactory(listView -> new TodoListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Eatery} using a {@code EateryCard}.
     */
    class TodoListViewCell extends ListCell<Eatery> {
        @Override
        protected void updateItem(Eatery eatery, boolean empty) {
            super.updateItem(eatery, empty);

            if (empty || eatery == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TodoCard(eatery, getIndex() + 1).getRoot());
            }
        }
    }

}
