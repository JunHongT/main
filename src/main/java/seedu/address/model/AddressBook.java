package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private boolean isMain;
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final UniquePersonList persons;
    private final UniquePersonList todo;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        todo = new UniquePersonList();
    }

    public AddressBook() {
        isMain = true;
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        isMain = true;
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        HashSet<Tag> temp = new HashSet<>();
        temp.add(new Tag("break"));
        Person breakLine = new Person(new Name("break"), new Address("break"), temp);
        int index = persons.indexOf(breakLine);
        ArrayList<Person> personList = new ArrayList<>();
        ArrayList<Person> todoList = new ArrayList<>();
        for (int i  = 0; i < persons.size(); i++) {
            if (i < index || index < 0) {
                personList.add(persons.get(i));
            } else if (i > index) {
                todoList.add(persons.get(i));
            }
        }

        this.persons.setPersons(personList);
        this.todo.setPersons(todoList);

    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        if (isMain) {
            return persons.contains(person);
        } else {
            return todo.contains(person);
        }

    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        if(isMain) {
            persons.add(p);
        } else {
            todo.add(p);
        }

    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        if (isMain) {
            persons.setPerson(target, editedPerson);
        } else {
            todo.setPerson(target, editedPerson);
        }

    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        if (isMain) {
            persons.remove(key);
        } else {
            todo.remove(key);
        }

    }

    //// util methods
    public void toggle() {
        isMain = !isMain;

        if (isMain) {
            logger.info("================Main Mode=============");
            logger.info(persons.toString());
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
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {

        return persons.asUnmodifiableObservableList();
    }

    public ObservableList<Person> getTodoList() {

        return todo.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
