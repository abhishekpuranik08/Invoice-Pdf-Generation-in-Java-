   @BEndpoint(uri = BookingDefs.Endpoints.BOOKING_INVOICE_PDF, httpMethod = UniversalConstants.GET, permission = BookingDefs.Permissions.USER_N_HOST_API_KEY, reqDTOClass = Reservationrequest.class)
    public BaseResponse bookingInvoice(BLogger logger, BLModServices blModServices, RequestContext reqCtx, Reservationrequest resObject) {
        BookingResponse bookingResponse = new BookingResponse();
        PdfResponse pdfResponse = new PdfResponse();
        try {
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
            Date currentDate = new Date();
            String filename = "Invoice" + dateFormat.format(currentDate);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=Bundee" + filename + ".pdf");
            pdfResponse.setPdfString(loadPdfFile(resObject.getVehicleDescription(), resObject.getPricePerDay(), resObject.getNumberOfDays(), resObject.getTripAmount(), resObject.getTaxAmount1(), resObject.getTripTaxAmount1(), resObject.getCityName(), resObject.getState(), resObject.getCountry(), resObject.getZipCode(), resObject.getFirstName(), resObject.getCityName(), resObject.getState(), resObject.getCountry(), resObject.getZipCode(), resObject.getInvoiceNumber(), resObject.getCreatedate()));
            pdfResponse.setFileName("Bundee" + filename + ".pdf");
            if (pdfResponse.getPdfString() == null) {
                pdfResponse.setErrorCode("1");
                pdfResponse.setErrorMessage("Pdf Data is Empty");
              return pdfResponse;
              
            }
          return pdfResponse;
        }catch(Exception e)
          {
            return PdfResponse;
          }
   }


