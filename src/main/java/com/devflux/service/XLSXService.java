package com.devflux.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


@Service
public class XLSXService
{
	private static final int MAX_ROWS = 1000;

	private static final List<String> BASE_HEADER = List.of("id", "Operation Status" , "Action", "First Name", "Last Name", "Email", "Phone", "Status");
	
	private final CustomerAttributeService attributeService;
	
	XLSXService(CustomerAttributeService attributeService)
	{
		this.attributeService = attributeService;
	}

	public byte[] createCustomerBulkUploadTemplate() throws IOException
	{
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			List<String> header = new ArrayList<>(BASE_HEADER);

			XSSFSheet sheet = workbook.createSheet("Customer");

			// header style
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.WHITE.getIndex());

			XSSFCellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFont(font);
			headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerStyle.setBorderBottom(BorderStyle.THIN);

			// text style so phone numbers keep leading zeros / +91
			XSSFCellStyle textStyle = workbook.createCellStyle();
			textStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));

			// header row
			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < header.size(); i++)
			{
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(header.get(i));
				cell.setCellStyle(headerStyle);
				sheet.setColumnWidth(i, 20 * 256);
			}
			sheet.setColumnWidth(5, 32 * 256); // Email wider
			
			// dropdowns
			addDropdown(sheet, header.indexOf("Action"), "ADD", "UPDATE", "DELETE");

			addDropdown(sheet, header.indexOf("Status"), "ACTIVE", "INACTIVE");

			// freeze header + filter
			sheet.createFreezePane(0, 1);
			sheet.setAutoFilter(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, header.size() - 1));
			attributeService.getAllCustomAttribute().forEach(attr -> {
				if(attr.isStatus())
				{
					if(attr.getAttributeType().equalsIgnoreCase("DROPDOWN") && attr.getValues() != null && !attr.getValues().isEmpty())
					{
						addDropdown(sheet, header.size(), attr.getValues().toArray(new String[0]));
					}
					else
					{
						Cell cell = headerRow.createCell(header.size());
						cell.setCellValue(attr.getAttributeName());
						cell.setCellStyle(headerStyle);
						sheet.setColumnWidth(header.size(), 20 * 256);
						header.add(attr.getAttributeName()); // skip inactive attributes
					}
					
				}
			});
			workbook.write(out);
			return out.toByteArray();
		}
	}

	private void addDropdown(XSSFSheet sheet, int col, String... values)
	{
		DataValidationHelper helper = sheet.getDataValidationHelper();
		DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
		CellRangeAddressList range = new CellRangeAddressList(1, MAX_ROWS, col, col);

		DataValidation validation = helper.createValidation(constraint, range);
		validation.setShowErrorBox(true);
		validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		validation.createErrorBox("Invalid value", "Pick a value from the dropdown.");
		sheet.addValidationData(validation);
	}
}