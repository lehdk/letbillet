package dk.letbillet.BLL;

import com.lowagie.text.DocumentException;
import dk.letbillet.database.VoucherDatabaseDAO;
import dk.letbillet.entity.IssuedVoucher;
import dk.letbillet.entity.VoucherType;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dk.letbillet.util.PDFGenerator.*;

public class VoucherManager {

    private final VoucherDatabaseDAO databaseDAO;

    public VoucherManager() {
        databaseDAO = new VoucherDatabaseDAO();
    }

    public ArrayList<VoucherType> getAllVoucherTypes() {
        return databaseDAO.getAllVoucherTypes();
    }

    public VoucherType createVoucherType(String name) throws SQLException {
        return databaseDAO.createVoucherType(name);
    }

    public List<IssuedVoucher> issueVouchers(VoucherType selectedItem, int amount) throws SQLException {
        List<IssuedVoucher> vouchers = databaseDAO.issueVouchers(selectedItem, amount);

        generatePdfFromVouchers(vouchers);

        return vouchers;
    }

    private void generatePdfFromVouchers(List<IssuedVoucher> vouchers) {
        if(vouchers == null ||vouchers.size() < 1) return;

        vouchers.parallelStream().forEach(v -> {
            try {
                var variables = new HashMap<String, String>();
                variables.put("event_name", "any event");
                variables.put("voucher_type", v.getVoucherType().getName());
                variables.put("voucher_guid", v.getGuid());

                String html = parseThymeleafTemplate(VOUCHER_TEMPLATE, variables);

                String outputFolder = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + v.getGuid() + ".pdf";
                OutputStream outputStream = new FileOutputStream(outputFolder);

                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(html);
                renderer.layout();
                renderer.createPDF(outputStream);

                outputStream.close();
            } catch(IOException ioe) {
                System.out.println("Failed to write file for voucher: " + v.getGuid());
            } catch (DocumentException de) {
                System.out.println("Failed to generate PDF file for voucher: " + v.getGuid());
            }
        });
    }
}
