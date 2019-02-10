
/*
 * Calendar.java
 * Calendar is main class and initializes CalendarGUI
 * 
 *  @author		Aparna Gangwar
 *  @version	1.0
 *  @since		2019-02-08
 */
public class Calendar {
	private CalendarGUI mainFrame;
	
	Calendar() {
		mainFrame = new CalendarGUI();

		// Mark Holidays
		mainFrame.setHoliday(1, 1, "New Year’s Day");
		mainFrame.setHoliday(21, 1, "Birthday of Martin Luther King, Jr.");
		mainFrame.setHoliday(18, 2, "Washington’s Birthday");
		mainFrame.setHoliday(27, 3, "Memorial Day");
		mainFrame.setHoliday(4, 7, "Independence Day");
		mainFrame.setHoliday(2, 9, "Labor Day");
		mainFrame.setHoliday(14, 10, "Columbus Day");
		mainFrame.setHoliday(11, 11, "Veterans Day");
		mainFrame.setHoliday(28, 11, "Thanksgiving Day");
		mainFrame.setHoliday(25, 12, "Christmas Day");
		
		// Show mainFrame
		mainFrame.setVisible(true);
	}
	
	/*
	 *  Main: Entry point of application
	 */
	public static void main(String args[]) {
		new Calendar();
	}
}
