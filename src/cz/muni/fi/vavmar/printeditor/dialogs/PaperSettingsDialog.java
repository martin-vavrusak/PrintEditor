/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor.dialogs;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;
import cz.muni.fi.vavmar.printeditor.MainScene;
import cz.muni.fi.vavmar.printeditor.PaperSettings;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JDialog;
import javax.swing.ListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaperSettingsDialog extends JDialog {
    protected static final Logger logger = LogManager.getLogger( PaperSettingsJPanel.class );

    private MainScene mainScene;
    private DBManager dataProvider;
    private PaperSettings selectedPaper = null;
    
    public PaperSettingsDialog(MainScene mainScene) {
        this.mainScene = mainScene;
        this.dataProvider = mainScene.getDataProvider();
        setModal(true);
        
        add(new PaperSettingsJPanel());
        
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2, screenSize.height / 2, 550, 455);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public PaperSettings getSelectedPaper() {
        return selectedPaper;
    }
    
    
    
    private static class PaperSettingsWrapper{
        private PaperSettings paperSettings;

        public PaperSettingsWrapper(PaperSettings paperSettings) {
            this.paperSettings = paperSettings;
        }

        public PaperSettings getPaperSettings() {
            return paperSettings;
        }

        public void setPaperSettings(PaperSettings paperSettings) {
            this.paperSettings = paperSettings;
        }

        @Override
        public String toString() {
            return paperSettings.getName() + " [" + paperSettings.getCode() + "]";
        }
        
        public static ListModel<PaperSettingsWrapper> createModel( final List<PaperSettings> paperSettings ){
            final List<PaperSettingsWrapper> papers = new ArrayList<PaperSettingsWrapper>();
            
            for(PaperSettings p: paperSettings){
                papers.add( new PaperSettingsWrapper(p) );
            }
            
            return new AbstractListModel<PaperSettingsWrapper>() {

                @Override
                public int getSize() {
                    return papers.size();
                }

                @Override
                public PaperSettingsWrapper getElementAt(int index) {
                    return papers.get(index);
                }
            };
        }
    }
/**
 *
 * @author Martin
 */
private class PaperSettingsJPanel extends javax.swing.JPanel {
    
    
    /**
     * Creates new form PaperSettingsDialog
     */
    public PaperSettingsJPanel() {
        initComponents();
    }

    private void setPaper(PaperSettings paper){
        landscapeCheckBox.setSelected( paper.isIsLandscape() );
        widthTextField.setText( Integer.toString( (int) paper.getWidth() ) );
        heightTextField.setText( Integer.toString( (int) paper.getHeight() ));
        
        leftMarginTextField.setText( Integer.toString( (int) paper.getLeftMargin() ));
        rightMarginTextField.setText( Integer.toString( (int) paper.getRightMargin() ));
        topMarginTextField.setText( Integer.toString( (int) paper.getTopMargin() ));
        bottomMarginTextField.setText( Integer.toString( (int) paper.getBottomMargin() ));
        
        descriptionTextField.setText( paper.getDescription() );
    }
    
    private void enableFields(boolean enable){

            landscapeCheckBox.setEnabled(enable);
            widthTextField.setEnabled(enable);
            heightTextField.setEnabled(enable);

            leftMarginTextField.setEnabled(enable);
            rightMarginTextField.setEnabled(enable);
            topMarginTextField.setEnabled(enable);
            bottomMarginTextField.setEnabled(enable);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        magrinsPanel = new javax.swing.JPanel();
        bottomMarginTextField = new javax.swing.JTextField();
        bottomMarginLabel = new javax.swing.JLabel();
        topMarginTextField = new javax.swing.JTextField();
        topMarginLabel = new javax.swing.JLabel();
        marginsLabel = new javax.swing.JLabel();
        rightMarginTextField = new javax.swing.JTextField();
        leftMarginTextField = new javax.swing.JTextField();
        rightMarginLabel = new javax.swing.JLabel();
        leftMarginLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        paperWidthLabel = new javax.swing.JLabel();
        heightLabel = new javax.swing.JLabel();
        widthTextField = new javax.swing.JTextField();
        heightTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        landscapeCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        descriptionPanel = new javax.swing.JPanel();
        descriptionTextField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();

        bottomMarginTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.bottomMarginTextField.text")); // NOI18N
        bottomMarginTextField.setEnabled(false);

        bottomMarginLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.bottomMarginLabel.text")); // NOI18N

        topMarginTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.topMarginTextField.text")); // NOI18N
        topMarginTextField.setEnabled(false);

        topMarginLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.topMarginLabel.text")); // NOI18N

        marginsLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.marginsLabel.text")); // NOI18N

        rightMarginTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.rightMarginTextField.text")); // NOI18N
        rightMarginTextField.setEnabled(false);

        leftMarginTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.leftMarginTextField.text")); // NOI18N
        leftMarginTextField.setEnabled(false);

        rightMarginLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.rightMarginLabel.text")); // NOI18N

        leftMarginLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.leftMarginLabel.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout magrinsPanelLayout = new javax.swing.GroupLayout(magrinsPanel);
        magrinsPanel.setLayout(magrinsPanelLayout);
        magrinsPanelLayout.setHorizontalGroup(
            magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(magrinsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(magrinsPanelLayout.createSequentialGroup()
                        .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(magrinsPanelLayout.createSequentialGroup()
                                .addComponent(rightMarginLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightMarginTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                            .addGroup(magrinsPanelLayout.createSequentialGroup()
                                .addComponent(leftMarginLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(leftMarginTextField)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bottomMarginLabel)
                            .addComponent(topMarginLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(topMarginTextField)
                            .addComponent(bottomMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addComponent(marginsLabel))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        magrinsPanelLayout.setVerticalGroup(
            magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(magrinsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(marginsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(leftMarginLabel)
                    .addComponent(leftMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(topMarginLabel)
                    .addComponent(topMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(magrinsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rightMarginLabel)
                    .addComponent(rightMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bottomMarginLabel)
                    .addComponent(bottomMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        paperWidthLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.paperWidthLabel.text")); // NOI18N

        heightLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.heightLabel.text")); // NOI18N

        widthTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.widthTextField.text")); // NOI18N
        widthTextField.setEnabled(false);

        heightTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.heightTextField.text")); // NOI18N
        heightTextField.setEnabled(false);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.jLabel5.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.jLabel6.text")); // NOI18N

        landscapeCheckBox.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.landscapeCheckBox.text")); // NOI18N
        landscapeCheckBox.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(heightLabel)
                    .addComponent(paperWidthLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(heightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(landscapeCheckBox))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paperWidthLabel)
                    .addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(landscapeCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heightLabel)
                    .addComponent(heightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jList1.setModel( PaperSettingsWrapper.createModel( dataProvider.getAvailablePapers() ) );
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        okButton.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        descriptionTextField.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.descriptionTextField.text")); // NOI18N
        descriptionTextField.setEnabled(false);

        descriptionLabel.setText(org.openide.util.NbBundle.getMessage(PaperSettingsDialog.class, "PaperSettingsDialog.descriptionLabel.text")); // NOI18N

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel)
                    .addComponent(descriptionTextField))
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(magrinsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(magrinsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(okButton)))
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased
        selectedPaper = ((PaperSettingsWrapper) jList1.getSelectedValue()).getPaperSettings();
        setPaper( selectedPaper );
    }//GEN-LAST:event_jList1MouseReleased

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        selectedPaper = null;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bottomMarginLabel;
    private javax.swing.JTextField bottomMarginTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox landscapeCheckBox;
    private javax.swing.JLabel leftMarginLabel;
    private javax.swing.JTextField leftMarginTextField;
    private javax.swing.JPanel magrinsPanel;
    private javax.swing.JLabel marginsLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel paperWidthLabel;
    private javax.swing.JLabel rightMarginLabel;
    private javax.swing.JTextField rightMarginTextField;
    private javax.swing.JLabel topMarginLabel;
    private javax.swing.JTextField topMarginTextField;
    private javax.swing.JTextField widthTextField;
    // End of variables declaration//GEN-END:variables
}

}