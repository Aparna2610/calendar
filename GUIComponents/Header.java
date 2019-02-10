/*
 * Header
 * Header class extends JPanel and constitute top white part of application. It
 * shows current date and scu logo on header
 * together all GUI components together.
 * 
 *  @author		Aparna Gangwar
 *  @version	1.0
 *  @since		2019-02-08
 */
package GUIComponents;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class Header extends JPanel{
	// UI and Color Scheme
	private Color backgroundColor;
	private Color headerTextColor;
	private Color headerSubTextColor;
	private Font headerTextFont;
	private Font headerSubTextFont;
	
	// State Variables
	private String month;
	private int year;
	private int date;
	
	// Components
	private JLabel headerText;
	private JLabel headerSubText;
	private JLabel scuImage;
	
	public Header() {
		// Initializing State Variables
		date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		month = "January";
		year = 2019;

		//Color Scheme
		backgroundColor = ColorPalette.PALETTE1;
		headerTextColor = ColorPalette.PALETTE2;
		headerSubTextColor = ColorPalette.PALETTE5;
		
		//Fonts
		headerTextFont = new Font("Helvetica Neue", Font.BOLD, 50);
		headerSubTextFont = new Font("Helvetica Neue", Font.PLAIN, 30);
		
		//Initializing Child Components
		headerText = new JLabel(month + ", ");
		headerSubText = new JLabel(String.valueOf(year));
		
		headerText.setFont(headerTextFont);
		headerSubText.setFont(headerSubTextFont);
		headerText.setForeground(headerTextColor);
		headerSubText.setForeground(headerSubTextColor);
		
		scuImage = new JLabel(new ImageIcon("SCU.png"));
	    scuImage.setSize(100, 100);
		
		// Configure Panel
		setBackground(backgroundColor);
		setLayout(null);
		add(headerText);
		add(headerSubText);
		add(scuImage);	
	}
	
	/*
	 * placeAndResizeComponents: Calculates size and location of child 
	 * components dynamically to ensure resizing flexibility
	 */
	private void placeAndResizeComponents() {
		Dimension d = getSize();
		
		//Location
		int lineHeight = headerText.getPreferredSize().height;
		int topPadding = (d.height - lineHeight)/2;
		int x = topPadding+50;
		int y = topPadding;
		
		headerText.setLocation(x, y);
		headerText.setSize(headerText.getPreferredSize());
		
		headerSubText.setSize(headerSubText.getPreferredSize().width,
				headerText.getPreferredSize().height - 5);
		headerSubText.setLocation(
				x + headerText.getPreferredSize().width,
				y);
		headerSubText.setVerticalAlignment(SwingConstants.BOTTOM);
		
		scuImage.setLocation(getSize().width - topPadding - 250, (getSize().height - 100)/2 );
		
	}
	
	/*
	 * setMonth: Sets the header text to the month string with date
	 */
	public void setMonth(String month) {
		this.month = month;
		//headerText.setText(date + " " + month + ", ");
		headerText.setText(month + ", ");
	}
	
	/*
	 * Overrides the superclass paint method to ensure dynamic recalculation of
	 * size and location of components before painting.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		placeAndResizeComponents();
	}
	
}
