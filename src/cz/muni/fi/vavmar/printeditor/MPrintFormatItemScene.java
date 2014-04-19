package cz.muni.fi.vavmar.printeditor;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;

import org.compiere.print.MPrintFont;
import org.compiere.print.MPrintFormatItem;

/**
 * Class wrapping {@link MPrintFormatItem} used for resolving relative position.
 * 
 * @author Martin
 *
 */
public class MPrintFormatItemScene {
	private int resolvedPositionX = 0;		//position based on predecessor resolvedWidth and this item settings
	private int resolvedPositionY = 0;
	private int resolvedWidth = 0;			//computed width of item or width set by user if set
	private int resolvedHeight = 0;
	MPrintFormatItem item;
	
	public MPrintFormatItemScene(MPrintFormatItem item) {
		this.item = item;
	}
	
	public MPrintFormatItem getMPrintFormatItem(){
		return this.item;
	}
	
	public int getResolvedPositionX() {
		return resolvedPositionX;
	}
	
	public int getResolvedPositionY() {
		return resolvedPositionY;
	}
	
	/**
	 * Resolve
	 * 
	 * @param predecessor valid resolved predecessor of this item. If null then absolute position is used.
	 */
	public void resolvePosition(MPrintFormatItemScene predecessor){
		if(item.isNextLine()){	//ignore X position
			
		}
	}
	
	public void resolveWidthAndHeight(){
		
		int itemWidth = item.getMaxWidth();
		int itemHeight = item.getMaxHeight();
		
		//Sirka a delka je vzdycky nejsilnejsi!! takze pokud je nastaveno uz se nic neresi
		if(itemHeight > 0 || itemWidth > 0){
			resolvedHeight = itemHeight;
			resolvedWidth = itemWidth;
			
		} else {

			if ( item.isTypeText() && !item.isHeightOneLine() ){	//pokud není nastaveno sirka a delka a neni zaskrtnuto "One Line Only"
				resolveTextParameters();							//je potreba vypocitat vysku a delku textu
			}
			//jinak neber sirku v potaz 
		}
	}
	
	/**
	 * Resolve width and height of text based on font and text string;
	 */
	private void resolveTextParameters(){
		//vypocitej hodnoty
//				FontMetrics fontMetrics = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(new Font("Font", Font.BOLD, 10));
//				fontMetrics.getHeight();
		
		String text = item.getPrintName();
		Font font = MPrintFont.get(item.getAD_PrintFont_ID()).getFont();
				
//				Font f = new Font("Dialog", Font.BOLD, 10);
				FontRenderContext frc = new FontRenderContext(null, true, true);
				
//				LineMetrics lm = f.getLineMetrics("Test String", frc);
//				lm.getHeight();
				
			TextLayout tl = new TextLayout(text, font, frc);
			resolvedHeight = (int) tl.getBounds().getHeight();
			resolvedWidth = (int) tl.getBounds().getWidth();
	}
}
