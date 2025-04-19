package stdev.domain.dreamanalysis.application;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DreamService {

    String dreamPost(MultipartFile image) throws IOException;
}
