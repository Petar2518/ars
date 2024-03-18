package rs.ac.fon.bg.ars.adapters.controllerServiceAdapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.dto.AmenityDto;
import rs.ac.fon.bg.ars.dto.ImageDto;
import rs.ac.fon.bg.ars.mapper.AmenityMapper;
import rs.ac.fon.bg.ars.mapper.ImageMapper;
import rs.ac.fon.bg.ars.service.AmenityService;
import rs.ac.fon.bg.ars.service.ImageService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageDtoDomainAdapter {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public Long save(ImageDto imageDto){
        return imageService.save(imageMapper.dtoToDomain(imageDto));
    }

    public List<ImageDto> getAll(Long accommodationId,Pageable pageable){
        return imageService.getAll(accommodationId,pageable)
                .stream()
                .map(imageMapper::domainToDto)
                .toList();
    }
    public ImageDto findById(Long id){
        return imageMapper.domainToDto(imageService.findById(id));
    }


    public void deleteById(Long id){
        imageService.deleteById(id);
    }
}
