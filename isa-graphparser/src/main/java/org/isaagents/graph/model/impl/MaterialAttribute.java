package org.isaagents.graph.model.impl;

import org.isaagents.graph.model.ISAMaterialAttribute;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 *         <p/>
 *         Date: 11/10/2011
 *         Time: 13:22
 */
public class MaterialAttribute extends Node implements ISAMaterialAttribute {

    public MaterialAttribute(int index, String name) {
        super(index, name);
    }

    @Override
    public String toString(){
        return getName();
    }

}