package seet.layout;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.osgi.framework.Bundle;

import seet.standalone.*;
import seet.ui.FileExtensionFilter;

 
public class Evaluation extends TitleAreaDialog {
	String result;
	public ArrayList<String> choose;
	String message;
	private Text text;
	private String Expected;
	
	public Evaluation(Shell parentShell) {
        super(parentShell);
        Bundle bundle = Platform.getBundle("seet.layout");
       URL url = FileLocator.find(bundle, new Path("icon/871-128.png"), null);
       ImageDescriptor desc = ImageDescriptor.createFromURL(url);
       Image image = desc.createImage();
       setTitleImage(image);
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
        Composite area = (Composite) super.createDialogArea(parent);
        setTitle("Oracle");
        setMessage("Select your expected symbolic metamodel footprint");
        
        Composite composite = new Composite(area, SWT.NULL);
    	composite.setLayout(new GridLayout());
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        composite.setLayoutData(data);
        
        Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
        grpModelProperties.setText("Expected Symbolic Metamodel Footprint");
        grpModelProperties.setLayout(new GridLayout(2, false));
        grpModelProperties.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
         
        text = new Text(grpModelProperties, SWT.NONE);
        text.setLayoutData(data);
            
        Button button = new Button(grpModelProperties, SWT.PUSH);
      	button.setText("Browse Workspace...");
      	  
    	final GridData buttonData = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
        button.setLayoutData(buttonData);
      	  
        button.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent e2) {
        		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
            			  Display.getDefault().getActiveShell(), 
              		    new WorkbenchLabelProvider(), 
              		    new BaseWorkbenchContentProvider());
        		dialog.setMessage("select a metamodel");
            	dialog.setTitle("Select a metamodel");
            	dialog.setEmptyListMessage("No entries available.");
            	dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
            	dialog.addFilter(new FileExtensionFilter("ecore"));
            	  
            	/* Open the dialog. */            	  
            	IResource resource2;
            	dialog.open();
            	resource2 = (IResource) dialog.getFirstResult();
            	String arg2 = resource2.getFullPath().toString();
            	Expected = arg2;
            	displayFiles(new String[] { arg2}, text);
            }
        });
          	
    	return area;
    }
    
    public void displayFiles(String[] files, Text text) {
    	  for (int i = 0; files != null && i < files.length; i++) {
    		  text.setText(files[i]);
    		  text.setEditable(true);
    	  }
    }
    
    protected void createButtonsForButtonBar(Composite parent) {
        	createButton(parent, IDialogConstants.OK_ID,
            IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID,
            IDialogConstants.CANCEL_LABEL, false);
    }
    
    protected Point getInitialSize() {
        return new Point(520, 409);
    }
    
    @Override
    protected void okPressed() {
    	try {
			new EclStandalone (Expected).execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        super.okPressed();
    }
}