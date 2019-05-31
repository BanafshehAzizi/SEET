package seet.checker;

import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import anatlyzer.atl.model.ATLModel;
import anatlyzer.atlext.OCL.OclExpression;

public class ATLtoAnATLyzer {

	public ArrayList<EObject> ATLtoAnATLyzerCall(Resource r) {
		
		try {
			r.load(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			r.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ATLModel m = new ATLModel(r, r.getURI().toString());
		ArrayList<EObject> expressions = new ArrayList<>();
		for (EObject i : m.allObjectsOf(OclExpression.class))
		{			
			if (i.eContainer() == null)
			{
				expressions.add(i);
			}
		}

		try {
			r.delete(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return expressions;
	}
	
}