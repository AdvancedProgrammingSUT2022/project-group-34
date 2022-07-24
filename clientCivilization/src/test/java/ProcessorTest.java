import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import app.models.connection.Processor;

public class ProcessorTest {
    private Processor processor;

    @Test
    public void checkInvalidCommand() {
        processor = new Processor("jjkhaf --jkfauh j -uhaf iuhaf");

        Assertions.assertFalse(processor.isValid());
    }


    @Test
    public void checkCompleteFormCommand() {
        processor = new Processor("Category   Section  SubsecTIon --first abc  def  --second ghi");

        Assertions.assertEquals(processor.getCategory(), "category");
        Assertions.assertEquals(processor.getSection(), "section");
        Assertions.assertEquals(processor.getSubSection(), "subsection");
        Assertions.assertEquals(processor.get("first"), "abc def");
        Assertions.assertEquals(processor.get("second"), "ghi");
        Assertions.assertEquals(processor.getNumberOfFields(), 2);
    }


    @Test
    public void checkCombinedFormCommand() {
        processor = new Processor("Category   Section  SubsecTIon   -h ghi --first abc  def   ");

        Assertions.assertEquals(processor.getCategory(), "category");
        Assertions.assertEquals(processor.getSection(), "section");
        Assertions.assertEquals(processor.getSubSection(), "subsection");
        Assertions.assertEquals(processor.get("first"), "abc def");
        Assertions.assertEquals(processor.get("help"), "ghi");
        Assertions.assertEquals(processor.getNumberOfFields(), 2);
    }
}
