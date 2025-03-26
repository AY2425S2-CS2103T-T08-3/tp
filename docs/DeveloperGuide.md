---
  layout: default.md
    title: "Developer Guide"
    pageNav: 3
---

# CafeConnect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**
### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T08-3/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T08-3/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `customerdelete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

> **_NOTE:_** The sequence diagram shows a simplified execution of the `DeleteCustomerCommand`.

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.
### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The **UI** consists of a `MainWindow` that is made up of parts e.g. `CommandBox`, `ResultDisplay`, `CustomerListPanel`, `StaffListPanel`, `DrinkListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The **UI** component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T08-3/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T08-3/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The **UI** component:
* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.
* Keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* Depends on classes in the `Model` component, as it displays `Customer`, `Staff`, and `Drink` objects residing in the `Model`.
* Contains a tabbed interface allowing users to switch between staff management, customer records, and the drink catalog.
* Features visual elements like charts to display customer metrics and sales data when requested.

The `HelpWindow` component is shown when you execute a help command. It contains a link to the detailed user and developer guide on this CafeConnect documentation website.
### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T08-3/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("customerdelete 1")` API call as an example.

<puml src="diagrams/DeleteCustomerSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `customerdelete 1` Command" />

> **_NOTE:_** The lifeline for `DeleteCustomerCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCustomerCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCustomerCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a staff).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCustomerCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.
### Model component
**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T08-3/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="850" height="1500" />

The `Model` component,

* stores CaféConnect data i.e., all `Person`, `Staff`, `Customer` and `Drink` objects (which are contained in a `UniquePersonList`, `UniqueStaffList`, `UniqueCustomerList` and `UniqueDrinkList` objects).
* stores the currently 'selected' `Person`, `Staff`, `Customer` or `DrinkList` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>`, `ObservableList<Staff>`, `ObservableList<Customer>` and `ObservableList<Drink>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

> **_NOTE:_** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>
### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.
## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Staff Management System

_{Implementation details for the Staff Management System will be added here}_

<br></br>

### Customer Management System

_{Implementation details for the Customer Management System will be added here}_

<br></br>

### Drink Management System

The implementation of the drink management system follows the convention of normal commands, where `AddressBookParser` is responsible for parsing user input strings into executable commands.

#### Adding a drink to the catalog

<puml src="diagrams/AddDrinkSequenceDiagram.puml" alt="AddDrinkSequenceDiagram" />

`AddressBookParser` creates `AddDrinkCommandParser` to parse user input string.

`AddDrinkCommandParser` first obtains the values corresponding to the prefixes `n/`, `p/`, and `c/`.
`AddDrinkCommandParser` ensures that:
- The name prefix `n/` must contain a valid drink name.
- The price prefix `p/` must contain a valid positive decimal value.
- The category prefix `c/` must contain a non-empty category name.

If any of the above constraints are violated, `AddDrinkCommandParser` throws a `ParseException`. Otherwise, it creates a new instance of `AddDrinkCommand` that corresponds to the user input.

`AddDrinkCommand` comprises of the drink to be added, which is an instance of `Drink`.

Upon execution, `AddDrinkCommand` first queries the supplied model if it contains a duplicate drink. If no duplicate drink exists (based on case-insensitive name matching), then `AddDrinkCommand` adds the drink into the drink catalog.

> **_NOTE:_** CafeConnect identifies a drink as a duplicate if its `NAME` matches (case-insensitive) with an existing drink in the catalog. Attempting to add a duplicate will result in an error.

<br></br>

### Customer Purchase and Points System

The implementation of the purchase command follows the convention of normal commands, where `AddressBookParser` is responsible for parsing user input strings into executable commands.

<puml src="diagrams/PurchaseSequenceDiagram.puml" alt="PurchaseSequenceDiagram" />

`AddressBookParser` creates `PurchaseCommandParser` to parse user input string.

`PurchaseCommandParser` obtains:
- The index of the customer from the preamble
- The drink name from the prefix `n/`
- The optional redemption flag from the prefix `redeem/`

`PurchaseCommandParser` ensures that:
- The index is a valid positive integer
- The drink name exists
- The redemption flag, if present, is either "true" or "false"

If any of the above constraints are violated, `PurchaseCommandParser` throws a `ParseException`. Otherwise, it creates a new instance of `PurchaseCommand` with the customer index, drink name, and redemption status.

Upon execution, `PurchaseCommand` performs the following steps:
1. Retrieves the customer at the specified index
2. Searches for the drink in the catalog by name
3. For regular purchases:
  - Calculates reward points (10 points per dollar)
  - Updates the customer's total spent
  - Updates the customer's reward points
  - Increments the visit count
4. For point redemptions:
  - Verifies the customer has sufficient points
  - Calculates points needed (100 points per dollar)
  - Deducts points from the customer
  - Keeps total spent unchanged
  - Increments the visit count

> **_NOTE:_** The points calculation follows a fixed rate of 10 points per dollar spent. Redemption follows a rate of 100 points equivalent to $1 in drink value.

<br></br>

### Quick Command Shortcuts

The implementation of shortcut commands (`c` for customers and `s` for staff) provides an efficient way to add new entries with minimal typing.

<puml src="diagrams/ShortcutCommandSequenceDiagram.puml" alt="ShortcutCommandSequenceDiagram" />

`AddressBookParser` detects single-letter commands and routes them to the appropriate handler:
- `c` routes to the customer shortcut handler
- `s` routes to the staff shortcut handler

The shortcut format follows a pattern of `ID:NAME:PHONE` with colon separators.

Upon receiving a shortcut command, the parser:
1. Splits the input at the colon separators
2. Validates that exactly three components are present
3. Creates a new entity with the provided values and default values for other fields
4. Adds the entity to the appropriate list

> **_NOTE:_** Shortcut commands only accept three parameters (ID, name, and phone). Other fields are set to default values and can be updated later using the edit commands.

<br></br>

### Hours Tracking for Staff

The implementation of the hours tracking system allows café owners to record staff working hours.

<puml src="diagrams/HoursAddSequenceDiagram.puml" alt="HoursAddSequenceDiagram" />

`AddressBookParser` creates `HoursAddCommandParser` to parse user input string.

`HoursAddCommandParser` obtains:
- The index of the staff member from the prefix `ind/`
- The hours to add from the prefix `h/`

`HoursAddCommandParser` ensures that:
- The index is a valid positive integer
- The hours value is a valid positive integer

Upon execution, `HoursAddCommand`:
1. Retrieves the staff member at the specified index
2. Adds the new hours to the existing hours worked
3. Creates an updated staff record with the new hours
4. Replaces the old staff record with the updated one

> **_NOTE:_** The hours tracking system is cumulative, with each `hoursadd` command adding to the staff member's existing hours rather than replacing them.

<br></br>

### Data Storage and Persistence

CaféConnect uses JSON-based storage to persist data across application restarts. The storage implementation separates different entity types into distinct files:

- `addressbook.json`: Stores staff and customer data
- `drinkcatalog.json`: Stores drink catalog data
- `preferences.json`: Stores user preferences like window size and position

<puml src="diagrams/StorageSequenceDiagram.puml" alt="StorageSequenceDiagram" />

When saving data, the following sequence occurs:
1. A command that modifies data is executed
2. The model is updated with the changes
3. The storage manager is notified to save the changes
4. The appropriate storage component serializes the data to JSON
5. The JSON data is written to the file system

On application startup, the reverse process occurs:
1. The storage manager attempts to read data from the JSON files
2. If successful, the data is deserialized into model objects
3. If any file is missing or corrupted, a new empty data structure is created

> **_NOTE:_** CafeConnect implements data backup and recovery mechanisms. If a data file is corrupted, the application attempts to back it up before creating a new empty data structure.

<br></br>
--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Use cases

(For all use cases below, the System is the Café Management System and the Actor is the café owner, unless specified otherwise)

**Use case: Add a Staff Member**

**MSS**

1. Café owner requests to add a new staff member.
2. System prompts for staff details: name, phone number, and role.
3. Café owner provides the required details.
4. System validates the input.
5. System adds the new staff member.
6. System confirms the addition.
   Use case ends.

**Extensions**

* 4a. Provided name format is invalid.

  * 4a1. System shows an error message.

  * 4a2. Use case resumes at step 2.

* 4b. Provided phone number is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Role description exceeds character limit.

  * 4c1. System shows an error message.

  * 4c2. Use case resumes at step 2.

* 4d. Staff member with the same name and phone number already exists.

  * 4d1. System shows a duplication error message.

    Use case ends.

* 4e. Staff member with the same name but different phone number exists.

  * 4e1. System prompts to confirm if phone number should be updated.

  * 4e2. Café owner chooses not to update.

  Use case ends.

  * 4e3. Café owner chooses to update.

    * System updates phone number.

    * System confirms update.

  Use case ends.

**Use case: Delete a Staff Member**

**MSS**

1. Café owner requests to delete a staff member.

2. System prompts for the staff member's name.

3. Café owner provides the staff member's name.

4. System checks if the staff member exists.

5. System deletes the staff member.

6. System confirms the deletion.

7. Use case ends.

**Extensions**
* 4a. No staff member with the given name is found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Multiple staff members with the same name are found.

  * 4b1. System prompts for the phone number.

  * 4b2. Café owner provides the phone number.

  * 4b3. System validates and deletes the correct entry.

  * 4b4. System confirms the deletion.

    Use case ends.

**Use case: View Staff List**

**MSS**

1. Café owner requests to view the staff list.

2. System retrieves and displays the list of staff members.

Use case ends.

**Extensions**
* 2a. The staff list is empty.

  * 2a1. System shows a message indicating no staff members are found.

    Use case ends.

**Use case: Search Staff by Name**

**MSS**

1. Café owner requests to search for a staff member by name.

2. System prompts for the staff member's name.

3. Café owner provides the staff member's name.

4. System searches for matching staff members.

5. System displays matching staff members.

Use case ends.

**Extensions**
* 4a. No staff members with the given name are found.

  * 4a1. System shows a message indicating no matches.

    Use case ends.

* 4b. Multiple staff members with the same name are found.

  * 4b1. System displays all matching entries.

    Use case ends.

**Use case: Define a Reward**

**MSS**

1. Café owner requests to define a new reward.

2. System prompts for reward details: reward name and points required.

3. Café owner provides the required details.

4. System validates the input.

5. System adds the new reward.

6. System confirms the addition.

Use case ends.

**Extensions**
* 4a. Provided reward name format is invalid.

  * 4a1. System shows an error message.

  * 4a2. Use case resumes at step 2.

* 4b. Provided points value is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Reward with the same name already exists.

  * 4c1. System shows a duplication error message.

    Use case ends

**Use case: Edit a Reward**

**MSS**

1. Café owner requests to edit an existing reward.

2. System prompts for reward name and new points required.

3. Café owner provides the reward name and updated points.

4. System checks if the reward exists.

5. System updates the reward.

6. System confirms the update.

Use case ends.

**Extensions**
* 4a. Reward not found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Provided points value is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

**Use case: Track Customer Points**

**MSS**

1. Café owner requests to update customer points.

2. System prompts for customer name and points to add or deduct.

3. Café owner provides the customer name and points.

4. System validates the input.

5. System updates the customer's points balance.

6. System confirms the update.

Use case ends.

**Extensions**
* 4a. Provided customer name format is invalid.

  * 4a1. System shows an error message.

  * 4a2. Use case resumes at step 2.

* 4b. Provided points value is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Customer not found.

  * 4c1. System shows an error message.

    Use case ends.

* 4d. Multiple customers with the same name are found.

  * 4d1. System prompts for a unique identifier (e.g., phone number).

  * 4d2. Café owner provides the identifier.

  * 4d3. System validates and updates the correct entry.

  * 4d4. System confirms the update.

    Use case ends.

**Use case: Redeem a Reward**

**MSS**

1. Café owner requests to redeem a reward for a customer.

2. System prompts for customer name and reward name.

3. Café owner provides the required details.

4. System checks if the customer and reward exist.

5. System verifies if the customer has enough points.

6. System deducts the required points and confirms the redemption.

Use case ends.

**Extensions**
* 4a. Customer not found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Reward not found.

  * 4b1. System shows an error message.

    Use case ends.

* 5a. Customer does not have enough points.

  * 5a1. System shows an insufficient points message.

    Use case ends.

**Use case: View Customer Visit History**

**MSS**

1. Café owner requests to view a customer's visit history.

2. System prompts for the customer's name.

3. Café owner provides the customer's name.

4. System retrieves and displays the customer's visit history.

Use case ends.

**Extensions**
* 4a. Customer not found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Customer has no recorded visits.

  * 4b1. System shows a message indicating no visit history.

    Use case ends.

**Use case: Generate Daily Sales Summary**

**MSS**

1. Café owner requests a daily sales summary for a specific date.

2. System validates the date format.

3. System retrieves sales data for the given date.

4. System compiles the sales summary, including total revenue, transaction count, and most popular item.

5. System displays the daily sales summary.

Use case ends.

**Extensions**
* 2a. Provided date format is invalid.

  * 2a1. System shows an error message.

    Use case ends.

* 3a. No sales data found for the given date.

  * 3a1. System shows a message indicating no recorded transactions.

    Use case ends.

**Use case: Generate Customer Insights Report**

**MSS**

1. Café owner requests customer insights by providing a customer name or choosing a predefined report type (e.g., top spenders, popular items).

2. System validates the input.

3. System retrieves and compiles the requested insights.

4. System displays the customer insights report.

Use case ends.

**Extensions**
* 2a. Provided customer name format is invalid.

  * 2a1. System shows an error message.

    Use case ends.

* 2b. No data available for the requested report.

  * 2b1. System shows a message indicating lack of data.

    Use case ends.

* 3a. Customer not found.

  * 3a1. System shows an error message.

    Use case ends.

**Use case: Monitor Stock Levels**

**MSS**

1. Café owner requests to check stock levels for a specific item or set a low-stock threshold alert.

2. System validates the input.

3. System retrieves current stock data.

4. System displays the stock levels or sets the threshold alert.

Use case ends.

**Extensions**
* 2a. Provided item name format is invalid.

  * 2a1. System shows an error message.

    Use case ends.

* 2b. Item not found in inventory.

  * 2b1. System shows an error message.

    Use case ends.

* 2c. Provided threshold value is invalid.

  * 2c1. System shows an error message.

    Use case ends.

* 3a. Stock level is below the threshold.

  * 3a1. System shows a low-stock alert message.

    Use case ends.

**Use case: View Customer Rewards**

**MSS**

1. Café owner requests to view a customer's reward points.

2. System validates the customer name.

3. System retrieves and displays the customer's reward points and eligible rewards.

Use case ends.

**Extensions**
* 2a. Provided customer name format is invalid.

  * 2a1. System shows an error message.

    Use case ends.

* 3a. Customer not found.

  * 3a1. System shows an error message.

    Use case ends.

**Use case: View Customer Order History**

**MSS**

1. Café owner requests to view a customer's order history.

2. System validates the customer name.

3. System retrieves and displays the customer's past orders.

Use case ends.

**Extensions**
* 2a. Provided customer name format is invalid.

  * 2a1. System shows an error message.

    Use case ends.

* 3a. Customer not found.

  * 3a1. System shows an error message.

    Use case ends.

* 3b. No past orders found for the customer.

  * 3b1. System shows a message indicating no recorded orders.

    Use case ends.

**Use case: Record Customer Feedback**

**MSS**

1. Café owner requests to add feedback for a customer.

2. System prompts for customer name, rating, and feedback message.

3. Café owner provides the required details.

4. System validates the input.

5. System records the feedback.

6. System confirms the feedback addition.

Use case ends.

**Extensions**
* 4a. Provided customer name format is invalid.

  * 4a1. System shows an error message.

  * 4a2. Use case resumes at step 2.

* 4b. Provided rating is out of the accepted range.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Feedback message exceeds character limit.

  * 4c1. System shows an error message.

  * 4c2. Use case resumes at step 2.

* 4d. Customer not found.

  * 4d1. System shows an error message.

    Use case ends.

**Use case: Add a Supplier Email**

**MSS**
1. Café owner requests to add a new supplier email.

2. System prompts for the supplier's name and email.

3. Café owner provides the required details.

4. System validates the input.

5. System adds the new supplier.

6. System confirms the addition.

Use case ends.

**Extensions**
* 4a. Provided name format is invalid.

  * 4a1. System shows an error message.

  * 4a2. Use case resumes at step 2.

* 4b. Provided email format is invalid.

  * 4b1. System shows an error message.

  * 4b2. Use case resumes at step 2.

* 4c. Supplier with the same name and email already exists.

  * 4c1. System shows a duplication error message.

    Use case ends.

* 4d. Supplier with the same name but a different email exists.

  * 4d1. System prompts to confirm if email should be updated.

  * 4d2. Café owner chooses not to update.

    Use case ends.

  * 4d3. Café owner chooses to update.

    * System updates the email.

    * System confirms the update.

    Use case ends.

* 4e. Required fields are missing.

  * 4e1. System shows an error message.

  * 4e2. Use case resumes at step 2.

**Use case: Delete a Supplier Email**

**MSS**

1. Café owner requests to delete a supplier email.

2. System prompts for the supplier's name.

3. Café owner provides the supplier's name.

4. System checks if the supplier exists.

5. System deletes the supplier.

6. System confirms the deletion.

Use case ends.

**Extensions**

* 4a. No supplier with the given name is found.

  * 4a1. System shows an error message.

    Use case ends.

* 4b. Multiple suppliers with the same name are found.

  * 4b1. System prompts for the email.

  * 4b2. Café owner provides the email.

  * 4b3. System validates and deletes the correct entry.

  * 4b4. System confirms the deletion.

    Use case ends.

**Use case: View Supplier Email List**

**MSS**

1. Café owner requests to view the supplier email list.

2. System retrieves and displays the list of suppliers.

Use case ends.

**Extensions**

* 2a. The supplier list is empty.

  * 2a1. System shows a message indicating no suppliers are found.

    Use case ends.
### Non-Functional Requirements

1. Performance & Scalability

  * The application should be able to handle up to 1000 total entries (staff, customers, and drinks combined) without noticeable sluggishness in performance.

  * The system should respond to user actions within 1 second for typical operations like adding, deleting, or searching for customers and staff.

  * The startup time should be no more than 2 seconds on a modern consumer-grade computer.

  * The application should consume no more than 200MB of RAM during normal operation with 1000 total entries.

2. Compatibility & Portability

  * The application should be compatible with Java 11 or above and run on any mainstream OS (Windows, Linux, macOS).

  * The application should work consistently across different screen resolutions and window sizes.

  * The system should be self-contained and should not require an internet connection or external databases.

3. Usability & Accessibility

  * A café owner with basic typing skills should be able to accomplish most tasks faster using commands than using a mouse-driven interface.

  * The tabbed interface should provide intuitive separation between staff management, customer records, and the drink catalog.

  * The application should provide clear, contextual error messages when a user makes an incorrect input.

  * Command shortcuts (like `c` and `s`) should make frequent operations more efficient.

4. Reliability & Robustness

  * The system should not crash or freeze when an invalid command is entered.

  * Customer, staff, and drink data should be persisted safely to prevent accidental loss due to application crashes.

  * The application should validate all inputs to prevent data corruption.

  * The system should be able to recover gracefully from unexpected failures (e.g., power loss, abrupt shutdowns).

5. Security & Privacy

  * The system should store customer and staff contact information securely.

  * The application should not expose private data unnecessarily in logs or error messages.

6. Maintainability & Extensibility

  * The codebase should maintain separation between the UI, Logic, Model, and Storage components.

  * New commands and features should be able to be added without modifying existing code.

  * The command structure should be consistent to make the application easier to learn and extend.

7. Domain-Specific Requirements

  * The reward points system should accurately track customer loyalty across multiple visits.

  * The application should support point redemption with clear feedback about points used and remaining.

  * Staff performance metrics should be maintainable and viewable.
### Glossary

* **Mainstream OS**: Operating systems commonly used by the majority of users, including Windows, Linux, Unix, and macOS.

* **Private contact detail**: A contact detail that is not meant to be shared with others, such as a personal phone number, home address, or private email.

* **Typical usage**: Standard operations performed within the application, including adding, editing, deleting, and searching for contacts, assuming a user base of up to 1000 persons.

* **Command-based interaction**: A method of input where users type textual commands to execute actions instead of using a graphical user interface (GUI).

* **Graphical User Interface (GUI)**: A visual interface that allows users to interact with the system through elements like buttons, forms, and icons instead of typing commands.

* **Error message**: A message displayed by the system when a user enters an invalid input or an operation cannot be completed, providing clear guidance on how to correct the issue.

* **Modern consumer-grade computer**: A personal computer with at least a quad-core processor and 8GB of RAM, manufactured within the last five years.

* **Modular codebase**: A structured code design where different components can be modified, replaced, or extended independently without affecting the overall system.

* **Persisted data**: Information that is stored and retained across application restarts, ensuring that user data is not lost when the application is closed.

* **Startup time**: The time taken from launching the application to when it is fully ready for user interaction.

* **Authorized user**: A user who has been granted specific permissions to access certain functionalities within the system.

* **Self-contained application**: An application that does not require external dependencies or an internet connection for its core features to function.

* **Scalability**: The system's ability to maintain performance and responsiveness even as the user base or data size increases.

* **Undo/Redo functionality**: A feature that allows users to reverse or reapply their last action, improving usability and error recovery.

* **Extensibility**: The ability to add new features or enhance existing ones without major rework of the system.

* **Logging and audit trail**: A system feature that records user actions and system events for security, debugging, or compliance purposes.
## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

> **_NOTE:_**  These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

### Launch

1. Initial launch
  1. Download the jar file and copy into an empty folder
  2. Run  `java -jar cafeconnect.jar` in a terminal.<br>
     Expected: The welcome window appears with buttons to navigate to view staff/customers or drink menu. The window size may not be optimum.

2. Saving window preferences
  1. After clicking on either of the two buttons, resize the window to an optimum size.<br>
     Move the window to a different location. Close the window.
  2. Re-launch the app by double-clicking the jar file.<br>
     Expected: The most recent window size and location is retained.

### Help Window

1. Opening help window via Command Line
  1. Prerequisites: Help window is not open
  2. Test case: `help`<br>
     Expected: Help window opens.

2. Opening help window via Tool bar
  1. Prerequisites: Help window is not open
  2. Test case: Click the `Help` menu, then click on the `Help` option<br>
     Expected: Help window opens.

3. Interacting the help window
  1. Prerequisites: Help window is now open
  2. Test case: Scroll through content<br>
     Expected: Help window content scrolls properly.

4. Closing the help window
  1. Prerequisites: Help window is open
  2. Test case: Click on the close button on the help window<br>
     Expected: Help window closes.

### Managing Customers

1. Adding a customer
  1. Prerequisites: The exact customer details shouldn't already be in the list
  2. Test case: `customeradd cid/C005 n/James Bond p/98765432 e/jamesbond@example.com a/123 Spy Street rp/0 vc/0 fi/Martini ts/0`<br>
     Expected: A new customer is successfully added with the specified details. The status message confirms the addition.
  3. Test case: `customeradd cid/C001 n/Duplicate Customer p/12345678 e/dup@example.com a/Duplicate Address rp/0 vc/0 fi/Coffee ts/0`<br>
     Expected: No customer is added. Error details about duplicate customer ID shown in the status message.

2. Adding a customer using shortcut
  1. Prerequisites: The customer ID shouldn't already exist in the list
  2. Test case: `c C099:John Smith:98761234`<br>
     Expected: A new customer is added with the specified ID, name, and phone number. Default values are used for other fields.

3. Deleting a customer
  1. Prerequisites: At least one customer in the list
  2. Test case: `customerdelete 1`<br>
     Expected: First customer is deleted from the list. Details of the deleted customer shown in the status message.
  3. Test case: `customerdelete 0`<br>
     Expected: No customer is deleted. Error details shown in the status message about invalid index.
  4. Other incorrect delete commands to try: `customerdelete`, `customerdelete x`, `customerdelete 999` (where x is non-numeric and 999 is larger than the list size)<br>
     Expected: Error message indicating invalid index.

### Managing Staff

1. Adding a staff member
  1. Prerequisites: The exact staff details shouldn't already be in the list
  2. Test case: `staffadd sid/S005 n/Emily Wong p/91234567 e/emily@example.com a/456 Worker Ave role/Manager shift/9am-5pm hours/0 rating/5.0`<br>
     Expected: A new staff member is successfully added with the specified details. The status message confirms the addition.

2. Adding a staff member using shortcut
  1. Prerequisites: The staff ID shouldn't already exist in the list
  2. Test case: `s S099:Jane Doe:90001234`<br>
     Expected: A new staff member is added with the specified ID, name, and phone number. Default values are used for other fields.

3. Deleting a staff member
  1. Prerequisites: At least one staff member in the list
  2. Test case: `staffdelete 1`<br>
     Expected: First staff member is deleted from the list. Details of the deleted staff shown in the status message.

4. Adding hours worked
  1. Prerequisites: At least one staff member in the list
  2. Test case: `hoursadd ind/1 h/5`<br>
     Expected: The hours worked for the first staff member is increased by 5 hours. Status message confirms the update.

### Managing Drinks and Purchases

1. Adding a drink to the catalog
  1. Prerequisites: The exact drink name shouldn't already be in the catalog
  2. Test case: `drinkadd n/Green Tea p/3.50 c/Tea`<br>
     Expected: A new drink is successfully added to the catalog. The status message confirms the addition.

2. Recording a purchase
  1. Prerequisites: At least one customer and one drink in the catalog
  2. Test case: `purchase 1 n/Espresso`<br>
     Expected: Purchase is recorded for the first customer. Their reward points, visit count, and total spent are updated. Status message confirms the purchase details.

3. Redeeming points for a purchase
  1. Prerequisites: At least one customer with sufficient reward points and one drink in the catalog
  2. Test case: `purchase 1 n/Cappuccino redeem/true`<br>
     Expected: Points are redeemed for the purchase. Customer's reward points decrease, visit count increases, and total spent remains unchanged. Status message confirms the redemption.
  3. Test case: `purchase 1 n/Expensive Drink redeem/true` (where the customer doesn't have enough points)<br>
     Expected: No redemption is made. Error message indicates insufficient points.

### Tab Navigation

1. Switching between tabs
  1. Test case: Click on the "Staff" tab<br>
     Expected: The staff list is displayed.
  2. Test case: Click on the "Customers" tab<br>
     Expected: The customer list is displayed.
  3. Test case: Click on the "Drinks Menu" tab<br>
     Expected: The drinks catalog is displayed.

### Exiting the Application

1. Exiting via command
  1. Test case: `exit`<br>
     Expected: Application closes and all data is saved.

2. Exiting via window controls
  1. Test case: Click on the X button at the top-right of the window<br>
     Expected: Application closes and all data is saved.

### Saving Data

1. Dealing with missing/corrupted data files
  1. Test case: Delete the addressbook.json file manually, then restart the application<br>
     Expected: Application starts with an empty customer and staff list, but creates a new addressbook.json file.
  2. Test case: Delete the drinkcatalog.json file manually, then restart the application<br>
     Expected: Application starts with an empty drink catalog, but creates a new drinkcatalog.json file.
  3. Test case: Corrupt the addressbook.json file by adding invalid JSON, then restart the application<br>
     Expected: Application starts with an empty customer and staff list, backing up the corrupted file.
