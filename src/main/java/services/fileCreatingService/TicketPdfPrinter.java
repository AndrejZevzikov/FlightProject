package services.fileCreatingService;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Ticket;
import exceptions.InvalidTicketPassengerException;
import exceptions.TicketNotAvailableException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class TicketPdfPrinter {
    private Document document = new Document();
    private Ticket ticket;

    public void createTicketPDF(Ticket ticket) throws IOException, DocumentException, URISyntaxException, InvalidTicketPassengerException {

        if (ticket.getPassenger() == null
                || ticket.getPassenger().getDayOfBirth() == null
                || ticket.getPassenger().getFullName().equals("")
                || ticket.getPassenger().getIdentityNumber() == null){
            throw new InvalidTicketPassengerException("Passenger information is invalid");
        }
            this.ticket = ticket;
        PdfWriter.getInstance(document, new FileOutputStream("src\\main\\resources\\ticket.pdf"));

        document.open();
        document.add(setUpGoogleTitle());
        document.add(setUpGoogleInfo());
        document.add(setUpGoogleLink());
        document.add(setUpRectangle());
        document.add(setUpQRCode());
        document.add(setUpPlaneImage());
        document.add(setUpTicketTitle());
        document.add(setUpFlightDetails());
        document.add(setUpThanks());
        document.close();
    }

    public File getTicketFile(Ticket ticket) throws DocumentException, IOException, URISyntaxException, TicketNotAvailableException, InvalidTicketPassengerException {
        if (ticket.getFlight().getFlightTime()
                .after(Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
            throw new TicketNotAvailableException("Ticket not Available at this time");
        }
        createTicketPDF(ticket);
        return new File("C:\\Users\\andre\\Downloads\\TestFx\\src\\main\\resources\\ticket.pdf");
    }

    private Rectangle setUpRectangle() {
        Rectangle ticketRectangle = new Rectangle(600, 400, 100, 700);
        ticketRectangle.setTop(650);
        ticketRectangle.setBottom(450);
        ticketRectangle.setLeft(50);
        ticketRectangle.setRight(550);
        ticketRectangle.setBorderWidth(1F);
        ticketRectangle.setBorderColor(BaseColor.BLUE);
        ticketRectangle.setBorder(15);
        ticketRectangle.setBackgroundColor(new BaseColor(1, 1, 1, 20));
        return ticketRectangle;
    }

    private Image setUpQRCode() throws BadElementException, IOException, URISyntaxException {
        Image QRCode = Image.getInstance(Paths.get(ClassLoader.getSystemResource("images/qr_code_barcode.jpg").toURI()).toAbsolutePath().toString());
        QRCode.scaleToFit(100, 100);
        QRCode.setAbsolutePosition(400, 500);
        return QRCode;
    }

    private Image setUpPlaneImage() throws DocumentException, IOException, URISyntaxException {
        Image planeImage = Image.getInstance(Paths.get(
                ClassLoader.getSystemResource("images/OIP.jpg").toURI()).toAbsolutePath().toString());
        planeImage.scaleToFit(300, 100);
        planeImage.setAbsolutePosition(50, 700);
        return planeImage;
    }

    private Paragraph setUpTicketTitle() {
        Paragraph ticketTitle = new Paragraph();
        Chunk title = new Chunk("Ticket Number: GFLY-" + ticket.getId(),
                new Font(Font.FontFamily.HELVETICA, 16, 2, BaseColor.BLACK));
        ticketTitle.add(title);
        ticketTitle.setIndentationLeft(170);
        ticketTitle.setSpacingBefore(80);
        return ticketTitle;
    }

    private Paragraph setUpFlightDetails() {
        Paragraph flightInfoParagraph = new Paragraph();
        Chunk detailedInfo = new Chunk(createFlightInfoString(), new Font(Font.FontFamily.COURIER, 12, 2, BaseColor.BLACK));
        flightInfoParagraph.setSpacingBefore(20);
        flightInfoParagraph.setIndentationLeft(30);
        flightInfoParagraph.add(detailedInfo);
        return flightInfoParagraph;
    }

    private String createFlightInfoString() {
        return "Departure: " + ticket.getFlight().getFlightDateToString() + "\n" +
                "Flight from: " + ticket.getFlight().getLocationFrom() + "\n" +
                "Flight to: " + ticket.getFlight().getLocationTo() + "\n" +
                "Passenger name: " + ticket.getPassenger().getFullName() + "\n" +
                "Passenger identity number: " + ticket.getPassenger().getIdentityNumber() + "\n" +
                "Plane No/Company: " + ticket.getFlight().getPlane().getNumber() + "/" +
                ticket.getFlight().getPlane().getCompanyName();
    }

    private Paragraph setUpGoogleTitle() {
        Chunk googleTitle = new Chunk("Google Fly", new Font(Font.FontFamily.HELVETICA, 17, 2, BaseColor.BLACK));
        Paragraph googleTitleParagraph = new Paragraph(googleTitle);
        googleTitleParagraph.setIndentationLeft(220);
        return googleTitleParagraph;
    }

    private Paragraph setUpGoogleInfo() {
        Chunk googleInfo = new Chunk(createGoogleInfoString(), new Font(Font.FontFamily.HELVETICA, 12, 2, BaseColor.BLACK));
        Paragraph googleInfoParagraph = new Paragraph(googleInfo);
        googleInfoParagraph.setIndentationLeft(220);
        return googleInfoParagraph;
    }

    private Paragraph setUpGoogleLink() {
        Chunk googleLink = new Chunk("www.google.com", new Font(Font.FontFamily.HELVETICA, 12, 2, BaseColor.BLUE));
        Paragraph linkParagraph = new Paragraph(googleLink);
        linkParagraph.setIndentationLeft(220);
        linkParagraph.setSpacingBefore(-3);
        return linkParagraph;
    }

    private String createGoogleInfoString() {
        return "1600 Amphitheatre Parkway\n" +
                "Mountain View, CA 94043\n" +
                "United States\n";
    }

    private Paragraph setUpThanks() {
        Chunk thanksText = new Chunk(createThanksString(), new Font(Font.FontFamily.COURIER, 23, 2, BaseColor.RED));
        Paragraph lastParagraph = new Paragraph(thanksText);
        lastParagraph.setSpacingBefore(120);
        lastParagraph.setAlignment(Element.ALIGN_CENTER);
        return lastParagraph;
    }

    private String createThanksString() {
        return "THANK YOU\n FOR CHOOSING\nGOOGLE FLY!";
    }

}
