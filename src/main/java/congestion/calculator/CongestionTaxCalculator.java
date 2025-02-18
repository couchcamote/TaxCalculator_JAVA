package congestion.calculator;

import java.util.*;

import org.springframework.stereotype.Service;

import java.text.*;


@Service
public class CongestionTaxCalculator {

    private static Map<String, Integer> tollFreeVehicles = new HashMap<>();

    //we can set this values from the application.yaml
    static {
        tollFreeVehicles.put("Motorcycle", 0);
        tollFreeVehicles.put("Tractor", 1);
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);

    }

    public static void main(String[] args) throws ParseException {
    	
    	//Test Timestamps
        String[] timestampStrings = {
            "2013-01-14 21:00:00",
            "2013-01-15 21:00:00",
            "2013-02-07 06:23:27",
            "2013-02-07 15:27:00",
            "2013-02-08 06:27:00",
            "2013-02-08 06:20:27",
            "2013-02-08 14:35:00",
            "2013-02-08 15:29:00",      //withinn 60 min
            "2013-02-08 15:47:00",      //withinn 60 min
            "2013-02-08 16:01:00",   
            "2013-02-08 16:48:00",
            "2013-02-08 17:49:00",
            "2013-02-08 18:29:00",      //withinn 60 min
            "2013-02-08 18:35:00",		//withinn 60 min	
            "2013-03-26 14:25:00",
            "2013-03-28 14:07:27"
        };
        
        //test specific dates
      //  String[] timestampStrings = {
           //   "2013-02-08 09:27:00",
             // "2013-02-08 06:20:27",
           //   "2013-02-08 14:35:00",
           // };	

    CongestionTaxCalculator taxCalculator = new CongestionTaxCalculator();
    taxCalculator.getTaxForVehicle("Car", timestampStrings);
    }
    
    public int getTaxForVehicle(String vehicle, String [] datesArray) throws ParseException {

       Vehicle vehicleObject = getVehicleInstance(vehicle);
       Date[] dates = parseDates(datesArray);
       
       int tax = getTax(vehicleObject, dates) ;
       System.out.println("tax value: "+ tax);
       
       return tax;
    }

    
    
    public int getTax(Vehicle vehicle, Date[] dates)
    {
    	
        Arrays.sort(dates); 

        Date intervalStart = dates[0];
        int totalFee = 0;

        for (int i = 0; i < dates.length ; i++) {
        	
            Date date = dates[i];
            int nextFee = GetTollFee(date, vehicle);
            
            int tempFee = GetTollFee(intervalStart, vehicle);

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies/1000/60;

            if (minutes <= 60)
            {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            }
            else
            {
                totalFee += nextFee;
            }

           
           //move on to next date as interalStart 
           intervalStart = date;
            
        }                
 
        if (totalFee > 60) totalFee = 60;

        return totalFee;
    }

    private boolean IsTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return tollFreeVehicles.containsKey(vehicleType);
    }

    /* 
    | Time        | Amount |
    | ----------- | :----: |
    | 06:00–06:29 | SEK 8  |
    | 06:30–06:59 | SEK 13 |
    | 07:00–07:59 | SEK 18 |
    | 08:00–08:29 | SEK 13 |
    | 08:30–14:59 | SEK 8  |
    | 15:00–15:29 | SEK 13 |
    | 15:30–16:59 | SEK 18 |
    | 17:00–17:59 | SEK 13 |
    | 18:00–18:29 | SEK 8  |
    | 18:30–05:59 | SEK 0  |
     */

    public int GetTollFee(Date date, Vehicle vehicle)
    {
        if (IsTollFreeDate(date) || IsTollFreeVehicle(vehicle)) return 0;

        int hour = date.getHours();
        int minute = date.getMinutes();

        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
      
        //else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;    // ?  how about 9:29? --> 08:30–14:59
        else if ((hour == 8 && minute >= 30) || (hour >= 9 && hour <= 14)) return 8;

        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        
        //else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18; // incorrect  --> 15:30–16:59 
        else if ((hour == 15 && minute >= 30) || (hour == 16 && minute <= 59)) return 18;
        
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }

    private Boolean IsTollFreeDate(Date date)
    {
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDay() + 1;
        int dayOfMonth = date.getDate();

        //weekends
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        
        //holidays
        if (year == 2013)
        {
            if ((month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31)))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Factory
     * @param vehicle
     * @return
     */
    private Vehicle getVehicleInstance(String vehicle) {
    	//We could add a factory for each vehicle type here
    	return new Car();
    }
    
    /**
     * Util
     * @param datesArray
     * @return
     * @throws ParseException
     */
	private Date[] parseDates(String[] datesArray) throws ParseException {
		Date dates[] = new Date[datesArray.length];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0 ;  i < datesArray.length; i++){
            dates[i] = formatter.parse(datesArray[i]);
        }
		return dates;
	}
}
