package com.github.omajid.bugreference.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.github.omajid.bugreference.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private static final String SEPARATOR = ": ";

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Automatically link bug numbers to URLs");
	}

	public void createFieldEditors() {
		addField(new MyEditor(PreferenceConstants.P_BUGS, 
                "&Bugs and URLs:", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}

	class MyEditor extends ListEditor {

        public MyEditor(String name, String label, Composite parent) {
            super(name, label, parent);
        }

        @Override
        protected String createList(String[] items) {
            Map<String, String> prefs = new HashMap<>();
            for (String item : items) {
                String[] parts = item.split(Pattern.quote(SEPARATOR));
                prefs.put(parts[0], parts[1]);
            }
            return PreferenceParser.serializeBugUrlsToPreferences(prefs);
        }

        @Override
        protected String getNewInputObject() {
            BugUrlDialog dialog = new BugUrlDialog(getShell());
            dialog.setBlockOnOpen(true);
            int result = dialog.open();
            if (result == IStatus.OK) {
                return dialog.getBugPattern() + SEPARATOR + dialog.getUrl();
            } else {
                return null;
            }
        }

        @Override
        protected String[] parseString(String stringList) {
            Map<String, String> prefs = PreferenceParser.parseBugUrlsFromPreferences(stringList);
            List<String> result = new ArrayList<>(prefs.size());
            for (Entry<String, String> entry : prefs.entrySet()) {
                result.add(entry.getKey() + SEPARATOR + entry.getValue());
            }
            return result.toArray(new String[0]);
        }

	}
	
}