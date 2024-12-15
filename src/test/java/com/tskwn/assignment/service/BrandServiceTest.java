package com.tskwn.assignment.service;

import com.tskwn.assignment.dto.BrandDto;
import com.tskwn.assignment.entity.Brand;
import com.tskwn.assignment.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;
    private BrandService brandService;

    @BeforeEach
    void init() {
        brandService = new BrandService(brandRepository);
    }

    @Test
    void test_create() {
        // given
        var brandDto = mock(BrandDto.class);
        var createdDto = mock(BrandDto.class);
        var brand = mock(Brand.class);

        // when
        when(brandRepository.save(brandDto.toEntity())).thenReturn(brand);
        when(brand.toDto()).thenReturn(createdDto);
        var result = brandService.create(brandDto);

        // then
        assertEquals(result, createdDto);
    }

    @Test
    void test_update_not_exist() {
        // given
        var brandDto = mock(BrandDto.class);
        var brandId = 1L;

        // when
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> brandService.update(brandId, brandDto));
    }

    @Test
    void test_update() {
        // given
        var brandDto = mock(BrandDto.class);
        var brand = mock(Brand.class);
        var updatedBrand = mock(Brand.class);
        var updatedDto = mock(BrandDto.class);
        var brandId = 1L;

        // when
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(brand.update(brandDto)).thenReturn(updatedBrand);
        when(updatedBrand.toDto()).thenReturn(updatedDto);
        var result = brandService.update(brandId, brandDto);

        // then
        assertEquals(result, updatedDto);
    }

    @Test
    void test_delete_not_exist() {
        // given
        var brandId = 1L;

        // when
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> brandService.delete(brandId));
    }

    @Test
    void test_delete() {
        // given
        var brand = mock(Brand.class);
        var brandId = 1L;

        // when
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        brandService.delete(brandId);

        // then
        verify(brandRepository, times(1)).delete(brand);
    }

}
