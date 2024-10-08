package org.isaagents.graph.model.impl;

import org.isaagents.graph.model.ISAMaterialAttribute;
import org.isaagents.graph.model.ISAMaterialNode;
import org.isaagents.graph.model.ISANode;
import org.isaagents.isacreator.model.GeneralFieldTypes;
import org.isaagents.syntax.ExtendedISASyntax;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 06/11/2012
 * Time: 16:11
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class MaterialNode extends NodeWithComments implements ISAMaterialNode {

    private List<ISAMaterialAttribute> materialAttributes;

    public MaterialNode(int index, String name) {
        super(index, name, NodeType.MATERIAL_NODE);
        materialAttributes = new ArrayList<ISAMaterialAttribute>();
    }

    @Override
    public void addMaterialAttribute(ISAMaterialAttribute attribute) {
        materialAttributes.add(attribute);
    }

    @Override
    public List<ISAMaterialAttribute> getMaterialAttributes() {
        return materialAttributes;
    }

    @Override
    public String getMaterialNodeType(){

        if (getName().equals(GeneralFieldTypes.SAMPLE_NAME.toString()))
            return ExtendedISASyntax.SAMPLE;

        if (getName().equals(GeneralFieldTypes.SOURCE_NAME.toString()))
            return ExtendedISASyntax.SOURCE;

        if (getName().equals(GeneralFieldTypes.EXTRACT_NAME.toString()))
            return ExtendedISASyntax.EXTRACT;

        if (getName().equals(GeneralFieldTypes.LABELED_EXTRACT_NAME.toString()))
            return ExtendedISASyntax.LABELED_EXTRACT;

        return null;

    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("MaterialNode: "+getName()+"\n");
        for (ISANode mp : getMaterialAttributes()) {
            buffer.append("\t attribute: " + mp.getName()+"\n");
        }
        if (getComments() != null) {
            for (Node commentNode : getComments()) {
                buffer.append("\t comment: " + commentNode.getName()+"\n");
            }
        }
        return buffer.toString();
    }

}