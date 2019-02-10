/*
 * DateCell
 * DateCell is individual customized cell of tabular structure
 * 
 *  @author		Aparna Gangwar
 *  @version	1.0
 *  @since		2019-02-08
 */
package GUIComponents;

import javax.swing.*;
import java.awt.*;

public class DateCell extends JPanel{
	//State variables
	private int month;
	private int padding;
	private boolean isFirstDay;
	private boolean isHighlighted;
	private Font dateFont;
	private Font holidayFont;
	private boolean isActiveMonth;
	
	private JLabel dateLabel;
	private JTextArea holidayLabel;
	
	//Constructor for empty cells
	public DateCell() {
		this(1,1);
		dateLabel.setText("");
		isFirstDay = false;
		isHighlighted = false;
	}
	
	//Constructor for cells with date
	public DateCell(int date, int month) {
		
		// Initialize State Variable
		this.month = month;
		dateFont = new Font("Helvetica Neue", Font.PLAIN, 20);
		holidayFont = new Font("Helvetica Neue", Font.BOLD, 12);
		isFirstDay = (date == 1);
		
		// Initialize Components
		dateLabel = new JLabel(String.valueOf(date));
		if(isFirstDay) {
			String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
			dateLabel.setText(String.valueOf(date) + " " + monthNames[month-1]);
		}
		dateLabel.setForeground(ColorPalette.PALETTE5);
		dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dateLabel.setFont(dateFont);
		
		add(dateLabel);
		setOpaque(false); //To avoid default gray color
		setLayout(null);
	}
	
	// setPadding: sets the padding of inner text of cell
	public void setPadding(int padding) {
		this.padding = padding;
		placeAndResizeComponent();
	}
	
	// setHighlighted: highlight cell with thick colored border
	public void setHighlighted(boolean highlighted) {
		this.isHighlighted = highlighted;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	// setActiveMonth: set flag true to show on screen month as active
	public void setActiveMonth(boolean val) {
		if(val != this.isActiveMonth) {
			this.isActiveMonth = val;
			if(val) {
				dateLabel.setForeground(ColorPalette.PALETTE2);
			} else {
				dateLabel.setForeground(ColorPalette.PALETTE5);
			}
		}
	}
	// setHoliday: set Holiday text on the cell
	public void setHoliday(String text) {
		if(holidayLabel == null) {
			holidayLabel  = new JTextArea(2, 10);
			holidayLabel.setText(text);
			holidayLabel.setWrapStyleWord(true);
			holidayLabel.setLineWrap(true);
			holidayLabel.setOpaque(false);
			holidayLabel.setEditable(false);
			holidayLabel.setFocusable(false);
		    holidayLabel.setForeground(ColorPalette.PALETTE3);
			holidayLabel.setFont(holidayFont);
			holidayLabel.setSize(holidayLabel.getPreferredSize());
			add(holidayLabel);
		}
		placeAndResizeComponent();
	}
	
	/*
	 * placeAndResizeComponents: Calculates size and location of child 
	 * components dynamically to ensure resizing flexibility
	 */
	private void placeAndResizeComponent() {
		dateLabel.setSize(dateLabel.getPreferredSize());
		dateLabel.setLocation(padding,padding);
		if(holidayLabel != null) {
			holidayLabel.setSize(getSize().width-padding,getSize().height);
			holidayLabel.setLocation(padding, padding + dateLabel.getSize().height);
		}
	}
	
	/*
	 * Overrides the superclass paint method to ensure dynamic recalculation of
	 * size and location of components before painting.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if(isFirstDay) {
			g.setColor(ColorPalette.PALETTE7);
			g.fillRect(0, 0, getSize().width, getSize().height);
		}
		
		Stroke oldStroke = g2d.getStroke();
		if(isHighlighted) {
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(ColorPalette.PALETTE3);
		} else {
			g2d.setColor(ColorPalette.PALETTE5);
		}
		g2d.drawRect(0, 0, getSize().width, getSize().height);
		g2d.setStroke(oldStroke);
		
		placeAndResizeComponent();
		super.paintChildren(g);
	}
}
