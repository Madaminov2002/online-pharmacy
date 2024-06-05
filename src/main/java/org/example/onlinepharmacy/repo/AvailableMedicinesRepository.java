package org.example.onlinepharmacy.repo;

import java.util.List;
import java.util.Optional;
import org.example.onlinepharmacy.domain.AvailableMedicines;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AvailableMedicinesRepository extends JpaRepository<AvailableMedicines, Long> {
    @Query(nativeQuery = true, value = """
            select public.district.name,
                   public.available_medicines.id,
                   public.available_medicines.count,
                   public.available_medicines.pharmacy_id,
                   public.available_medicines.medicine_id,
                   public.available_medicines.price
            from district
                     inner join public.pharmacy p on district.id = p.district_id
                     inner join public.available_medicines on p.id = available_medicines.pharmacy_id
                     inner join public.medicine on medicine.id = available_medicines.medicine_id
            where district.name =:districtName
            """)
    List<AvailableMedicines> findAvailableMedicinesByDistrictName(@Param("districtName") String name);

    @Query(nativeQuery = true, value = "select public.available_medicines.price from" +
            " public.available_medicines where available_medicines.medicine_id=:mId and available_medicines.pharmacy_id=:pId")
    Double findPriceByMedicineId(@Param("mId") Long medicineId, @Param("pId") Long pharmacyId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update available_medicines set count=count-1 where medicine_id=:mId and pharmacy_id=:pId")
    void updateCountByMedicineIdAndPharmacyID(@Param("mId") Long medicineId, @Param("pId") Long pharmacyId);

    @Query(nativeQuery = true, value = """
            select public.available_medicines.id,
                   public.available_medicines.count,
                   public.available_medicines.pharmacy_id,
                   public.available_medicines.medicine_id,
                   public.available_medicines.price
            from district
                     inner join public.pharmacy p on district.id = p.district_id
                     inner join public.available_medicines on p.id = available_medicines.pharmacy_id
                     inner join
                 public.medicine on medicine.id = available_medicines.medicine_id
            where district.name = :dName and medicine_id= :mId
            """)
    Optional<AvailableMedicines> getAvailableMedicinesForSearching(@Param("dName") String districtName, @Param("mId") Long medicineId);

    Optional<AvailableMedicines> getAvailableMedicinesByMedicineId(Long medicineId);
}