
package com.github.omajid.bugreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.URLHyperlink;

import com.github.omajid.bugreference.preferences.PreferenceConstants;
import com.github.omajid.bugreference.preferences.PreferenceParser;

public class LinkDetector extends AbstractHyperlinkDetector {

    private Map<String, String> expressions = new HashMap<>();

    public LinkDetector() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        expressions = PreferenceParser.parseBugUrlsFromPreferences(store.getString(PreferenceConstants.P_BUGS));
    }

    @Override
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        IDocument document = textViewer.getDocument();
        int offset = region.getOffset();

        try {
            IRegion lineRegion = document.getLineInformationOfOffset(offset);
            String candidate = document.get(lineRegion.getOffset(), lineRegion.getLength());

            List<IHyperlink> result = new ArrayList<>();
            for (Entry<String, String> entry : expressions.entrySet()) {
                result.addAll(findLinks(lineRegion, candidate, entry));
            }
            return result.isEmpty() ? null : result.toArray(new IHyperlink[0]);
        } catch (BadLocationException ex) {
            return null;
        }
    }

    private List<IHyperlink> findLinks(IRegion region, String candidate, Entry<String, String> entry) {
        List<IHyperlink> result = new ArrayList<>();
        String regex = entry.getKey();
        String url = entry.getValue();
        Matcher matcher = Pattern.compile(regex).matcher(candidate);
        while (matcher.find()) {
            int start = matcher.start(0);
            int end = matcher.end(0);
            String id = matcher.group(1);
            IRegion targetRegion = new Region(region.getOffset() + start, (end - start));
            System.out.println("Found match for '" + regex + "' at " + targetRegion.toString());
            result.add(new URLHyperlink(targetRegion, String.format(url, id)));
        }
        return result;
    }

}
