package pi.statistics.logic.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import pi.population.Specimen;

public class ReportManager {

	JasperReport jasperReport = null;
	JasperPrint jasperPrint = null;
	JasperDesign jasperDesign = null;
	JRBeanCollectionDataSource dataSource = null;

	public ReportManager() throws JRException {
		initReport();
	}

	public void refreshReport() throws JRException {
		initReport();
	}

	@SuppressWarnings("unchecked")
	private void initReport() throws JRException {
		System.out.println("Init report");
		long start = System.currentTimeMillis();
		@SuppressWarnings("rawtypes")
		Map parameters = new HashMap();

		// TODO Sprawdziæ czy klonowanie dataSource jest wydajniejsze od
		// generowania go na nowo za ka¿dym razem
		dataSource = new JRBeanCollectionDataSource(
				FigureStatistic.getFigureStatistics());

		// File file = new File("report3.jrxml");
		// jasperDesign = JRXmlLoader.load(file);
		// jasperReport = JasperCompileManager.compileReport(jasperDesign);

		File file = new File("report3.jasper");
		jasperReport = (JasperReport) JRLoader.loadObject(file);
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
				dataSource);

		/*
		 * File file = new File("tymczasowy brak pliku"); jasperReport =
		 * (JasperReport) JRLoader.loadObject(file); jasperPrint =
		 * JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		 * //dataSource.cloneDataSource());
		 */

		long time = System.currentTimeMillis() - start;
		System.out.println("Czas inicjowania raportu: " + time);

	}

	public void viewRaport() throws JRException {
		JasperViewer.viewReport(jasperPrint, false);
	}

	public void saveRaportAsPdf(String path) throws JRException {
		if (path == null || path == "") {
			path = "newReport.pdf";
		} else if (!path.endsWith(".pdf")) {
			path += ".pdf";
		}
		JasperExportManager.exportReportToPdfFile(jasperPrint, path);
		System.out.println("PDF ready");
	}

	@SuppressWarnings("unchecked")
	public void saveReportAsHtml(String path) throws JRException {
		if (path == null || path == "") {
			path = "newReport.html";
		} else if (!path.endsWith(".html")) {
			path += ".html";
		}
		@SuppressWarnings("rawtypes")
		Map parameters = new HashMap();
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		JasperPrint htmlReport = JasperFillManager.fillReport(jasperReport,
				parameters, dataSource.cloneDataSource());
		JasperExportManager.exportReportToHtmlFile(htmlReport, path);
		System.out.println("HTML ready");
	}

	public void comboReport() {
		try {
			// JasperReport jasperReport = null;
			// JasperPrint jasperPrint = null;
			// JasperDesign jasperDesign = null;
			// Map parameters = new HashMap();
			// File file = new File("report3.jrxml");
			// jasperDesign = JRXmlLoader.load(file);
			// jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// jasperPrint = JasperFillManager.fillReport(
			// jasperReport,
			// parameters,
			// new JRBeanCollectionDataSource(ChannelStatistic
			// .getChannelStatistics()));
			// JasperExportManager.exportReportToPdfFile(jasperPrint,
			// "FirstSpecimenReport.pdf");
			// JasperViewer.viewReport(jasperPrint);
			// System.out.println("Specimen report generated successfully");
		} catch (Exception ex) {
			System.out.println("EXCEPTION: " + ex);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO REMOVE

		// http://sourceforge.net/projects/jasperreports/files/jasperreports/JasperReports%205.5.0/
		// http://stackoverflow.com/questions/12178615/eclipse-jasper-report-not-compiling-java-lang-noclassdeffounderror-org-apach

		try {
			ReportManager rm = new ReportManager();
			rm.viewRaport();

			// rm.saveRaportAsPdf(null);
			// rm.saveReportAsHtml(null);
		} catch (JRException e) {
			System.out.println("Report exception");
			e.printStackTrace();
		}

	}

}
