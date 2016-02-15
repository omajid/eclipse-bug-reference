package com.github.omajid.bugreference.preferences;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

public class PreferenceParser {

    private static final String ROOT = "bug-reference-preferences";
    private static final String ENTRY = "bug-reference";
    private static final String FORMAT = "format";
    private static final String URL = "url";

    private PreferenceParser() {
    }

    public static Map<String, String> parseBugUrlsFromPreferences(String input) {
        Map<String, String> result = new HashMap<>();
        try {
            XMLMemento mem = XMLMemento.createReadRoot(new StringReader(input));
            for (IMemento entry : mem.getChildren(ENTRY)) {
                String format = entry.getString(FORMAT);
                String url = entry.getString(URL);
                result.put(format, url);
            }
        } catch (WorkbenchException e) {
            // TODO log me
        }
        return result;
    }

    public static String serializeBugUrlsToPreferences(Map<String, String> expressions) {
        StringWriter writer = new StringWriter();
        try {
            XMLMemento mem = XMLMemento.createWriteRoot(ROOT);
            for (Entry<String, String> entry : expressions.entrySet()) {
                IMemento child = mem.createChild(ENTRY);
                child.putString(FORMAT, entry.getKey());
                child.putString(URL, entry.getValue());
            }
            mem.save(writer);
        } catch (IOException e) {
            // TODO log me
        }
        return writer.toString();
    }
}
