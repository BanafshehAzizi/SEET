package seet.ui;

import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import seet.standalone.CFGGenerator;
import seet.standalone.EolStandalone;
import seet.time.CPUUtils;
import org.eclipse.epsilon.haetae.ast2exl.*;

public class StartWizard extends Wizard implements IObjectActionDelegate{
	FrontPage front;
	WizardDialog dialog;
	StartWizard wizard;
	public static long startTime;
	
    public StartWizard() {
    	new CPUUtils();
		startTime = CPUUtils.getUserTime();
        setWindowTitle("SEET");
        setNeedsProgressMonitor(true);
        TrayDialog.setDialogHelpAvailable(false);
        Bundle bundle = Platform.getBundle("seet.engine");
        URL url = FileLocator.find(bundle, new Path("icon/Configuration.png"), null);
        ImageDescriptor desc = ImageDescriptor.createFromURL(url);
        setDefaultPageImageDescriptor(desc);
    }

    public void addPages() {
    	Shell shell = getContainer().getShell();
    	Bundle bundle = Platform.getBundle("seet.engine");
 	    URL url = FileLocator.find(bundle, new Path("icon/Logo.png"), null);
 	    ImageDescriptor desc = ImageDescriptor.createFromURL(url);
 	    Image image = desc.createImage();
    	shell.setImage(image);
    	
    	AST2ETLAction etltomodel = new AST2ETLAction();
    	front = new FrontPage(etltomodel.ETLCodetoModel());
        addPage(front);
    }
    
    public boolean performFinish() {
    	if (FrontPage.inputModel != null)
    	{
	    	try {
				new CFGGenerator (front).execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	try {
		    	getShell().setVisible(false);
		    	new EolStandalone(front).execute();
			}
		    catch(EolRuntimeException a){
		    	IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow win =wb.getActiveWorkbenchWindow();
				ResultError UD = new ResultError(win.getShell(), a.getMessage().substring(0, a.getMessage().indexOf("\n")));
				UD.open();
		    }
		    catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   return true;
    }

    public boolean performCancel() {
        boolean ans = MessageDialog.openConfirm(getShell(), "Confirmation",
                        "Are you sure to cancel the task?");
        if(ans)
            return true;
        else return false;
    }
    
    public Boolean canFlipToNextPage(){
    	return false;
    }

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		wizard = new StartWizard();
		dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		dialog.setBlockOnOpen(true);
		dialog.open();
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	} 
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		// TODO Auto-generated method stub
		return null;
	}

}