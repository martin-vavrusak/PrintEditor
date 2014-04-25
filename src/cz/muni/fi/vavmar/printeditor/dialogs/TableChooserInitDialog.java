/**
 *
 * @author Martin Vavrusak
 * @mail vavmar@gmail.com
 */

package cz.muni.fi.vavmar.printeditor.dialogs;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;
import cz.muni.fi.vavmar.printeditor.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TableChooserInitDialog extends JDialog {
	protected static final Logger logger = LogManager.getLogger( TableChooserInitDialog.class );
    
    protected DBManager dataProvider;
    private String selectedTable = null;
    private String selectedPrintFormatName;
    private int selectedPrintFormatID;
    private boolean isNewFormat = false;
    
    public TableChooserInitDialog(DBManager dbManager, boolean isSubreportSelection) {
        dataProvider = dbManager;
        setModal(true);
        
        add(new TableChooserJPanel( isSubreportSelection ));
        
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2, screenSize.height / 2, 490, 455);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Return name of selected table.
     * 
     * @return name of selected table
     */
    public String getSelectedTable(){
        return selectedTable;
    }
    
    /**
     * Return id of selected print format or -1 otherwise.
     * 
     * @return id of selected print format or -1 otherwise
     */
    public int getSelectedPrintFormatID() {
            return selectedPrintFormatID;
	}
    
    public String getSelectedPrintFormatName(){
        return selectedPrintFormatName;
    }
    
    public boolean isNewFormat(){
        return this.isNewFormat;
    }
    /**
     * Temporary class used for passing data to list for table format selection.
     * Allows to easily get ID and name of selected print format.
     * 
     * @author Martin
     *
     */
    private static class PrintFormat {
        private int id;
        private String name;

        public PrintFormat(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return id + " " + name;
        }
        
        /**
         * Creates populated ListModel which could be used for JList.
         * 
         * @param map map containing ID and name of obtainable print format.
         * 
         * @return populated ListModel which could be used for JList
         */
        public static ListModel<PrintFormat> createListModel(final Map<Integer, String> map){
            final List<PrintFormat> printFormats = new ArrayList<PrintFormat>();
            
            for(Entry<Integer, String> pf : map.entrySet()){
                printFormats.add(new PrintFormat(pf.getKey(), pf.getValue()));
            }
            
            return new AbstractListModel<PrintFormat>() {

                @Override
                public int getSize() {
                    return printFormats.size();
                }

                @Override
                public PrintFormat getElementAt(int index) {
                    return printFormats.get(index);
                }
            };
        }
    }
    
private class TableChooserJPanel extends javax.swing.JPanel {
    private List<String> searchList;
    private Map<Integer, String> searchReportMap;
    
    /**
     * Creates new form JPanelExtended
     */
    public TableChooserJPanel( boolean isSubreportSelection ) {
        initComponents();
        if(isSubreportSelection){
            createNewPrintFormatButton.setVisible(false);
//            createNewPrintFormatButton.setEnabled(false);
//            revalidate();
//            repaint();
            
            //initialize list for print format (subreport) selection
            searchReportMap = dataProvider.getPrintFormats(-1, true);       //get all reports with standard header
            availablePrintFormats.setModel( PrintFormat.createListModel(searchReportMap) );
            
            //disable List for chosing table, its search box and view selectinon check box
            availableTablesAndVievsList.setEnabled(false);
            searchTableTextField.setEnabled(false);
            viewRestrictionCheckbox.setEnabled(false);
            
            //select Only print formats check box
            searchOnlyReportsCheckBox.setSelected(true);
            
            //select Simple print formats check box
            simpleFormatsOnlyCheckBox.setSelected(true);
        }
    }
    
    private List<String> search(List<String> original, String stringToFind){
        System.out.println("Searching for: " + stringToFind);
        
        if(original != null){
             List<String> list = new ArrayList<String>();
             List<String> listInnerMatch = new ArrayList<String>();
        
        for(String t: original){
                if(t.length() < stringToFind.length()) continue;

                //compare first s.lenght() characters
                if( t.substring(0, stringToFind.length()).equalsIgnoreCase( stringToFind ) ){
                    list.add(t);
  
                } //If there is a match of "s" (searched string) anywhere as substring
                else if( t.toLowerCase().contains( stringToFind.toLowerCase() ) ){ 
                    
                    listInnerMatch.add(t);
                }
            }
            
            list.addAll(listInnerMatch);
            return list;
            }
        
        return null;
    }
    
    private Map<Integer, String> searchReport(Map<Integer, String> searchMap, String stringToFind){
        
        if(searchMap != null){
            Map<Integer, String> resultMap = new HashMap<>();
             Map<Integer, String> innerMatch = new HashMap<>();
             
            for(Entry<Integer, String> report: searchMap.entrySet()){
                String s = report.getValue();
                if(s.length() < stringToFind.length()) continue;

                //compare first s.lenght() characters
                if( s.substring(0, stringToFind.length()).equalsIgnoreCase( stringToFind ) ){
                    resultMap.put(report.getKey(), report.getValue());
  
                } //If there is a match of "s" (searched string) anywhere as substring
                else if( s.toLowerCase().contains( stringToFind.toLowerCase() ) ){ 
                    
                    innerMatch.put(report.getKey(), report.getValue());
                }
            }
            resultMap.putAll(innerMatch);
            return resultMap;
        }
        return null;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        credentialsPanel = new javax.swing.JPanel();
        roleTitle = new javax.swing.JLabel();
        userTitle = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        viewRestrictionCheckbox = new javax.swing.JCheckBox();
        availableTablesAndVievsListSrcollPane = new javax.swing.JScrollPane();
        availableTablesAndVievsList = new javax.swing.JList();
        searchTableTextField = new javax.swing.JTextField();
        buttonOk = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        availablePrintFormats = new javax.swing.JList();
        createNewPrintFormatButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        searchOnlyReportsCheckBox = new javax.swing.JCheckBox();
        simpleFormatsOnlyCheckBox = new javax.swing.JCheckBox();
        searchReportTextField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        credentialsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        roleTitle.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.roleTitle.text")); // NOI18N

        userTitle.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.userTitle.text")); // NOI18N

        userLabel.setBackground(new java.awt.Color(255, 255, 255));
        userLabel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.userLabel.text")); // NOI18N
        userLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        userLabel.setMaximumSize(new java.awt.Dimension(30, 14));
        userLabel.setMinimumSize(new java.awt.Dimension(30, 14));
        userLabel.setOpaque(true);
        userLabel.setPreferredSize(new java.awt.Dimension(30, 14));

        roleLabel.setBackground(new java.awt.Color(255, 255, 255));
        roleLabel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.roleLabel.text")); // NOI18N
        roleLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        roleLabel.setMaximumSize(new java.awt.Dimension(30, 14));
        roleLabel.setMinimumSize(new java.awt.Dimension(30, 14));
        roleLabel.setOpaque(true);
        roleLabel.setPreferredSize(new java.awt.Dimension(30, 14));

        javax.swing.GroupLayout credentialsPanelLayout = new javax.swing.GroupLayout(credentialsPanel);
        credentialsPanel.setLayout(credentialsPanelLayout);
        credentialsPanelLayout.setHorizontalGroup(
            credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(credentialsPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userTitle)
                    .addComponent(roleTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        credentialsPanelLayout.setVerticalGroup(
            credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, credentialsPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roleTitle))
                .addGap(5, 5, 5))
        );

        userLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.userLabel.AccessibleContext.accessibleName")); // NOI18N

        titleLabel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.titleLabel.text")); // NOI18N

        viewRestrictionCheckbox.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.viewRestrictionCheckbox.text")); // NOI18N
        viewRestrictionCheckbox.setToolTipText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.viewRestrictionCheckbox.toolTipText")); // NOI18N
        viewRestrictionCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewRestrictionCheckboxItemStateChanged(evt);
            }
        });

        List<String> tablesList = dataProvider.getTables(null);
        availableTablesAndVievsList.setModel(Utils.createListModel( tablesList ));
        availableTablesAndVievsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        availableTablesAndVievsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                availableTablesAndVievsListMouseClicked(evt);
            }
        });
        availableTablesAndVievsListSrcollPane.setViewportView(availableTablesAndVievsList);

        searchTableTextField.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.searchTableTextField.text")); // NOI18N
        searchTableTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTableTextFieldFocusGained(evt);
            }
        });
        searchTableTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTableTextFieldKeyReleased(evt);
            }
        });

        buttonOk.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.buttonOk.text")); // NOI18N
        buttonOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonOkMouseReleased(evt);
            }
        });

        buttonCancel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.buttonCancel.text")); // NOI18N
        buttonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCancelMouseClicked(evt);
            }
        });

        availablePrintFormats.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(availablePrintFormats);

        createNewPrintFormatButton.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.createNewPrintFormatButton.text")); // NOI18N
        createNewPrintFormatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                createNewPrintFormatButtonMouseReleased(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.jLabel2.text")); // NOI18N

        searchOnlyReportsCheckBox.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.searchOnlyReportsCheckBox.text")); // NOI18N
        searchOnlyReportsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.searchOnlyReportsCheckBox.toolTipText")); // NOI18N
        searchOnlyReportsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchOnlyReportsCheckBoxItemStateChanged(evt);
            }
        });

        simpleFormatsOnlyCheckBox.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.simpleFormatsOnlyCheckBox.text")); // NOI18N
        simpleFormatsOnlyCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.simpleFormatsOnlyCheckBox.toolTipText")); // NOI18N
        simpleFormatsOnlyCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                simpleFormatsOnlyCheckBoxItemStateChanged(evt);
            }
        });

        searchReportTextField.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.searchReportTextField.text")); // NOI18N
        searchReportTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchReportTextFieldFocusGained(evt);
            }
        });
        searchReportTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchReportTextFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(createNewPrintFormatButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(searchTableTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(availableTablesAndVievsListSrcollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(viewRestrictionCheckbox)
                                .addGap(18, 18, 18)
                                .addComponent(searchOnlyReportsCheckBox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 92, Short.MAX_VALUE)
                        .addComponent(buttonOk)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCancel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchReportTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(simpleFormatsOnlyCheckBox)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchOnlyReportsCheckBox)
                    .addComponent(simpleFormatsOnlyCheckBox)
                    .addComponent(viewRestrictionCheckbox))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchReportTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(availableTablesAndVievsListSrcollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonOk)
                    .addComponent(buttonCancel)
                    .addComponent(createNewPrintFormatButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void viewRestrictionCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_viewRestrictionCheckboxItemStateChanged
//        JCheckBox checkBox = (JCheckBox) evt.getSource();
//        logger.trace("chceckBox: " + checkBox);
//        logger.trace("Is selected: " + checkBox.isSelected());
        logger.trace("Is selected: " + viewRestrictionCheckbox.isSelected());
        if(viewRestrictionCheckbox.isSelected()){
            availableTablesAndVievsList.setModel(Utils.createListModel( dataProvider.getViews() ));
        } else {
            availableTablesAndVievsList.setModel(Utils.createListModel( dataProvider.getTables(null) ));
        }
    }//GEN-LAST:event_viewRestrictionCheckboxItemStateChanged

    private void searchTableTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTableTextFieldFocusGained
        JTextField jt = (JTextField) evt.getComponent();
        jt.selectAll();
        if(viewRestrictionCheckbox.isSelected()){
            searchList = dataProvider.getViews();
        } else {
            searchList = dataProvider.getTables(null);
        }
        
    }//GEN-LAST:event_searchTableTextFieldFocusGained

    private void searchTableTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTableTextFieldKeyReleased
        String s = ((JTextField) evt.getComponent()).getText();
        System.out.println("Searching for: " + s);
        
