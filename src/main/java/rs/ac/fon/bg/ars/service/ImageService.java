package rs.ac.fon.bg.ars.service;

import org.springframework.data.domain.Pageable;
import rs.ac.fon.bg.ars.domain.AmenityDomain;
import rs.ac.fon.bg.ars.domain.ImageDomain;

import java.util.List;

public interface ImageService {
    Long save(ImageDomain imageDomain);

    List<ImageDomain> getAll(Long accommodationId, Pageable pageable);

    void deleteById(Long id);


    ImageDomain findById(Long id);
}
