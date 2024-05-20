package org.example.onlinepharmacy.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.advice.exception.MedicineNotFoundException;
import org.example.onlinepharmacy.domain.Medicine;
import org.example.onlinepharmacy.dto.MedicineDto;
import org.example.onlinepharmacy.updatedto.MedicineUpdateDto;
import org.example.onlinepharmacy.repo.MedicineRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    private Medicine dtoToEntity(MedicineDto medicineDto) {
        return Medicine.builder()
                .name(medicineDto.getMedicineName())
                .dateOfManufacture(medicineDto.getDateOfManufacture())
                .dateOfExpiry(medicineDto.getDateOfExpiry())
                .farm(medicineDto.getFarm())
                .build();
    }

    public Medicine save(MedicineDto medicineDto) {
        return medicineRepository.save(dtoToEntity(medicineDto));
    }

    public Medicine medicineUpdate(MedicineUpdateDto updateDto) {

        Medicine medicine = medicineRepository.findById(updateDto.getId()).orElse(null);
        if (medicine == null) {
            throw new MedicineNotFoundException(updateDto.getId());
        }
        if (updateDto.getName() != null) {
            medicine.setName(updateDto.getName());
        }
        if (updateDto.getFarm() != null) {
            medicine.setFarm(updateDto.getFarm());
        }
        if (updateDto.getDateOfManufacture() != null) {
            medicine.setDateOfManufacture(updateDto.getDateOfManufacture());
        }
        if (updateDto.getDateOfExpiry() != null) {
            medicine.setDateOfExpiry(updateDto.getDateOfExpiry());
        }

        return medicineRepository.save(medicine);
    }
}
