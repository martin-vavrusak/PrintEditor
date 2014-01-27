/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.sidepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;


/**
 * Trida reprezentujici panel na obrazky
 * 
 * @author Martin
 */

public class MyIconImage extends JComponent {

    private Image image;
    private Dimension dim = new Dimension(128, 128);
    
    public MyIconImage() {
        this(null);
    }
    
    public MyIconImage(Image image) {        
        setOpaque(true);
        
        if( image == null ){
            this.image = createDefaultImage(this.dim);
        } else {
            this.image = image;
        }

        setSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
        setLayout(null);
    }
    
    
    public static Image createDefaultImage(Dimension dim){
        int r = 0;
        int g = 0;
        int b = 0;
        
        BufferedImage bi = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_BGR);
        int widthIncrease = (255 / dim.width) <= 0 ? 1 : (255 / dim.width);
        int heightIncrease = (255 / dim.height) <= 0 ? 1 : (255 / dim.height);
        
        for(int i = 0; i < dim.width; i++){    //Sirka obrazku
            for (int j = 0; j < dim.height; j++){    //Delka obrazku
                bi.setRGB(i, j, getRGBA(r, g, b, 0) );
                r += r > 255 ? 255 : heightIncrease ; //zabrani preteceni
            }
            r = 0;
            b += b > 255 ? 255 : widthIncrease ;
            
        }
        
        return bi;
    }
    
    private static int getRGBA (int r, int g, int b, int a){
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
    
}
