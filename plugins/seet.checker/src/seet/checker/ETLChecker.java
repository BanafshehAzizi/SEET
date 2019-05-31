package seet.checker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import anatlyzer.atl.tests.api.StandaloneUSEWitnessFinder;
import anatlyzer.atl.util.AnalyserUtils;
import anatlyzer.atlext.OCL.OclExpression;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import anatlyzer.atl.witness.ConstraintSatisfactionChecker;
import anatlyzer.atl.witness.WitnessUtil;

public class ETLChecker {
	
	public ETLChecker (){
		
	}
	
	public ArrayList test(List<OclExpression> exprs, EPackage metamodel){ 
		ArrayList a = new ArrayList(); 
		Resource mmResource = metamodel.eResource();
		String mmName = "MM";
		mmResource.getResourceSet().setPackageRegistry(null);
		ConstraintSatisfactionChecker checker = ConstraintSatisfactionChecker.
			withExpr(exprs).
	//		withFinder(WitnessUtil.getFirstWitnessFinder()).
			withFinder(new StandaloneUSEWitnessFinder()).
			configureMetamodel(mmName, mmResource).
			check();
		String result = "";
		
		// Check satisfiability
		if ( AnalyserUtils.isConfirmed(checker.getFinderResult()) ) {
			// Do something with checker.getWitnessModel()
			try {
					checker.getWitnessModel().getModel().save(null);
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			result = "Satisfiable";
			a.add(result);
			a.add(checker.getWitnessModel());
		} else if ( AnalyserUtils.isDiscarded(checker.getFinderResult()) ) {
			result = "UnSatisfiable";
			a.add(result);
			a.add("");
		} else if ( AnalyserUtils.isErrorStatus(checker.getFinderResult()) ) {
			// Internal errors, unsupported features, etc. (see the console)
			result = "ErrorStatus";
			a.add(result);
		}
		return a;
	}

}