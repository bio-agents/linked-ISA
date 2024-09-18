package org.isaagents.linkedISA.mapping;

import org.apache.log4j.Logger;
import org.isaagents.graph.model.ISAMaterialAttribute;
import org.isaagents.graph.model.ISAMaterialNode;
import org.isaagents.isacreator.model.GeneralFieldTypes;
import org.isaagents.isacreator.model.InvestigationPublication;
import org.isaagents.isacreator.model.StudyDesign;
import org.isaagents.syntax.ExtendedISASyntax;
import org.isaagents.util.Pair;
import org.semanticweb.owlapi.model.IRI;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encapsulates ISA to OWL mapping information. All data validation is done in this class.
 * 
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 *
 */
public class ISASyntax2LinkedMapping {

    private static final Logger log = Logger.getLogger(ISASyntax2LinkedMapping.class);

    public static String SEPARATOR_REGEXPR = "\\|";
    public static String CHAIN_PROPERTY = "chain";

    Set<String> mappingFiles = null;
	
	Map<String,IRI> sourceOntoIRIs = null;

    //for each type string (from the ISA syntax) there might be one or more IRIs to be used as classes
	Map<String, Set<IRI>> typeMappings = null;

    //all property mappings
	Map<String,List<Pair<IRI, String>>> propertyMappings = null;

    //property mappings divided by types
    Map<String,List<Pair<IRI, String>>> contactPropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> protocolPropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> protocolREFPropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> materialNodePropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> assayPropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> publicationPropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> factorPropertyMappings = null;
    Map<String,List<Pair<IRI, String>>> groupPropertyMappings = null;

    //the rest of the property mappings, after splitting by types
    Map<String,List<Pair<IRI, String>>> otherPropertyMappings = null;


	public ISASyntax2LinkedMapping(){
		init();
	}
	
	private void init(){
        mappingFiles = new HashSet<String>();
		sourceOntoIRIs = new HashMap<String,IRI>();
		typeMappings = new HashMap<String, Set<IRI>>();
		propertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        contactPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        protocolPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        protocolREFPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        materialNodePropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        assayPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        publicationPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        factorPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        groupPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
        otherPropertyMappings = new HashMap<String, List<Pair<IRI,String>>>();
		
	}

    public void addMappingFile(String mappingFile){
        mappingFiles.add(mappingFile);
    }

    public Set<String> getMappingFiles(){
        return mappingFiles;
    }

	/**
	 * 
	 * @param iri ontology IRI 
	 */
	public void addOntology(String name,String iri){
		if (!iri.equals(""))
			sourceOntoIRIs.put(name,IRI.create(iri));
	}

	/**
	 * 
	 * @return list of source ontologies
	 */
	public Map<String,IRI> getSourceOntoIRIs(){
		return sourceOntoIRIs;
	}
	
	public IRI getOntoIRI(String ontoID){
		return sourceOntoIRIs.get(ontoID);
	}

    public Set<IRI> getTypeMapping(String label){
        return typeMappings.get(label);
    }

	public void addTypeMapping(String label, String type){
        Set<IRI> iris = typeMappings.get(label);
        if (iris==null)
            iris = new HashSet<IRI>();
        iris.add(IRI.create(type));
		typeMappings.put(label, iris);
	}