//        if(searchList != null){
//             List<String> list = new ArrayList<String>();
//             List<String> listInnerMatch = new ArrayList<String>();
//        
//        for(String t: searchList){
//                if(t.length() < s.length()) continue;
//
//                //compare first s.lenght() characters
//                if( t.substring(0, s.length()).equalsIgnoreCase(s) ){
//                    list.add(t);
//                    
//                    
//                    
//                } //If there is a match of "s" (searched string) anywhere as substring
//                else if( t.toLowerCase().contains( s.toLowerCase() ) ){ 
//                    
//                    listInnerMatch.add(t);
//                }
//            }
//            
//            list.addAll(listInnerMatch);
//            availableTablesAndVievsList.setModel(Utils.createListModel(list));
        
        List<String> result = search(searchList, s);
        if(result != null){
            availableTablesAndVievsList.setModel(Utils.createListModel(result));
        }
    }//GEN-LAST:event_searchTableTextFieldKeyReleased

    private void buttonCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCancelMouseClicked
        selectedTable = null;
        selectedPrintFormatID = -1;
        selectedPrintFormatName = null;
        isNewFormat = false;
        dispose();
    }//GEN-LAST:event_buttonCancelMouseClicked

    private void buttonOkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOkMouseReleased
        selectedTable = (String) availableTablesAndVievsList.getSelectedValue();
        
        PrintFormat selectedFormat = (PrintFormat) availablePrintFormats.getSelectedValue();
        if( selectedFormat == null ){
        	selectedPrintFormatID = -1;
                selectedPrintFormatName = null;
                
        } else {
        	selectedPrintFormatID = selectedFormat.getId();
                selectedPrintFormatName = selectedFormat.getName();
        }
        isNewFormat = false;
        dispose();
    }//GEN-LAST:event_buttonOkMouseReleased

    private void availableTablesAndVievsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableTablesAndVievsListMouseClicked
        
        if(!availableTablesAndVievsList.isEnabled()) return;                          //if not enabled do nothing
        
        //retrieve model from list wich fired this event
        JList list = (JList) evt.getComponent();

        String s = (String) list.getSelectedValue();
        logger.trace("Selected table: " + s);

        int tableID = dataProvider.getTableID(s);
        Map<Integer, String> printFormats;
        
        if( simpleFormatsOnlyCheckBox.isSelected() ){
            printFormats = dataProvider.getPrintFormats(tableID, true);
        } else {
            printFormats = dataProvider.getPrintFormats(tableID, false);
        }

        availablePrintFormats.setModel( PrintFormat.createListModel(printFormats) );
        searchReportMap = printFormats;
    }//GEN-LAST:event_availableTablesAndVievsListMouseClicked

    private void createNewPrintFormatButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createNewPrintFormatButtonMouseReleased
         selectedTable = (String) availableTablesAndVievsList.getSelectedValue();
         selectedPrintFormatID = -1;
         selectedPrintFormatName = null;
         isNewFormat = true;
         dispose();
    }//GEN-LAST:event_createNewPrintFormatButtonMouseReleased

    private void searchOnlyReportsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchOnlyReportsCheckBoxItemStateChanged
        
        if(searchOnlyReportsCheckBox.isSelected()){             //New state is selected (changed fron unselected to selected)
            viewRestrictionCheckbox.setEnabled(false);
            availableTablesAndVievsList.setEnabled(false);
            searchTableTextField.setEnabled(false);
            
            if(simpleFormatsOnlyCheckBox.isSelected()){
                searchReportMap = dataProvider.getPrintFormats(-1, true);
            } else {
                searchReportMap = dataProvider.getPrintFormats(-1, false);
            }
        } else {
            viewRestrictionCheckbox.setEnabled(true);
            availableTablesAndVievsList.setEnabled(true);
            searchTableTextField.setEnabled(true);
            
            searchReportMap = new HashMap<Integer, String>();
        }
        
        availablePrintFormats.setModel( PrintFormat.createListModel(searchReportMap) );
        
    }//GEN-LAST:event_searchOnlyReportsCheckBoxItemStateChanged

    private void searchReportTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchReportTextFieldFocusGained
        JTextField jt = (JTextField) evt.getComponent();
        jt.selectAll();
        
        if( searchOnlyReportsCheckBox.isSelected() ){                              //Ignore table selection and search through all print formats
            if( simpleFormatsOnlyCheckBox.isSelected() ){                           //Search only for "non form" (standard header type) reports
                searchReportMap = dataProvider.getPrintFormats(-1, true);
            } else {
                searchReportMap = dataProvider.getPrintFormats(-1, false);          //Search throught all reports
            }
            
        } else {                                                                    //Search only in formats beloging to selected table
            String tableName = (String) availableTablesAndVievsList.getSelectedValue();
            
            if( simpleFormatsOnlyCheckBox.isSelected() ){                           //Search only for "non form" (standard header type) reports
                searchReportMap = dataProvider.getPrintFormats(tableName, true);
            } else {
                searchReportMap = dataProvider.getPrintFormats(tableName, false);          //Search throught all reports
            }
        }

