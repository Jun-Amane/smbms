# User Mapper Interface

- extends `GenericMapper`

## Methods

- Basic CRUD methods
	* insert
	* update
	* deleteById
	* findById
	* findAll
  
- Specific methods
	* `User findByCodeAndPassword(String code, String password)`
		+ for login, check if there is a user with the certain code and password.
		+ return: the `User` object if found, otherwise `null`

	* `List<Users> findAllUsersByQuery(String name ,int roleId)`
		+ Get user list by query condition
		+ param name: optional query condition, support fuzzy find.
		+ param roleId: optional query condition, support fuzzy find.
		+ return: the `List` of users found
	
	* `Integer updateUserPassword(Long id, String password)`
		+ update user password
		+ return: rows affected
	
	* `User findByCode(String code)`
		+ find user by code
		+ return: user object



