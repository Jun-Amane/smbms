# Bill Mapper Interface

- extends `GenericMapper`

## Methods

- Basic CRUD methods
	* insert
	* update
	* deleteById
	* findById
	* findAll
  
- Specific methods
	* `int findBillCountByProviderId(Long id)`
		+ param id: provider ID
		+ return: the number of bills with the certain provider

	* `List<Bill> findAllBillsByQuery(Bill condition)`
		+ Get bill list by query condition
		+ param condition: optional query conditions packed into `Bill` object, support fuzzy find.
		+ return: the `List` of bills found

	