    public Map<String,List<Pair<IRI, String>>> getPropertyMappings(){
        return propertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getOtherPropertyMappings(){
        return otherPropertyMappings;
    }

    public IRI getPropertyIRI(String subject, String object){
        Map<String,List<Pair<IRI, String>>> map = getPropertyMappings();
        List<Pair<IRI, String>> list = map.get(subject);
        for(Pair<IRI, String> pair: list){
            if (pair.getSecond().equals(object))
                return pair.getFirst();
        }
        return null;
    }


    public IRI getPropertyIRISubjectRegexObjectRegex(String regexSubject, String regexObject){
        ArrayList<String> candidates = new ArrayList<String>();

        Pattern pSubject = Pattern.compile(regexSubject);
        Map<String,List<Pair<IRI, String>>> map = getPropertyMappings();

        Set<String> keys = map.keySet();
        Iterator<String> ite = keys.iterator();

        while (ite.hasNext()) {
            String candidate = ite.next();
            Matcher m = pSubject.matcher(candidate);
            if (m.matches()) {
                candidates.add(candidate);
            }
        }

        for(String candidate: candidates){
            List<Pair<IRI, String>> list = map.get(candidate);
            for(Pair<IRI, String> pair: list){
                Pattern pObject = Pattern.compile(regexObject);
                Matcher m = pObject.matcher(pair.getSecond());
                if (m.matches()){
                    return pair.getFirst();
                }
            }
        }
        return null;
    }


    /**
     *
     * @param regex
     * @param object
     * @return
     */
    public IRI getPropertyIRISubjectRegexObject(String regex, String object){

        ArrayList<String> candidates = new ArrayList<String>();

        Pattern p = Pattern.compile(regex);
        Map<String,List<Pair<IRI, String>>> map = getPropertyMappings();

        Set<String> keys = map.keySet();
        Iterator<String> ite = keys.iterator();

        while (ite.hasNext()) {
            String candidate = ite.next();
            Matcher m = p.matcher(candidate);
            if (m.matches()) {
                candidates.add(candidate);
            }
        }

        for(String candidate: candidates){
            return getPropertyIRI(candidate, object);
        }
        return null;
    }

    public List<Pair<IRI, String>> getPropertyMappings(String subject){
        return propertyMappings.get(subject);
    }



    public Map<String,List<Pair<IRI, String>>> getContactMappings(){
        return contactPropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getPublicationPropertyMappings(){
        return publicationPropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getAssayPropertyMappings(){
        return assayPropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getProtocolMappings(){
        return protocolPropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getProtocolREFMappings(){
        return protocolREFPropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getMaterialNodePropertyMappings(){
        return materialNodePropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getFactorPropertyMappings(){
        return factorPropertyMappings;
    }

    public Map<String,List<Pair<IRI, String>>> getGroupPropertyMappings(){
        return groupPropertyMappings;
    }
	
	public void addPropertyMapping(String subject, String predicate, String object){
		List<Pair<IRI,String>> predobjs = propertyMappings.get(subject);
		if (predobjs==null)
			predobjs = new ArrayList<Pair<IRI,String>>();
		if (!predicate.equals("") && !object.equals("")){
			predobjs.add(new Pair<IRI,String>(IRI.create(predicate), object));
		}
		propertyMappings.put(subject, predobjs);

        boolean added = false;

        if (subject.startsWith(ExtendedISASyntax.STUDY_PERSON) ||
                subject.startsWith(ExtendedISASyntax.INVESTIGATION_PERSON)){
            contactPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.startsWith(ExtendedISASyntax.STUDY_PROTOCOL)){
            protocolPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.startsWith(ExtendedISASyntax.STUDY_PROTOCOL_REF.toString())
                || subject.startsWith(ExtendedISASyntax.ASSAY_PROTOCOL_REF.toString())
                || subject.startsWith(GeneralFieldTypes.PARAMETER_VALUE.name)){
            protocolREFPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.matches(ISAMaterialNode.REGEXP)
                || subject.matches(ISAMaterialAttribute.REGEXP)){
            materialNodePropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.startsWith(ExtendedISASyntax.STUDY_ASSAY)){
            assayPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.startsWith(ExtendedISASyntax.INVESTIGATION_PUBLICATION)
                || subject.startsWith(ExtendedISASyntax.STUDY_PUBLICATION)
                || subject.startsWith((ExtendedISASyntax.PUBLICATION))
                || subject.startsWith(InvestigationPublication.PUBMED_ID)){
            publicationPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.startsWith(ExtendedISASyntax.STUDY_FACTOR)
                || subject.startsWith(GeneralFieldTypes.FACTOR_VALUE.name)
                || subject.startsWith(StudyDesign.STUDY_DESIGN_TYPE)
                || subject.startsWith(ExtendedISASyntax.SAMPLE)){
            factorPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (subject.startsWith(ExtendedISASyntax.STUDY_GROUP)
            || subject.startsWith(StudyDesign.STUDY_DESIGN_TYPE)) {
            groupPropertyMappings.put(subject, predobjs);
            added = true;
        }

        if (!added || subject.startsWith(StudyDesign.STUDY_DESIGN_TYPE)) {
            otherPropertyMappings.put(subject, predobjs);
        }

	}


    @Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("MAPPING OBJECT(");
		builder.append("ONTOLOGIES=");
        builder.append(this.mapToString(sourceOntoIRIs));
        builder.append("\nTYPE MAPPINGS=\n");
        builder.append(this.mapToString(typeMappings));
		builder.append("\nPROPERTY MAPPINGS=\n");
		builder.append(this.mapToString(propertyMappings));
        builder.append("\nCONTACT PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(contactPropertyMappings));
        builder.append("\nPROTOCOL PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(protocolPropertyMappings));
        builder.append("\nPROTOCOL REF PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(protocolREFPropertyMappings));
        builder.append("\nMATERIAL NODE PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(materialNodePropertyMappings));
        builder.append("\nASSAY PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(assayPropertyMappings));
        builder.append("\nPUBLICATION PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(publicationPropertyMappings));
        builder.append("\nFACTOR PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(factorPropertyMappings));
        builder.append("\nGROUP PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(groupPropertyMappings));
        builder.append("\nOTHER PROPERTY MAPPINGS=\n");
        builder.append(this.mapToString(otherPropertyMappings));
        return builder.toString();
	}
	
	private <A,B> String mapToString(Map<A, B> map){
		if (map==null)
			return "";
		StringBuilder builder = new StringBuilder();
		for(A key: map.keySet()){
			builder.append(key+ "," + map.get(key)+"\n");
		}
		return builder.toString();
	}


	
}
