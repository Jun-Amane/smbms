# Bill Service Interface

- extends `GenericGrudService`

## Methods

- Basic CRUD methods (inherited)
	* insert
	* update
	* deleteById
	* findById
	* findAll
  
- Specific methods
	* `public PageInfo<Bill> getBillList(Bill condition, Integer pageNum, Integer pageSize)`
		+ param condition: query conditions packed into Bill object
		+ param pageNum: current page number
		+ return: the PageInfo object by page helper
	
# Bill Service Impl

- extends `GenericGrudServiceImpl`
- implements `BillService`

## Attributes

- **private BillMapper billMapper**: bill mapper, use constructor injection

## Methods

- inherited methods
	

