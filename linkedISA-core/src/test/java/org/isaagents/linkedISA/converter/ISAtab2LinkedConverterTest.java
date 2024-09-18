package org.isaagents.linkedISA.converter;

import org.isaagents.linkedISA.mapping.ISASyntax2LinkedMapping;
import org.isaagents.linkedISA.mapping.ISASyntax2LinkedMappingFiles;
import org.isaagents.linkedISA.mapping.LinkedISAMappingParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;


/**
 * Test class for ISAtab2OWLConverter
 * 
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 *
 */
public class ISAtab2LinkedConverterTest {

	private String configDir = null;
	private String isatabParentDir = null;
    private String path = "/ISAtab-Datasets/";
    private String savePath=ISAtab2LinkedConverterTest.class.getClass().getResource(path).getFile();
    private ISAtab2LinkedConverter isatab2owl = null;
    private LinkedISAMappingParser parser = null;
    private ISASyntax2LinkedMapping mapping = null;
    private String iri = null;
	
	@Before
    public void setUp() throws Exception {
		iri = "http://isa-agents.org/isa/faahko.owl";

    	configDir = //getClass().getResource("/configurations/isaconfig-default_v2013-02-13").getFile();
                getClass().getResource("/configurations/isaconfig-default_v2014-01-16").getFile();
    	System.out.println("configDir="+configDir);
        path = "/ISAtab-Datasets/";

        //System.out.println("Parsing the mapping...");

        parser = new LinkedISAMappingParser();
        URL isa_obi_mapping_url = getClass().getClassLoader().getResource(ISASyntax2LinkedMappingFiles.ISA_OBI_MAPPING_FILENAME);
        //System.out.println("isa_obi_mapping_url="+isa_obi_mapping_url);
        parser.parseCSVMappingFile(isa_obi_mapping_url.toURI().getRawPath().toString());

        URL isa_isa_mapping_url = getClass().getClassLoader().getResource(ISASyntax2LinkedMappingFiles.ISA_ISA_MAPPING_FILENAME);
        //System.out.println("isa_isa_mapping_url="+isa_isa_mapping_url);
        parser.parseCSVMappingFile(isa_isa_mapping_url.toURI().getRawPath().toString());

        URL isa_prov_o_mapping_url = getClass().getClassLoader().getResource(ISASyntax2LinkedMappingFiles.ISA_PROV_O_MAPPING_FILENAME);
        //System.out.println("isa_isa_mapping_url="+isa_isa_mapping_url);
        parser.parseCSVMappingFile(isa_prov_o_mapping_url.toURI().getRawPath().toString());

        mapping = parser.getMapping();
        //System.out.println("MAPPING-----");
        //System.out.println(mapping);

		isatab2owl = new ISAtab2LinkedConverter(configDir, mapping);
    }

    @After
    public void tearDown() {
    }

    //@Test
    public void consecutiveConversions() {
        iri = "http://w3id.org/isa/BII-I-1.owl#";
        isatabParentDir = getClass().getResource(path+"BII-I-1").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        assert(isatab2owl.convert(isatabParentDir, iri));
        isatab2owl.saveOntology(savePath+"BII-I-1.owl");

        iri = "http://isa-agents.org/isa/MTBLS6.owl";
        isatabParentDir = getClass().getResource( path+  "MTBLS6").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        assert(isatab2owl.convert(isatabParentDir, iri));
        isatab2owl.saveOntology(savePath+"MTBLS6.owl");
    }

    @Test
    public void testConvertBII_I_1() throws Exception {
        iri = "http://w3id.org/isa/BII-I-1.owl";
        isatabParentDir = getClass().getResource(path+"BII-I-1").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"BII-I-1.owl");

        isatab2owl.saveInferredOntology(savePath+"BII-I-1-inferred.owl");
    }

//    @Test
//    public void testConvertBII_S_9() {
//        iri = "http://isa-agents.org/isa/BII-S-9.owl";
//        isatabParentDir = getClass().getResource(path+"BII-S-9").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
////        assert(isatab2owl.convert(isatabParentDir, iri));
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"BII-S-9.owl");
//
//    }
    
