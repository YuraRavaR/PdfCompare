package pdfCompare;

import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.PageArea;
import de.redsix.pdfcompare.PdfComparator;
import de.redsix.pdfcompare.env.SimpleEnvironment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A utility class for comparing PDF files.
 */
public class PdfCompare {
    /**
     * Compares two PDF files for equality.
     *
     * @param expectedFilePath The file path to the expected PDF file.
     * @param actualFilePath   The file path to the actual PDF file.
     * @return True if the PDF files are equal, false otherwise.
     */
    public boolean comparePdf(String expectedFilePath, String actualFilePath) {
        PdfComparator comparator = new PdfComparator(expectedFilePath, actualFilePath);
        CompareResult result = null;
        try {
            result = comparator.compare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.isEqual();
    }

    /**
     * Compares two PDF files while ignoring specified areas.
     *
     * @param expectedFilePath The file path to the expected PDF file.
     * @param actualFilePath   The file path to the actual PDF file.
     * @param exclusions       A list of PageArea objects representing areas to be ignored.
     *                         Each PageArea object specifies a rectangular region on a page.
     *                         - page: Page number to which the exclusion applies (optional).
     *                         *                           - x1: Horizontal starting coordinate of the area to be ignored.
     *                         *                           - y1: Vertical starting coordinate of the area to be ignored.
     *                         *                           - x2: Horizontal ending coordinate of the area to be ignored.
     *                         *                           - y2: Vertical ending coordinate of the area to be ignored.
     * @return True if the PDF files are equal after ignoring specified areas, false otherwise.
     */
    public boolean comparePdfWithIgnoredAreas(String expectedFilePath, String actualFilePath, List<PageArea> exclusions) {
        PdfComparator comparator = new PdfComparator(expectedFilePath, actualFilePath);

        if (exclusions != null && !exclusions.isEmpty()) {
            for (PageArea exclusion : exclusions) {
                comparator = comparator.withIgnore(exclusion);
            }
        }
        CompareResult result = null;
        try {
            result = comparator.compare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.isEqual();
    }

    /**
     * Writes a new PDF file highlighting the differences between the expected and actual files.
     */
    public void writePdfWithDifference(String expectedPdfFilePath, String actualPdfFilePath, String diffPdfFilePath) {
        try {
            new PdfComparator(expectedPdfFilePath, actualPdfFilePath).compare().writeTo(diffPdfFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Compares two PDF files and returns a map of page numbers to the percentage of differences on each page.
     * Writes a new PDF file highlighting the differences.
     */
    public Map<Integer, Double> getDiffPercentWritePdfWithDifference(String expectedPdfFilePath, String actualPdfFilePath, String diffPdfFilePath) {
        try {
            PdfComparator comparator = new PdfComparator(expectedPdfFilePath, actualPdfFilePath);
            CompareResult result = comparator.compare();
            result.writeTo(diffPdfFilePath);
            return result.getPageDiffsInPercent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
