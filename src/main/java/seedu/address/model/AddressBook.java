package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
<<<<<<< HEAD
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Name;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
=======
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.UniqueEateryList;
>>>>>>> upstream/master

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEatery comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private boolean isMain;
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final UniqueEateryList eateries;
    private final UniqueEateryList todo;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        eateries = new UniqueEateryList();
        todo = new UniqueEateryList();
    }

    public AddressBook() {
        isMain = true;
    }

    /**
     * Creates an AddressBook using the Eaterys in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        isMain = true;
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the eatery list with {@code eateries}.
     * {@code eateries} must not contain duplicate eateries.
     */
    public void setEateries(List<Eatery> eateries) {
        HashSet<Tag> temp = new HashSet<>();
        temp.add(new Tag("break"));
        Eatery breakLine = new Eatery(new Name("break"), new Address("break"), temp);
        int index = eateries.indexOf(breakLine);
        ArrayList<Eatery> personList = new ArrayList<>();
        ArrayList<Eatery> todoList = new ArrayList<>();
        for (int i  = 0; i < eateries.size(); i++) {
            if (i < index || index < 0) {
                personList.add(eateries.get(i));
            } else if (i > index) {
                todoList.add(eateries.get(i));
            }
        }

        this.eateries.setEateries(personList);
        this.todo.setEateries(todoList);

    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setEateries(newData.getEateryList());
    }

    //// eatery-level operations

    /**
     * Returns true if a eatery with the same identity as {@code eatery} exists in the address book.
     */
    public boolean hasEatery(Eatery eatery) {
        requireNonNull(eatery);
        if (isMain) {
            return eateries.contains(eatery);
        } else {
            return todo.contains(eatery);
        }
    }

    /**
     * Adds a eatery to the address book.
     * The eatery must not already exist in the address book.
     */

    public void addEatery(Eatery e) {
        if (isMain) {
            eateries.add(e);
        } else {
            todo.add(e);
        }
    }

    /**
     * Replaces the given eatery {@code target} in the list with {@code editedEatery}.
     * {@code target} must exist in the address book.
     * The eatery identity of {@code editedEatery} must not be the same as another existing eatery in the address book.
     */
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireNonNull(editedEatery);
        if (isMain) {
            eateries.setEatery(target, editedEatery);
        } else {
            todo.setEatery(target, editedEatery);
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEatery(Eatery key) {
        if (isMain) {
            eateries.remove(key);
        } else {
            todo.remove(key);
        }
    }

    //// util methods
    public void toggle() {
        isMain = !isMain;

        if (isMain) {
            logger.info("================Main Mode=============");
            logger.info(eateries.toString());
        } else {
            logger.info("================Todo Mode=============");
            logger.info(todo.toString());
        }
    }

    public boolean modeStatus() {
        return isMain;
    }

    @Override
    public String toString() {
        return eateries.asUnmodifiableObservableList().size() + " eateries";
        // TODO: refine later
    }

    @Override
    public ObservableList<Eatery> getEateryList() {
        return eateries.asUnmodifiableObservableList();
    }

    public ObservableList<Eatery> getTodoList() {
        return todo.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && eateries.equals(((AddressBook) other).eateries));
    }

    @Override
    public int hashCode() {
        return eateries.hashCode();
    }
}
