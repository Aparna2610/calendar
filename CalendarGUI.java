/*
 * CalendarGUI
 * CalendarGUI implements GUI of the application and responsible for putting
 * together all GUI components together.
 * 
 *  @author		Aparna Gangwar
 *  @version	1.0
 *  @since		2019-02-08
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import GUIComponents.*;

public class CalendarGUI extends JFrame {
	private String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	private Dimension dimension;
	private Dimension minDimension;
	
	private JPanel contentPanel;
	
	//Custom Components
	private JScrollPane scrollPane;
	private SidePanel sidePanel;
	private Header headerPanel;
	private CalendarPanel calendarPanel;
	
	//Constructor
	public CalendarGUI() {
		
		// Initialize State Configuration for JFrame
		
		//Set Default Dimension of Application window
		dimension = getSize();
		dimension.height = 800;
		dimension.width = 1000;
		
		//Set Minimum Dimension of Application window, resizing below this 
		//threshold will activate scroll bars
		minDimension = new Dimension();
		minDimension.height = 640;
		minDimension.width = 800;
		
		//Primary Container
		contentPanel = new JPanel();
		
		//Scroll Container that holds primary container
		scrollPane = new JScrollPane(contentPanel);
		
		// Configuration
		setSize(dimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPanel.setPreferredSize(minDimension);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setBorder(null); //By default it has a border. Not required.
		
		getContentPane().add(scrollPane);
		
		// Initialize Components
		initializeChildComponents();
		
		// Initialize Action Listener
		
		// To keep all three panel in sync of currently active month
		ActionListener al = new ActionListener() {
			  @Override
		      public void actionPerformed(ActionEvent evt) {
				  //System.out.println(dateTable.getVisibleRect());
				  Rectangle visRect = calendarPanel.getTableVisibleRect();
				  int targetY = visRect.y + visRect.height/2;
				  DateCell dc = (DateCell)calendarPanel.getComponentAt(0,targetY);
				  //System.out.println(visRect);
				  if(dc != null) {
					  calendarPanel.setActiveMonth(dc.getMonth());
					  headerPanel.setMonth(monthNames[dc.getMonth()-1]);
					  sidePanel.setActiveMonth(monthNames[dc.getMonth()-1].substring(0,3));
				  }
		      }
		  };
		javax.swing.Timer tm = new javax.swing.Timer(5,al);
		tm.start();
		
		// Adding click functionality to sidepanel options
		sidePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
		        int month = sidePanel.getClickedMonth(me.getComponent());
		        calendarPanel.setActiveMonth(month);
		        headerPanel.setMonth(monthNames[month-1]);
				sidePanel.setActiveMonth(monthNames[month-1].substring(0,3));
				calendarPanel.scrollToMonth(month);
		      }
		});
		
		// Set Todays Date on Calendar
		//setTodaysDate();
	}
	
	/*
	 * initializeChildComponents: Initialize sub panel of GUI and add it to 
	 * primary container
	 */
	private void initializeChildComponents() {
		//Header: Shows date on top
		headerPanel = new Header();
		
		//Side Panel: Shows list of months
		sidePanel = new SidePanel();
		
		//Calendar Panel: Shows calendar table
		calendarPanel = new CalendarPanel();
		
		placeAndResizeComponents();
		
		contentPanel.setLayout(null); //Preferred to place by coordinates
		contentPanel.add(headerPanel);
		contentPanel.add(sidePanel);
		contentPanel.add(calendarPanel);
	}
	
	/*
	 * placeAndResizeComponents: Calculates size and location of child 
	 * components dynamically to ensure resizing flexibility
	 */
	private void placeAndResizeComponents() {
		Dimension viewPortDimension = contentPanel.getSize();
		int sidePanelWidth = 150;
		int headerPanelHeight = 150;
		
		sidePanel.setLocation(0,0);
		sidePanel.setSize(sidePanelWidth, viewPortDimension.height);
		
		headerPanel.setLocation(sidePanelWidth,0);
		headerPanel.setSize(viewPortDimension.width, headerPanelHeight);
		
		calendarPanel.setLocation(sidePanelWidth, headerPanelHeight);
		calendarPanel.setSize(
				viewPortDimension.width - sidePanelWidth, 
				viewPortDimension.height - headerPanelHeight
				);
	}
	
	/*
	 * setHoliday: Put holiday name on specific date of the month
	 */
	public void setHoliday(int date, int month, String text) {
		calendarPanel.setHoliday(date, month, text);
	}
	
	/*
	 * setTodaysDate: invoke child components method to sync today's date among
	 * it
	 */
	public void setTodaysDate() {
		//String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		String currentMonthName = monthNames[currentMonth];
		sidePanel.setActiveMonth(currentMonthName.substring(0, 3));
		headerPanel.setMonth(currentMonthName);
	}
	
	/*
	 * Overrides the superclass paint method to ensure dynamic recalculation of
	 * size and location of components before painting.
	 */
	public void paint(Graphics g) {
		placeAndResizeComponents();
		super.paint(g);
	}
	
}
