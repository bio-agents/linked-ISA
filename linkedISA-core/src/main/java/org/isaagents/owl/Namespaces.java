package org.isaagents.owl;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 25/02/2013
 * Time: 12:37
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public enum Namespaces {

    OBI("http://purl.obolibrary.org/obo/OBI_"),
    IAO("http://purl.obolibrary.org/obo/IAO_"),
    BFO("http://purl.obolibrary.org/obo/BFO_"),
    ISA("http://purl.org/isa-agents/ISA_");

    final String ns;

    Namespaces(String ns) {
        this.ns = ns;
    }

    @Override
    public String toString() {
        return ns;
    }
}
