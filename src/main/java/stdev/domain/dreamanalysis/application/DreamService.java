package stdev.domain.dreamanalysis.application;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DreamService {

    void dreamPost(String comment, MultipartFile image) throws IOException;
}
