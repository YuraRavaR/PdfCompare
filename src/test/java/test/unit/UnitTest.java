package test.unit;

import de.redsix.pdfcompare.PageArea;
import org.testng.Assert;
import org.testng.annotations.Test;
import pdfCompare.PdfCompare;
import java.util.Collections;
import java.util.List;

import static test.unit.Constant.*;

public class UnitTest {
    @Test
    public void testComparePdf_shouldReturnTrueForEqualFiles() {
        PdfCompare pdfCompare = new PdfCompare();
        boolean result = pdfCompare.comparePdf(FIlE_1, FILE_2);
        Assert.assertTrue(result);
    }

    @Test
    public void testComparePdf_shouldReturnFalseForDifferentFiles() {
        PdfCompare pdfCompare = new PdfCompare();
        boolean result = pdfCompare.comparePdf(FIlE_1, FILE_3);
        Assert.assertFalse(result);
    }

    @Test
    public void testComparePdfWithIgnoredAreas_shouldReturnTrueForEqualFilesWithIgnoredAreas() {
        PdfCompare pdfCompare = new PdfCompare();
        List<PageArea> exclusions = Collections.singletonList(new PageArea(1));
        boolean result = pdfCompare.comparePdfWithIgnoredAreas(FIlE_1, FILE_3, exclusions);
        Assert.assertTrue(result);
    }

    @Test
    public void testComparePdfWithIgnoredAreas_shouldReturnFalseForDifferentFilesWithIgnoredAreas() {
        PdfCompare pdfCompare = new PdfCompare();
        List<PageArea> exclusions = Collections.singletonList(new PageArea(1, 10, 20, 30, 40));
        boolean result = pdfCompare.comparePdfWithIgnoredAreas(FIlE_1, FILE_4, exclusions);
        Assert.assertFalse(result);
    }

}
