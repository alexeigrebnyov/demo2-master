package com.example.demo.controller;

import com.example.demo.model.Analysis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.curs.xylophone.XML2SpreadSheetError;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UniversalController {
    //    @GetMapping("/time")
//    public ResponseEntity<List<Long>> getTime() {
//        List<Long> times =new ArrayList<>();
//        times.add(System.currentTimeMillis());
//        return ResponseEntity.ok(times);
//    }
    @GetMapping("/code")
    ResponseEntity<List<String>> getCode();

    @GetMapping(value = "/gormonu/{code}/{done}")
    List<Analysis> getGormonu(@PathVariable("code") String code, @PathVariable String done) throws SQLException;

    @GetMapping("/run")
    void exportResult() throws InterruptedException, IOException;

    @GetMapping("/chekGormonu")
    List<Analysis> checkGormonu() throws SQLException;

    @GetMapping("/writeGormonu")
    void writeGormonu() throws XML2SpreadSheetError, IOException;

    void setAnalysisList(List<Analysis> analysisList);

    void setCode(String code);

    List<Analysis> getAnalysisList(String code, String done, String GPRM) throws SQLException;

    @PostMapping("/delete")
    void delete(@RequestBody String delete);

    @GetMapping("/clear")
    void clearList();
}
