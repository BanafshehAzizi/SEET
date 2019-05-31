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
import org.eclipse.swt.custom.ScrolledComposite;
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

import anatlyzer.atl.witness.IWitnessModel;
import anatlyzer.atl.witness.IWitnessVisualizer;
import anatlyzer.atl.witness.WitnessUtil;
import seet.time.CPUUtils;
import seet.ui.StartWizard;

public class ResultModel extends TitleAreaDialog {

	ArrayList<String> AllPC;
	String elapsedTime;
	ArrayList<IWitnessModel> model;
	private Composite composite;
	private Group grpModelProperties1;
	private Composite innerComposite;
	/**
	 * Create the dialog.
	 */
	public ResultModel(Shell parentShell,ArrayList<String> AllPC, ArrayList<IWitnessModel> model) {
		   super(parentShell);
		   this.AllPC = AllPC;
		   this.model = model;
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
 		composite = (Composite) super.createDialogArea(parent);
 		setTitle("Result");
 		composite.setLayout(new GridLayout());
 	 	GridData data = new GridData(GridData.FILL_HORIZONTAL);
 	 	Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
 	 	grpModelProperties.setLayout(new GridLayout(2, false));
 	 	grpModelProperties.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
 	 	Label l3 = new Label(grpModelProperties, SWT.NULL);
 	 	l3.setLayoutData(data);
 	 	new CPUUtils();
		long stopTime = CPUUtils.getUserTime();
 	 	l3.setText("Time: " + (stopTime - StartWizard.startTime) /1000000 +" ms");  
  		new Label(grpModelProperties, SWT.NONE);
  		Label l4 = new Label(grpModelProperties, SWT.NULL);
  		l4.setText("Symbolic metamodel footprint is created.");
  	
  		Label line1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL );
  		line1.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

  		Group grpModelProperties3 = new Group(composite, SWT.SHADOW_IN);
  		GridData data2 = new GridData(SWT.FILL, SWT.TOP, true, false);
  		data2.heightHint = 120;
  		grpModelProperties3.setLayoutData(data2);
  		grpModelProperties3.setLayout(new GridLayout());
  		grpModelProperties3.setText("Executable paths details");

  		// Within the Group, create the ScrolledComposite
  		ScrolledComposite scrolledComposite1 = new ScrolledComposite(grpModelProperties3, SWT.H_SCROLL | SWT.V_SCROLL);
  		scrolledComposite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
  		scrolledComposite1.setExpandHorizontal(true);
  		scrolledComposite1.setExpandVertical(true);
  		// Create the scrolled content (the inner composite)
  		Composite innerComposite1 = new Composite(scrolledComposite1, SWT.NONE);
  		innerComposite1.setLayout(new GridLayout());
  		for (int i = 0 ; i < AllPC.size(); i++)
  		{
  			Group grpModelProperties2= new Group(innerComposite1, SWT.SHADOW_IN);
  			grpModelProperties2.setLayout(new GridLayout());
  			grpModelProperties2.setLayoutData(new GridData());
  			Label l2 = new Label(grpModelProperties2, SWT.NULL);
  			
  			l2.setLayoutData(data);
  			l2.setText("Path Condition" + (i+1) + ": " + AllPC.get(i));
    
  			Label line = new Label(grpModelProperties2, SWT.SEPARATOR | SWT.HORIZONTAL );
  			line.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
  			grpModelProperties1 = new Group(grpModelProperties2, SWT.SHADOW_IN);
  			GridData data1 = new GridData(SWT.FILL, SWT.TOP, true, false);
  			data1.heightHint = 120;
  			grpModelProperties1.setLayoutData(data1);
  			grpModelProperties1.setLayout(new GridLayout());
  			grpModelProperties1.setText("Test Model");

  			// Within the Group, create the ScrolledComposite
  			ScrolledComposite scrolledComposite = new ScrolledComposite(grpModelProperties1, SWT.H_SCROLL | SWT.V_SCROLL);
  			scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
  			scrolledComposite.setExpandHorizontal(true);
  			scrolledComposite.setExpandVertical(true);
  			// Create the scrolled content (the inner composite)
  			innerComposite = new Composite(scrolledComposite, SWT.NONE);
  			innerComposite.setLayout(new GridLayout());
  			try {
  			drawWitness(model.get(i));
  			}
  			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  			scrolledComposite.setContent(innerComposite);
  			scrolledComposite.setMinSize(innerComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
  		}
   scrolledComposite1.setContent(innerComposite1);
   scrolledComposite1.setMinSize(innerComposite1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
   return composite;
 }
	
 private void drawWitness(IWitnessModel witness) {
	IWitnessVisualizer visualizer = WitnessUtil.getWitnessVisualizer(witness);
	for (Control ctrl : innerComposite.getChildren()) {
		ctrl.dispose();
	}
	visualizer.render(innerComposite);
	innerComposite.pack();
 }

 protected void createButtonsForButtonBar(Composite parent) {
    	createButton(parent, IDialogConstants.OK_ID,
            "Evaluate More", true);
        createButton(parent, IDialogConstants.CANCEL_ID,
            IDialogConstants.FINISH_LABEL, false);
 }
 
  protected Point getInitialSize() {
      return new Point(520, 409);
  }
	
  @Override
  protected void okPressed() {
  	new interfaceRule().showEvaluation();
    super.okPressed();
  }

}