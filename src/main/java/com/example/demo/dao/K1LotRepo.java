package com.example.demo.dao;

import com.example.demo.model.qc.protein.K1Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface K1LotRepo extends JpaRepository<K1Lot, Long> {
    List<K1Lot> findAll();
    List<K1Lot> findK1LotByDateFromAfter(LocalDateTime dateFrom);
    List<K1Lot> findK1LotByLot(String lot);
    List<K1Lot> findByName(String lot);
    List<K1Lot> findByInuse(String inuse);
    @Query(value = "select k1lot.id,  date_from, date_to, inuse, lot, name, number\n" +
            "from k1lot\n" +
            "where id in (select urine.k1lot_fk \n" +
            "                    from urine where measure_date>=?1 \n" +
            "                    and measure_date<=?2 and org=?3)", nativeQuery = true)
    List<K1Lot> findByMeasureListAfterAndMeasureListBefore(LocalDateTime after, LocalDateTime before, int org);
    @Modifying
    @Query("update K1Lot k set k.name = ?1, k.inuse = ?2 where k.id = ?3")
    void setK1LotInfoById(String name, String inuse, Long userId);
    void deleteByIdIn(List<Long> ids);


}
