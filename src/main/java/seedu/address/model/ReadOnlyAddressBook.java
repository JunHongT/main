package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.eatery.Eatery;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the eateries list.
     * This list will not contain any duplicate eateries.
     */
    ObservableList<Eatery> getEateryList();

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getTodoList();
}
