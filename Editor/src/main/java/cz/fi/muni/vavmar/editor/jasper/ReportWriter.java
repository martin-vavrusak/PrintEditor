/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.jasper;

import cz.fi.muni.vavmar.editor.MainScene;
import cz.fi.muni.vavmar.editor.tools.ColumnWidget;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.tree.DefaultDocumentType;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class ReportWriter {
    private static Logger logger = LogManager.getLogger(ReportWriter.class);
    private StringBuilder mainSQL = new StringBuilder("SELECT ");
    
    public static void writeXML(MainScene scene, String outputPath){
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        
        DefaultDocumentType dtd = new DefaultDocumentType();
        dtd.setElementName("jasperReport PUBLIC \"//JasperReports//DTD Report Design//EN\"\n" +
                                            "\"http://jasperreports.sourceforge.net/dtds/jasperreport.dtd\"");
        
//        List<String> list = new ArrayList<String>();
//        list.add("sdfdsf");
//        dtd.setExternalDeclarations(list);
        
        document.setDocType(dtd);
        Element root = document.addElement("jasperReport");
        
        root.addAttribute("name", "reportName")
                .addAttribute("xmlns", "http://jasperreports.sourceforge.net/jasperreports")
                .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
                .addAttribute("xsi:schemalocation", "http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd");
       
        
        //--------------- Widgets parsing -------------
        LayerWidget mainLayer = scene.getMainLayer();
        
        //create detail field:
        Element detailElement = root.addElement("detail");
        Element detailElementBand = root.addElement("band");
        
        
        Set<String> columns = new HashSet<String>();
        Set<String> tables = new HashSet<String>();
        
        int bandMaxHeight = 0;
        for(Widget w: mainLayer.getChildren()){
            logger.trace("Zapisuju widget: " + w);
            
            //jestlize mame column widget ulozime jeho nazev a tabulku pro vytvoreni
            if(w instanceof ColumnWidget){
               ColumnWidget columnWidget = (ColumnWidget) w;
               tables.add( columnWidget.getParentTable().getName() );
               columns.add( columnWidget.getLabel() );
               Element e = detailElement.addElement("");
            }
            Element e = detailElement.addElement("");
            smazat
            
            //zjisteni maximalni velikosti prvku
            bandMaxHeight = bandMaxHeight < (int) w.getPreferredSize().getHeight() ? (int) w.getPreferredSize().getHeight() : bandMaxHeight ;
        }
        
        detailElementBand.addAttribute("height", Integer.toBinaryString(bandMaxHeight));
        
        
        JasperDesign design = new JasperDesign();   //Tohle nejspis nebude fungovat

                
        
        
        
        
        
        
        
        
        String outputFilePath = outputPath + ".jrxml";
        File outputFile = new File(outputFilePath);
        
        FileWriter fw;
        try {
            
            outputFile.delete();
            outputFile.createNewFile();
            
            fw = new FileWriter(outputFile);
            
            OutputFormat format = OutputFormat.createPrettyPrint();
            org.dom4j.io.XMLWriter writer = new org.dom4j.io.XMLWriter( fw, format );
            writer.write( document );
            writer.close();
                    
//            document.write(fw);
//            fw.close();
            
            logger.info("Document saved: " + outputFile);

        } catch (IOException ex) {
            logger.error(ex);
            return;
        }
       
    }
}
