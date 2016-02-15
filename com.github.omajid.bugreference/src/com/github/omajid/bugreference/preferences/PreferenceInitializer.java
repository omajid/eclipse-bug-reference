package com.github.omajid.bugreference.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.github.omajid.bugreference.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
	    Map<String, String> expressions = createDefaults();

	    String serialized = PreferenceParser.serializeBugUrlsToPreferences(expressions);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BUGS, serialized);
	}

    private Map<String, String> createDefaults() {
        Map<String, String> expressions = new HashMap<>();
	    expressions.put("RFC[ -]?(\\d+)", "https://tools.ietf.org/html/rfc%s");
        expressions.put("CVE[: -](\\d+-\\d+)", "http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-%s");
        expressions.put("JDK[ -]?(\\d+)", "https://bugs.openjdk.java.net/browse/JDK-%s");
        expressions.put("ECLIPSE[ -]?(\\d+)", "https://bugs.eclipse.org/bugs/show_bug.cgi?id=%s");
        return expressions;
    }

}
