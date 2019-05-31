package seet.layout;

import java.net.URL;

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


public class FinalResult extends TitleAreaDialog {

	String PCString;
	String c1;
	Boolean result;
	
	/**
	 * Create the dialog.
	 */
	public FinalResult(Shell parentShell, Boolean result) {
		   super(parentShell);
		   this.result = result;
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
        setTitle("Final Result");
    	composite.setLayout(new GridLayout());
    	GridData data = new GridData(GridData.FILL_HORIZONTAL);
    
    	Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
    	grpModelProperties.setLayout(new GridLayout(2, false));
    	grpModelProperties.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
    
    	Label l1 = new Label(grpModelProperties, SWT.NULL);
    	new Label(grpModelProperties, SWT.NONE);
    	Label l2 = new Label(grpModelProperties, SWT.NULL);
    	l1.setLayoutData(data);
    	if (result == false)
         	l1.setText("Your ETL transformation is not written correctly.");
    	else
         	l1.setText("Your ETL transformation is written correctly.");
     	l2.setLayoutData(data);
     	new CPUUtils();
		long stopTime = CPUUtils.getUserTime();
     	l2.setText("Time: " + (stopTime - StartWizard.startTime) /1000000 +" ms");  
 		new Label(grpModelProperties, SWT.NONE);    	
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

