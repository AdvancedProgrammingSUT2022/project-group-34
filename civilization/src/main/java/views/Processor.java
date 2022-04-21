//besmellah

package views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Processor {
    private static final String FIELD_NAME_REGEX = "((--[a-zA-Z\\d]+)|(-[a-zA-Z]))";
    private static final String VALIDITY_REGEX = "^\\S+( \\S+)?( \\S+)? (" + FIELD_NAME_REGEX + "[a-zA-Z\\d/ ]*)*?$";
    /* A valid command :
    category(\S) section(\S, optional) subsection(\S, optional) fields(optional)
    fields : --fieldName|-f [a-zA-Z\d/ ]*
     */
    private boolean validity;
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
        ArrayList<String> commandParse = new ArrayList<>(Arrays.asList(command.split("\\S")));
        String fieldName = null;
        for (int i = 0; i < commandParse.size(); i++) {
            String string = commandParse.get(i);
            if (string.matches(FIELD_NAME_REGEX)) {
                fields.put(string, null);
                fieldName = string;
            }
            else if (fieldName == null) {
                if (i == 0) category = string;
                else if (i == 1) section = string;
                else if (i == 2) subSection = string;
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
}