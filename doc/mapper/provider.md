# Provider Mapper Interface

- extends `GenericMapper`

## Methods

- Basic CRUD methods
	* insert
	* update
	* deleteById
	* findById
	* findAll
  
- Specific methods
	* `List<Provider> findAllProviderssByQuery(String name, String code)`
		+ Get provider list by query condition
		+ param name: provider name, optional, supports fuzzy find
		+ param code: provider code, optional, supports fuzzy find
		+ return: the `List` of providers found

	
	
