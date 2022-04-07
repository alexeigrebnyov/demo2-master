package com.example.demo;

//import com.example.demo.config.Database;
//import com.example.demo.utils.BarCodeConnector;
//import com.example.demo.utils.BarcodeReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
        import ru.curs.xylophone.XML2SpreadSheetError;

//import javax.comm.SerialPortEvent;
        import java.io.IOException;
        import java.sql.SQLException;
        import java.text.ParseException;

@SpringBootApplication
public class DemoApplication {

//	private static HIVService hivService;
//	private static UptakeService uptakeService;
//	private static ContService contService;
//	private static Long i = 1L;
//	private static String[] paths = new String[3];
//	private static DataInit dataInit;
//	private static UptakeController uptakeController;
//	private static BarcodeReader barcodeReader;

//	@Autowired
//	public DemoApplication(HIVService hivService, UptakeService uptakeService, ContService contService,
//						   DataInit dataInit
//	) {
//
//		this.hivService = hivService;
//		this.uptakeService = uptakeService;
//		this.contService = contService;
//		this.dataInit = dataInit;
//	}



//	private static String[] getPath() {
//		System.out.println("Укажите путь к источнику данных");
//		Scanner pathScanner = new Scanner(System.in);
//		paths[0] = pathScanner.next();
//		paths[1] = pathScanner.next();
//		paths[2] = pathScanner.next();
//		return paths;
//
//	}
//
//
//	private static void further() {
//		System.out.println("Выберите дальнейшие действия:"
//				+ System.lineSeparator() +
//				"q -выход" + System.lineSeparator() +
//				"указать id пациента" + System.lineSeparator() +
//				"сделать выборку по результату (указать результат)");
//		Scanner scanner = new Scanner(System.in);
//
////		if (scanner.hasNextInt()) {
////			System.out.println(hivService.getById((long)scanner.nextInt()));
////			further();
////		}
////		else { String act = scanner.next();
////			if (act.equals("q")) {
////				scanner.close();
////			}
////			else {
////				hivService.allPos(act).forEach(System.out::println);
////				further();
////			}
////		}
//		if (scanner.next().equals("q")) {
//			scanner.close();
//		}
//	}

	public static void main(String[] args) throws IOException, XML2SpreadSheetError, ParseException, SQLException {
		SpringApplication.run(DemoApplication.class, args);


//		Analysis analysis = new Analysis();
//		List<Object[]> data = new ArrayList<>();
//		data = uptakeService.getData("2289");
////		System.out.println(data.size());
//		try {
//			Object[] data1 = data
//					.stream()
//					.findAny().get();
//			analysis.setEmc(data1[0].toString());
//			analysis.setFio(data1[1].toString());
//			analysis.setKontengent(data1[2].toString());
//
//			analysis.setMain_org_id(data1[5].toString());
//			analysis.setLabel(data1[6].toString());
//			analysis.setPatdirect_id(data1[7].toString());
//			analysis.setDate_bio(data1[8].toString());
//			analysis.setCode(data1[10].toString());
//			analysis.setMotconsu_resp_id(data1[3].toString());
//			analysis.setUpsent(data1[4].toString());
//
//		} catch (Exception ignored) {}
//
//
//		for (Object[] data1 : data) {
//			System.out.println(data1[0] + " " + data1[1] + " " + data1[2] + " " + data1[3] + " " + data1[4] + " "
//					+ data1[5] + data1[6] + " " + data1[7] + " " + data1[8] + " " + data1[9]
//					+ "" + data1[10]
//			);
//			try {
//
//
//
//			if (data1[9].toString().equals("А/т к ВИЧ 1,2 +А/г")) {
//				analysis.setHiv("1");
//				}
//			if (data1[9].toString().equals("HBsAg")){
//				analysis.setHbsAg("1");
//			}
//			if (data1[9].toString().equals("Ат .к. HCV")){
//				analysis.setAtHCV("1");
//			}
//			if (data1[9].toString().equals("Syphilis МРП")) {
//				analysis.setSyphMRP("1");
//			}
//			if (data1[9].toString().equals("Syphilis ИФА")) {
//				analysis.setSyphIFA("1");
//			}
//
//
//			} catch (Exception ignored) {}
////
//		}
//		System.out.println(analysis);

//		}

//		dataInit.hivInit();
//		dataInit.contInit();

//		dataInit.contInit();
//		Test.getScan();
//		System.out.println(Test.getScan());
//		RCPRreporter.addContingent("C:/Users/alexei/Desktop/Новый.txt");
//		RCPRreporter.addUptakeReport("C:/Users/alexei/Desktop/Новый1.txt");
//		RCPRreporter.generate();


//
//		getPath();
//		Resulter.addHiv(paths[0]);
////		XLConstructor.writeXML();
////		XLConstructor.xml2XLSX();
//		RCPRreporter.addContingent(paths[1]);
////		RCPRreporter.addUptakeReport(paths[2]);
//		RCPRreporter.generate();
		}

	}

