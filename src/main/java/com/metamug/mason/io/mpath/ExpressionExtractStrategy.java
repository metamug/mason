package com.metamug.mason.io.mpath;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert MPath to Expression Language Syntax
 *
 * @author anishhirlekar
 */
public class ExpressionExtractStrategy extends ExtractStrategy<Map<String, Object>> {

    /**
     *
     * @param path
     * @param target This will be mostly null.
     * @return
     */
    @Override
    public String extract(String path, Map<String, Object> target) {

        String element, expn;
        Pattern p = Pattern.compile("\\$\\[(.+?)\\](.*?)");
        Matcher m = p.matcher(path);

        if (target.isEmpty()) {
            throw new IllegalStateException("Bus is empty.");
        }

        if (m.find()) {
            element = m.group(1);
            expn = m.group(2);
        } else {
            throw new IllegalArgumentException(path + " not valid for generating expression");
        }

        if (target.get(element) == null) {
            throw new NoSuchElementException("Mason Bus does not contain the element mentioned in the path");
        }

        return "${masonBus['" + element + "']" + expn + "}";
    }

}
