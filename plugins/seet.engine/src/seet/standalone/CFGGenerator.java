/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package seet.standalone;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

import seet.ui.FrontPage;

public class CFGGenerator extends EpsilonStandalone{
	private String model;
	private String inputMetaModel;
	public CFGGenerator(FrontPage frontPage)
	{
		model = FrontPage.inputModel;
		inputMetaModel = frontPage.getText2();
	}
	
	@Override
	public IEolExecutableModule createModule() {
		return new EolModule();
	}
	
	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		URL url1 = FileLocator.find(Platform.getBundle("seet.engine"), new Path("metamodel/CFG.ecore"), null);
		java.net.URI javaURI = url1.toURI();			
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(javaURI.toString());
		EmfUtil.register(uri, EPackage.Registry.INSTANCE);
		URI emfURI = URI.createFileURI(inputMetaModel);
		registerMetamodel(inputMetaModel);
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl ());
		ResourceSet resourceSet = new ResourceSetImpl(); 
		Resource resource1 = resourceSet.getResource(emfURI, true);
		EPackage inputPackage = (EPackage)resource1.getContents().get(0);
			
		/* Models */
		models.add(createEmfModelByURI("ETL", model, 
				"http://www.eclipse.org/epsilon/etl", true, false));
		models.add(createEmfMetaModelByURI("IN_MM", "IN_MM", inputPackage.getNsURI()));
		
		String[] parts = model.split("/");
		String path = "";
		
		for (Integer i = 0; i< parts.length-1; i++)
		{
			path += parts[i] + "/";
		}
		models.add(createEmfModelByURI("CFG", path + "CFGModel.model", 
				"CFG", false, true));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "ETLModel2CFG.eol";
	}

	@Override
	public void postProcess() {
		
	}
	
	private void registerMetamodel(String MetaModelPath)
	{
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
	        "*", new EcoreResourceFactoryImpl());

	    ResourceSet rs = new ResourceSetImpl();
	    final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
	    rs.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
	        extendedMetaData);

	    java.net.URI javaURI = new File(MetaModelPath).toURI() ; 
	    org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(javaURI.toString());
	    
	    Resource r = rs.getResource(uri, true);
	    EObject eObject = r.getContents().get(0);
	    if (eObject instanceof EPackage) {
	        EPackage p = (EPackage)eObject;
	        EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
	    }
	    
	    Registry packageRegistry = rs.getPackageRegistry();
	    EPackage pack = (EPackage) packageRegistry.get(EcorePackage.eINSTANCE.getNsURI());
	    if (!(pack instanceof EcorePackage)) {
	      packageRegistry.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
	    }
	  }
	
}