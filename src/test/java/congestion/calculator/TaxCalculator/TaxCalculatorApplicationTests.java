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
	    void testGetTaxForCar() throws ParseException {
	        String[] timestamps = {
	            "2013-02-08 06:29:00",  // Expected tax: 8 SEK
	            "2013-02-08 06:30:27",  // Expected tax: 13 SEK
	            "2013-02-08 07:15:00"   // Expected tax: 18 SEK
	        };

	        int tax = taxCalculator.getTaxForVehicle("Car", timestamps);

	        assertEquals(18, tax, "The tax for the car should be 18 SEK.");
	    }

}
