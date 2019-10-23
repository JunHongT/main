package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.eatery.*;
import seedu.address.testutil.EateryBuilder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEateries.AMY;
import static seedu.address.testutil.TypicalEateries.BOB;

public class AddTodoCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Eatery expectedEatery = new EateryBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC + TAG_DESC_FRIEND, new AddCommand(expectedEatery));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC + TAG_DESC_FRIEND, new AddCommand(expectedEatery));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + CATEGORY_DESC + TAG_DESC_FRIEND, new AddCommand(expectedEatery));

        // multiple tags - all accepted
        Eatery expectedEateryMultipleTags = new EateryBuilder(BOB).withTags(VALID_TAG_FRIEND,
                VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + ADDRESS_DESC_BOB + CATEGORY_DESC
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, new AddCommand(expectedEateryMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Eatery expectedEatery = new EateryBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + ADDRESS_DESC_AMY + CATEGORY_DESC,
                new AddCommand(expectedEatery));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_MAIN);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ADDRESS_DESC_BOB + CATEGORY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ADDRESS_DESC + CATEGORY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, NAME_DESC_BOB + ADDRESS_DESC_BOB + INVALID_CATEGORY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Category.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + ADDRESS_DESC_BOB + CATEGORY_DESC
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_ADDRESS_DESC + CATEGORY_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                + ADDRESS_DESC_BOB + CATEGORY_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_MAIN));
    }
}
