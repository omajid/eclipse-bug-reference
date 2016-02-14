package com.github.omajid.bugreference.preferences;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class BugUrlDialog extends TitleAreaDialog {

    private Text bugPatternText;
    private Text urlText;

    private String bugPattern;
    private String url;

    public BugUrlDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    public void create() {
        super.create();
        setTitle("Add a link");
        setMessage("Add a regular expression and a URL as a format String. The regular expression should capture - as group 1 - the identifier that should be substituted in the URL", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createBugPattern(container);
        createUrl(container);

        return area;
    }

    private void createBugPattern(Composite container) {
        Label lbtBugPattern = new Label(container, SWT.NONE);
        lbtBugPattern.setText("Bug Pattern");

        GridData databugPattern = new GridData();
        databugPattern.grabExcessHorizontalSpace = true;
        databugPattern.horizontalAlignment = GridData.FILL;

        bugPatternText = new Text(container, SWT.BORDER);
        bugPatternText.setLayoutData(databugPattern);
    }

    private void createUrl(Composite container) {
        Label lbtUrl = new Label(container, SWT.NONE);
        lbtUrl.setText("URL Format");

        GridData dataUrl = new GridData();
        dataUrl.grabExcessHorizontalSpace = true;
        dataUrl.horizontalAlignment = GridData.FILL;
        urlText = new Text(container, SWT.BORDER);
        urlText.setLayoutData(dataUrl);
    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    private void saveInput() {
        bugPattern = bugPatternText.getText();
        url = urlText.getText();
    }

    public String getBugPattern() {
        // return "JDK-(\\d+)";
        return bugPattern;
    }

    public String getUrl() {
        // return "https://bugs.openjdk.java.net/browse/JDK-%s";
        return url;
    }

}
