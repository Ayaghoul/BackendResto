package tn.pfe.spring.Service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;

public interface FilesStorageService {
    void init();

    void save(MultipartFile file, String fileName);

    Resource load(String filename);

//    void deleteAll();

    Stream<Path> loadAll();
}
