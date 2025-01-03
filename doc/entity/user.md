# User Entity

## Attributes

- **`private Long id`**: Unique identifier for the user.
- **`private String code`**: Username used to uniquely identify the user.
- **`private String name`**: Name of the user.
- **`private String password`**: Password for the user's account, typically stored in an encrypted format.
- **`private Integer gender`**: Gender of the user, could be represented numerically (1 for male, 2 for female, 0 for others).
- **`private Date birthday`**: Birthdate of the user.
- **`private String phone`**: Contact phone number of the user.
- **`private String address`**: Physical address of the user.
- **`private Integer age`**: Age of the user.
- **`private Role role`**: Role of the user within the system, represented by ID.
- **`private User createdBy`**: ID of the user who created this user account.
- **`private Date creationDate`**: Date when the user account was created.
- **`private User modifiedBy`**: ID of the user who last modified this user account.
- **`private Date modificationDate`**: Date when the user account was last updated.

## Methods

- Constructors
  - `User()`: Default constructor.
  - `User(...)`: Parameterized constructor to initialize user properties.
  
- Getters and Setters


