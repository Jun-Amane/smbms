# Provider Service Interface

- extends `GenericGrudService`

## Methods

- Basic CRUD methods (inherited)
	* insert
	* update
	* deleteById
	* findById
	* findAll
  
- Specific methods
	* `public PageInfo<Provider> getProviderList(String name, String code, Integer pageNum, Integer pageSize)`
		+ param pageNum: current page number
		+ return: the PageInfo object by page helper

	* `public Boolean deleteProviderById(Long id)`
		+ Before operation, use `userMapper.findBillCountByProviderId()` to find out the number of bills related to the provider. If there are bills in system related to the provider, then the provider cannot be deleted.
		+ return: successful or not

	
# Provider Service Impl

- extends `GenericGrudServiceImpl`
- implements `ProviderService`

## Attributes

- **private ProviderMapper providerMapper**: provider mapper, use constructor injection
- **private BillMapper billMapper**: bill mapper, use constructor injection

## Methods

- inherited methods
	
