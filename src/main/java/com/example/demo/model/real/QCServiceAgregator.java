package com.example.demo.model.real;

import com.example.demo.model.qc.amg.AMGMeasure;
import com.example.demo.model.qc.hbs.HBsAgMeasure;
import com.example.demo.model.qc.hcv.Measure;
import com.example.demo.model.qc.hiv.HIVAbMeasure;
import com.example.demo.model.qc.hiv.HIVAgMeasure;
import com.example.demo.model.qc.hydroxyprog.HydroMeasure;
import com.example.demo.model.qc.syph.SyphMeasure;
import com.example.demo.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QCServiceAgregator {


    private HBsAgMeasureService hbsmeasureService;
    private HBsAgLotService hbslotService;
    private HBsAgTestService hbsTestService;

    private HIVAbMeasureService hivAbMeasureService;
    private HIVAbTestService hivAbTestService;
    private HIVAbLotService hivAbLotService;

    private HIVAgMeasureService hivAgMeasureService;
    private HIVAgTestService hivAgTestService;
    private HIVAgLotService hivAgLotService;

    private MeasureService hcvMeasureService;
    private TestService hcvTestService;
    private LotService hcvLotService;


    private SyphMeasureService syphMeasureService;
    private SyphTestService syphTestService;
    private SyphLotService syphLotService;

    private AMGMeasureService amgMeasureService;
    private AMGTestService amgTestService;
    private AMGLotService amgLotService;

    private HydroMeasureService hydroMeasureService;
    private HydroTestService hydroTestService;
    private HydroLotService hydroLotService;
    @Autowired
    public QCServiceAgregator(HBsAgMeasureService hbsmeasureService, HBsAgLotService hbslotService,
                              HBsAgTestService hbsTestService, HIVAbMeasureService hivAbMeasureService,
                              HIVAbTestService hivAbTestService, HIVAbLotService hivAbLotService,
                              HIVAgMeasureService hivAgMeasureService, HIVAgTestService hivAgTestService,
                              HIVAgLotService hivAgLotService, MeasureService hcvMeasureService,
                              TestService hcvTestService, LotService hcvLotService, SyphMeasureService syphMeasureService,
                              SyphTestService syphTestService, SyphLotService syphLotService, AMGMeasureService amgMeasureService, AMGTestService amgTestService, AMGLotService amgLotService, HydroMeasureService hydroMeasureService, HydroTestService hydroTestService, HydroLotService hydroLotService) {

        this.hbsmeasureService = hbsmeasureService;
        this.hbslotService = hbslotService;
        this.hbsTestService = hbsTestService;

        this.hivAbMeasureService = hivAbMeasureService;
        this.hivAbTestService = hivAbTestService;
        this.hivAbLotService = hivAbLotService;

        this.hivAgMeasureService = hivAgMeasureService;
        this.hivAgTestService = hivAgTestService;
        this.hivAgLotService = hivAgLotService;

        this.hcvMeasureService = hcvMeasureService;
        this.hcvTestService = hcvTestService;
        this.hcvLotService = hcvLotService;


        this.syphMeasureService = syphMeasureService;
        this.syphTestService = syphTestService;
        this.syphLotService = syphLotService;
        this.amgMeasureService = amgMeasureService;
        this.amgTestService = amgTestService;
        this.amgLotService = amgLotService;
        this.hydroMeasureService = hydroMeasureService;
        this.hydroTestService = hydroTestService;
        this.hydroLotService = hydroLotService;
    }

    public boolean saveHBSMeasure(InterMeasure measure) {

        HBsAgMeasure hBsAgMeasure = (HBsAgMeasure) measure;
        hBsAgMeasure.setTest(hbsTestService.findByInuse("1"));
        hBsAgMeasure.setLot(hbslotService.getByUse("1"));
        hbsmeasureService.save(hBsAgMeasure);
        return true;
    }

    public boolean saveHivAbMeasure(InterMeasure measure) {

        HIVAbMeasure hivAbMeasure = (HIVAbMeasure) measure;
        hivAbMeasure.setTest(hivAbTestService.findByInuse("1"));
        hivAbMeasure.setLot(hivAbLotService.getByUse("1"));
        hivAbMeasureService.save(hivAbMeasure);
        return true;
    }

    public boolean saveHivAgMeasure(InterMeasure measure) {

        HIVAgMeasure hivAgMeasure = (HIVAgMeasure) measure;
        hivAgMeasure.setTest(hivAgTestService.findByInuse("1"));
        hivAgMeasure.setLot(hivAgLotService.getByUse("1"));
        hivAgMeasureService.save(hivAgMeasure);
        return true;
    }

    public boolean saveHCVMeasure(InterMeasure measure) {

        Measure hcvMeasure = (Measure) measure;
        hcvMeasure.setTest(hcvTestService.findByInuse("1"));
        hcvMeasure.setLot(hcvLotService.getByUse("1", "ВЛК ГЕП С"));

        hcvMeasureService.save(hcvMeasure);
        return true;
    }

    public boolean saveSyphMeasure(InterMeasure measure) {

        SyphMeasure syphMeasure = (SyphMeasure) measure;
        syphMeasure.setTest(syphTestService.findByInuse("1"));
        syphMeasure.setLot(syphLotService.getByUse("1"));

        syphMeasureService.save(syphMeasure);
        return true;
    }

    public boolean saveAMGMeasure(InterMeasure measure) {

        AMGMeasure amgMeasure = (AMGMeasure) measure;
        amgMeasure.setTest(amgTestService.findByInuse("1"));
        amgMeasure.setLot(amgLotService.getByUse("1"));

        amgMeasureService.save(amgMeasure);
        return true;
    }

    public boolean saveHydroMeasure(InterMeasure measure) {

        HydroMeasure hydroMeasure = (HydroMeasure) measure;
        hydroMeasure.setTest(hydroTestService.findByInuse("1"));
        hydroMeasure.setLot(hydroLotService.getByUse("1"));

        hydroMeasureService.save(hydroMeasure);
        return true;
    }

    private void writeResults(String content, String path, LocalDate ld) {

        try {
            Files.write(Paths.get(Constants.directory+path, ld+path+".txt"), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла", e);
        }
    }


    public void realRParse() throws IOException {
        String realDirectory = Constants.directory+"RealR\\";

        File folder = new File(realDirectory);
        List<File> listOfFiles = Arrays.stream(Objects.requireNonNull(folder.listFiles())).collect(Collectors.toList());
        for (File current : listOfFiles) {
            if (current.isFile()) {
//                System.out.println("file "+current.getName());
                StringBuilder sb = new StringBuilder();

                byte[] jsonData = Files.readAllBytes(Paths.get(realDirectory, current.getName()));
//        byte[] jsonData = Files.readAllBytes(Paths.get("C:\\Users\\Grebnev_A\\Downloads\\АМГ от 24.10.2025.json"));

                ObjectMapper mapper = new ObjectMapper();

                Root root = mapper.readValue(jsonData, Root.class);
                LocalDateTime dateTime = LocalDateTime.parse(root.getReadOn().replaceAll("Z",""));
                Parameters parameters = Constants.params.get(root.getMethodCode());
                if (parameters.isCheckControl()) {
                    Map<String, String> vlc = root.getControls().stream().filter(s ->
                                    Optional.ofNullable(s.getType()).orElse("0").equals(parameters.getControlType()))
                            .map(sample -> new String[]{sample.getFormulas().stream().filter(f -> f.getKey().equals(parameters.getControlName()))
                                    .map(Formula::getValue)
                                    .collect(Collectors.toList()).stream().findFirst().orElse("0"),
                                    sample.getFormulas().stream().filter(f -> f.getKey().equals(parameters.getControlMarker())).map(Formula::getValue)
                                            .collect(Collectors.toList()).stream().findFirst().orElse("0")})
                            .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1], (oldValue, newValue) -> newValue));

                    System.out.println(vlc);

                    String vlcName = parameters.getVlcName();
                    InterMeasure m = parameters.getMeasure();
                    InterMeasure mHIVAg = new HIVAgMeasure();
                    mHIVAg.setMeasure_type(" HIVAg");
                    mHIVAg.setMeasure_date(dateTime);

                    m.setMeasure_date(dateTime);

                    m.setMeasure_val(vlcName.equals("AMG")?vlc.get("S3"):vlcName.equals("HIVAb")?vlc.get("VLC1"):
                            vlcName.equals("17-OH")?vlc.get("S3"):vlc.get("VLC1"));
                    mHIVAg.setMeasure_val(vlcName.equals("HIVAb")?vlc.get("VLC2"):"");

                    m.setMeasure_type(vlcName);
                    boolean b =vlcName.equals("HBsAg")?saveHBSMeasure(m):(vlcName.equals("HIVAb")?
                            saveHivAbMeasure(m)&&saveHivAgMeasure(mHIVAg):vlcName.equals("anti_HCV")?saveHCVMeasure(m):
                            vlcName.equals("SyphIFA")?saveSyphMeasure(m):
                            vlcName.equals("17-OH")?saveHydroMeasure(m):vlcName.equals("AMG") && saveAMGMeasure(m));

                }
                String sMarker = parameters.getSampleMarker();


                new HashSet<>(root.getSamples())
                        .forEach(a -> {
                            if (!Objects.equals(a.getBarcode(), "")) {
                                sb.append(a.getBarcode()).append(" ").append(a.getFormulas().stream().filter(f -> f.getName().equals(sMarker))
                                        .map(e -> {
                                                    String val = e.getValue();
                                                    return sMarker.equals("КП_Ti") ? (Double.parseDouble(val) >= 1.0d ? "+" : "-") :
                                                            val.replaceAll("\"", "")
//                                                                    .replaceAll("<b>POS</b>", "+")
                                                            ;
                                                }
                                        ).collect(Collectors.toList()).get(0)).append("\n");
                            }
                        } );
                String res = sb.toString().trim();
                writeResults(res, parameters.getFileName(), dateTime.toLocalDate());

                File dir = new File(Constants.directory + "\\Backup\\RealR");
//                 Move file to new directory
                boolean success = current.renameTo(new File(dir, current.getName()));
                if (!success) {
                    System.out.print("not good");
                }
            }
        }
    }
}
