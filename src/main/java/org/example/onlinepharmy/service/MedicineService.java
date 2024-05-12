package org.example.onlinepharmy.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.advice.exception.MedicineNotFoundException;
import org.example.onlinepharmy.domain.Medicine;
import org.example.onlinepharmy.dto.MedicineDto;
import org.example.onlinepharmy.updateDto.MedicineUpdateDto;
import org.example.onlinepharmy.repo.MedicineRepository;
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