//        if(viewRestrictionCheckbox.isSelected()){
//            searchList = dataProvider.getViews();
//        } else {
//            searchList = dataProvider.getTables(null);
//        }
    }//GEN-LAST:event_searchReportTextFieldFocusGained

    private void searchReportTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchReportTextFieldKeyReleased
        String s = ((JTextField) evt.getComponent()).getText();
        System.out.println("Searching for: " + s);
        
        Map<Integer, String> resultMap = searchReport(searchReportMap,s);
        if(resultMap != null){
            availablePrintFormats.setModel( PrintFormat.createListModel(resultMap) );
        }
    }//GEN-LAST:event_searchReportTextFieldKeyReleased

    private void simpleFormatsOnlyCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_simpleFormatsOnlyCheckBoxItemStateChanged
        
        if( simpleFormatsOnlyCheckBox.isSelected() ){                                   //New state is selected (changed fron unselected to selected)
            
            if( searchOnlyReportsCheckBox.isSelected() ){                               //Ignore table selection and search through all print formats
                searchReportMap = dataProvider.getPrintFormats(-1, true);
                
            } else {                                                                    //Search only in formats beloging to selected table
                String tableName = (String) availableTablesAndVievsList.getSelectedValue();
                searchReportMap = dataProvider.getPrintFormats(tableName, true);
            }
            
        } else {
            if( searchOnlyReportsCheckBox.isSelected() ){
                searchReportMap = dataProvider.getPrintFormats(-1, false);
                
            } else {
                String tableName = (String) availableTablesAndVievsList.getSelectedValue();
                searchReportMap = dataProvider.getPrintFormats(tableName, false);
            }
        }
        availablePrintFormats.setModel( PrintFormat.createListModel(searchReportMap) );
    }//GEN-LAST:event_simpleFormatsOnlyCheckBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList availablePrintFormats;
    private javax.swing.JList availableTablesAndVievsList;
    private javax.swing.JScrollPane availableTablesAndVievsListSrcollPane;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOk;
    private javax.swing.JButton createNewPrintFormatButton;
    private javax.swing.JPanel credentialsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel roleTitle;
    private javax.swing.JCheckBox searchOnlyReportsCheckBox;
    private javax.swing.JTextField searchReportTextField;
    private javax.swing.JTextField searchTableTextField;
    private javax.swing.JCheckBox simpleFormatsOnlyCheckBox;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userTitle;
    private javax.swing.JCheckBox viewRestrictionCheckbox;
    // End of variables declaration//GEN-END:variables
    }
}