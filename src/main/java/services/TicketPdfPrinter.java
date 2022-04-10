package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import entities.OrderedFlights;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.net.URISyntaxException;
import java.nio.file.Paths;


public class TicketPdfPrinter {
    private Document document = new Document();
    private OrderedFlights orderedFlights;

    public void createTicketPDF(OrderedFlights orderedFlight) throws IOException, DocumentException, URISyntaxException {
        this.orderedFlights = orderedFlight;
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

        System.out.println("nice");
    }

    public File getTicketFile(OrderedFlights ordered) throws DocumentException, IOException, URISyntaxException {
        createTicketPDF(ordered);
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
        Image QRCode = Image.getInstance(Paths.get(ClassLoader.getSystemResource("qr_code_barcode.jpg").toURI()).toAbsolutePath().toString());
        QRCode.scaleToFit(100, 100);
        QRCode.setAbsolutePosition(400, 500);
        return QRCode;
    }

    private Image setUpPlaneImage() throws DocumentException, IOException, URISyntaxException {
        Image planeImage = Image.getInstance(Paths.get(
                ClassLoader.getSystemResource("OIP.jpg").toURI()).toAbsolutePath().toString());
        planeImage.scaleToFit(300, 100);
        planeImage.setAbsolutePosition(50, 700);
        return planeImage;
    }

    private Paragraph setUpTicketTitle() {
        Paragraph ticketTitle = new Paragraph();
        Chunk title = new Chunk("Ticket Number: " + orderedFlights.getId(),
                new Font(Font.FontFamily.HELVETICA, 16, 2, BaseColor.BLACK));
        ticketTitle.add(title);
        ticketTitle.setIndentationLeft(170);
        ticketTitle.setSpacingBefore(90);
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
        return "Departure: " + orderedFlights.getFlightSchedule().getFlightDateToString() + "\n" +
                "Flight from: " + orderedFlights.getFlightSchedule().getLocationFrom() + "\n" +
                "Flight to: " + orderedFlights.getFlightSchedule().getLocationTo() + "\n" +
                "Passenger name: " + orderedFlights.getPassenger().getFullName() + "\n" +
                "Passenger identity number: " + orderedFlights.getPassenger().getIdentityNumber() + "\n" +
                "Plane No/Company: " + orderedFlights.getFlightSchedule().getPlane().getNumber() + "/" +
                orderedFlights.getFlightSchedule().getPlane().getCompanyName();
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

    private Paragraph setUpGoogleLink(){
        Chunk googleLink = new Chunk("www.google.com", new Font(Font.FontFamily.HELVETICA,12,2,BaseColor.BLUE));
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

    private String createThanksString(){
        return "THANK YOU\n FOR CHOOSING\nGOOGLE FLY!";
    }

}
