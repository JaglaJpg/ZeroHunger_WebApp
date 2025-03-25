package com.example.zerohunger.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ListingService {
	
	public <T> void createListing(Class<T> entityClass, Map<String, Object> fields, JpaRepository<T, ?> repo) {
		try {
			
			validateFields(fields, entityClass);
			
			T entity = entityClass.getDeclaredConstructor().newInstance();
			
			for(Map.Entry<String, Object> entry : fields.entrySet()) {
				String fieldName = entry.getKey();
				Object value = entry.getValue();
				
				Field declaredField = entityClass.getDeclaredField(fieldName);
				declaredField.setAccessible(true);
				declaredField.set(entity,  value);
				
			}
			repo.save(entity);
		} catch (Exception e) {
	        throw new RuntimeException("Failed to create Listing of type: " + entityClass.getSimpleName(), e);
	    }
	}

	private void validateFields(Map<String, Object> fields, Class<?> entity) {
		List<String> fieldsList = Arrays.stream(entity.getDeclaredFields()).map(Field::getName).toList();
		
		if(fieldsList.size() -1 != fields.size()){
			throw new IllegalArgumentException("Not enough information");
		}
		
		for(Map.Entry<String, Object> entry : fields.entrySet()) {
			String fieldName = entry.getKey();
			Object value = entry.getValue();
			
			if(!fieldsList.contains(fieldName)) {
				throw new IllegalArgumentException("Invalid field name: " + fieldName);
			}
			
			try {
				Field declaredField = entity.getDeclaredField(fieldName);
				Class<?> fieldType = declaredField.getType();
				
				if(value != null && !fieldType.isInstance(value)) {
					throw new IllegalArgumentException(
		                    "Field '" + fieldName + "' expects type " + fieldType.getSimpleName() + 
		                    ", but got " + value.getClass().getSimpleName()
		                );
				}
			} catch(NoSuchFieldException e){
				throw new RuntimeException("Field not found: " + fieldName, e);
			}
		}
	}
	
	
	
}
