package seet.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ResultError extends TitleAreaDialog {

	String Error;
	
	/**
	 * Create the dialog.
	 */
	public ResultError(Shell parentShell,String Error) {
		   super(parentShell);
		   this.Error = Error; 
	}
	
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("SEET");
    }
	
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent); 
        Composite composite = new Composite(area, SWT.NULL);
    	setTitle("Result");
    	composite.setLayout(new RowLayout(SWT.HORIZONTAL));        
    	Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
    	grpModelProperties.setLayout(new RowLayout(SWT.VERTICAL));
    	Label l1 = new Label(grpModelProperties, SWT.NULL);
    	new Label(grpModelProperties, SWT.NONE);
    	Label l2 = new Label(grpModelProperties, SWT.NULL);
     	l1.setText("Sorry, You have the runtime error:");
     	l2.setText(Error); 
        return area;
    }
  
	protected void createButtonsForButtonBar(Composite parent) {  		
        createButton(parent, IDialogConstants.CANCEL_ID,
            IDialogConstants.FINISH_LABEL, false);
    }
	
    protected Point getInitialSize() {
    	return new Point(525, 409);
    }

}