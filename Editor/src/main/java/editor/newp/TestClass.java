/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editor.newp;

import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Martin
 */
public class TestClass extends JFrame {
    private MainScene1 mainScene = new MainScene1();
    private JPanel mainScenePanel = new JPanel();

    public TestClass() {
      
        mainScenePanel.setSize(600, 600);
        mainScenePanel.add(mainScene.getSceneEnvelope());
        add(mainScenePanel);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestClass().setVisible(true);
            }
        });
    }
    
    
}
