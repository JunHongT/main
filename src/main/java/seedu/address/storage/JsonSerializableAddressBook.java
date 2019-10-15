package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Address;
import seedu.address.model.tag.Tag;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_EATERY = "Eaterys list contains duplicate eatery(ies).";

    private final List<JsonAdaptedEatery> eateries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given eateries.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("eateries") List<JsonAdaptedEatery> eateries) {
        this.eateries.addAll(eateries);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        eateries.addAll(source.getEateryList().stream().map(JsonAdaptedEatery::new).collect(Collectors.toList()));
        HashSet<Tag> temp = new HashSet<>();
        temp.add(new Tag("break"));
        eateries.add(new JsonAdaptedEatery(new Eatery(new Name("break"), new Address("break"), temp)));
        eateries.addAll(source.getTodoList().stream().map(JsonAdaptedEatery::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedEatery jsonAdaptedEatery : eateries) {
            Eatery eatery = jsonAdaptedEatery.toModelType();
            if (addressBook.hasEatery(eatery)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EATERY);
            }
            addressBook.addEatery(eatery);
        }
        return addressBook;
    }

}
