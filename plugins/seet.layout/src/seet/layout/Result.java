package seet.layout;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import seet.time.CPUUtils;
import seet.ui.StartWizard;

public class Result extends TitleAreaDialog {

	ArrayList<String> AllPC;
	String elapsedTime;
	String message;
	
	/**
	 * Create the dialog.
	 */
	public Result(Shell parentShell, ArrayList<String> AllPC, String message) {
		   super(parentShell);
	        this.AllPC = AllPC;
	        this.message = message;
	}
	
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("SEET");
        Bundle bundle = Platform.getBundle("seet.engine");
 	    URL url = FileLocator.find(bundle, new Path("icon/Logo.png"), null);
 	    ImageDescriptor desc = ImageDescriptor.createFromURL(url);
 	    Image image = desc.createImage();
        shell.setImage(image);
     }
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
    	
        setTitle("Result");
    	composite.setLayout(new GridLayout());
    	GridData data = new GridData(GridData.FILL_HORIZONTAL);
    
    	Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
    	grpModelProperties.setLayout(new GridLayout(2, false));
    	grpModelProperties.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
    	for (String PCString : AllPC)
    	{
    		Label l2 = new Label(grpModelProperties,SWT.BORDER | SWT.WRAP);
    		new Label(grpModelProperties, SWT.NONE);
    		l2.setLayoutData(data);
    		l2.setText("Path Condition: " + PCString);
    		l2.setBounds(0, 0, 470, 200);
    	}
    	Label l3 = new Label(grpModelProperties, SWT.NULL);
     	l3.setLayoutData(data);
     	new CPUUtils();
		long stopTime = CPUUtils.getUserTime();
     	l3.setText("Time: " + (stopTime - StartWizard.startTime) /1000000 +" ms");  
 
 		new Label(grpModelProperties, SWT.NONE);
     	Label l4 = new Label(grpModelProperties, SWT.NULL);
      	l4.setText("Symbolic metamodel footprint is created.\n");
        new Label(grpModelProperties, SWT.NONE);
      	
     	Label l5 = new Label(grpModelProperties, SWT.NULL);
     	l5.setLayoutData(data);
    	if (message.equals(""))
    		l5.setText("The Symbolic metamodel footprint conforms to the target metamodel.");
    	else
    	{
    		l5.setText("The Symbolic metamodel footprint does not conform to the target metamodel.\n");
    		new Label(grpModelProperties, SWT.NONE);
    		Label l6 = new Label(grpModelProperties, SWT.NULL);
         	l6.setLayoutData(data);
         	l6.setText(message);
    	}    	
    	
    	return composite;
    }
  
	protected void createButtonsForButtonBar(Composite parent) {	
        createButton(parent, IDialogConstants.CANCEL_ID,
            IDialogConstants.FINISH_LABEL, false);
    }
	
    protected Point getInitialSize() {
      return new Point(525, 409);
    }	

}

