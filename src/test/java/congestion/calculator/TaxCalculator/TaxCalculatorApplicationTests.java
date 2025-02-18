package congestion.calculator.TaxCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import congestion.calculator.service.CongestionTaxCalculator;

@SpringBootTest
class TaxCalculatorApplicationTests {

	 private CongestionTaxCalculator taxCalculator;

	    @BeforeEach
	    void setUp() {
	        taxCalculator = new CongestionTaxCalculator();
	    }
	    
	    
	    @Test
	    void testGetTaxForException() throws ParseException {
	        String[] timestamps = {
	            "2013-02-08 06:29:00",  // Expected tax: 8 SEK
	            "2013-02-08 06:30:27",  // Expected tax: 13 SEK
	            "2013-02-08 07:15:00"   // Expected tax: 18 SEK
	        };

	        int tax = taxCalculator.getTaxForVehicle("Military", timestamps);

	        assertEquals(0, tax, "The tax for the car should be 0 SEK.");
	    }
	    
	    @Test
	    void testGetTaxForWithin60min() throws ParseException {
	        String[] timestamps = {
	            "2013-02-08 06:29:00",  // Expected tax: 8 SEK
	            "2013-02-08 06:30:27",  // Expected tax: 13 SEK
	            "2013-02-08 07:15:00"   // Expected tax: 18 SEK
	        };

	        int tax = taxCalculator.getTaxForVehicle("Car", timestamps);

	        assertEquals(18, tax, "The tax for the car should be 18 SEK.");
	    }
	   
	    @Test
	    void testGetTaxForCarWrongOrder() throws ParseException {
	        String[] timestamps = {
	            "2013-02-08 06:29:00",  // Expected tax: 8 SEK
	            "2013-02-08 07:15:00",   // Expected tax: 18 SEK
	            "2013-02-08 06:30:27"  // Expected tax: 13 SEK
	        };

	        int tax = taxCalculator.getTaxForVehicle("Car", timestamps);

	        assertEquals(18, tax, "The tax for the car should be 18 SEK.");
	    }
	    
	    @Test
	    void testGetTaxBugOn8AM() throws ParseException {
	        String[] timestamps = {
	            "2013-02-08 9:29:00",  // Expected tax: 8 SEK
	        };

	        int tax = taxCalculator.getTaxForVehicle("Car", timestamps);

	        assertEquals(8, tax, "The tax for the car should be 8 SEK.");
	    }
	    
	    @Test
	    void testGetTax() throws ParseException {
	        String[] timestamps = {
	            "2013-02-08 06:29:00",  // Expected tax: 8 SEK
	            "2013-02-10 06:30:27",  // Expected tax: 13 SEK
	            "2013-02-11 07:15:00"   // Expected tax: 18 SEK
	        };

	        int tax = taxCalculator.getTaxForVehicle("Car", timestamps);

	        assertEquals(39, tax, "The tax for the car should be 39 SEK.");
	    }
	    


}
