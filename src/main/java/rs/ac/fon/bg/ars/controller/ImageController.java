package rs.ac.fon.bg.ars.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.AmenityDtoDomainAdapter;
import rs.ac.fon.bg.ars.adapters.controllerServiceAdapters.ImageDtoDomainAdapter;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.ImageDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageDtoDomainAdapter imageAdapter;

    @GetMapping("accommodations/{accommodationId}/images")
    public List<ImageDto> getAllByPage(
            @PathVariable Long accommodationId,
            @PageableDefault(sort="id",page=0,size=10,direction = Sort.Direction.ASC) Pageable pageable){
        return imageAdapter.getAll(accommodationId,pageable);
    }

    @GetMapping("images/{id}")
    public ImageDto findById(@PathVariable Long id){
        return imageAdapter.findById(id);
    }

    @PostMapping("images")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@Valid @RequestBody ImageDto imageDto){
        return imageAdapter.save(imageDto);
    }
    @DeleteMapping("images/{id}")
    public void delete(@PathVariable Long id){
        imageAdapter.deleteById(id);
    }

}