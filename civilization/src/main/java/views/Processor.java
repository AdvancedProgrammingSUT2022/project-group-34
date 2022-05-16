//besmellah

package views;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Processor {
    private static final String DOUBLE_DASH_FIELD_NAME = "(--[a-zA-Z\\d]+)";
    private static final String ONE_DASH_FIELD_NAME = "(-[a-zA-Z])";
    private static final String FIELD_NAME_REGEX = "(" + ONE_DASH_FIELD_NAME + "|" + DOUBLE_DASH_FIELD_NAME + ")";
    private static final String VALIDITY_REGEX = "^\\S+(\\s+\\S+)?(\\s+\\S+)?(\\s+" + FIELD_NAME_REGEX + " [a-zA-Z\\d/ ]*)*$";
    //TODO REDO SOME MISTAKES

    /* A valid command :
    category(\S) section(\S, optional) subsection(\S, optional) fields(optional)
    fields : --fieldName|-f [a-zA-Z\d/ ]*
    if there are many similar field names, the last one's value overrides.
    */
    private final boolean validity;
    private String category;
    private String section;
    private String subSection;
    private HashMap<String, String> fields = new HashMap<>();

    public Processor(String command) {
        if (!command.matches(VALIDITY_REGEX)) {
            validity = false;
            category = null;
            section = null;
            subSection = null;
            fields = null;
            return;
        }
        ArrayList<String> commandParse = new ArrayList<>(Arrays.asList(command.trim().split("\\s+")));
        String fieldName = null;
        for (int i = 0; i < commandParse.size(); i++) {
            String string = commandParse.get(i);
            if (string.matches(FIELD_NAME_REGEX)) {
                if (string.matches(DOUBLE_DASH_FIELD_NAME)) fieldName = string.substring(2);
                else fieldName = getCompleteForm(string.charAt(1));
                fields.put(fieldName, null);
            }
            else if (fieldName == null) {
                if (i == 0) category = string.toLowerCase();
                else if (i == 1) section = string.toLowerCase();
                else if (i == 2) subSection = string.toLowerCase();
                else {
                    validity = false;
                    return;
                }
            }
            else {
                if (commandParse.get(i - 1).matches(FIELD_NAME_REGEX)) fields.replace(fieldName, string);
                else fields.replace(fieldName, fields.get(fieldName) + " " + string);
            }
        }
        validity = true;
    }

    public boolean isValid() {
        return validity;
    }

    public String getCategory() {
        return category;
    }

    public String getSection() {
        return section;
    }

    public String getSubSection() {
        return subSection;
    }

    public String get(String fieldName) {
        return fields.get(fieldName);
    }

    public int getNumberOfFields(){
        return fields.size();
    }


    private String getCompleteForm(char c) {
        // This is not static because maybe in future there will be need to return complete form in terms of category.
        if (c == 'u') return "username";
        else if (c == 'p') return "password";
        else if (c == 'n') return "nickname";
        else if (c == 'x') return "x";
        else if (c == 'y') return "y";
        else if (c == 'c') return "c";
        else if (c == 'h') return "help";
        else return "randomInvalidFieldName";
        //TODO... Complete these.
    }
}
