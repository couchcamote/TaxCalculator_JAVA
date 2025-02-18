package congestion.calculator.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import congestion.calculator.CongestionTaxCalculator;

@RestController
public class TaxCaclulatorController {
	
	@Autowired
	CongestionTaxCalculator congestionTaxCalculator;


    @GetMapping("/calculateTax")
	public ResponseEntity<Integer> calculateTax(@RequestParam(value = "vehicle") String vehicle, 
    @RequestParam(value = "dates") String[] dates) {
    
    	int tax = 0;
    	
    	try {
			tax = congestionTaxCalculator.getTaxForVehicle(vehicle, dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	 return new ResponseEntity<Integer>(tax, HttpStatus.OK);
	}
    
}
