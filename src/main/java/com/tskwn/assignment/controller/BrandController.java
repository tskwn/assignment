package com.tskwn.assignment.controller;

import com.tskwn.assignment.dto.BrandDto;
import com.tskwn.assignment.dto.ResponseDto;
import com.tskwn.assignment.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 브랜드 관련 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
public class BrandController {
    private final BrandService brandService;

    /**
     * 브랜드 정보 생성.
     *
     * @param brandDto 브랜드 정보
     * @return 생성된 브랜드 정보를 포함하는 ResponseDto Response
     */
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> create(@RequestBody final BrandDto brandDto) {
        return ResponseEntity.ok().body(brandService.create(brandDto));
    }

    /**
     * 브랜드 정보 생성.
     *
     * @param brandId  브랜드 아이디
     * @param brandDto 브랜드 정보
     * @return 업데이트된 브랜드 정보를 포함하는 ResponseDto Response
     */
    @PutMapping(value = "/{brandId}/update")
    public ResponseEntity<ResponseDto> update(@PathVariable("brandId") final Long brandId, @RequestBody final BrandDto brandDto) {
        return ResponseEntity.ok().body(brandService.update(brandId, brandDto));
    }

    /**
     * 브랜드 정보 삭제.
     *
     * @param brandId 브랜드 아이디
     * @return ResponseDto Response
     */
    @DeleteMapping(value = "/{brandId}/delete")
    public ResponseEntity<ResponseDto> delete(@PathVariable("brandId") final Long brandId) {
        brandService.delete(brandId);
        return ResponseEntity.ok().build();
    }
}
