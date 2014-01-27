/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.sidepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Martin
 */
public class MyIcon extends JLabel {

    public MyIcon(String text, Image img, Object tool) {
    }

    
    
    private MyIconImage iconImage;
    private JLabel label;
    private Dimension dim;
    
//    public MyIcon() {
//        this(null, "DefaultLabel");
//    }
    
    public MyIcon(Image image, String labelText) {
        //vytvorit iconimage a zavolat dalsi konstruktor
        this(new MyIconImage(image), labelText);
    }
            
    public MyIcon(MyIconImage image, String labelText) {        
        setOpaque(true);
        labelText = labelText == null ? "Default label text" : labelText ;
        
        this.iconImage = image;
        this.label = new JLabel(labelText);
        
        dim = new Dimension(iconImage.getWidth(), (iconImage.getHeight() + (int) label.getPreferredSize().getHeight()) );
        System.out.println("Dimension: " + dim);
        System.out.println("icon width: " + iconImage.getWidth() 
                            +  " icon height: " + iconImage.getHeight()
                            + " label height: " + label.getPreferredSize().getHeight());
        setSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new FlowLayout());
        add(iconImage);
        add(label);
    }
    
    
//    private Image createDefaultImage(){
//        int r = 0;
//        int g = 0;
//        int b = 0;
//        
//        BufferedImage bi = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_BGR);
//        int widthIncrease = (255 / dim.width) <= 0 ? 1 : (255 / dim.width);
//        int heightIncrease = (255 / dim.height) <= 0 ? 1 : (255 / dim.height);
//        
//        for(int i = 0; i < dim.width; i++){    //Sirka obrazku
//            for (int j = 0; j < dim.height; j++){    //Delka obrazku
//                bi.setRGB(i, j, getRGBA(r, g, b, 0) );
//                r += r > 255 ? 255 : heightIncrease ; //zabrani preteceni
//            }
//            r = 0;
//            b += b > 255 ? 255 : widthIncrease ;
//            
//        }
//        
//        return bi;
//    }
//    
//    private int getRGBA (int r, int g, int b, int a){
//        return (a << 24) | (r << 16) | (g << 8) | b;
//    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(image, 0, 0, null);
//    }
    
    
}