public String loadPdfFile(String description, String pricePerDay, String noOfDays, String tripAmount, String taxAmount, String TripTaxAmount, String cityName, String StateName, String countryName, String zipCode, String firstName, String CityName, String state, String country, String zipCode1, String invoiceNumber, String date) throws ParseException {
        String out = PdfFile(description, pricePerDay, noOfDays, tripAmount, taxAmount, TripTaxAmount, cityName, StateName, countryName, zipCode, firstName, cityName, state, country, zipCode1, invoiceNumber, date);
        return out;
    }

    public String PdfFile(String description, String pricePerDay, String noOfDays, String tripAmount, String taxAmount, String TripTaxAmount, String cityName, String stateNAme, String Country, String zipCode, String firstName, String CityName, String state, String country, String zipCode1, String invoiceNumber, String date) {
        Document document = new Document();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            // ---------------Header Color---------------------------
            BaseColor myColorTop = WebColors.getRGBColor("#2596be");
            PdfPCell headercell = new PdfPCell();
            headercell.setBackgroundColor(myColorTop);
            headercell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headercell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            headercell.setUseAscender(true);
            headercell.setFixedHeight(20);
            headercell.setBorderColor(myColorTop);
            PdfPTable topRow = new PdfPTable(new float[]{100});
            topRow.setWidthPercentage(100);
            topRow.addCell(headercell);
            topRow.completeRow();
            document.add(topRow);
            document.add(Chunk.NEWLINE);
            Paragraph paraheader1 = new Paragraph("");
            paraheader1.setSpacingBefore(0);
            paraheader1.setSpacingAfter(10);
            document.add(paraheader1);
            // ---------------------------Header Section
            // -----------------------------------------
            try {
                PdfPTable tableHeaderSec = new PdfPTable(new float[]{35, 30, 35});
                tableHeaderSec.setWidthPercentage(100);
                List<PdfPCell> tableHeader = headerSection(cityName, stateNAme, Country, zipCode);
                tableHeaderSec.addCell(tableHeader.get(0));
                tableHeaderSec.addCell(tableHeader.get(1));
                tableHeaderSec.addCell(tableHeader.get(2));
                tableHeaderSec.completeRow();
                document.add(tableHeaderSec);
            } catch (Exception ignored) {
            }
            document.add(Chunk.NEWLINE);
            Font regularadd = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY);
            document.add(new LineSeparator(regularadd));
            document.add(Chunk.NEWLINE);
            // ----------------------------Bill Section-------------------------------------
            try {
                PdfPTable tableBillSec = new PdfPTable(new float[]{50, 50});
                tableBillSec.setWidthPercentage(100);
                List<PdfPCell> tableHeader = billSection(firstName, cityName, stateNAme, country, zipCode1, invoiceNumber, date);
                tableBillSec.addCell(tableHeader.get(0));
                tableBillSec.addCell(tableHeader.get(1));
                tableBillSec.completeRow();
                document.add(tableBillSec);
            } catch (Exception ignored) {
            }
            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator(regularadd));
            document.add(Chunk.NEWLINE);
            // ----------------------------Description
            // Section-------------------------------------
            Font boldhed1 = new Font(FontFamily.HELVETICA, 8, Font.BOLD);
            Font regularadd1 = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
            try {
                PdfPTable tableDescSec = new PdfPTable(new float[]{12, 25, 13, 12, 13, 12, 13});
                tableDescSec.setWidthPercentage(100);
                List<PdfPCell> tableHeader = descSection("Item", "Description", "Price per Day", "No of Days", "Price", "Tax", "Amount", "header", 40, boldhed1);
                tableDescSec.addCell(tableHeader.get(0));
                tableDescSec.addCell(tableHeader.get(1));
                tableDescSec.addCell(tableHeader.get(2));
                tableDescSec.addCell(tableHeader.get(3));
                tableDescSec.addCell(tableHeader.get(4));
                tableDescSec.addCell(tableHeader.get(5));
                tableDescSec.addCell(tableHeader.get(6));
                tableDescSec.completeRow();
                document.add(tableDescSec);
            } catch (Exception e) {
            }
            // ----------------------------Description
            // Section-------------------------------------
            try {
                PdfPTable tableDescSec = new PdfPTable(new float[]{12, 25, 13, 12, 13, 12, 13});
                tableDescSec.setWidthPercentage(100);
                List<PdfPCell> tableHeader = descSection("1", description, pricePerDay, noOfDays, tripAmount, taxAmount, TripTaxAmount, "header", 60, regularadd1);
                tableDescSec.addCell(tableHeader.get(0));
                tableDescSec.addCell(tableHeader.get(1));
                tableDescSec.addCell(tableHeader.get(2));
                tableDescSec.addCell(tableHeader.get(3));
                tableDescSec.addCell(tableHeader.get(4));
                tableDescSec.addCell(tableHeader.get(5));
                tableDescSec.addCell(tableHeader.get(6));
                tableDescSec.completeRow();
                document.add(tableDescSec);
            } catch (Exception e) {
            }
            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator(regularadd));
            document.add(Chunk.NEWLINE);
            // ------------------------------Notes------------------------------------------
            try {
                PdfPTable tableNoteSec = new PdfPTable(new float[]{50, 50});
                tableNoteSec.setWidthPercentage(100);
                List<PdfPCell> tableHeader = notesSection(TripTaxAmount);
                tableNoteSec.addCell(tableHeader.get(0));
                tableNoteSec.addCell(tableHeader.get(1));
                tableNoteSec.completeRow();
                document.add(tableNoteSec);
            } catch (Exception e) {
            }
            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator(regularadd));
            document.add(Chunk.NEWLINE);
            // ---------------------------Bottom
            // Section---------------------------------------------
            Paragraph paraheader2 = new Paragraph("");
            paraheader2.setSpacingBefore(0);
            paraheader2.setSpacingAfter(10);
            document.add(paraheader2);
            // ---------------bottom Color---------------------------
            BaseColor myColorbottom = WebColors.getRGBColor("#e7f0ff");
            PdfPCell bottomcell = new PdfPCell();
            bottomcell.setBackgroundColor(myColorbottom);
            bottomcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            bottomcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            bottomcell.setUseAscender(true);
            bottomcell.setFixedHeight(40);
            bottomcell.setBorderColor(myColorbottom);
            PdfPTable bottomRow = new PdfPTable(new float[]{100});
            bottomRow.setWidthPercentage(100);
            bottomRow.addCell(bottomcell);
            bottomRow.completeRow();
            document.add(bottomRow);
            document.add(Chunk.NEWLINE);
            // ------------------------------------------------------------------
            document.close();
            writer.close();
            String encodedString = Base64Utils.encodeToString(out.toByteArray());
            return encodedString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PdfPCell> headerSection(String cityName, String state, String Country, String zipCode) {
        List<PdfPCell> result = new ArrayList<PdfPCell>();
        Font boldhed = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
        Font boldhed1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        Font boldhed2 = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
        Font regularadd = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        // ------------------------------------------------------------
        PdfPCell cell1 = new PdfPCell();
        cell1.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell1.setHorizontalAlignment(Element.ALIGN_BOTTOM);
        cell1.setUseAscender(true);
        cell1.setFixedHeight(90);
        cell1.setBorder(Rectangle.NO_BORDER);
        String imageurllogohp = "https://d64supplychain1.blob.core.windows.net/bundeeimage/logo_bundee.png";
        Image image2;
        try {
            image2 = Image.getInstance(new URL(imageurllogohp));
            image2.scaleAbsolute(100, 150);
            image2.setAlignment(Element.ALIGN_LEFT);
            image2.setSpacingAfter(0);
            image2.setSpacingBefore(0);
            image2.setIndentationLeft(10);
            cell1.addElement(image2);
        } catch (Exception e) {
            Paragraph imagena = new Paragraph();
            Chunk cwpna = new Chunk("Not Available", boldhed);
            imagena.add(cwpna);
            imagena.setIndentationLeft(10);
            imagena.setAlignment(Element.ALIGN_CENTER);
            imagena.setSpacingBefore(0);
            imagena.setIndentationLeft(10);
            cell1.addElement(imagena);
        }
        // image2.setAbsolutePosition(0f, 100f);
        // ------------------------------------------------------------
        PdfPCell cell2 = new PdfPCell();
        cell2.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell2.setHorizontalAlignment(Element.ALIGN_BOTTOM);
        cell2.setUseAscender(true);
        cell2.setFixedHeight(90);
        cell2.setBorder(Rectangle.NO_BORDER);
        Paragraph parwp = new Paragraph();
        Chunk cwp = new Chunk("RECEIPT", boldhed);
        parwp.add(cwp);
        parwp.setIndentationLeft(30);
        parwp.setAlignment(Element.ALIGN_BOTTOM);
        parwp.setSpacingAfter(0);
        parwp.setSpacingBefore(0);
        cell2.addElement(parwp);
        // ------------------------------------------------------------
        PdfPCell cell3 = new PdfPCell();
        cell3.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell3.setUseAscender(true);
        cell3.setFixedHeight(90);
        cell3.setBorder(Rectangle.NO_BORDER);
        Paragraph parbun = new Paragraph();
        Chunk cwptbun = new Chunk("Bundee", boldhed1);
        parbun.add(cwptbun);
        parbun.setIndentationLeft(10);
        parbun.setAlignment(Element.ALIGN_RIGHT);
        parbun.setSpacingAfter(5);
        parbun.setSpacingBefore(20);
        Paragraph parbun1 = new Paragraph();
        Chunk cwpbun1 = new Chunk(cityName, regularadd);
        parbun1.add(cwpbun1);
        parbun1.setIndentationLeft(10);
        parbun1.setAlignment(Element.ALIGN_RIGHT);
        parbun1.setSpacingAfter(0);
        Paragraph parbun2 = new Paragraph();
        Chunk cwpbun2 = new Chunk(state, regularadd);
        parbun2.add(cwpbun2);
        parbun2.setIndentationLeft(10);
        parbun2.setAlignment(Element.ALIGN_RIGHT);
        parbun2.setSpacingAfter(0);
        Paragraph parbun3 = new Paragraph();
        Chunk cwpbun3 = new Chunk(Country, regularadd);
        parbun3.add(cwpbun3);
        parbun3.setIndentationLeft(10);
        parbun3.setAlignment(Element.ALIGN_RIGHT);
        parbun3.setSpacingAfter(0);
        Paragraph parbun4 = new Paragraph();
        Chunk cwpbun4 = new Chunk(zipCode, regularadd);
        parbun4.add(cwpbun4);
        parbun4.setIndentationLeft(10);
        parbun4.setAlignment(Element.ALIGN_RIGHT);
        parbun4.setSpacingAfter(0);
        cell3.addElement(parbun);
        cell3.addElement(parbun1);
        cell3.addElement(parbun2);
        cell3.addElement(parbun3);
        cell3.addElement(parbun4);
        // ------------------------------------------------------------
        result.add(cell1);
        result.add(cell2);
        result.add(cell3);
        return result;
    }

    public List<PdfPCell> billSection(String firstName, String cityName, String stateName, String country, String zipCode, String inVoiceNumber, String date) {
        List<PdfPCell> result = new ArrayList<PdfPCell>();
        Font boldhed = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
        Font boldhed1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        Font boldhed2 = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
        Font regularadd = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        // ------------------------------------------------------------
        PdfPCell cell1 = new PdfPCell();
        cell1.setVerticalAlignment(Element.ALIGN_CENTER);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setUseAscender(true);
        cell1.setFixedHeight(120);
        cell1.setBorder(Rectangle.NO_BORDER);
        Paragraph parbill = new Paragraph();
        Chunk cwptbill = new Chunk("BILL TO", boldhed1);
        parbill.add(cwptbill);
        parbill.setIndentationLeft(10);
        parbill.setAlignment(Element.ALIGN_LEFT);
        parbill.setSpacingAfter(0);
        parbill.setSpacingBefore(0);
        Paragraph parname = new Paragraph();
        Chunk cwpname = new Chunk(firstName, boldhed2);
        parname.add(cwpname);
        parname.setIndentationLeft(10);
        parname.setAlignment(Element.ALIGN_LEFT);
        parname.setSpacingAfter(0);
        Paragraph parbun1 = new Paragraph();
        Chunk cwpbun1 = new Chunk(cityName, regularadd);
        parbun1.add(cwpbun1);
        parbun1.setIndentationLeft(10);
        parbun1.setAlignment(Element.ALIGN_LEFT);
        parbun1.setSpacingAfter(0);
        Paragraph parbun2 = new Paragraph();
        Chunk cwpbun2 = new Chunk(stateName, regularadd);
        parbun2.add(cwpbun2);
        parbun2.setIndentationLeft(10);
        parbun2.setAlignment(Element.ALIGN_LEFT);
        parbun2.setSpacingAfter(0);
        Paragraph parbun3 = new Paragraph();
        Chunk cwpbun3 = new Chunk(country, regularadd);
        parbun3.add(cwpbun3);
        parbun3.setIndentationLeft(10);
        parbun3.setAlignment(Element.ALIGN_LEFT);
        parbun3.setSpacingAfter(0);
        Paragraph parbun4 = new Paragraph();
        Chunk cwpbun4 = new Chunk(zipCode, regularadd);
        parbun4.add(cwpbun4);
        parbun4.setIndentationLeft(10);
        parbun4.setAlignment(Element.ALIGN_LEFT);
        parbun4.setSpacingAfter(0);
        cell1.addElement(parbill);
        cell1.addElement(parname);
        cell1.addElement(parbun1);
        cell1.addElement(parbun2);
        cell1.addElement(parbun3);
        cell1.addElement(parbun4);
        // ------------------------------------------------------------
        PdfPCell cell3 = new PdfPCell();
        cell3.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell3.setUseAscender(true);
        cell3.setFixedHeight(120);
        cell3.setBorder(Rectangle.NO_BORDER);
        Paragraph parbun = new Paragraph();
        Chunk cwptbun = new Chunk("INVOICE NO #", boldhed1);
        parbun.add(cwptbun);
        parbun.setIndentationLeft(10);
        parbun.setAlignment(Element.ALIGN_RIGHT);
        parbun.setSpacingAfter(0);
        parbun.setSpacingBefore(0);
        Paragraph parinvnum = new Paragraph();
        Chunk cwpinvnum = new Chunk(inVoiceNumber, regularadd);
        parinvnum.add(cwpinvnum);
        parinvnum.setIndentationLeft(10);
        parinvnum.setAlignment(Element.ALIGN_RIGHT);
        parinvnum.setSpacingAfter(0);
        Paragraph pardate = new Paragraph();
        Chunk cwpdate = new Chunk("DATE", boldhed1);
        pardate.add(cwpdate);
        pardate.setIndentationLeft(10);
        pardate.setAlignment(Element.ALIGN_RIGHT);
        pardate.setSpacingAfter(0);
        Paragraph pardtev = new Paragraph();
        Chunk cwpdtev = new Chunk(date, regularadd);
        pardtev.add(cwpdtev);
        pardtev.setIndentationLeft(10);
        pardtev.setAlignment(Element.ALIGN_RIGHT);
        pardtev.setSpacingAfter(0);
        cell3.addElement(parbun);
        cell3.addElement(parinvnum);
        cell3.addElement(pardate);
        cell3.addElement(pardtev);
        // ------------------------------------------------------------
        result.add(cell1);
        result.add(cell3);
        return result;
    }

    public List<PdfPCell> notesSection(String totalAmount) {
        List<PdfPCell> result = new ArrayList<PdfPCell>();
        Font boldhed = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
        Font boldhed1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        Font boldhed2 = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
        Font regularadd = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        // ------------------------------------------------------------
        PdfPCell cell1 = new PdfPCell();
        cell1.setVerticalAlignment(Element.ALIGN_LEFT);
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setUseAscender(true);
        cell1.setFixedHeight(70);
        cell1.setBorder(Rectangle.NO_BORDER);
        Paragraph parbill = new Paragraph();
        Chunk cwptbill = new Chunk("NOTES:", boldhed2);
        parbill.add(cwptbill);
        parbill.setIndentationLeft(10);
        parbill.setAlignment(Element.ALIGN_LEFT);
        parbill.setSpacingAfter(0);
        parbill.setSpacingBefore(0);
        cell1.addElement(parbill);
        // ------------------------------------------------------------
        PdfPCell cell3 = new PdfPCell();
        cell3.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell3.setUseAscender(true);
        cell3.setFixedHeight(70);
        cell3.setBorder(Rectangle.NO_BORDER);
        Paragraph parbun = new Paragraph();
        Chunk cwptbun = new Chunk("TOTAL", boldhed2);
        parbun.add(cwptbun);
        parbun.setIndentationLeft(10);
        parbun.setAlignment(Element.ALIGN_RIGHT);
        parbun.setSpacingAfter(0);
        parbun.setSpacingBefore(0);
        Paragraph parinvnum = new Paragraph();
        Chunk cwpinvnum = new Chunk(totalAmount, boldhed1);
        parinvnum.add(cwpinvnum);
        parinvnum.setIndentationLeft(10);
        parinvnum.setAlignment(Element.ALIGN_RIGHT);
        parinvnum.setSpacingAfter(0);
        cell3.addElement(parbun);
        cell3.addElement(parinvnum);
        // ------------------------------------------------------------
        result.add(cell1);
        result.add(cell3);
        return result;
    }

    public List<PdfPCell> descSection(String item, String desc, String perDay, String noDays, String price, String tax, String totAmount, String header, int height, Font font) {
        List<PdfPCell> result = new ArrayList<PdfPCell>();
        Font boldhed = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
        Font boldhed1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        Font boldhed2 = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
        Font regularadd = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        Font regularadd1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        // ------------------------------------------------------------
        PdfPCell cell1 = new PdfPCell();
        cell1.setVerticalAlignment(Element.ALIGN_LEFT);
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setUseAscender(true);
        cell1.setFixedHeight(height);
        cell1.setBorder(Rectangle.NO_BORDER);
        Paragraph parbill = new Paragraph();
        Chunk cwptbill = new Chunk(item, font);
        parbill.add(cwptbill);
        parbill.setIndentationLeft(10);
        parbill.setAlignment(Element.ALIGN_LEFT);
        parbill.setSpacingAfter(0);
        parbill.setSpacingBefore(0);
        cell1.addElement(parbill);
        // ------------------------------------------------------------
        PdfPCell cell2 = new PdfPCell();
        cell2.setVerticalAlignment(Element.ALIGN_LEFT);
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setUseAscender(true);
        cell2.setFixedHeight(height);
        cell2.setBorder(Rectangle.NO_BORDER);
        Paragraph pardesc = new Paragraph();
        Chunk cwptdesc = new Chunk(desc, font);
        pardesc.add(cwptdesc);
        pardesc.setIndentationLeft(10);
        pardesc.setAlignment(Element.ALIGN_LEFT);
        pardesc.setSpacingAfter(0);
        pardesc.setSpacingBefore(0);
        cell2.addElement(pardesc);
        // ------------------------------------------------------------
        PdfPCell cell3 = new PdfPCell();
        cell3.setVerticalAlignment(Element.ALIGN_LEFT);
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setUseAscender(true);
        cell3.setFixedHeight(height);
        cell3.setBorder(Rectangle.NO_BORDER);
        Paragraph parperDay = new Paragraph();
        Chunk cwptperDay = new Chunk(perDay, font);
        parperDay.add(cwptperDay);
        parperDay.setIndentationLeft(10);
        parperDay.setAlignment(Element.ALIGN_LEFT);
        parperDay.setSpacingAfter(0);
        parperDay.setSpacingBefore(0);
        cell3.addElement(parperDay);
        // ------------------------------------------------------------
        PdfPCell cell4 = new PdfPCell();
        cell4.setVerticalAlignment(Element.ALIGN_LEFT);
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setUseAscender(true);
        cell4.setFixedHeight(height);
        cell4.setBorder(Rectangle.NO_BORDER);
        Paragraph parnoDays = new Paragraph();
        Chunk cwptnoDays = new Chunk(noDays, font);
        parnoDays.add(cwptnoDays);
        parnoDays.setIndentationLeft(10);
        parnoDays.setAlignment(Element.ALIGN_CENTER);
        parnoDays.setSpacingAfter(0);
        parnoDays.setSpacingBefore(0);
        cell4.addElement(parnoDays);
        // ------------------------------------------------------------
        PdfPCell cell5 = new PdfPCell();
        cell5.setVerticalAlignment(Element.ALIGN_LEFT);
        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell5.setUseAscender(true);
        cell5.setFixedHeight(height);
        cell5.setBorder(Rectangle.NO_BORDER);
        Paragraph parprice = new Paragraph();
        Chunk cwptprice = new Chunk(price, font);
        parprice.add(cwptprice);
        parprice.setIndentationLeft(10);
        parprice.setAlignment(Element.ALIGN_LEFT);
        parprice.setSpacingAfter(0);
        parprice.setSpacingBefore(0);
        cell5.addElement(parprice);
        // ------------------------------------------------------------
        PdfPCell cell6 = new PdfPCell();
        cell6.setVerticalAlignment(Element.ALIGN_LEFT);
        cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell6.setUseAscender(true);
        cell6.setFixedHeight(height);
        cell6.setBorder(Rectangle.NO_BORDER);
        Paragraph partax = new Paragraph();
        Chunk cwpttax = new Chunk(tax, font);
        partax.add(cwpttax);
        partax.setIndentationLeft(10);
        partax.setAlignment(Element.ALIGN_LEFT);
        partax.setSpacingAfter(0);
        partax.setSpacingBefore(0);
        cell6.addElement(partax);
        // ------------------------------------------------------------
        PdfPCell cell7 = new PdfPCell();
        cell7.setVerticalAlignment(Element.ALIGN_LEFT);
        cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell7.setUseAscender(true);
        cell7.setFixedHeight(height);
        cell7.setBorder(Rectangle.NO_BORDER);
        Paragraph partotAmount = new Paragraph();
        Chunk cwpttotAmount = new Chunk(totAmount, font);
        partotAmount.add(cwpttotAmount);
        partotAmount.setIndentationLeft(10);
        partotAmount.setAlignment(Element.ALIGN_LEFT);
        partotAmount.setSpacingAfter(0);
        partotAmount.setSpacingBefore(0);
        cell7.addElement(partotAmount);
        // ------------------------------------------------------------
        result.add(cell1);
        result.add(cell2);
        result.add(cell3);
        result.add(cell4);
        result.add(cell5);
        result.add(cell6);
        result.add(cell7);
        return result;
    }
