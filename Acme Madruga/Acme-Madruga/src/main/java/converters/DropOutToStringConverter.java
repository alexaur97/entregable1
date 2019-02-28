package converters; 

import org.springframework.core.convert.converter.Converter; 
import org.springframework.stereotype.Component; 
import org.springframework.transaction.annotation.Transactional; 

import domain.DropOut; 

@Component 
@Transactional 
public class DropOutToStringConverter implements Converter<DropOut, String>{ 

	@Override 
	public String convert(DropOut dropOut){ 
		String result; 
		if(dropOut == null){ 
			result = null; 
		}else{ 
			result = String.valueOf(dropOut.getId()); 
		} 
		return result; 
	} 

} 
