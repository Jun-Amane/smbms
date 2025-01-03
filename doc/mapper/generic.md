# Generic Mapper Interface

## Type Parameters

- `T`: Entity type
- `PK`: Primary key type, usually is Long

## Methods

- Basic CRUD methods
	* `Integer insert(T entity)`
		+ param: entity
		+ return: rows affected
	* `Integer update(T entity)`
		+ param: entity
		+ return: rows affected
	* `Integer deleteById(PK id)`
		+ param: primary key ID
		+ return: rows affected
	* `T findById(PK id)`
		+ param: primary key ID
		+ return: object found
	* `List<T> findAll()`
		+ return: a list of object found
  

