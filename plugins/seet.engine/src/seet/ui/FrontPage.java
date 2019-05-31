package seet.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class FrontPage extends WizardPage {
        private Text text;
        public static String inputModel;
        private String inputMetaModel;
        private String outputMetaModel;
        Text text2;
        
        public String getText ()
        {
			return text.getText();        	
        }
        
        public FrontPage(String i) {
            super("FrontPage");
            setTitle("Configuration");
            setDescription("Select Input and output metamodels");
            inputModel = i;
        }

       
        public void createControl(Composite parent) {
        	        	
        	Composite composite = new Composite(parent, SWT.NULL);
        	composite.setLayout(new GridLayout());
        	GridData data = new GridData(GridData.FILL_HORIZONTAL);
             
        	if (inputModel == "")
        	{
        		Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
        		grpModelProperties.setText("ETL Transformation Model");
        		grpModelProperties.setLayout(new GridLayout(2, false));
        		grpModelProperties.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        
        		text = new Text(grpModelProperties, SWT.NONE);
        		text.setLayoutData(data);
            	  
            	Button button = new Button(grpModelProperties, SWT.PUSH);
            	button.setText("Browse Workspace...");
            	button.addSelectionListener(new SelectionAdapter() {
            		public void widgetSelected(SelectionEvent e) {
            			ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
                			Display.getDefault().getActiveShell(), 
                  		    new WorkbenchLabelProvider(), 
                  		    new BaseWorkbenchContentProvider());
            			dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
            			/* Open the dialog. */
            			IResource resource;
            			dialog.open();
            			resource = (IResource) dialog.getFirstResult(); 
            			String arg = resource.getFullPath().toString();
            			inputModel = arg;
            			displayFiles(new String[] { arg}, text);
            	  }
            	});  
            	  	          
            	// draws a line.
            	Label line = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL );
            	line.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        	}
        	
        	Group grpModelProperties1 = new Group(composite, SWT.SHADOW_IN);
        	grpModelProperties1.setText("Metamodels");
        	grpModelProperties1.setLayout(new GridLayout(2, false));
        	grpModelProperties1.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
          
        	new Label(grpModelProperties1, SWT.NULL).setText("Input Metamodel:");
          
        	Text text1 = new Text(grpModelProperties1, SWT.NONE);
        	text1.setLayoutData(data);
          	  
        	new Label(grpModelProperties1, SWT.NONE);
          	Button button1 = new Button(grpModelProperties1, SWT.PUSH);
          	button1.setText("Browse Workspace...");
          	
          	final GridData button1Data = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
           	button1.setLayoutData(button1Data);
        	button1.addSelectionListener(new SelectionAdapter() {
        		public void widgetSelected(SelectionEvent e1) {
        			ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
          			  Display.getDefault().getActiveShell(), 
            		    new WorkbenchLabelProvider(), 
            		    new BaseWorkbenchContentProvider());
          	  
        			dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
        			dialog.addFilter(new FileExtensionFilter("ecore"));
          	  
        			dialog.setMessage("select a metamodel");
        			dialog.setTitle("Select a metamodel");
        			dialog.setEmptyListMessage("No entries available.");
        			IResource resource1;
          	  
        			dialog.open();
          			resource1 = (IResource) dialog.getFirstResult();
          			String arg1 = resource1.getFullPath().toString();
          			inputMetaModel = resource1.getLocation().toString();
          			displayFiles(new String[] { arg1}, text1);  
          	  }
          	});         

          	new Label(grpModelProperties1, SWT.NULL).setText("Output Metamodel:");
            
            Text text2 = new Text(grpModelProperties1, SWT.NONE);
            text2.setLayoutData(data);
            	  
            new Label(grpModelProperties1, SWT.NONE);
            Button button2 = new Button(grpModelProperties1, SWT.PUSH);
            button2.setText("Browse Workspace...");
            	  
            final GridData button2Data = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
            button2.setLayoutData(button2Data);
            button2.addSelectionListener(new SelectionAdapter() {
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
                	outputMetaModel = resource2.getLocation().toString();
                	displayFiles(new String[] { arg2}, text2);
                	}
            });
            	  
            setControl(composite);
        }
      
        public void displayFiles(String[] files, Text text) {
        	for (int i = 0; files != null && i < files.length; i++) {
        		text.setText(files[i]);
        		text.setEditable(true);
      	  	}
      	}
        
        public IWizardPage getPreviousPage(IWizardPage page) {
    		// TODO Auto-generated method stub
    		return null;
    	}
        
		public String getText2() {
			return inputMetaModel;
		}
		
		public String getText3() {
			return outputMetaModel;
		}
		
}