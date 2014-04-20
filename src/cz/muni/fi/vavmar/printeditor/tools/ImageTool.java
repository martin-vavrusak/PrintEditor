/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.tools;

import static cz.muni.fi.vavmar.printeditor.tools.AbstractTool.logger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import org.apache.logging.log4j.LogManager;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;

/**
 *
 * @author Martin
 */
public class ImageTool extends AbstractTool {

	private static final long serialVersionUID = -1455669078896303373L;

	public ImageTool(Icon icon) {
        super(icon);
        logger = LogManager.getLogger(ImageTool.class.getName());    //inherited logger
    }

    public ImageTool(String text) {
        super(text);
        logger = LogManager.getLogger(ImageTool.class.getName());    //inherited logger
    }
    
    public ImageTool(String text, String iconPath) {
        super(text, iconPath);
        logger = LogManager.getLogger(ImageTool.class.getName());    //inherited logger
    }
    
    @Override
    public Widget createWidget(Scene scene) {
        ImageWidget iw = null;
        
        final JFileChooser fc = new JFileChooser();
        int retValue = fc.showDialog(this, "Open");
        logger.trace("Value from filechooser: " + retValue);
        
        if(retValue == JFileChooser.APPROVE_OPTION){
            iw = new ImageWidget(scene);
            File file = fc.getSelectedFile();
            logger.trace("File selected: " + file);
            
            String filePath;
            try {
                filePath = file.getCanonicalPath();
            } catch (IOException ex) {
                logger.error(ex);
                return null;
            }
            
            logger.trace("Canonical file Path: " + filePath);
            
            int lastOcurrence = filePath.lastIndexOf(".");
            
            String extesion;
            if(lastOcurrence + 1 < filePath.length()){
                extesion = filePath.substring(lastOcurrence + 1);
                logger.trace("Extension: " + extesion);
            } else {
                logger.warn("Extension missing or not recognized: " + filePath);
                return null;
            }
            
                        
            if(lastOcurrence > 0 && (extesion.equalsIgnoreCase("JPEG") 
                                       || extesion.equalsIgnoreCase("JPG") 
                                       || extesion.equalsIgnoreCase("PNG") 
                                       || extesion.equalsIgnoreCase("GIF") 
                                       || extesion.equalsIgnoreCase("BMP")) ) {
                
                BufferedImage bi = null;
                try {
                    bi = ImageIO.read(file);
                } catch (IOException ex) {
                    logger.error(ex);
                    return null;
                }
                iw.setImage(bi);
                iw.setOpaque(true);
                
//                iw.setBorder(BorderFactory.createResizeBorder(7));
//                iw.getActions().addAction(ActionFactory.createResizeAction());
            } else {
                logger.warn("extension missing or not recognized: " + filePath);
            }
            
        }
        
        
        return iw;
    }
    
}
