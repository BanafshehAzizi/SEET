package seet.standalone;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

import seet.ui.FrontPage;

import org.eclipse.epsilon.ecl.EclModule;

public class EclStandalone extends EpsilonStandalone{
	private String Expected;
	public EclStandalone(String Expected)
	{
		this.Expected = Expected;
	}
	
	@Override
	public IEolExecutableModule createModule() {
		return new EclModule();
	}
	
	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		/* Models */
		String[] parts = FrontPage.inputModel.split("/");
		String path = "";		
		for (Integer i = 0; i< parts.length-1; i++)
		{
			path += parts[i] + "/";
		}
		models.add(createEmfModelByURI("SMF", path + "SMF.model", 
				"http://www.eclipse.org/emf/2002/Ecore", true, false));
		models.add(createEmfModelByURI("Expected", Expected, 
				"http://www.eclipse.org/emf/2002/Ecore", true, false));
		return models;
	}

	@Override
	public String getSource() throws Exception {	
		return "Compare.ecl";
	}

	@Override
	public void postProcess() {
		
	}
	
}
