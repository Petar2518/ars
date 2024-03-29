package rs.ac.fon.bg.ars.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.fon.bg.ars.adapters.serviceRepositoryAdapters.ImageDomainEntityAdapter;
import rs.ac.fon.bg.ars.domain.ImageDomain;
import rs.ac.fon.bg.ars.exception.specific.ImageNotFoundException;
import rs.ac.fon.bg.ars.service.ImageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageDomainEntityAdapter serviceRepositoryAdapter;

    @Override
    public Long save(ImageDomain imageDomain) {
        return serviceRepositoryAdapter.save(imageDomain);
    }

    @Override
    public List<ImageDomain> getAll(Long accommodationId, Pageable pageable) {
        return serviceRepositoryAdapter.findAll(accommodationId,pageable);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepositoryAdapter.deleteById(id);
    }

    @Override
    public ImageDomain findById(Long id) {
        return serviceRepositoryAdapter.findById(id).orElseThrow(()-> new ImageNotFoundException(id));
    }
}
