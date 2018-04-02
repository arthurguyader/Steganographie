/**
 * 
 */
package pack_TI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Simple image viewer (automatic scaling of source image)
 * 
 * @author Pierre E. Chauvet
 *
 */
public class JImageViewer extends JPanel {

	private static final long serialVersionUID = 4128520194137988025L;
	
	private ImageIcon thumbnail = null;
	private int wi,hi;
	
	private Image image=null;
	
	private int margin = 2;
	

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
		wi=image.getWidth(null);
		hi=image.getHeight(null);
		repaint();
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	
    /**
	 * @param margin the margin to set
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

	/**
	 * @return the margin
	 */
	public int getMargin() {
		return margin;
	}

	protected void paintComponent(Graphics g) {
        if (image != null) {
        	int w=this.getWidth();
        	int h=this.getHeight();
        	float rw=(float)w / wi;
        	float rh=(float)h / hi;
        	if(rw<rh) {
            	thumbnail = new ImageIcon(image.getScaledInstance(w-2*margin, -1,Image.SCALE_DEFAULT));	
        	}
        	else {
            	thumbnail = new ImageIcon(image.getScaledInstance(-1, h-2*margin,Image.SCALE_DEFAULT));	
        	}
        	thumbnail.paintIcon(this, g, margin, margin);
        }
    }
	
}
