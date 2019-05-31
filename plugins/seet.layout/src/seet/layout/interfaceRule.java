package seet.layout;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import anatlyzer.atl.witness.IWitnessModel;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

public class interfaceRule {

	public String callMode() {	
		ArrayList<String> choose = new ArrayList<String>();
		choose.add("Automatic");
		choose.add("Interactive");
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		CreateDialog UD = new CreateDialog(win.getShell(), "Mode Selection", "Select the symbolic execution mode. If you select Interactive mode you will be able to determine the satisfiablity of the conditions by yourselves.", choose);
		UD.open();
		String message = UD.result ;
	    return message;
	}
	
	public String callGuard(String guard, Boolean cond, String ruleName) {
		ArrayList<String> choose = new ArrayList<String>();
		choose.add("Yes");
		choose.add("No");	
		Boolean n = false;
		if (guard.substring(0, 4).equals("not "))
		{
			n = true;
			guard = guard.substring(4, guard.length());
		}
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win =wb.getActiveWorkbenchWindow();
		CreateDialog UD;
		if(cond)
			UD = new CreateDialog(win.getShell(), "Guard", "Is \"" + guard + "\" guard of \""+ ruleName + "\" rule true?", choose);
		else
		{
			int count = guard.length() - guard.replaceAll("\\.","").replaceAll(" ", "").length();
			if (count == 0)
				UD = new CreateDialog(win.getShell(), "Entry Condition", "Is there any instance of \"" + guard + "\" for \"" + ruleName + "\" rule?", choose);
			else
				UD = new CreateDialog(win.getShell(), "Condition", "Is \"" + guard + "\" condition of \"" + ruleName + "\" rule true?", choose);
		}
		UD.open();		
	    String message = UD.result ;
	    if (n)
	    {
	    	if (message == "Yes")
	    		return "No";
	    	else
	    		return "Yes";
	    }
	    return message;
	}
	
	public void showResultModel(ArrayList<String> AllPC, ArrayList<IWitnessModel> model) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    	ISelection selection = window.getSelectionService().getSelection();
    	IFile file = (IFile) ((IStructuredSelection) selection).getFirstElement();
    	IPath path = file.getLocation();
    	String[] parts = path.toString().split("/");
		String path1 = "";
		
		for (Integer i = 0; i< parts.length-1; i++)
		{
			path1 += parts[i] + "/";
		}
		File pc = new File(path1 + "PC.txt");
		try {
			pc.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		FileWriter write = new FileWriter( path1 + "PC.txt" , true);
		PrintWriter print_line = new PrintWriter( write );
		PrintWriter writer = new PrintWriter(path1 + "PC.txt", "UTF-8");
		for (int i = 0 ; i < AllPC.size(); i++)
  		{
			writer.println("Path Condition" + (i+1) + ": " + AllPC.get(i));
  		}
		writer.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win =wb.getActiveWorkbenchWindow();
		ResultModel UD = new ResultModel(win.getShell(), AllPC, model);
		UD.open();
	}
	
	public void showResult(ArrayList<String> AllPC, String message) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    	ISelection selection = window.getSelectionService().getSelection();
    	IFile file = (IFile) ((IStructuredSelection) selection).getFirstElement();
    	IPath path = file.getLocation();
    	String[] parts = path.toString().split("/");
		String path1 = "";
		
		for (Integer i = 0; i< parts.length-1; i++)
		{
			path1 += parts[i] + "/";
		}
		File pc = new File(path1 + "PC.txt");
		try {
			pc.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		FileWriter write = new FileWriter( path1 + "PC.txt" , true);
		PrintWriter print_line = new PrintWriter( write );
		PrintWriter writer = new PrintWriter(path1 + "PC.txt", "UTF-8");
		for (int i = 0 ; i < AllPC.size(); i++)
  		{
			writer.println("Path Condition" + (i+1) + ": " + AllPC.get(i));
  		}
		writer.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win =wb.getActiveWorkbenchWindow();
		Result UD = new Result(win.getShell(), AllPC, message);
		UD.open();
	}
	
	public void showFinalResult(Boolean result) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win =wb.getActiveWorkbenchWindow();
		FinalResult UD = new FinalResult(win.getShell(), result);
		UD.open();
	 }
	
	public void showEvaluation() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win =wb.getActiveWorkbenchWindow();
		Evaluation UD = new Evaluation(win.getShell());
		UD.open();
	 }
	
}