//    @Test
//    public void testConvertArmstrong() throws Exception {
//        iri = "http://isa-agents.org/isa/ARMSTRONG-3.owl";
//        isatabParentDir = getClass().getResource(path+"ARMSTRONG-3").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"ARMSTRONG-3.owl");
//
//        isatab2owl.saveInferredOntology(savePath+"ARMSTRONG-3-inferred.owl");
//    }

    @Test
    public void testConvertBII_S_3() throws Exception {
        iri = "http://purl.org/isatab";
        isatabParentDir = getClass().getResource(path+"BII-S-3").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"BII-S-3.owl");

        isatab2owl.saveInferredOntology(savePath+"BII-S-3-inferred.owl");
    }


    @Test
    public void testConvertMTBLS6() {
        iri = "http://isa-agents.org/isa/MTBLS6.owl";
        isatabParentDir = getClass().getResource( path+  "MTBLS6").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        assert(isatab2owl.convert(isatabParentDir, iri));
        isatab2owl.saveOntology(savePath+"MTBLS6.owl");
    }


    @Test
    public void testConvertFaahKO() {
        iri = "http://purl.org/isatab/faahko.owl";
        isatabParentDir = getClass().getResource( path +"faahKO").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"faahko.owl");
    }

   @Test
    public void testConvertT12by2strainsex() {
       iri = "http://isa-agents.org/isa/T1.owl";
        isatabParentDir = getClass().getResource( path + "T1-2x2-strain-sex").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"T1.owl");
    }

    @Test
    public void testConvertT3() {
        iri = "http://isa-agents.org/isa/T3.owl";
        isatabParentDir = getClass().getResource( path + "T3").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);

        System.out.println("Converting the ISA-tab dataset into OWL");

        assert(isatab2owl.convert(isatabParentDir, iri));

        isatab2owl.saveOntology(savePath+"T3.owl");

    }

    @Test
    public void testConvertT4() {
        iri = "http://isa-agents.org/isa/T4.owl";
        isatabParentDir = getClass().getResource(path + "T4").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        assert(isatab2owl.convert(isatabParentDir, iri));
        isatab2owl.saveOntology(savePath+"T4.owl");
    }

    @Test
    public void testConvertGWAS() {
        iri = "http://isa-agents.org/isa/GWAS.owl";
        isatabParentDir = getClass().getResource( path +"GWAS-E-GEOD-11948-corrected-with-publication").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"GWAS.owl");
    }

    @Test
    public void testConvertEGEOD() {
        iri = "http://isa-agents.org/isa/E-GEOD-25835-MPBRCA1.owl";
        isatabParentDir = getClass().getResource( path + "E-GEOD-25835-MPBRCA1").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"E-GEOD-25835-MPBRCA1.owl");
    }

//    @Test
//    public void testConvertMTBLS2() {
//        iri = "http://isa-agents.org/isa/MTBLS2.owl";
//        isatabParentDir = getClass().getResource( path + "MTBLS2").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"MTBLS2.owl");
//    }

//    @Test
//    public void testConvertHeck_ISAtab() {
//        iri = "http://isa-agents.org/isa/Heck.owl";
//        isatabParentDir = getClass().getResource( path + "Heck_ISA-tab-July10").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"Heck.owl");
//    }

    @Test
    public void testConvertSOAPdenovo2(){
        iri = "http://w3id.org/isa/soapdenovo2";
        isatabParentDir = getClass().getResource( path + "BGI-SOAPdenovo2").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"soapdenovo2.rdf");
    }

    @Test
    public void testConvertSOAPdenovo2Compact(){
        iri = "http://w3id.org/isa/soapdenovo2";
        isatabParentDir = getClass().getResource( path + "soapdenovo2-compact").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"soapdenovo2.rdf");
    }


    @Test
    public void testConvertISATABTest1(){
        iri = "http://w3id.org/isa/isatab-test1";
        isatabParentDir = getClass().getResource( path + "isatab-test1").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"isatab-test1.rdf");

        System.out.println();
        System.out.println();
        isatab2owl.getMintedIRIs();

    }

    @Test
    public void testConvertISATABTest2(){
        iri = "https://w3id.org/isa/isatab-test2";
        isatabParentDir = getClass().getResource( path + "isatab-test2").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"isatab-test1.rdf");

        System.out.println();
        System.out.println();
        isatab2owl.getMintedIRIs();

    }

    @Test
    public void testConvertISATABTest3(){
        iri = "https://w3id.org/isa/isatab-test3";
        isatabParentDir = getClass().getResource( path + "isatab-test3").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"isatab-test3.rdf");

        System.out.println();
        System.out.println();
        isatab2owl.getMintedIRIs();

    }

    @Test
    public void testConvertISATABTest4(){
        iri = "http://w3id.org/isa/isatab-test4";
        isatabParentDir = getClass().getResource( path + "isatab-test4").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"isatab-test4.rdf");

        System.out.println();
        System.out.println();
        isatab2owl.getMintedIRIs();

    }

//    @Test
//    public void testConvertISATABTest5(){
//        iri = "http://w3id.org/isa/isatab-test5";
//        isatabParentDir = getClass().getResource( path + "isatab-test5").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"isatab-test5.rdf");
//
//        System.out.println();
//        System.out.println();
//        isatab2owl.getMintedIRIs();
//
//    }

//    @Test
//    public void testConvertISATABTest6(){
//        iri = "http://w3id.org/isa/isatab-test6";
//        isatabParentDir = getClass().getResource( path + "isatab-test6").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"isatab-test6.rdf");
//
//        System.out.println();
//        System.out.println();
//        isatab2owl.getMintedIRIs();
//
//    }

//    @Test
//    public void testConvertISATABTest7(){
//        iri = "http://w3id.org/isa/isatab-test7";
//        isatabParentDir = getClass().getResource( path + "isatab-test7").getFile();
//        System.out.println("isatabParentDir="+isatabParentDir);
//        System.out.println("Converting the ISA-tab dataset into OWL");
//        isatab2owl.convert(isatabParentDir, iri);
//        isatab2owl.saveOntology(savePath+"isatab-test7.rdf");
//
//        System.out.println();
//        System.out.println();
//        isatab2owl.getMintedIRIs();
//
//    }

    @Test
    public void testConvertISACompositeTest(){
        iri = "https://w3id.org/isa/isa-composite-test";
        isatabParentDir = getClass().getResource( path + "ISA-composite-test").getFile();
        System.out.println("isatabParentDir="+isatabParentDir);
        System.out.println("Converting the ISA-tab dataset into OWL");
        isatab2owl.convert(isatabParentDir, iri);
        isatab2owl.saveOntology(savePath+"isa-composite-test.rdf");

        System.out.println();
        System.out.println();
        isatab2owl.getMintedIRIs();

    }


}
