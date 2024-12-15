package com.tskwn.assignment.service;

import com.tskwn.assignment.dto.BrandDto;
import com.tskwn.assignment.entity.Brand;
import com.tskwn.assignment.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 브랜드 정보 서비스.
 */
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    /**
     * 브랜드 정보 생성.
     *
     * @param brandDto 브랜드 정보
     * @return 생성된 브랜드 정보 BrandDto
     */
    public BrandDto create(BrandDto brandDto) {
        return brandRepository.save(brandDto.toEntity()).toDto();
    }

    /**
     * 브랜드 정보 업데이트.
     *
     * @param brandId  브랜드 아이디
     * @param brandDto 브랜드 정보
     * @return 업데이트 된 브랜드 정보 BrandDto
     */
    public BrandDto update(Long brandId, BrandDto brandDto) {
        return brandRepository.findById(brandId).orElseThrow(() -> new IllegalArgumentException("Brand not found")).update(brandDto).toDto();
    }

    /**
     * 브랜드 정보 삭제.
     *
     * @param brandId 브랜드 아이디
     */
    public void delete(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        brandRepository.delete(brand);
    }

}
