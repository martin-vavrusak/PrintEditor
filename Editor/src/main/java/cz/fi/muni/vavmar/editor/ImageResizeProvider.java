/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class ImageResizeProvider implements ResizeProvider{
    private static final Logger logger = LogManager.getLogger(ImageResizeProvider.class);
    
    public void resizingStarted(Widget widget) {
        logger.trace("");
    }

    public void resizingFinished(Widget widget) {
        logger.trace("");
    }
    
}
