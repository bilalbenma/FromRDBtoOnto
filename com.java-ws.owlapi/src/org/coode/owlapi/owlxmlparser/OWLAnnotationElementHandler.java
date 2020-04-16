/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2006<br><br>
 */
@SuppressWarnings("javadoc")
public class OWLAnnotationElementHandler extends AbstractOWLElementHandler<OWLAnnotation> {

    private Set<OWLAnnotation> annotations;

    private OWLAnnotationProperty property;

    private OWLAnnotationValue object;

    public OWLAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
    }

    @Override
    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
    public void attribute(String localName, String value) throws OWLParserException {
        super.attribute(localName, value);
        // Legacy Handling
        if(localName.equals(OWLXMLVocabulary.ANNOTATION_URI.getShortName())) {
            IRI iri = getIRI(value);
            property = getOWLDataFactory().getOWLAnnotationProperty(iri);
        }
    }

    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if(annotations == null) {
            annotations = new HashSet<OWLAnnotation>();
        }
        annotations.add(handler.getOWLObject());
    }

    @Override
	public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
	public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
	public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        property = handler.getOWLObject();
    }

    @Override
	public void handleChild(AbstractIRIElementHandler handler) throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
    public OWLAnnotation getOWLObject() {
        if (annotations == null) {
            return getOWLDataFactory().getOWLAnnotation(property, object);
        }
        else {
            return getOWLDataFactory().getOWLAnnotation(property, object, annotations);
        }
    }


    @Override
	public boolean isTextContentPossible() {
        return false;
    }
}