//besmellah

package views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Processor {
    private static final String VALIDITY_REGEX = "^\\S+( \\S+)?( \\S+)? (((--[a-zA-Z\\d]+)|(-[a-zA-Z])) [a-zA-Z\\d/ ]*)*?$";
    /* A valid command :
    category(\S) section(\S, optional) subsection(\S, optional) fields(optional)
    fields : --fieldName|-f [a-zA-Z\d/ ]*
     */

    private String category;
    private String section;
    private String subSection;
    private HashMap<String, String> fields;
    private boolean validity;

    public Processor(String command) {
        ArrayList<String> commandParse = new ArrayList<String>((Arrays.asList(command.split("\\S"))));

    }
}