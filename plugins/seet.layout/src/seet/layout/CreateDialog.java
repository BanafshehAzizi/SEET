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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
 
public class CreateDialog extends TitleAreaDialog {
	String result;
	public ArrayList<String> choose;
	public Button[] radios;
	String title;
	String message;
	
	public CreateDialog(Shell parentShell, String title, String message, ArrayList<String> choose) {
        super(parentShell);
        this.title = title;
        this.message = message;
        this.choose = choose;
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
        setTitle(title);
        setMessage(message);

        Composite composite = new Composite(area, SWT.NULL);       
        composite.setLayout(new RowLayout(SWT.HORIZONTAL));
      
        Font font= composite.getFont();
        Group grpModelProperties = new Group(composite, SWT.SHADOW_IN);
        if(title == "Rule Selection")
          	grpModelProperties.setText("Rule");
        else
        	grpModelProperties.setText("Mode");  	
        grpModelProperties.setLayout(new RowLayout(SWT.VERTICAL));
        grpModelProperties.setFont(font);
       
        radios = new Button[choose.size()];
        for(int i=0 ; i<choose.size(); i++)
        {
        	radios[i] = new Button(grpModelProperties, SWT.RADIO);
        	if (i == 0)
        		radios[i].setSelection(true);
            radios[i].setText(choose.get(i));
        }
    		return area;
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
    	for(int i=0; i<choose.size(); i++)
    	{
    		if(radios[i].getSelection()==true)
    		{
    			result = choose.get(i) ; 
    			break ; 
    		}
    	}	
        super.okPressed();
    }
}